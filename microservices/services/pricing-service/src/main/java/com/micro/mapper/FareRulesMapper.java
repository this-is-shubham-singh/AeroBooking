package com.micro.mapper;

import com.micro.model.Fare;
import com.micro.model.FareRules;
import com.micro.payload.request.FareRulesRequest;
import com.micro.payload.response.FareRulesResponse;

public class FareRulesMapper {

    public static FareRules toEntity(FareRulesRequest request, Fare fare) {
        if (request == null) {
            return null;
        }

        return FareRules.builder()
                .ruleName(request.getRuleName())
                .airlineId(request.getAirlineId())
                .fare(fare)
                .isRefundable(Boolean.TRUE.equals(request.getIsRefundable()))
                .changeFee(request.getChangeFee() != null ? request.getChangeFee() : 0.0)
                .cancellationFee(request.getCancellationFee() != null ? request.getCancellationFee() : 0.0)
                .refundDeadlineDays(request.getRefundDeadlineDays() != null ? request.getRefundDeadlineDays() : 0)
                .changeDeadlineHours(request.getChangeDeadlineHours() != null ? request.getChangeDeadlineHours() : 0)
                .isChangeable(Boolean.TRUE.equals(request.getIsChangeable()))
                .build();
    }

    public static FareRulesResponse toResponse(FareRules entity) {
        if (entity == null) {
            return null;
        }

        return FareRulesResponse.builder()
                .id(entity.getId())
                .ruleName(entity.getRuleName())
                .fareId(entity.getFare() != null ? entity.getFare().getId() : null)
                .airlineId(entity.getAirlineId())
                .isRefundable(entity.getIsRefundable())
                .changeFee(entity.getChangeFee())
                .cancellationFee(entity.getCancellationFee())
                .refundDeadlineDays(entity.getRefundDeadlineDays())
                .changeDeadlineHours(entity.getChangeDeadlineHours())
                .isChangeable(entity.getIsChangeable())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FareRulesRequest request, FareRules entity) {
        if (request == null || entity == null) {
            return;
        }

        if (request.getRuleName() != null) {
            entity.setRuleName(request.getRuleName());
        }
        if (request.getAirlineId() != null) {
            entity.setAirlineId(request.getAirlineId());
        }
        if (request.getIsRefundable() != null) {
            entity.setIsRefundable(request.getIsRefundable());
        }
        if (request.getChangeFee() != null) {
            entity.setChangeFee(request.getChangeFee());
        }
        if (request.getCancellationFee() != null) {
            entity.setCancellationFee(request.getCancellationFee());
        }
        if (request.getRefundDeadlineDays() != null) {
            entity.setRefundDeadlineDays(request.getRefundDeadlineDays());
        }
        if (request.getChangeDeadlineHours() != null) {
            entity.setChangeDeadlineHours(request.getChangeDeadlineHours());
        }
        if (request.getIsChangeable() != null) {
            entity.setIsChangeable(request.getIsChangeable());
        }
    }
}
