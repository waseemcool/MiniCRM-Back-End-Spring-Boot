package com.practice.mini_crm.service;

import com.practice.mini_crm.dto.AnalyticsResponse;
import com.practice.mini_crm.entity.Category;
import com.practice.mini_crm.entity.InterestLevel;
import com.practice.mini_crm.entity.Lead;
import com.practice.mini_crm.repository.LeadRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final LeadRepo leadRepo;

    public AnalyticsService(LeadRepo leadRepo) {
        this.leadRepo = leadRepo;
    }

    public AnalyticsResponse getAnalytics(Integer days){
        LocalDateTime startDate = days != null ?
                LocalDateTime.now().minusDays(days) : LocalDateTime.now().minusDays(30);

        // Category Distribution
        Map<String, Long> categoryDistribution = new HashMap<>();
        for(Object[] result : leadRepo.countLeadsByCategory()){
            Category category = (Category) result[0];
            Long count = (Long) result[1];
            categoryDistribution.put(category.getDisplayName(), count);
        }

        // Interest Level Distribution
        Map<String, Long> interestLevelDistribution = new HashMap<>();
        for(Object[] result : leadRepo.countLeadsByInterestLevel()){
            InterestLevel interestLevel = (InterestLevel) result[0];
            Long count = (Long) result[1];
            interestLevelDistribution.put(interestLevel.getDisplayName(), count);
        }

        // Daily Lead Volume
        List<AnalyticsResponse.DailyLeadVolume> dailyLeadVolumes = new ArrayList<>();
        for(Object[] result : leadRepo.getDailyLeadVolume(startDate)){
            String date = result[0].toString();
            Long count = (Long) result[1];
            dailyLeadVolumes.add(new AnalyticsResponse.DailyLeadVolume(date, count));
        }

        // Monthly Lead Volume
        List<AnalyticsResponse.MonthlyLeadVolume> monthlyLeadVolumes = new ArrayList<>();
        LocalDateTime monthsAgo = LocalDateTime.now().minusMonths(6);
        for (Object[] result : leadRepo.getMonthlyLeadVolume(monthsAgo)){
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            Long count = (Long) result[2];
            String monthStr = String.format("%d-%02d", year, month);
            monthlyLeadVolumes.add(new AnalyticsResponse.MonthlyLeadVolume(monthStr, count));
        }

        // Top Requirements
        List<AnalyticsResponse.TopRequirement> topRequirements = new ArrayList<>();
        List<Object[]> requirements = leadRepo.getTopRequirementNotes();
        for(int k = 0; k < Math.min(5, requirements.size()); k++){
            Object[] result = requirements.get(k);
            String requirement = (String) result[0];
            Long count = (Long) result[1];
            if(requirement != null && !requirement.trim().isEmpty()){
                topRequirements.add(new AnalyticsResponse.TopRequirement(requirement, count));
            }
        }

        List<Lead> leads = leadRepo.findAll();

        // Conversion Metrics
        long justAskingOrCurious = leads.stream()
                .filter(l -> "JUST_ASKING_OR_CURIOUS".equals(l.getInterestLevel().getInterestLevelName()))
                .count();
        long interestedInProduct = leads.stream()
                .filter(l -> "INTERESTED_IN_PRODUCT".equals(l.getInterestLevel().getInterestLevelName()))
                .count();
        long requestingAQuotation = leads.stream()
                .filter(l -> "REQUESTING_A_QUOTATION".equals(l.getInterestLevel().getInterestLevelName()))
                .count();
        long agreementOrPaymentPending = leads.stream()
                .filter(l -> "AGREEMENT_OR_PAYMENT_PENDING".equals(l.getInterestLevel().getInterestLevelName()))
                .count();

        double conversionRate = leads.size() > 0 ?
                (double) (requestingAQuotation + agreementOrPaymentPending) / leads.size() * 100
                : 0.0;

        AnalyticsResponse.ConversionMetrics conversionMetrics = new AnalyticsResponse.ConversionMetrics(
                justAskingOrCurious, interestedInProduct, requestingAQuotation, agreementOrPaymentPending, conversionRate
        );

        return new AnalyticsResponse(
                (long) leads.size(),
                categoryDistribution,
                interestLevelDistribution,
                dailyLeadVolumes,
                monthlyLeadVolumes,
                topRequirements,
                conversionMetrics
        );
    }
}