package com.micro.payload.request;

import com.micro.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {

    @NotBlank(message = "Flight number cannot be blank")
    @Size(max = 10)
    private String flightNumber;

    @NotNull(message = "Airline ID is required")
    @Positive(message = "Airline ID must be positive")
    private Long airlineId;

    @NotNull(message = "Aircraft ID is required")
    @Positive(message = "Aircraft ID must be positive")
    private Long aircraftId;

    @NotNull(message = "Departure airport ID is required")
    @Positive(message = "Departure airport ID must be positive")
    private Long departureAirportId;

    @NotNull(message = "Arrival airport ID is required")
    @Positive(message = "Arrival airport ID must be positive")
    private Long arrivalAirportId;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    private FlightStatus status;

    @Positive(message = "Price must be positive")
    private Double lowestPrice;

    @PositiveOrZero(message = "Total available seats must be zero or positive")
    private Integer totalAvailableSeats;
}
