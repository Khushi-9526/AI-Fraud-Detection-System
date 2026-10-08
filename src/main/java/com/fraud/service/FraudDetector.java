package com.fraud.service;

import com.fraud.model.Transaction;

public interface FraudDetector {

    double calculateRiskScore(Transaction transaction);

    String getRiskLevel(double score);

    String getReason(Transaction transaction);
}
