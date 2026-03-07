package com.practice.mini_crm.controller;

import com.practice.mini_crm.entity.InterestLevel;
import com.practice.mini_crm.repository.InterestLevelRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/interest-levels")
public class InterestLevelController {

    private final InterestLevelRepo interestLevelRepo;

    public InterestLevelController(InterestLevelRepo interestLevelRepo) {
        this.interestLevelRepo = interestLevelRepo;
    }

    @GetMapping
    public ResponseEntity<List<InterestLevel>> getAllInterestLevels() {
        return ResponseEntity.ok(interestLevelRepo.findAll());
    }

}