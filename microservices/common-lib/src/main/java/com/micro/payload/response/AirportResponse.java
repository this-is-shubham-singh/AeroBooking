package com.micro.payload.response;

import com.micro.embeddable.Address;
import com.micro.embeddable.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {

    private Long id;
    private String iataCode;
    private String name;
    private String detailedName;
    private String timezone;
    private Address address;
    private CityResponse city;
    private GeoCode geoCode;
}
