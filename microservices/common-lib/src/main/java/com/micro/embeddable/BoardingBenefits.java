package com.micro.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardingBenefits {

    @Builder.Default
    private Boolean priorityBoarding = false;

    @Builder.Default
    private Boolean priorityCheckIn = false;

    @Column(name = "fast_track_security", nullable = false)
    @Builder.Default
    private Boolean fastTrackSecurity = false;
}
