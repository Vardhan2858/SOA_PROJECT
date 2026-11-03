
package com.insurance.policy_service.controller;

import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.repository.PolicyRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyRepository policyRepository;

    public PolicyController(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // CREATE
    @PostMapping
    public Policy createPolicy(@RequestBody Policy policy) {
        return policyRepository.save(policy);
    }

    // READ ALL
    @GetMapping
    public List<Policy> getAllPolicies(
            HttpServletRequest request) {

        System.out.println(
                "Request handled by port: "
                + request.getLocalPort());

        return policyRepository.findAll();
    }

    // READ ONE
    @GetMapping("/{policyNumber}")
    public Policy getPolicyByNumber(
            @PathVariable String policyNumber) {

        return policyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() ->
                        new RuntimeException("Policy not found"));
    }

    // UPDATE
    @PutMapping("/{policyNumber}")
    public Policy updatePolicy(
            @PathVariable String policyNumber,
            @RequestBody Policy updatedPolicy) {

        Policy policy = policyRepository
                .findByPolicyNumber(policyNumber)
                .orElseThrow(() ->
                        new RuntimeException("Policy not found"));

        policy.setPolicyHolderName(
                updatedPolicy.getPolicyHolderName());
        policy.setPolicyType(updatedPolicy.getPolicyType());
        policy.setCoverageLimit(updatedPolicy.getCoverageLimit());
        policy.setActive(updatedPolicy.isActive());

        return policyRepository.save(policy);
    }

    // DELETE
    @DeleteMapping("/{policyNumber}")
    public String deletePolicy(
            @PathVariable String policyNumber) {

        Policy policy = policyRepository
                .findByPolicyNumber(policyNumber)
                .orElseThrow(() ->
                        new RuntimeException("Policy not found"));

        policyRepository.delete(policy);

        return "Policy deleted successfully";
    }
}
