package com.practice.mini_crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkEmailRequest {

    @NotNull(message = "Category Id is required")
    private Long categoryId;

    @NotBlank(message = "Email Subject is required")
    private String emailSubject;

    @NotBlank(message = "Email Message is required")
    private String emailMessage;

}