package dk.cphbusiness.flightdemo;

import java.time.Duration;
import java.util.Map;

public class FlightReport {

    public void printTotalForAirline(String airline, Duration duration) {
        System.out.println("Total flight time for " + airline + " " + formatDuration(duration));
    }

    public void printAverageForAirline(String airline, double flightTime) {
        System.out.println("Average flight time for " + airline + " " + formatDouble(flightTime));
    }

    public void printAverageFlightTimeForEachAirline(Map<String,Double> airlines) {
        airlines.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(f -> {
                    long totalMinutes = Math.round(f.getValue());
                    long hours = totalMinutes / 60;
                    long minutes = totalMinutes % 60;
                    System.out.println("Airline: " + f.getKey() + ", Average flight time: " + hours + " h " + minutes + " m");
                });
    }

    public void printTotalFlightTimeForEachAirline(Map<String,Double> airlines) {
        airlines.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(f -> {
                    long totalMinutes = Math.round(f.getValue());
                    long hours = totalMinutes / 60;
                    long minutes = totalMinutes % 60;
                    System.out.println("Airline: " + f.getKey() + ", Total flight time: " + hours + " h " + minutes + " m");
                });
    }

    private String formatDuration(Duration d){
        return d.toHours() + "h " + d.toMinutesPart() + "m";
    }

    private String formatDouble(Double totalFlightTime){
        Double hours =  totalFlightTime / 60;
        Double minutes = totalFlightTime % 60;
        return String.format("%.0f h %.0f m",hours,minutes);
    }
}
