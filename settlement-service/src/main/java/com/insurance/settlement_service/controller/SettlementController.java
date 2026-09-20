package com.insurance.settlement_service.controller;

import com.insurance.settlement_service.entity.Settlement;
import com.insurance.settlement_service.repository.SettlementRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/settlements")
public class SettlementController {

    private final SettlementRepository settlementRepository;

    public SettlementController(
            SettlementRepository settlementRepository) {

        this.settlementRepository = settlementRepository;
    }

    // Create settlement
    @PostMapping
    public Settlement createSettlement(
            @RequestBody Settlement settlement) {

        // Generate settlement number
        settlement.setSettlementNumber(
                "SET" + System.currentTimeMillis()
        );

        // Set settlement status
        settlement.setStatus("CREATED");

        return settlementRepository.save(settlement);
    }

    // Get all settlements
    @GetMapping
    public List<Settlement> getAllSettlements() {

        return settlementRepository.findAll();
    }

    // Get settlement by settlement number
    @GetMapping("/{settlementNumber}")
    public Optional<Settlement> getSettlementByNumber(
            @PathVariable String settlementNumber) {

        return settlementRepository
                .findBySettlementNumber(settlementNumber);
    }

    // Get settlement by claim number
    @GetMapping("/claim/{claimNumber}")
    public Optional<Settlement> getSettlementByClaimNumber(
            @PathVariable String claimNumber) {

        return settlementRepository
                .findByClaimNumber(claimNumber);
    }
}