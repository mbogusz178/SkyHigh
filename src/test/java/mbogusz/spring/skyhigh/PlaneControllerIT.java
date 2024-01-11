package mbogusz.spring.skyhigh;

import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.PlaneModel;
import mbogusz.spring.skyhigh.entity.SeatConfiguration;
import mbogusz.spring.skyhigh.repository.PlaneModelRepository;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import mbogusz.spring.skyhigh.repository.SeatConfigurationRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SkyHighApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Testcontainers
public class PlaneControllerIT {
    private MockMvc mvc;
    private PlaneRepository planeRepository;
    private SeatConfigurationRepository seatConfigurationRepository;
    private PlaneModelRepository planeModelRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

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

    @Autowired
    public void setPlaneRepository(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Autowired
    public void setSeatConfigurationRepository(SeatConfigurationRepository seatConfigurationRepository) {
        this.seatConfigurationRepository = seatConfigurationRepository;
    }

    @Autowired
    public void setPlaneModelRepository(PlaneModelRepository planeModelRepository) {
        this.planeModelRepository = planeModelRepository;
    }

    private SeatConfiguration getTestSeatConfig() {
        SeatConfiguration seatConfiguration = new SeatConfiguration();
        seatConfiguration.setRowConfig("3-3");
        seatConfiguration.setNumRows(2);
        return seatConfiguration;
    }

    private PlaneModel getTestPlaneModel() {
        PlaneModel airbus = new PlaneModel();
        airbus.setManufacturer("Airbus");
        airbus.setFamily("A320");
        airbus.setModelNumber(200);
        airbus.setVersion(1);
        return airbus;
    }

    private void createTestPlane() {
        PlaneModel airbus = getTestPlaneModel();
        SeatConfiguration seatConfig = getTestSeatConfig();
        Plane plane = new Plane("SKY-AA", airbus, seatConfig);
        seatConfigurationRepository.save(seatConfig);
        planeModelRepository.save(airbus);
        planeRepository.save(plane);
    }

    @Test
    public void getPlanes_noPlanes_status200() throws Exception {
        mvc.perform(get("/api/planes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPlanes_onePlane_status200() throws Exception {
        createTestPlane();

        mvc.perform(get("/api/planes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPlanes_noPlanes_emptyList() throws Exception {
        mvc.perform(get("/api/planes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(Matchers.empty()));
    }

    @AfterEach
    public void cleanup() {
        seatConfigurationRepository.deleteAll();
        planeModelRepository.deleteAll();
        planeRepository.deleteAll();
    }
}
