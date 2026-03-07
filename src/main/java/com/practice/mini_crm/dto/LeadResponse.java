package com.practice.mini_crm.dto;

import com.practice.mini_crm.entity.Lead;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeadResponse {

    private Long id;
    private String name;
    private String contactNumber;
    private String emailAddress;
    private String requirementNotes;
    private Long categoryId;
    private String categoryName;
    private String categoryDisplay;
    private Long interestLevelId;
    private String interestLevelName;
    private String interestLevelDisplay;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LeadResponse toDTO(Lead lead) {
        LeadResponse response = new LeadResponse();
        response.setId(lead.getId());
        response.setName(lead.getName());
        response.setEmailAddress(lead.getEmailAddress());
        response.setContactNumber(lead.getContactNumber());
        response.setRequirementNotes(lead.getRequirementNotes());
        response.setInterestLevelId(lead.getInterestLevel().getId());
        response.setInterestLevelName(lead.getInterestLevel().getInterestLevelName());
        response.setInterestLevelDisplay(lead.getInterestLevel().getDisplayName());
        response.setCategoryId(lead.getCategory().getId());
        response.setCategoryName(lead.getCategory().getCategoryName());
        response.setCategoryDisplay(lead.getCategory().getDisplayName());
        response.setCreatedAt(lead.getCreatedAt());
        response.setUpdatedAt(lead.getUpdatedAt());

        return response;
    }

}