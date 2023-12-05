package mbogusz.spring.skyhigh.mapper;


import mbogusz.spring.skyhigh.entity.Airport;
import mbogusz.spring.skyhigh.entity.Flight;
import mbogusz.spring.skyhigh.entity.dto.AirportDTO;
import mbogusz.spring.skyhigh.entity.dto.FlightSearchResponseDTO;
import mbogusz.spring.skyhigh.mapper.context.PassengerComposition;
import mbogusz.spring.skyhigh.repository.AirportRepository;
import mbogusz.spring.skyhigh.repository.FlightRepository;
import mbogusz.spring.skyhigh.service.TotalFlightPriceFilterService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.ws.rs.NotFoundException;
import java.util.function.Supplier;

@Mapper(componentModel = "spring", uses = {FlightRepository.class, AirportRepository.class, AirportMapper.class, TotalFlightPriceFilterService.class})
public abstract class FlightSearchResponseMapper extends EntityMapper<Long, Flight, FlightSearchResponseDTO> {

    private FlightRepository repository;
    private AirportRepository airportRepository;
    private AirportMapper airportMapper;
    private TotalFlightPriceFilterService priceFilterService;

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

    @Autowired
    public void setAirportRepository(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Autowired
    public void setAirportMapper(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }

    @Autowired
    public void setPriceFilterService(TotalFlightPriceFilterService priceFilterService) {
        this.priceFilterService = priceFilterService;
    }

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "airportToDto"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "airportToDto"),
    })
    @Override
    public abstract FlightSearchResponseDTO toDto(Flight entity);

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "airportToDto"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "airportToDto"),
    })
    public abstract FlightSearchResponseDTO toDto(Flight entity, @Context PassengerComposition composition);

    @Mappings({
            @Mapping(source = "source", target = "source", qualifiedByName = "dtoToAirport"),
            @Mapping(source = "destination", target = "destination", qualifiedByName = "dtoToAirport")
    })
    @Override
    public abstract Flight map(FlightSearchResponseDTO flightDTO, @MappingTarget Flight entity);

    @Named("airportToDto")
    protected AirportDTO airportToDto(Airport airport) {
        return airportMapper.toDto(airport);
    }

    @Named("dtoToAirport")
    protected Airport dtoToAirport(AirportDTO dto) {
        return airportRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
    }

    @AfterMapping
    protected void mapTotalTicketPrice(Flight entity, @MappingTarget FlightSearchResponseDTO dto, @Context PassengerComposition composition) {
        dto.setTotalTicketPrice(priceFilterService.calculateFlightPrice(entity, composition.getAdultCount(), composition.getChildCount()));
    }
}
