package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Seat;
import mbogusz.spring.skyhigh.entity.auth.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT DISTINCT s FROM Seat s JOIN Flight f ON s.flight = f WHERE f.id = :flightId AND s.rowNumber = :rowNumber AND s.seatLetter = :seatLetter")
    Seat getSeatFromPosition(@Param("flightId") Long flightId, @Param("rowNumber") Integer rowNumber, @Param("seatLetter") String seatLetter);

    @Query(nativeQuery = true, value = "SELECT p.user_id, p.first_name, p.last_name, p.city, p.country FROM passenger p JOIN ticket t JOIN seat s JOIN Flight f ON f.id = s.flight ON t.seat = s.id ON t.passenger = p.user_id WHERE s.seat_letter = :seatLetter AND s.row_number = :rowNumber AND f.id = :flightId")
    Passenger getPassengerAtSeat(@Param("flightId") Long flightId, @Param("rowNumber") Integer rowNumber, @Param("seatLetter") String seatLetter);
}
