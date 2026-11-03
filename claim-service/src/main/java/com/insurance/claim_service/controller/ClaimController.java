
package com.insurance.claim_service.controller;

import com.insurance.claim_service.client.PolicyClient;
import com.insurance.claim_service.client.SettlementClient;
import com.insurance.claim_service.dto.PolicyResponse;
import com.insurance.claim_service.dto.SettlementResponse;
import com.insurance.claim_service.entity.Claim;
import com.insurance.claim_service.repository.ClaimRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // CREATE CLAIM
    @PostMapping
    public Claim createClaim(@RequestBody Claim claim) {
        return claimRepository.save(claim);
    }

    // GET ALL CLAIMS
    @GetMapping
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    // GET CLAIM BY NUMBER
    @GetMapping("/{claimNumber}")
    public Claim getClaimByNumber(
            @PathVariable String claimNumber) {

        return claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() ->
                        new RuntimeException("Claim not found"));
    }

    // UPDATE CLAIM
    @PutMapping("/{claimNumber}")
    public Claim updateClaim(
            @PathVariable String claimNumber,
            @RequestBody Claim updatedClaim) {

        Claim claim = claimRepository
                .findByClaimNumber(claimNumber)
                .orElseThrow(() ->
                        new RuntimeException("Claim not found"));

        claim.setPolicyNumber(updatedClaim.getPolicyNumber());
        claim.setClaimantName(updatedClaim.getClaimantName());
        claim.setClaimAmount(updatedClaim.getClaimAmount());
        claim.setStatus(updatedClaim.getStatus());

        return claimRepository.save(claim);
    }

    // DELETE CLAIM
    @DeleteMapping("/{claimNumber}")
    public String deleteClaim(
            @PathVariable String claimNumber) {

        Claim claim = claimRepository
                .findByClaimNumber(claimNumber)
                .orElseThrow(() ->
                        new RuntimeException("Claim not found"));

        claimRepository.delete(claim);

        return "Claim deleted successfully";
    }

    // VERIFY CLAIM AND CREATE SETTLEMENT
    @PostMapping("/{claimNumber}/verify")
    public String verifyClaim(
            @PathVariable String claimNumber,
            HttpServletRequest request) {

        Claim claim = claimRepository
                .findByClaimNumber(claimNumber)
                .orElseThrow(() ->
                        new RuntimeException("Claim not found"));

        // Check whether settlement already exists
        SettlementResponse existingSettlement =
                settlementClient.getSettlementByClaimNumber(
                        claim.getClaimNumber());

        if (existingSettlement != null) {
            return "Claim already settled. Settlement Number: "
                    + existingSettlement.getSettlementNumber();
        }

        // Forward JWT token to Policy Service
        String authorizationHeader =
                request.getHeader("Authorization");

        // Get policy details
        PolicyResponse policy =
                policyClient.getPolicy(
                        claim.getPolicyNumber(),
                        authorizationHeader);

        // Check policy status
        if (!policy.isActive()) {

            claim.setStatus("REJECTED");
            claimRepository.save(claim);

            return "Claim REJECTED: Policy is not active";
        }

        // Check coverage limit
        if (claim.getClaimAmount() > policy.getCoverageLimit()) {

            claim.setStatus("REJECTED");
            claimRepository.save(claim);

            return "Claim REJECTED: Claim amount exceeds coverage limit";
        }

        // Create settlement
        SettlementResponse settlement =
                settlementClient.createSettlement(
                        claim.getClaimNumber(),
                        claim.getPolicyNumber(),
                        claim.getClaimAmount());

        // Update claim status
        claim.setStatus("APPROVED");
        claimRepository.save(claim);

        return "Claim APPROVED: Settlement created successfully. "
                + "Settlement Number: "
                + settlement.getSettlementNumber();
    }
}
