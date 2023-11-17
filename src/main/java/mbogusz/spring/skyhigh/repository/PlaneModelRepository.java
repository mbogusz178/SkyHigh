package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.PlaneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneModelRepository extends JpaRepository<PlaneModel, Long> {
}
