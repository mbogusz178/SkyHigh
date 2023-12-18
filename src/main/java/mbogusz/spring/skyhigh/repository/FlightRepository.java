package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM search_flights(:source, :destination, :departureAfter, :departureBefore, :arrivalAfter, :arrivalBefore)")
    List<Flight> searchFlights(@Param("source") String source,
                               @Param("destination") String destination,
                               @Param("departureAfter") Timestamp departureAfter,
                               @Param("departureBefore")Timestamp departureBefore,
                               @Param("arrivalAfter") Timestamp arrivalAfter,
                               @Param("arrivalBefore") Timestamp arrivalBefore);
    @Query(nativeQuery = true, value = "SELECT * FROM search_flights(:source, :destination, :departureAfter, :departureBefore, :arrivalAfter, :arrivalBefore)")
    Page<Flight> searchFlightsPaginated(@Param("source") String source,
                                        @Param("destination") String destination,
                                        @Param("departureAfter") Timestamp departureAfter,
                                        @Param("departureBefore")Timestamp departureBefore,
                                        @Param("arrivalAfter") Timestamp arrivalAfter,
                                        @Param("arrivalBefore") Timestamp arrivalBefore,
                                        Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT DISTINCT f.id FROM flight f JOIN seat s JOIN ticket t JOIN passenger p ON t.passenger = p.user_id ON s.id = t.seat ON f.id = s.flight WHERE p.user_id = :userId AND t.id = ANY (SELECT t.id FROM ticket t WHERE t.status != 'CANCELLED')")
    List<Long> getFlightIdsOfUser(Long userId);

    @Query(nativeQuery = true, value = "SELECT CAST(CASE WHEN EXISTS(SELECT t.id FROM ticket t JOIN passenger p on p.user_id = t.passenger JOIN seat s ON s.id = t.seat JOIN flight f ON f.id = s.flight WHERE t.status = 'CONFIRMED' AND f.id = :flightId AND p.user_id = :passengerId) AND NOT EXISTS(SELECT t.id FROM ticket t JOIN passenger p on p.user_id = t.passenger JOIN seat s ON s.id = t.seat JOIN flight f ON f.id = s.flight WHERE t.status = 'PENDING' AND f.id = :flightId AND p.user_id = :passengerId) THEN 0 ELSE 1 END AS bit)")
    boolean isConfirmed(Long flightId, Long passengerId);

    @Query(nativeQuery = true, value = "SELECT CAST(CASE WHEN EXISTS(SELECT t.id FROM ticket t JOIN passenger p on p.user_id = t.passenger JOIN seat s ON s.id = t.seat JOIN flight f ON f.id = s.flight WHERE t.status != 'CANCELLED' AND f.id = :flightId AND p.user_id = :passengerId) THEN 0 ELSE 1 END AS bit)")
    boolean isCancelled(Long flightId, Long passengerId);
}
