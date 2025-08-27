package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FlightStatistic statistic = new FlightStatistic();
        FlightReader reader = new FlightReader();
        FlightReport report = new FlightReport();

        String airline = "Lufthansa";

        List<FlightInfoDTO> flightInfoDTOList = reader.getFlightInfoDTOList();

        //1 Print all flights
        //flightInfoDTOList.forEach(System.out::println);

        //Calculate total flight time for a specifc airline (for all flights operated by Lufthansa)

        Duration totalTime = statistic.calculateTotalForAirlineOperator(flightInfoDTOList, airline);
        report.printTotalForAirline(airline,totalTime);
        System.out.println("########################");

        //Add a new feature (e.g. calculate the average flight time for a specific airline.
        double avgTime = statistic.calculateTotalAverageForAirlineOperator(flightInfoDTOList, airline);
        report.printAverageForAirline(airline,avgTime);
        System.out.println("#########################");

        //Add a new feature (make a list of flights that are operated between two specific airports
        List<FlightInfoDTO> flightsOperationsBetweenAirports = statistic.getFlightsOperatingBetweenTowAirports(flightInfoDTOList, "Pulkovo", "Kurumoch");
        flightsOperationsBetweenAirports.forEach(System.out::println);
        System.out.println("#########################");

        //Add a new feature (make a list of flights that leaves before a specific time in the day/night)
        List<FlightInfoDTO> flightsLeavingBeforeSpecificTime = statistic.getFlightsLeavingAtSpecificTime(flightInfoDTOList, LocalTime.of(1, 0));
        //flightsLeavingBeforeSpecificTime.forEach(System.out::println);
        System.out.println("#########################");

        //Add a new feature (calculate the average flight time for each airline)
        Map<String, Double> averageFlightTimeForEachAirline = statistic.getAverageFlightTime(flightInfoDTOList);
        report.printAverageFlightTimeForEachAirline(averageFlightTimeForEachAirline);
    }
}
