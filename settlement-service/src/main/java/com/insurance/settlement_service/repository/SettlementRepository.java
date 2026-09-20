package com.insurance.settlement_service.repository;

import com.insurance.settlement_service.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    Optional<Settlement> findBySettlementNumber(String settlementNumber);

    Optional<Settlement> findByClaimNumber(String claimNumber);
}