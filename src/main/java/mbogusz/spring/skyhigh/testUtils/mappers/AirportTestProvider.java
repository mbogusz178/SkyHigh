package mbogusz.spring.skyhigh.testUtils.mappers;

import mbogusz.spring.skyhigh.entity.Airport;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AirportTestProvider implements Function<String, Airport> {

    private static final Map<String, Airport> AIRPORT_SET = new HashMap<>();

    static {
        Airport airport = new Airport();
        airport.setId("WAW");
        airport.setAirportName("Lotnisko Chopina w Warszawie");
        airport.setCity("Warszawa");
        airport.setCountry("Polska");

        AIRPORT_SET.put("sample_airport_source", airport);

        Airport airport2 = new Airport();
        airport2.setId("FRA");
        airport2.setAirportName("Frankfurt Airport");
        airport2.setCity("Frankfurt");
        airport2.setCountry("Niemcy");

        AIRPORT_SET.put("sample_airport_dest", airport2);
    }

    @Override
    public Airport apply(String value) {
        return AIRPORT_SET.get(value);
    }
}
