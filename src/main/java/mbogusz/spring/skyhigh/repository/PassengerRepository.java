package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}