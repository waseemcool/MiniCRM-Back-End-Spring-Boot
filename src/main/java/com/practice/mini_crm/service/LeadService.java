package com.practice.mini_crm.service;

import com.practice.mini_crm.dto.ApiResponse;
import com.practice.mini_crm.dto.LeadRequest;
import com.practice.mini_crm.dto.LeadResponse;
import com.practice.mini_crm.entity.Category;
import com.practice.mini_crm.entity.InterestLevel;
import com.practice.mini_crm.entity.Lead;
import com.practice.mini_crm.repository.CategoryRepo;
import com.practice.mini_crm.repository.InterestLevelRepo;
import com.practice.mini_crm.repository.LeadRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeadService {

    private final LeadRepo leadRepo;
    private final InterestLevelRepo interestLevelRepo;
    private final CategoryRepo categoryRepo;

    public LeadService(LeadRepo leadRepo, InterestLevelRepo interestLevelRepo, CategoryRepo categoryRepo) {
        this.leadRepo = leadRepo;
        this.interestLevelRepo = interestLevelRepo;
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public ApiResponse<LeadResponse> saveLead(LeadRequest leadRequest) {
        Optional<Lead> existLead = leadRepo.findByNameAndEmailAddressAndContactNumber(leadRequest.getName(),
                leadRequest.getEmailAddress(), leadRequest.getContactNumber());

        if(existLead.isPresent()) {
            LeadResponse existingLeadResponse = LeadResponse.toDTO(existLead.get());

            return ApiResponse.error(
                    "Lead already exists with this Name, E-mail Address and Contact Number",
                    existingLeadResponse,
                    "DUPLICATE_LEAD"
            );
        }

        Optional<Lead> existEmailAddress = leadRepo.findByEmailAddress(leadRequest.getEmailAddress());

        if(existEmailAddress.isPresent()) {
            LeadResponse existingLeadResponse = LeadResponse.toDTO(existEmailAddress.get());

            return ApiResponse.error(
                    "This E-mail Address already exists in database",
                    existingLeadResponse,
                    "DUPLICATE_LEAD"
            );
        }

        Optional<Lead> existContactNumber = leadRepo.findByContactNumber(leadRequest.getContactNumber());

        if(existContactNumber.isPresent()) {
            LeadResponse existingLeadResponse = LeadResponse.toDTO(existContactNumber.get());

            return ApiResponse.error(
                    "This Contact Number already exists in database",
                    existingLeadResponse,
                    "DUPLICATE_LEAD"
            );
        }

        InterestLevel interestLevel = interestLevelRepo.findById(leadRequest.getInterestLevelId())
                .orElseThrow(() -> new RuntimeException("Interest Level not found with Id " + leadRequest.getInterestLevelId()));

        Category category = categoryRepo.findById(leadRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with Id " + leadRequest.getCategoryId()));

        Lead lead = new Lead();
        lead.setName(leadRequest.getName());
        lead.setEmailAddress(leadRequest.getEmailAddress());
        lead.setContactNumber(leadRequest.getContactNumber());
        lead.setRequirementNotes(leadRequest.getRequirementNotes());
        lead.setInterestLevel(interestLevel);
        lead.setCategory(category);

        LocalDateTime now = LocalDateTime.now();
        lead.setCreatedAt(now);
        lead.setUpdatedAt(now);

        Lead savedLead = leadRepo.save(lead);
        LeadResponse leadResponse = LeadResponse.toDTO(savedLead);

        return ApiResponse.success("Lead Saved Successfully", leadResponse);
    }

    public List<LeadResponse> getAllLeads() {
        return leadRepo.findAll().stream()
                .map(LeadResponse::toDTO).collect(Collectors.toList());
    }

    public LeadResponse getLeadById(Long id) {
        Lead lead = leadRepo.findById(id).orElseThrow(() -> new RuntimeException("Lead not found with Id " + id));
        return LeadResponse.toDTO(lead);
    }

    public List<LeadResponse> getAllLeadsByCategory(Long categoryId) {
        return leadRepo.findByCategoryId(categoryId).stream()
                .map(LeadResponse::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse<LeadResponse> update(Long id, LeadRequest leadRequest) {
        Lead lead = leadRepo.findById(id).orElseThrow(() -> new RuntimeException("Lead not found with Id " + id));

        InterestLevel interestLevel = interestLevelRepo.findById(leadRequest.getInterestLevelId())
                .orElseThrow(() -> new RuntimeException("Interest Level not found with Id " + leadRequest.getInterestLevelId()));

        Category category = categoryRepo.findById(leadRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with Id " + leadRequest.getCategoryId()));

        lead.setName(leadRequest.getName());
        lead.setEmailAddress(leadRequest.getEmailAddress());
        lead.setContactNumber(leadRequest.getContactNumber());
        lead.setRequirementNotes(leadRequest.getRequirementNotes());
        lead.setInterestLevel(interestLevel);
        lead.setCategory(category);
        lead.setUpdatedAt(LocalDateTime.now());

        Lead updatedLead = leadRepo.save(lead);
        LeadResponse leadResponse = LeadResponse.toDTO(updatedLead);

        return ApiResponse.success("Lead Updated Successfully", leadResponse);
    }

    @Transactional
    public void removeLead(Long id) {
        if (!leadRepo.existsById(id)){
            throw new RuntimeException("Lead not found with Id " + id);
        }
        leadRepo.deleteById(id);
    }
}