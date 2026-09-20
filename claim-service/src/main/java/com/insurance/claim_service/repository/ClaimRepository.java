package com.insurance.claim_service.repository;

import com.insurance.claim_service.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNumber(String claimNumber);
}