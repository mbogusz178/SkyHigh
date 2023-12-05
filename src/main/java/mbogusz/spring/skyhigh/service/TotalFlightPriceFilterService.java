package mbogusz.spring.skyhigh.service;

import mbogusz.spring.skyhigh.entity.Flight;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TotalFlightPriceFilterService {

    public double calculateFlightPrice(Flight flight, Integer adultCount, Integer childCount) {
        return adultCount * flight.getTicketPriceAdult() + childCount * flight.getTicketPriceChild();
    }

    public List<Flight> filterTotalFlightPrice(List<Flight> flights, Integer adultCount, Integer childCount, Double minFlightPrice, Double maxFlightPrice) {
        return flights.stream().filter(flight -> {
            double totalPrice = calculateFlightPrice(flight, adultCount, childCount);
            boolean eligible = true;
            if(minFlightPrice != null) eligible = totalPrice >= minFlightPrice;
            if(maxFlightPrice != null) eligible = eligible && totalPrice <= maxFlightPrice;
            return eligible;
        }).collect(Collectors.toList());
    }
}
