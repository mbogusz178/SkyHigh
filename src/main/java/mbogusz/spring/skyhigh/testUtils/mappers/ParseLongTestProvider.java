package mbogusz.spring.skyhigh.testUtils.mappers;

import java.util.function.Function;

public class ParseLongTestProvider implements Function<String, Long> {
    @Override
    public Long apply(String s) {
        return Long.parseLong(s);
    }
}
