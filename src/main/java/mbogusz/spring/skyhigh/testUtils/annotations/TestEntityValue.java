package mbogusz.spring.skyhigh.testUtils.annotations;

import mbogusz.spring.skyhigh.testUtils.mappers.StringAsIs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestEntityValue {
    String fieldName();
    String fieldValue() default "";
    Class<? extends Function<String, ?>> valueMapper() default StringAsIs.class;
    boolean autoLookup() default false;
}
