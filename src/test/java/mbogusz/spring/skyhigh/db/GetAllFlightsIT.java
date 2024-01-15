package mbogusz.spring.skyhigh.db;

import mbogusz.spring.skyhigh.SkyHighApplication;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.FlightDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.FlightMapper;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SkyHighApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class GetAllFlightsIT extends GetAllIT<Long, Flight, FlightDTO> {

    private FlightRepository flightRepository;
    private FlightMapper flightMapper;

    @Override
    protected JpaRepository<Flight, Long> getRepository() {
        return flightRepository;
    }

    @Override
    protected EntityMapper<Long, Flight, FlightDTO> getMapper() {
        return flightMapper;
    }

    @Override
    protected String getControllerURL() {
        return "/api/flights";
    }

    @Override
    protected Class<Flight> getType() {
        return Flight.class;
    }

    @Override
    protected Class<FlightDTO> getDtoType() {
        return FlightDTO.class;
    }

    @Autowired
    public void setFlightMapper(FlightMapper flightMapper) {
        this.flightMapper = flightMapper;
    }

    @Autowired
    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
}
