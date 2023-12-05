package mbogusz.spring.skyhigh.service.auth;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import mbogusz.spring.skyhigh.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PassengerDetailsService implements UserDetailsService {

    private final PassengerRepository repository;

    @Autowired
    public PassengerDetailsService(PassengerRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Passenger passenger = repository.getByEmail(username);
        if(passenger == null) {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o adresie email " + username);
        }

        return passenger;
    }
}
