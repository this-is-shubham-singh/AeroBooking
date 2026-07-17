package com.micro.payload.response;

import com.micro.embeddable.Support;
import com.micro.enums.AirlineStatus;
import com.micro.payload.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineResponse {

    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String alias;
    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private Long headquartersCityId;
    private CityResponse headquartersCity;
    private Long ownerId;
    private UserDto owner;
    private Support support;
    private Long updatedById;
    private Instant createdAt;
    private Instant updatedAt;
}
