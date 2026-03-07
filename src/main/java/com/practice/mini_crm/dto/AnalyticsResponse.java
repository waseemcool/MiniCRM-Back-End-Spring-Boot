package com.practice.mini_crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

    private Long totalLeads;
    private Map<String, Long> categoryDistribution;
    private Map<String, Long> interestLevelDistribution;
    private List<DailyLeadVolume> dailyLeadVolumes;
    private List<MonthlyLeadVolume> monthlyLeadVolumes;
    private List<TopRequirement> topRequirements;
    private ConversionMetrics conversionMetrics;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyLeadVolume{
        private String date;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyLeadVolume{
        private String month;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopRequirement{
        private String requirement;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConversionMetrics{
        private Long justAskingOrCurious;
        private Long interestedInProduct;
        private Long requestingAQuotation;
        private Long agreementOrPaymentPending;
        private Double conversionRate;
    }

}