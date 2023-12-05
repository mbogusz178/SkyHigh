package mbogusz.spring.skyhigh.repository;

import mbogusz.spring.skyhigh.entity.auth.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM passenger p JOIN app_user u ON p.user_id = u.id WHERE u.email = :email")
    Passenger getByEmail(@Param("email") String email);
}
