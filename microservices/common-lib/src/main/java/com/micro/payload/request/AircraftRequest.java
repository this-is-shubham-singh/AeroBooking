package com.micro.payload.request;

import com.micro.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftRequest {

    @NotBlank(message = "Aircraft code cannot be blank")
    private String code;

    @NotBlank(message = "Aircraft model cannot be blank")
    private String model;

    @NotBlank(message = "Manufacturer cannot be blank")
    private String manufacturer;

    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be positive")
    private Integer seatingCapacity;

    @PositiveOrZero(message = "Economy seats must be zero or positive")
    private Integer economySeats;

    @PositiveOrZero(message = "Premium economy seats must be zero or positive")
    private Integer premiumEconomySeats;

    @PositiveOrZero(message = "Business seats must be zero or positive")
    private Integer businessSeats;

    @PositiveOrZero(message = "First class seats must be zero or positive")
    private Integer firstClassSeats;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    @Positive(message = "Cruising speed must be positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "Max altitude must be positive")
    private Integer maxAltitudeFt;

    @Positive(message = "Year of manufacture must be positive")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    @NotNull(message = "status is required")
    private AircraftStatus status;

    @NotNull(message = "availibility is required")
    private Boolean isAvailable;

    @Positive(message = "Current airport ID must be positive")
    private Long currentAirportId;
}
