package com.micro.mapper;

import com.micro.model.BaggagePolicy;
import com.micro.model.Fare;
import com.micro.payload.request.BaggagePolicyRequest;
import com.micro.payload.response.BaggagePolicyResponse;

public class BaggagePolicyMapper {

    public static BaggagePolicy toEntity(BaggagePolicyRequest request, Fare fare) {
        if (request == null) {
            return null;
        }

        return BaggagePolicy.builder()
                .fare(fare)
                .name(request.getName())
                .description(request.getDescription())
                .cabinBaggageMaxWeight(request.getCabinBaggageMaxWeight() != null ? request.getCabinBaggageMaxWeight() : 0.0)
                .cabinBaggagePieces(request.getCabinBaggagePieces() != null ? request.getCabinBaggagePieces() : 1)
                .cabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece() != null ? request.getCabinBaggageWeightPerPiece() : 0.0)
                .cabinBaggageMaxDimension(request.getCabinBaggageMaxDimension() != null ? request.getCabinBaggageMaxDimension() : 0.0)
                .checkInBaggageMaxWeight(request.getCheckInBaggageMaxWeight() != null ? request.getCheckInBaggageMaxWeight() : 0.0)
                .checkInBaggagePieces(request.getCheckInBaggagePieces() != null ? request.getCheckInBaggagePieces() : 1)
                .checkInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPiece() != null ? request.getCheckInBaggageWeightPerPiece() : 0.0)
                .freeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance() != null ? request.getFreeCheckedBagsAllowance() : 0)
                .priorityBaggage(Boolean.TRUE.equals(request.getPriorityBaggage()))
                .extraBaggageAllowance(Boolean.TRUE.equals(request.getExtraBaggageAllowance()))
                .airlineId(request.getAirlineId())
                .build();
    }

    public static BaggagePolicyResponse toResponse(BaggagePolicy entity) {
        if (entity == null) {
            return null;
        }

        return BaggagePolicyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .cabinBaggageMaxWeight(entity.getCabinBaggageMaxWeight())
                .cabinBaggagePieces(entity.getCabinBaggagePieces())
                .cabinBaggageWeightPerPiece(entity.getCabinBaggageWeightPerPiece())
                .cabinBaggageMaxDimension(entity.getCabinBaggageMaxDimension())
                .checkInBaggageMaxWeight(entity.getCheckInBaggageMaxWeight())
                .checkInBaggagePieces(entity.getCheckInBaggagePieces())
                .checkInBaggageWeightPerPiece(entity.getCheckInBaggageWeightPerPiece())
                .freeCheckedBagsAllowance(entity.getFreeCheckedBagsAllowance())
                .priorityBaggage(entity.getPriorityBaggage())
                .extraBaggageAllowance(entity.getExtraBaggageAllowance())
                .airlineId(entity.getAirlineId())
                .fareId(entity.getFare() != null ? entity.getFare().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static void updateEntity(BaggagePolicyRequest request, BaggagePolicy entity) {
        if (request == null || entity == null) {
            return;
        }

        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getCabinBaggageMaxWeight() != null) {
            entity.setCabinBaggageMaxWeight(request.getCabinBaggageMaxWeight());
        }
        if (request.getCabinBaggagePieces() != null) {
            entity.setCabinBaggagePieces(request.getCabinBaggagePieces());
        }
        if (request.getCabinBaggageWeightPerPiece() != null) {
            entity.setCabinBaggageWeightPerPiece(request.getCabinBaggageWeightPerPiece());
        }
        if (request.getCabinBaggageMaxDimension() != null) {
            entity.setCabinBaggageMaxDimension(request.getCabinBaggageMaxDimension());
        }
        if (request.getCheckInBaggageMaxWeight() != null) {
            entity.setCheckInBaggageMaxWeight(request.getCheckInBaggageMaxWeight());
        }
        if (request.getCheckInBaggagePieces() != null) {
            entity.setCheckInBaggagePieces(request.getCheckInBaggagePieces());
        }
        if (request.getCheckInBaggageWeightPerPiece() != null) {
            entity.setCheckInBaggageWeightPerPiece(request.getCheckInBaggageWeightPerPiece());
        }
        if (request.getFreeCheckedBagsAllowance() != null) {
            entity.setFreeCheckedBagsAllowance(request.getFreeCheckedBagsAllowance());
        }
        if (request.getPriorityBaggage() != null) {
            entity.setPriorityBaggage(request.getPriorityBaggage());
        }
        if (request.getExtraBaggageAllowance() != null) {
            entity.setExtraBaggageAllowance(request.getExtraBaggageAllowance());
        }
        if (request.getAirlineId() != null) {
            entity.setAirlineId(request.getAirlineId());
        }
    }
}
