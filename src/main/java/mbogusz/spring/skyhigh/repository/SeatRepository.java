package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT DISTINCT s FROM Seat s JOIN Flight f ON s.flight = f WHERE f.id = :flightId AND s.rowNumber = :rowNumber AND s.seatLetter = :seatLetter")
    Seat getSeatFromPosition(@Param("flightId") Long flightId, @Param("rowNumber") Integer rowNumber, @Param("seatLetter") String seatLetter);
}
