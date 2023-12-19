package mbogusz.spring.skyhigh.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.entity.dto.PassengerGetDTO;
import mbogusz.spring.skyhigh.entity.dto.PassengerLoginDTO;
import mbogusz.spring.skyhigh.entity.dto.PassengerPostDTO;
import mbogusz.spring.skyhigh.entity.dto.PassengerRegistrationDTO;
import mbogusz.spring.skyhigh.entity.validation.SimpleValidationMessage;
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
@Tag(name = "Passengers")
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

    @Operation(summary = "Register and create an account", description = "Creates an account")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "User with the email already exists", content = @Content(schema = @Schema(implementation = SimpleValidationMessage.class))),
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody @Parameter(name = "filledForm", description = "Registration form") PassengerRegistrationDTO filledForm) {
        try {
            Passenger registered = passengerRegistrationService.register(filledForm);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(new SimpleValidationMessage(e.getMessage()), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Registered successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Login to the account", description = "Logs in to account and updates session data")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Wrong email or password", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "200", description = "Logged in successfully", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody @Parameter(name = "filledForm", description = "Login form") PassengerLoginDTO filledForm) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(filledForm.getEmail(), filledForm.getPassword()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new SimpleValidationMessage("Wrong email or password"), HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(new SimpleValidationMessage("Login successful"), HttpStatus.OK);
    }

    @Operation(summary = "Get current user data", description = "Returns current user data if logged in")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data retrieved successfully", content = @Content(schema = @Schema(implementation = PassengerGetDTO.class))),
            @ApiResponse(responseCode = "401", description = "Not logged in")
    })
    @GetMapping("/currentUser")
    public ResponseEntity<PassengerGetDTO> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails instanceof Passenger) {
            PassengerGetDTO dto = getMapper.toDto((Passenger)userDetails);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Log out", description = "Logs out of website")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "Not logged in", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "200", description = "Logged out successfully", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null) return new ResponseEntity<>("Not logged in", HttpStatus.UNAUTHORIZED);
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }
}
