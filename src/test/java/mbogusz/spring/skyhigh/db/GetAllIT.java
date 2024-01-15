package mbogusz.spring.skyhigh.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.testUtils.annotations.TestEntityValue;
import mbogusz.spring.skyhigh.testUtils.annotations.TestEntityValues;
import mbogusz.spring.skyhigh.testUtils.mappers.StringAsIs;
import mbogusz.spring.skyhigh.util.Identifiable;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Testcontainers
public abstract class GetAllIT<ID, E extends Identifiable<ID>, DTO extends Identifiable<ID>> {
    protected MockMvc mvc;
    protected abstract JpaRepository<E, ID> getRepository();
    protected abstract EntityMapper<ID, E, DTO> getMapper();
    protected abstract String getControllerURL();

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16").withReuse(true);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        registry.add("spring.liquibase.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.liquibase.user", postgreSQLContainer::getUsername);
        registry.add("spring.liquibase.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    public void setMvc(MockMvc mvc) {
        this.mvc = mvc;
    }

    protected abstract Class<E> getType();

    protected abstract Class<DTO> getDtoType();

    private static Function<String, ?> getMapper(TestEntityValue valueInfo) {
        try {
            Class<? extends Function<String, ?>> mapperClass = valueInfo.valueMapper();
            return mapperClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StringAsIs();
        }
    }

    private static Object mapFieldType(TestEntityValue valueInfo) {
        return getMapper(valueInfo).apply(valueInfo.fieldValue());
    }

    private static <T> void setEntityField(T entity, Class<T> type, TestEntityValue valueInfo) {
        try {
            Field field = type.getDeclaredField(valueInfo.fieldName());
            Class<?> fieldType = field.getType();
            Object value;
            if(valueInfo.autoLookup() && (fieldType.getAnnotation(TestEntityValues.class) != null)) {
                value = createTestObject(fieldType);
            } else {
                value = mapFieldType(valueInfo);
            }
            field.setAccessible(true);
            field.set(entity, value);
        } catch (NoSuchFieldException e) {
            System.out.println("Field " + valueInfo.fieldName() + " not found in class " + type.getTypeName() + ", ignoring");
        } catch (IllegalAccessException ignored) {
            // setAccessible(true) was called
        } catch (Exception e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        }
    }

    private static <T> T createTestObject(Class<T> type) throws Exception {
        try {
            T newObject = type.getDeclaredConstructor().newInstance();
            Optional.of(type.getAnnotation(TestEntityValues.class)).ifPresent(values -> {
                for(TestEntityValue valueInfo: values.value()) {
                    setEntityField(newObject, type, valueInfo);
                }
            });
            return newObject;
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException("Class " + type.getTypeName() + " must have a no-args constructor");
        }
    }

    protected E createTestEntity() throws Exception {
        Class<E> type = getType();
        E object = createTestObject(type);
        return getRepository().save(object);
    }

    @Test
    public void getEntities_noEntities_status200() throws Exception {
        mvc.perform(get("/api/planes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getEntities_oneEntity_status200() throws Exception {
        createTestEntity();

        mvc.perform(get(getControllerURL())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getEntities_noEntities_emptyList() throws Exception {
        mvc.perform(get(getControllerURL())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(Matchers.empty()));
    }

    @Test
    public void getEntities_oneEntity_oneElement() throws Exception {
        createTestEntity();

        mvc.perform(get(getControllerURL())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void getEntities_oneEntity_theAddedEntity() throws Exception {
        E entity = createTestEntity();
        DTO dto = getMapper().toDto(entity);

        mvc.perform(get(getControllerURL())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    JSONArray array = new JSONArray(json);
                    String firstEntity = array.getJSONObject(0).toString();
                    DTO responseDto = new ObjectMapper().readValue(firstEntity, getDtoType());
                    Assertions.assertEquals(dto, responseDto);
                });
    }

    @AfterEach
    public void cleanup() {
        getRepository().deleteAll();
    }
}
