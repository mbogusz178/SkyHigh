package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    List<Airport> findByCity(String city);

    @Query("SELECT DISTINCT a.city FROM Airport a")
    List<String> findAllCities();
}
