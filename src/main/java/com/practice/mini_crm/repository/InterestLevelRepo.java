package com.practice.mini_crm.repository;

import com.practice.mini_crm.entity.InterestLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestLevelRepo extends JpaRepository<InterestLevel, Long> {



}