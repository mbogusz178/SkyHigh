package mbogusz.spring.skyhigh.testUtils.mappers;

import java.util.function.Function;

public class ParseDoubleTestProvider implements Function<String, Double> {
    @Override
    public Double apply(String s) {
        return Double.parseDouble(s);
    }
}
