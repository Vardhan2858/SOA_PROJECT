package com.insurance.policy_service.controller;

import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.repository.PolicyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    private final PolicyRepository policyRepository;

    public PolicyController(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // Create a new policy
    @PostMapping
    public Policy createPolicy(@RequestBody Policy policy) {
        return policyRepository.save(policy);
    }

    // Get all policies
    @GetMapping
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    // Get policy by policy number
    @GetMapping("/{policyNumber}")
    public Optional<Policy> getPolicyByNumber(@PathVariable String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber);
    }
}