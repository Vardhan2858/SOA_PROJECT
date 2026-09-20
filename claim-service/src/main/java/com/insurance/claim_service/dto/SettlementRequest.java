package com.insurance.claim_service.dto;

public class SettlementRequest {

    private String claimNumber;
    private String policyNumber;
    private double settlementAmount;

    public SettlementRequest() {
    }

    public SettlementRequest(
            String claimNumber,
            String policyNumber,
            double settlementAmount) {

        this.claimNumber = claimNumber;
        this.policyNumber = policyNumber;
        this.settlementAmount = settlementAmount;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }
}