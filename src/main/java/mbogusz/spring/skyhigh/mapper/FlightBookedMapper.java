package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.AirportDTO;
import mbogusz.spring.skyhigh.entity.dto.FlightBookedDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AirportMapper.class})
public abstract class FlightBookedMapper {
    private AirportMapper airportMapper;

    @Autowired
    public void setAirportMapper(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "airportToDto"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "airportToDto")
    })
    public abstract FlightBookedDTO toDto(Flight entity);

    @Named("airportToDto")
    protected AirportDTO airportToDto(Airport airport) {
        return airportMapper.toDto(airport);
    }
}
