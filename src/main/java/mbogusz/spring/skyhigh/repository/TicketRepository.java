package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(nativeQuery = true, value = "SELECT t.id, t.passenger, t.seat, t.date_booked, t.status, t.flight_class, t.first_name, t.last_name, t.age_group, t.notified FROM Ticket t JOIN Seat s JOIN Flight f ON s.flight = f.id ON t.seat = s.id WHERE (f.departure_date - interval '30 minutes') < now() AND t.notified = false AND t.status = 'PENDING'")
    List<Ticket> getTicketsForNotification();

    @Query(nativeQuery = true, value = "SELECT t.id, t.passenger, t.seat, t.date_booked, t.status, t.flight_class, t.first_name, t.last_name, t.age_group, t.notified FROM Ticket t JOIN Seat s JOIN Flight f ON s.flight = f.id ON t.seat = s.id JOIN Passenger p ON t.passenger = p.user_id WHERE p.user_id = :userId AND t.status != 'CANCELLED' AND f.id = :flightId")
    List<Ticket> getTicketsOfUserForFlight(@Param("flightId") Long flightId, @Param("userId") Long userId);
}
