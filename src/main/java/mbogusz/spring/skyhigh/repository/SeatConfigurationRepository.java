package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.SeatConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatConfigurationRepository extends JpaRepository<SeatConfiguration, Long> {
}
