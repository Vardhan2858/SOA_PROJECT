package com.insurance.claim_service.controller;

import com.insurance.claim_service.client.PolicyClient;
import com.insurance.claim_service.client.SettlementClient;
import com.insurance.claim_service.dto.PolicyResponse;
import com.insurance.claim_service.dto.SettlementResponse;
import com.insurance.claim_service.entity.Claim;
import com.insurance.claim_service.repository.ClaimRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimRepository claimRepository;
    private final PolicyClient policyClient;
    private final SettlementClient settlementClient;

    public ClaimController(
            ClaimRepository claimRepository,
            PolicyClient policyClient,
            SettlementClient settlementClient) {

        this.claimRepository = claimRepository;
        this.policyClient = policyClient;
        this.settlementClient = settlementClient;
    }

    @PostMapping
    public Claim createClaim(@RequestBody Claim claim) {
        return claimRepository.save(claim);
    }

    @GetMapping
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @GetMapping("/{claimNumber}")
    public Optional<Claim> getClaimByNumber(
            @PathVariable String claimNumber) {

        return claimRepository.findByClaimNumber(claimNumber);
    }

    @GetMapping("/{claimNumber}/verify")
    public String verifyClaim(
            @PathVariable String claimNumber) {

        Optional<Claim> claimOptional =
                claimRepository.findByClaimNumber(claimNumber);

        if (claimOptional.isEmpty()) {
            return "Claim not found";
        }

        Claim claim = claimOptional.get();

        // Check whether a settlement already exists
        SettlementResponse existingSettlement =
                settlementClient.getSettlementByClaimNumber(
                        claim.getClaimNumber()
                );

        if (existingSettlement != null) {
            return "Claim already settled. Settlement Number: "
                    + existingSettlement.getSettlementNumber();
        }

        // Get policy details
        PolicyResponse policy =
                policyClient.getPolicy(claim.getPolicyNumber());

        if (!policy.isActive()) {

            claim.setStatus("REJECTED");
            claimRepository.save(claim);

            return "Claim REJECTED: Policy is not active";
        }

        // Check coverage limit
        if (claim.getClaimAmount() >
                policy.getCoverageLimit()) {

            claim.setStatus("REJECTED");
            claimRepository.save(claim);

            return "Claim REJECTED: Claim amount exceeds coverage limit";
        }

        // Create settlement
        SettlementResponse settlement =
                settlementClient.createSettlement(
                        claim.getClaimNumber(),
                        claim.getPolicyNumber(),
                        claim.getClaimAmount()
                );

        // Update claim status
        claim.setStatus("APPROVED");
        claimRepository.save(claim);

        return "Claim APPROVED: Settlement created successfully. "
                + "Settlement Number: "
                + settlement.getSettlementNumber();
    }
}