package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.FlightClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightClassRepository extends JpaRepository<FlightClass, Long> {
}
