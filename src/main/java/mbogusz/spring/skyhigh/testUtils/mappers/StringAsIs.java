package mbogusz.spring.skyhigh.testUtils.mappers;

import java.util.function.Function;

public class StringAsIs implements Function<String, String> {
    @Override
    public String apply(String s) {
        return s;
    }
}
