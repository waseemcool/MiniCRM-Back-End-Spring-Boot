package com.practice.mini_crm.repository;

import com.practice.mini_crm.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeadRepo extends JpaRepository<Lead, Long> {

    Optional<Lead> findByNameAndEmailAddressAndContactNumber(String name, String emailAddress, String contactNumber);

    Optional<Lead> findByEmailAddress(String emailAddress);

    Optional<Lead> findByContactNumber(String contactNumber);

    List<Lead> findByCategoryId(Long categoryId);

    @Query("SELECT l.category, COUNT(l) FROM Lead l GROUP BY l.category")
    List<Object[]> countLeadsByCategory();

    @Query("SELECT l.interestLevel, COUNT(l) FROM Lead l GROUP BY l.interestLevel")
    List<Object[]> countLeadsByInterestLevel();

    @Query("SELECT DATE(l.createdAt) as date, COUNT(l) FROM Lead l " +
            "WHERE l.createdAt >= :startDate GROUP BY date ORDER BY DATE(l.createdAt)")
    List<Object[]> getDailyLeadVolume(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT FUNCTION('YEAR', l.createdAt), FUNCTION('MONTH', l.createdAt), COUNT(l) " +
            "FROM Lead l WHERE l.createdAt >= :startDate " +
            "GROUP BY FUNCTION('YEAR', l.createdAt), FUNCTION('MONTH', l.createdAt)" +
            "ORDER BY FUNCTION('YEAR', l.createdAt), FUNCTION('MONTH', l.createdAt)")
    List<Object[]> getMonthlyLeadVolume(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT l.requirementNotes, COUNT(l) FROM Lead l " +
            "WHERE l.requirementNotes IS NOT NULL GROUP BY l.requirementNotes ORDER BY COUNT(l) DESC")
    List<Object[]> getTopRequirementNotes();
}