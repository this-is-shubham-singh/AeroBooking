package com.micro.mapper;

import com.micro.embeddable.BoardingBenefits;
import com.micro.embeddable.FlexibilityBenefits;
import com.micro.embeddable.InFlightBenefits;
import com.micro.embeddable.PremiumServiceBenefits;
import com.micro.embeddable.SeatBenefits;
import com.micro.model.Fare;
import com.micro.payload.request.FareRequest;
import com.micro.payload.response.FareResponse;
import java.time.ZoneOffset;

public class FareMapper {

    public static Fare toEntity(FareRequest request) {
        if (request == null) {
            return null;
        }

        return Fare.builder()
                .name(request.getName())
                .rbdCode(request.getRbdCode())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .baseFare(request.getBaseFare())
                .taxesAndFees(request.getTaxesAndFees() != null ? request.getTaxesAndFees() : 0.0)
                .airlineFees(request.getAirlineFees() != null ? request.getAirlineFees() : 0.0)
                .currentPrice(request.getCurrentPrice() != null ? request.getCurrentPrice() : request.getBaseFare())
                .fareLabel(request.getFareLabel())
                .seatBenefits(SeatBenefits.builder()
                        .extraSeatSpace(Boolean.TRUE.equals(request.getExtraSeatSpace()))
                        .preferredSeatChoice(Boolean.TRUE.equals(request.getPreferredSeatChoice()))
                        .advanceSeatSelection(Boolean.TRUE.equals(request.getAdvanceSeatSelection()))
                        .guaranteedSeatTogether(Boolean.TRUE.equals(request.getGuaranteedSeatTogether()))
                        .build())
                .boardingBenefits(BoardingBenefits.builder()
                        .priorityBoarding(Boolean.TRUE.equals(request.getPriorityBoarding()))
                        .priorityCheckIn(Boolean.TRUE.equals(request.getPriorityCheckIn()))
                        .fastTrackSecurity(Boolean.TRUE.equals(request.getFastTrackSecurity()))
                        .build())
                .inFlightBenefits(InFlightBenefits.builder()
                        .complimentaryMeals(Boolean.TRUE.equals(request.getComplimentaryMeals()))
                        .premiumMealChoice(Boolean.TRUE.equals(request.getPremiumMealChoice()))
                        .inFlightInternet(Boolean.TRUE.equals(request.getInFlightInternet()))
                        .inFlightEntertainment(Boolean.TRUE.equals(request.getInFlightEntertainment()))
                        .complimentaryBeverages(Boolean.TRUE.equals(request.getComplimentaryBeverages()))
                        .build())
                .flexibilityBenefits(FlexibilityBenefits.builder()
                        .freeDateChange(Boolean.TRUE.equals(request.getFreeDateChange()))
                        .partialRefund(Boolean.TRUE.equals(request.getPartialRefund()))
                        .fullRefund(Boolean.TRUE.equals(request.getFullRefund()))
                        .build())
                .premiumServiceBenefits(PremiumServiceBenefits.builder()
                        .loungeAccess(Boolean.TRUE.equals(request.getLoungeAccess()))
                        .airportTransfer(Boolean.TRUE.equals(request.getAirportTransfer()))
                        .build())
                .build();
    }

