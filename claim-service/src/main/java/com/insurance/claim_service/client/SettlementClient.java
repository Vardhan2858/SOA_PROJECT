package com.insurance.claim_service.client;

import com.insurance.claim_service.dto.SettlementResponse;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SettlementClient {

    private final RestClient restClient;

    public SettlementClient(
            @LoadBalanced RestClient.Builder restClientBuilder) {

        this.restClient = restClientBuilder
                .baseUrl("http://settlement-service")
                .build();
    }

    public SettlementResponse createSettlement(
            String claimNumber,
            String policyNumber,
            double amount) {

        String jsonBody = """
                {
                    "claimNumber": "%s",
                    "policyNumber": "%s",
                    "settlementAmount": %s
                }
                """.formatted(
                    claimNumber,
                    policyNumber,
                    amount
                );

        return restClient.post()
                .uri("/settlements")
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBody)
                .retrieve()
                .body(SettlementResponse.class);
    }

    public SettlementResponse getSettlementByClaimNumber(
            String claimNumber) {

        return restClient.get()
                .uri("/settlements/claim/{claimNumber}", claimNumber)
                .retrieve()
                .body(SettlementResponse.class);
    }
}