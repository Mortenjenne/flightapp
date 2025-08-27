package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;
import dk.cphbusiness.utils.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class FlightReader {

    public static void main(String[] args) {
        try {
            List<FlightDTO> flightList = getFlightsFromFile("flights.json");
            List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);
            //1 Print all flights
            //flightInfoDTOList.forEach(System.out::println);

            //Calculate total flight time for a specifc airline (for all flights operated by Lufthansa)
           calculateTotalForAirlineOperator(flightInfoDTOList,"Lufthansa");

           //Add a new feature (e.g. calculate the average flight time for a specific airline.
            // For example, calculate the average flight time for all flights operated by Lufthansa)
            calculateTotalAverageForAirlineOperator(flightInfoDTOList,"Lufthansa");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<FlightDTO> getFlightsFromFile(String filename) throws IOException {

        ObjectMapper objectMapper = Utils.getObjectMapper();

        // Deserialize JSON from a file into FlightDTO[]
        FlightDTO[] flightsArray = objectMapper.readValue(Paths.get("flights.json").toFile(), FlightDTO[].class);

        // Convert to a list
        List<FlightDTO> flightsList = List.of(flightsArray);
        return flightsList;
    }

    public static List<FlightInfoDTO> getFlightInfoDetails(List<FlightDTO> flightList) {
        List<FlightInfoDTO> flightInfoList = flightList.stream()
           .map(flight -> {
                LocalDateTime departure = flight.getDeparture().getScheduled();
                LocalDateTime arrival = flight.getArrival().getScheduled();
                Duration duration = Duration.between(departure, arrival);
                FlightInfoDTO flightInfo =
                        FlightInfoDTO.builder()
                            .name(flight.getFlight().getNumber())
                            .iata(flight.getFlight().getIata())
                            .airline(flight.getAirline().getName())
                            .duration(duration)
                            .departure(departure)
                            .arrival(arrival)
                            .origin(flight.getDeparture().getAirport())
                            .destination(flight.getArrival().getAirport())
                            .build();

                return flightInfo;
            })
        .toList();
        return flightInfoList;
    }

    private static void calculateTotalForAirlineOperator(List<FlightInfoDTO> flightInfoDTOList, String airlineCompany){
        Long totalFlightTime = flightInfoDTOList.stream()
                .filter(airline -> airline.getAirline() != null &&
                        airline.getDestination() != null &&
                                airline.getOrigin() != null &&
                        airline.getAirline().equals(airlineCompany))
                .mapToLong(flight -> flight.getDuration().toMinutes())
                .sum();

        System.out.println(totalFlightTime);

        long hours = totalFlightTime / 60;
        long minutes = totalFlightTime % 60;

        System.out.println("Total flight time for " + airlineCompany + ": " + hours + "h " + minutes + "m");
    }

    private static void calculateTotalAverageForAirlineOperator(List<FlightInfoDTO> flightInfoDTOList, String airlineCompany){
        Double totalFlightTime = flightInfoDTOList.stream()
                .filter(airline -> airline.getAirline() != null &&
                        airline.getDestination() != null &&
                        airline.getOrigin() != null &&
                        airline.getAirline().equals(airlineCompany))
                .collect(Collectors.averagingLong(flight -> flight.getDuration().toMinutes()));


        Double hours =  totalFlightTime / 60;
        Double minutes = totalFlightTime % 60;

        System.out.printf("Average flight time for %s: %.0f hours and %.0f minutes",airlineCompany,hours,minutes);
    }



}
