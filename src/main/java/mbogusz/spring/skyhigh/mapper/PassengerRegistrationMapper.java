package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerRegistrationDTO;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Supplier;

@Mapper(componentModel = "spring")
public abstract class PassengerRegistrationMapper {
    public abstract Passenger toEntity(PassengerRegistrationDTO registrationDTO);
}
