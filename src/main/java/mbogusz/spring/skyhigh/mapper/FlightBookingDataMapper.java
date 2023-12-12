package mbogusz.spring.skyhigh.mapper;

import mbogusz.spring.skyhigh.entity.*;
import mbogusz.spring.skyhigh.entity.dto.FlightBookingDataDTO;
import mbogusz.spring.skyhigh.entity.dto.SeatBookingDataDTO;
import mbogusz.spring.skyhigh.entity.dto.SeatConfigurationBookingDataDTO;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FlightRepository.class})
public abstract class FlightBookingDataMapper extends EntityMapper<Long, Flight, FlightBookingDataDTO> {

    private FlightRepository repository;

    @Override
    protected JpaRepository<Flight, Long> getRepository() {
        return repository;
    }

    @Override
    protected Supplier<Flight> createInstance() {
        return Flight::new;
    }

    @Autowired
    public void setRepository(FlightRepository repository) {
        this.repository = repository;
    }

    @Mappings({
            @Mapping(source = "plane", target = "planeModel", qualifiedByName = "planeToModel"),
            @Mapping(source = "plane", target = "seatConfig", qualifiedByName = "planeToSeatConfig"),
            @Mapping(source = "flightSeats", target = "seats", qualifiedByName = "seatsToBooking")
    })
    @Override
    public abstract FlightBookingDataDTO toDto(Flight entity);

    @Named("planeToModel")
    protected String planeToModel(Plane plane) {
        return plane.getModel().getFullName();
    }

    @Named("planeToSeatConfig")
    protected SeatConfigurationBookingDataDTO planeToSeatConfig(Plane plane) {
        SeatConfiguration seatConfiguration = plane.getSeatConfiguration();
        return new SeatConfigurationBookingDataDTO(seatConfiguration.getRowConfig(), seatConfiguration.getNumRows(), seatConfiguration.getRowSize());
    }

    @Named("seatsToBooking")
    protected List<SeatBookingDataDTO> seatsToBooking(Set<Seat> seats) {
        return seats.stream().map(seat -> {
            Set<SeatClassRange> seatClassRanges = seat.getFlight().getPlane().getSeatConfiguration().getSeatClassRanges();
            String seatClass = "Y";
            String seatClassName = "ekonomiczna";
            for (SeatClassRange seatClassRange : seatClassRanges) {
                if (seat.getRowNumber() >= seatClassRange.getFromRow() && seat.getRowNumber() <= seatClassRange.getToRow()) {
                    seatClass = seatClassRange.getSeatClass().getCode();
                    seatClassName = seatClassRange.getSeatClass().getName();
                }
            }
            return new SeatBookingDataDTO(seat.getRowNumber(), seat.getSeatLetter(), seatClass, seatClassName, seat.getStatus().toString());
        }).sorted(Comparator.comparing(SeatBookingDataDTO::getRowNumber).thenComparing(SeatBookingDataDTO::getSeatLetter)).collect(Collectors.toList());
    }
}
