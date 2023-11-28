package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
