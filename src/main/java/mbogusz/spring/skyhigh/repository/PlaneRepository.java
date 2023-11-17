package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, String> {
}
