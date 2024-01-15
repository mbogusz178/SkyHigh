package mbogusz.spring.skyhigh.testUtils.mappers;

import mbogusz.spring.skyhigh.entity.SeatConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SeatConfigurationTestProvider implements Function<String, SeatConfiguration> {

    private static final Map<String, SeatConfiguration> SEAT_CONFIGURATION_SET = new HashMap<>();

    static {
        SeatConfiguration seatConfiguration = new SeatConfiguration();
        seatConfiguration.setId(0L);
        seatConfiguration.setRowConfig("3-3");
        seatConfiguration.setNumRows(2);

        SEAT_CONFIGURATION_SET.put("sample_seat_configuration", seatConfiguration);
    }

    @Override
    public SeatConfiguration apply(String value) {
        return SEAT_CONFIGURATION_SET.get(value);
    }
}
