package com.micro.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "City code cannot be blank")
    @Size(max = 10, message = "City code cannot exceed 10 characters")
    private String cityCode;

    @NotBlank(message = "Country code cannot be blank")
    @Size(max = 10, message = "Country code cannot exceed 10 characters")
    private String countryCode;

    @NotBlank(message = "Country name cannot be blank")
    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    private String countryName;

    @Size(max = 10, message = "Region code cannot exceed 10 characters")
    private String regionCode;

    @Size(max = 10, message = "Timezone offset cannot exceed 10 characters")
    private String timezoneOffset;
}
