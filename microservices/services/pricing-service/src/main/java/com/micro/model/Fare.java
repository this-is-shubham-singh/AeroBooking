package com.micro.model;

import com.micro.enums.CabinClassType;
import com.micro.embeddable.SeatBenefits;
import com.micro.embeddable.BoardingBenefits;
import com.micro.embeddable.InFlightBenefits;
import com.micro.embeddable.FlexibilityBenefits;
import com.micro.embeddable.PremiumServiceBenefits;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fares")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Character rbdCode;

    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    @Column(name = "cabin_class_id", nullable = false)
    private Long cabinClassId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cabin_class")
    private CabinClassType cabinClass;

    @Column(name = "base_fare", nullable = false)
    private Double baseFare;

    @Column(name = "taxes_and_fees")
    private Double taxesAndFees;

    @Column(name = "airline_fees")
    private Double airlineFees;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;

    @Column(name = "fare_label")
    private String fareLabel;

    @OneToOne
    @JoinColumn(name = "baggage_policy_id")
    private BaggagePolicy baggagePolicy;

    @OneToOne
    @JoinColumn(name = "fare_rules_id")
    private FareRules fareRules;

    @Embedded
    @Builder.Default
    private SeatBenefits seatBenefits = new SeatBenefits();

    @Embedded
    @Builder.Default
    private BoardingBenefits boardingBenefits = new BoardingBenefits();

    @Embedded
    @Builder.Default
    private InFlightBenefits inFlightBenefits = new InFlightBenefits();

    @Embedded
    @Builder.Default
    private FlexibilityBenefits flexibilityBenefits = new FlexibilityBenefits();

    @Embedded
    @Builder.Default
    private PremiumServiceBenefits premiumServiceBenefits = new PremiumServiceBenefits();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
