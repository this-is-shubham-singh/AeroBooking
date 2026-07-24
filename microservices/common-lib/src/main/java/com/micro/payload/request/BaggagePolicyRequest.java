package com.micro.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggagePolicyRequest {

    @NotBlank(message = "Policy name is required")
    private String name;

    @NotNull(message = "fare id is required")
    private Long fareId;

    private String description;

    @PositiveOrZero(message = "Cabin baggage max weight must be positive or zero")
    private Double cabinBaggageMaxWeight;

    @PositiveOrZero(message = "Cabin baggage pieces must be positive or zero")
    private Integer cabinBaggagePieces;

    @PositiveOrZero(message = "Cabin baggage weight per piece must be positive or zero")
    private Double cabinBaggageWeightPerPiece;

    @PositiveOrZero(message = "Cabin baggage max dimension must be positive or zero")
    private Double cabinBaggageMaxDimension;

    @PositiveOrZero(message = "Check-in baggage max weight must be positive or zero")
    private Double checkInBaggageMaxWeight;

    @PositiveOrZero(message = "Check-in baggage pieces must be positive or zero")
    private Integer checkInBaggagePieces;

    @PositiveOrZero(message = "Check-in baggage weight per piece must be positive or zero")
    private Double checkInBaggageWeightPerPiece;

    @PositiveOrZero(message = "Free checked bags allowance must be positive or zero")
    private Integer freeCheckedBagsAllowance;

    private Boolean priorityBaggage;

    private Boolean extraBaggageAllowance;

    private Long airlineId;
}
