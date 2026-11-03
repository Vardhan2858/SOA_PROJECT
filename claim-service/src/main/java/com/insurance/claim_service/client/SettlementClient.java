
package com.insurance.claim_service.client;

import com.insurance.claim_service.dto.SettlementResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SettlementClient {

    private final RestClient restClient;
    private final HttpServletRequest request;

    public SettlementClient(
            @LoadBalanced RestClient.Builder restClientBuilder,
            HttpServletRequest request) {

        this.restClient = restClientBuilder
                .baseUrl("http://settlement-service")
                .build();

        this.request = request;
    }

    public SettlementResponse createSettlement(
            String claimNumber,
            String policyNumber,
            double amount) {

        String token = request.getHeader("Authorization");

        String jsonBody = """
                {
                    "claimNumber": "%s",
                    "policyNumber": "%s",
                    "settlementAmount": %s
                }
                """.formatted(claimNumber, policyNumber, amount);

        return restClient.post()
                .uri("/settlements")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBody)
                .retrieve()
                .body(SettlementResponse.class);
    }

    public SettlementResponse getSettlementByClaimNumber(
            String claimNumber) {

        String token = request.getHeader("Authorization");

        return restClient.get()
                .uri("/settlements/claim/{claimNumber}", claimNumber)
                .header("Authorization", token)
                .retrieve()
                .body(SettlementResponse.class);
    }
}
