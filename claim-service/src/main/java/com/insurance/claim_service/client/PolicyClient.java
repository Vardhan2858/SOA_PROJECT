package com.insurance.claim_service.client;

import com.insurance.claim_service.dto.PolicyResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PolicyClient {

    private final RestClient restClient;

    public PolicyClient(@LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://policy-service")
                .build();
    }

    public PolicyResponse getPolicy(String policyNumber) {

        return restClient.get()
                .uri("/policies/{policyNumber}", policyNumber)
                .retrieve()
                .body(PolicyResponse.class);
    }
}