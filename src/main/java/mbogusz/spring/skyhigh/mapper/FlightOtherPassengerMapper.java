package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.Ticket;
import mbogusz.spring.skyhigh.entity.dto.FlightOtherPassengerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class FlightOtherPassengerMapper {
    @Mappings({
            @Mapping(source = "seat.rowNumber", target = "rowNumber"),
            @Mapping(source = "seat.seatLetter", target = "seatLetter")
    })
    public abstract FlightOtherPassengerDTO toDto(Ticket ticket);
}
