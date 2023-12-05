package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerGetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PassengerGetMapper {
    public abstract PassengerGetDTO toDto(Passenger passenger);
}
