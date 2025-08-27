package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightStatistic {

    public Duration calculateTotalForAirlineOperator(List<FlightInfoDTO> flightInfoDTOList, String airlineCompany){
        Duration totalFlightTime = flightInfoDTOList.stream()
                .filter(airline -> airline.getAirline() != null &&
                        airline.getDestination() != null &&
                        airline.getOrigin() != null &&
                        airline.getAirline().equals(airlineCompany))
                .map(FlightInfoDTO::getDuration)
                        .reduce(Duration.ZERO,(d1,d2) -> d1.plus(d2));

        return totalFlightTime;
    }

    public Double calculateTotalAverageForAirlineOperator(List<FlightInfoDTO> flightInfoDTOList, String airlineCompany){
        Double totalFlightTime = flightInfoDTOList.stream()
                .filter(airline -> airline.getAirline() != null &&
                        airline.getDestination() != null &&
                        airline.getOrigin() != null &&
                        airline.getAirline().equals(airlineCompany))
                .collect(Collectors.averagingLong(flight -> flight.getDuration().toMinutes()));
        return totalFlightTime;
    }

    public List<FlightInfoDTO> getFlightsOperatingBetweenTowAirports(List<FlightInfoDTO> flightInfoDTOList, String airport1, String airport2) {
            return flightInfoDTOList.stream()
                    .filter(flight -> flight.getOrigin() != null && flight.getDestination() != null)
                    .filter(flight ->
                            (flight.getOrigin().equals(airport1) && flight.getDestination().equals(airport2)) ||
                                    (flight.getOrigin().equals(airport2) && flight.getDestination().equals(airport1))
                    )
                    .distinct()
                    .collect(Collectors.toList());
        }

    public List<FlightInfoDTO> getFlightsLeavingBefore(List<FlightInfoDTO> flightInfoDTOList, LocalTime time) {
        List<FlightInfoDTO> flights = flightInfoDTOList.stream()
                .filter(flight -> flight.getDeparture() != null && flight.getDeparture().toLocalTime().isBefore(time))
                .distinct()
                .collect(Collectors.toList());

        return flights;
    }

    public Map<String, Double> getAverageFlightTime(List<FlightInfoDTO> flightInfoDTOList) {
        Map<String,Double> averageFlightTimes = flightInfoDTOList.stream()
                .filter(f -> f.getAirline() != null && f.getDuration() != null && !f.getDuration().isNegative())
                .collect(Collectors.groupingBy(
                        FlightInfoDTO::getAirline,
                        Collectors.averagingDouble(value -> value.getDuration().toMinutes())
                ));
        return averageFlightTimes;
    }

    public List<FlightInfoDTO> sortListByArrivalTime(List<FlightInfoDTO> flightInfoDTOList){
        return flightInfoDTOList.stream()
                .filter(f -> f.getArrival() != null)
                .distinct()
                .sorted(Comparator.comparing(FlightInfoDTO::getArrival))
                .collect(Collectors.toList());
    }

    public Map<String, Double> getTotalFlightTime(List<FlightInfoDTO> flightInfoDTOList) {
        Map<String,Double> totalFlightTimes = flightInfoDTOList.stream()
                .filter(f -> f.getAirline() != null && f.getDuration() != null && !f.getDuration().isNegative())
                .collect(Collectors.groupingBy(
                        FlightInfoDTO::getAirline,
                        Collectors.summingDouble(value -> value.getDuration().toMinutes())
                ));
        return totalFlightTimes;
    }

    public List<FlightInfoDTO> sortListByDuration(List<FlightInfoDTO> flightInfoDTOList) {
        return flightInfoDTOList.stream()
                .filter(f -> f.getDuration() != null && !f.getDuration().isNegative())
                .distinct()
                .sorted(Comparator.comparing(FlightInfoDTO::getDuration))
                .collect(Collectors.toList());
    }
}
