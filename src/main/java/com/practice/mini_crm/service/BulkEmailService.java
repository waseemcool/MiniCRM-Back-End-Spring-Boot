package com.practice.mini_crm.service;

import com.practice.mini_crm.dto.BulkEmailRequest;
import com.practice.mini_crm.entity.Category;
import com.practice.mini_crm.entity.Lead;
import com.practice.mini_crm.repository.CategoryRepo;
import com.practice.mini_crm.repository.LeadRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BulkEmailService {

    private final CategoryRepo categoryRepo;
    private final LeadRepo leadRepo;

    public BulkEmailService(CategoryRepo categoryRepo, LeadRepo leadRepo) {
        this.categoryRepo = categoryRepo;
        this.leadRepo = leadRepo;
    }

    public Map<String, Object> sendBulkEmail(BulkEmailRequest request) {
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + request.getCategoryId()));

        List<Lead> leads = leadRepo.findByCategoryId(request.getCategoryId());

        List<String> recipients = leads.stream().map(Lead::getEmailAddress).toList();

        // Start - Code for Sending Bulk E-mail with E-mail Service (MailTrap, SendGrid, etc.)
        // Code
        // End - Code for Sending Bulk E-mail with E-mail Service (MailTrap, SendGrid, etc.)

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("category", category.getCategoryName());
        response.put("recipients", recipients);
        response.put("recipientsCount", recipients.size());
        response.put("message", "Emails Sent successfully to " + recipients.size() + " recipients");

        return response;
    }
}