package com.practice.mini_crm.controller;

import com.practice.mini_crm.dto.ApiResponse;
import com.practice.mini_crm.dto.LeadRequest;
import com.practice.mini_crm.dto.LeadResponse;
import com.practice.mini_crm.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeadResponse>> saveLead(@Valid @RequestBody LeadRequest leadRequest) {
        ApiResponse<LeadResponse> response = leadService.saveLead(leadRequest);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LeadResponse>> getAllLeads() {
        return ResponseEntity.ok(leadService.getAllLeads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadResponse> getLeadById(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getLeadById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<LeadResponse>> getAllLeadsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(leadService.getAllLeadsByCategory(categoryId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeadResponse>> update(@PathVariable Long id, @Valid @RequestBody LeadRequest leadRequest) {
        ApiResponse<LeadResponse> response = leadService.update(id, leadRequest);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @DeleteMapping("/{id}")
    public String removeLead(@PathVariable Long id) {
        leadService.removeLead(id);
        return "Lead removed successfully";
    }

}