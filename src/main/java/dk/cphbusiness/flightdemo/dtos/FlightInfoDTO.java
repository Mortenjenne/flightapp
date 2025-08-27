package dk.cphbusiness.flightdemo.dtos;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class FlightInfoDTO {
    private String name;
    private String iata;
    private String airline;
    private Duration duration;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private String origin;
    private String destination;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightInfoDTO that = (FlightInfoDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(iata, that.iata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iata);
    }
}
