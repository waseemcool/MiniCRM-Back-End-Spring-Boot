package com.practice.mini_crm.controller;

import com.practice.mini_crm.dto.BulkEmailRequest;
import com.practice.mini_crm.service.BulkEmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bulk-email")
public class BulkEmailController {

    private final BulkEmailService bulkEmailService;

    public BulkEmailController(BulkEmailService bulkEmailService) {
        this.bulkEmailService = bulkEmailService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> sendBulkEmail(@Valid @RequestBody BulkEmailRequest request) {
        return ResponseEntity.ok(bulkEmailService.sendBulkEmail(request));
    }
}