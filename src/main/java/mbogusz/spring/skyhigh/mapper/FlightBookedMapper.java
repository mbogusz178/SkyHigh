package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.AirportDTO;
import mbogusz.spring.skyhigh.entity.dto.FlightBookedDTO;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AirportMapper.class, FlightRepository.class})
public abstract class FlightBookedMapper {
    private AirportMapper airportMapper;
    private FlightRepository flightRepository;

    @Autowired
    public void setAirportMapper(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }

    @Autowired
    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "airportToDto"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "airportToDto")
    })
    public abstract FlightBookedDTO toDto(Flight entity, @Context Passenger passenger);

    @Named("airportToDto")
    protected AirportDTO airportToDto(Airport airport) {
        return airportMapper.toDto(airport);
    }

    @AfterMapping
    protected void setConfirmed(Flight entity, @MappingTarget FlightBookedDTO dto, @Context Passenger passenger) {
        dto.setConfirmed(flightRepository.isConfirmed(entity.getId(), passenger.getId()));
        dto.setCancelled(flightRepository.isCancelled(entity.getId(), passenger.getId()));
    }
}
