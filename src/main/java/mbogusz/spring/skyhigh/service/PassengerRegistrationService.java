package mbogusz.spring.skyhigh.service;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerRegistrationDTO;
import mbogusz.spring.skyhigh.mapper.PassengerRegistrationMapper;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

@Service
@Transactional
public class PassengerRegistrationService {
    private final PassengerRepository passengerRepository;
    private final PassengerRegistrationMapper registrationMapper;
    private final PasswordEncoder encoder;

    @Autowired
    public PassengerRegistrationService(PassengerRepository passengerRepository, PassengerRegistrationMapper registrationMapper, PasswordEncoder encoder) {
        this.passengerRepository = passengerRepository;
        this.registrationMapper = registrationMapper;
        this.encoder = encoder;
    }

    public Passenger register(PassengerRegistrationDTO registrationDTO) throws EntityExistsException {
        if(passengerRepository.getByEmail(registrationDTO.getEmail()) != null) throw new EntityExistsException("Adres email " + registrationDTO.getEmail() + " jest zajęty");
        Passenger passenger = registrationMapper.toEntity(registrationDTO);
        passenger.setPassword(encoder.encode(passenger.getPassword()));
        return passengerRepository.save(passenger);
    }
}
