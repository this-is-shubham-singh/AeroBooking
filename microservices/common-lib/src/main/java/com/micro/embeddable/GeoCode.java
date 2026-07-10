package com.micro.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoCode {

    private Double latitude;
    private Double longitude;
}
