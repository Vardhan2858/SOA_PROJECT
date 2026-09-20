package com.insurance.claim_service.dto;

public class SettlementResponse {

    private Long id;
    private String settlementNumber;
    private String claimNumber;
    private String policyNumber;
    private double settlementAmount;
    private String status;

    public SettlementResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettlementNumber() {
        return settlementNumber;
    }

    public void setSettlementNumber(String settlementNumber) {
        this.settlementNumber = settlementNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}