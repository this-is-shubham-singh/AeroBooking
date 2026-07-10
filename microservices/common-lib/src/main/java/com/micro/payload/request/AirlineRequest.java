package com.micro.payload.request;

import com.micro.embeddable.Support;
import com.micro.enums.AirlineStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {

    @NotBlank(message = "IATA code cannot be blank")
    @Size(min = 2, max = 2, message = "IATA code must be exactly 3 characters")
    private String iataCode;

    @NotBlank(message = "ICAO code cannot be blank")
    @Size(min = 3, max = 3, message = "ICAO code must be exactly 4 characters")
    private String icaoCode;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    private String alias;

    private String logoUrl;

    private String website;

    private AirlineStatus status;

    private String alliance;

    @NotNull(message = "Headquarters City ID is required")
    private Long headquartersCityId;

    @Valid
    private Support support;
}
