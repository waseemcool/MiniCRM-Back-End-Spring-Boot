package com.practice.mini_crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest_levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest_level_name", nullable = false)
    private String interestLevelName;

    @Column(name = "display_name", nullable = false)
    private String displayName;

}