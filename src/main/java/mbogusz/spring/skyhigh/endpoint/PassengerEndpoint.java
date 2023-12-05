package mbogusz.spring.skyhigh.endpoint;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerGetDTO;
import mbogusz.spring.skyhigh.entity.dto.PassengerLoginDTO;
import mbogusz.spring.skyhigh.entity.dto.PassengerPostDTO;
import mbogusz.spring.skyhigh.entity.dto.PassengerRegistrationDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.PassengerGetMapper;
import mbogusz.spring.skyhigh.mapper.PassengerMapper;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import mbogusz.spring.skyhigh.service.PassengerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

@RestController
@RequestMapping("/passengers")
public class PassengerEndpoint extends BaseEndpoint<Long, Passenger, PassengerPostDTO> {

    private final PassengerRepository repository;
    private final PassengerMapper mapper;
    private final PassengerRegistrationService passengerRegistrationService;
    private final PassengerGetMapper getMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public EntityMapper<Long, Passenger, PassengerPostDTO> getMapper() {
        return mapper;
    }

    @Override
    public JpaRepository<Passenger, Long> getRepository() {
        return repository;
    }

    @Autowired
    public PassengerEndpoint(PassengerRepository repository, PassengerMapper mapper, PassengerRegistrationService passengerRegistrationService, AuthenticationManager authenticationManager, PassengerGetMapper getMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.passengerRegistrationService = passengerRegistrationService;
        this.authenticationManager = authenticationManager;
        this.getMapper = getMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody PassengerRegistrationDTO filledForm) {
        try {
            Passenger registered = passengerRegistrationService.register(filledForm);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Zarejestrowano pomyślnie", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody PassengerLoginDTO filledForm) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(filledForm.getEmail(), filledForm.getPassword()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Nieprawidłowe dane logowania", HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Zalogowano pomyślnie", HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<PassengerGetDTO> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails instanceof Passenger) {
            PassengerGetDTO dto = getMapper.toDto((Passenger)userDetails);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null) return new ResponseEntity<>("Nie jesteś zalogowany", HttpStatus.UNAUTHORIZED);
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Wylogowano pomyślnie", HttpStatus.OK);
    }
}
