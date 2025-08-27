package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.time.Duration;
import java.time.LocalTime;
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
        List<FlightInfoDTO> flights = flightInfoDTOList.stream()
                .filter(airline -> airline.getOrigin() != null && airline.getDestination() != null && airline.getOrigin().equals(airport1) && airline.getDestination().equals(airport2) || airline.getOrigin() != null && airline.getDestination() != null && airline.getOrigin().equals(airport2) && airline.getDestination().equals(airport1))
                .collect(Collectors.toList());
        return flights;
    }

    public List<FlightInfoDTO> getFlightsLeavingAtSpecificTime(List<FlightInfoDTO> flightInfoDTOList, LocalTime time) {
        List<FlightInfoDTO> flights = flightInfoDTOList.stream()
                .filter(flight -> flight.getDeparture().toLocalTime().isBefore(time))
                .collect(Collectors.toList());

        return flights;
    }

    public Map<String, Double> getAverageFlightTime(List<FlightInfoDTO> flightInfoDTOList) {
        Map<String,Double> averageFlightTimes = flightInfoDTOList.stream()
                .filter(f -> f.getAirline() != null && f.getDuration() != null)
                .collect(Collectors.groupingBy(
                        FlightInfoDTO::getAirline,
                        Collectors.averagingDouble(value -> value.getDuration().toMinutes())
                ));
        return averageFlightTimes;
    }
}