    public static FareResponse toResponse(Fare entity) {
        if (entity == null) {
            return null;
        }

        return FareResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rbdCode(entity.getRbdCode())
                .flightId(entity.getFlightId())
                .cabinClassId(entity.getCabinClassId())
                .cabinClass(entity.getCabinClass())
                .baseFare(entity.getBaseFare())
                .taxesAndFees(entity.getTaxesAndFees())
                .airlineFees(entity.getAirlineFees())
                .currentPrice(entity.getCurrentPrice())
                .totalPrice(entity.getCurrentPrice()) // Maps to currentPrice since getTotalPrice() was removed
                .fareLabel(entity.getFareLabel())
                // Seat benefits
                .extraSeatSpace(entity.getSeatBenefits() != null && Boolean.TRUE.equals(entity.getSeatBenefits().getExtraSeatSpace()))
                .preferredSeatChoice(entity.getSeatBenefits() != null && Boolean.TRUE.equals(entity.getSeatBenefits().getPreferredSeatChoice()))
                .advanceSeatSelection(entity.getSeatBenefits() != null && Boolean.TRUE.equals(entity.getSeatBenefits().getAdvanceSeatSelection()))
                .guaranteedSeatTogether(entity.getSeatBenefits() != null && Boolean.TRUE.equals(entity.getSeatBenefits().getGuaranteedSeatTogether()))
                // Boarding benefits
                .priorityBoarding(entity.getBoardingBenefits() != null && Boolean.TRUE.equals(entity.getBoardingBenefits().getPriorityBoarding()))
                .priorityCheckIn(entity.getBoardingBenefits() != null && Boolean.TRUE.equals(entity.getBoardingBenefits().getPriorityCheckIn()))
                .fastTrackSecurity(entity.getBoardingBenefits() != null && Boolean.TRUE.equals(entity.getBoardingBenefits().getFastTrackSecurity()))
                // In-flight benefits
                .complimentaryMeals(entity.getInFlightBenefits() != null && Boolean.TRUE.equals(entity.getInFlightBenefits().getComplimentaryMeals()))
                .premiumMealChoice(entity.getInFlightBenefits() != null && Boolean.TRUE.equals(entity.getInFlightBenefits().getPremiumMealChoice()))
                .inFlightInternet(entity.getInFlightBenefits() != null && Boolean.TRUE.equals(entity.getInFlightBenefits().getInFlightInternet()))
                .inFlightEntertainment(entity.getInFlightBenefits() != null && Boolean.TRUE.equals(entity.getInFlightBenefits().getInFlightEntertainment()))
                .complimentaryBeverages(entity.getInFlightBenefits() != null && Boolean.TRUE.equals(entity.getInFlightBenefits().getComplimentaryBeverages()))
                // Flexibility benefits
                .freeDateChange(entity.getFlexibilityBenefits() != null && Boolean.TRUE.equals(entity.getFlexibilityBenefits().getFreeDateChange()))
                .partialRefund(entity.getFlexibilityBenefits() != null && Boolean.TRUE.equals(entity.getFlexibilityBenefits().getPartialRefund()))
                .fullRefund(entity.getFlexibilityBenefits() != null && Boolean.TRUE.equals(entity.getFlexibilityBenefits().getFullRefund()))
                // Premium service benefits
                .loungeAccess(entity.getPremiumServiceBenefits() != null && Boolean.TRUE.equals(entity.getPremiumServiceBenefits().getLoungeAccess()))
                .airportTransfer(entity.getPremiumServiceBenefits() != null && Boolean.TRUE.equals(entity.getPremiumServiceBenefits().getAirportTransfer()))
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toInstant(ZoneOffset.UTC) : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toInstant(ZoneOffset.UTC) : null)
                .build();
    }

    public static void updateEntity(FareRequest request, Fare entity) {
        if (request == null || entity == null) {
            return;
        }

        entity.setName(request.getName());
        entity.setRbdCode(request.getRbdCode());
        entity.setFlightId(request.getFlightId());
        entity.setCabinClassId(request.getCabinClassId());
        entity.setBaseFare(request.getBaseFare());
        entity.setTaxesAndFees(request.getTaxesAndFees() != null ? request.getTaxesAndFees() : 0.0);
        entity.setAirlineFees(request.getAirlineFees() != null ? request.getAirlineFees() : 0.0);
        entity.setCurrentPrice(request.getCurrentPrice() != null ? request.getCurrentPrice() : request.getBaseFare());
        entity.setFareLabel(request.getFareLabel());

        entity.setSeatBenefits(SeatBenefits.builder()
                .extraSeatSpace(Boolean.TRUE.equals(request.getExtraSeatSpace()))
                .preferredSeatChoice(Boolean.TRUE.equals(request.getPreferredSeatChoice()))
                .advanceSeatSelection(Boolean.TRUE.equals(request.getAdvanceSeatSelection()))
                .guaranteedSeatTogether(Boolean.TRUE.equals(request.getGuaranteedSeatTogether()))
                .build());

        entity.setBoardingBenefits(BoardingBenefits.builder()
                .priorityBoarding(Boolean.TRUE.equals(request.getPriorityBoarding()))
                .priorityCheckIn(Boolean.TRUE.equals(request.getPriorityCheckIn()))
                .fastTrackSecurity(Boolean.TRUE.equals(request.getFastTrackSecurity()))
                .build());

        entity.setInFlightBenefits(InFlightBenefits.builder()
                .complimentaryMeals(Boolean.TRUE.equals(request.getComplimentaryMeals()))
                .premiumMealChoice(Boolean.TRUE.equals(request.getPremiumMealChoice()))
                .inFlightInternet(Boolean.TRUE.equals(request.getInFlightInternet()))
                .inFlightEntertainment(Boolean.TRUE.equals(request.getInFlightEntertainment()))
                .complimentaryBeverages(Boolean.TRUE.equals(request.getComplimentaryBeverages()))
                .build());

        entity.setFlexibilityBenefits(FlexibilityBenefits.builder()
                .freeDateChange(Boolean.TRUE.equals(request.getFreeDateChange()))
                .partialRefund(Boolean.TRUE.equals(request.getPartialRefund()))
                .fullRefund(Boolean.TRUE.equals(request.getFullRefund()))
                .build());

        entity.setPremiumServiceBenefits(PremiumServiceBenefits.builder()
                .loungeAccess(Boolean.TRUE.equals(request.getLoungeAccess()))
                .airportTransfer(Boolean.TRUE.equals(request.getAirportTransfer()))
                .build());
    }
}
