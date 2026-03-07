package com.practice.mini_crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeadRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact Number is required")
    private String contactNumber;

    @NotBlank(message = "E-mail Address is required")
    private String emailAddress;

    private String requirementNotes;

    @NotNull(message = "Interest Level is required")
    private Long interestLevelId;

    @NotNull(message = "Category is required")
    private Long categoryId;

}