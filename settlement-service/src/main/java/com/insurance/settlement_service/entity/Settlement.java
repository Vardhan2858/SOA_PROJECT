package com.insurance.settlement_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String settlementNumber;
    private String claimNumber;
    private String policyNumber;
    private double settlementAmount;
    private String status;

    public Settlement() {
    }

    public Long getId() {
        return id;
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