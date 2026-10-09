package com.fraud.service;

import com.fraud.exception.InvalidTransactionException;
import com.fraud.model.Transaction;

public class FraudDetectionEngine implements FraudDetector {

    @Override
    public double calculateRiskScore(Transaction transaction) {

        double score = 0;

        // Check transaction amount
        if (transaction.getAmount() >= 50000) {
            score += 35;
        } else if (transaction.getAmount() >= 20000) {
            score += 20;
        }

        // Check international transaction
        if (transaction.isInternational()) {
            score += 25;
        }

        // Check transaction type
        if ("Online".equalsIgnoreCase(
                transaction.getTransactionType())) {
            score += 10;
        }

        // Check location
        if ("Unknown".equalsIgnoreCase(
                transaction.getLocation())) {
            score += 20;
        }

        // Check device
        if ("Unknown".equalsIgnoreCase(
                transaction.getDevice())) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    @Override
    public String getRiskLevel(double score) {

        if (score >= 70) {
            return "HIGH";
        }

        if (score >= 40) {
            return "MEDIUM";
        }

        return "LOW";
    }

    @Override
    public String getReason(Transaction transaction) {

        StringBuilder reason = new StringBuilder();

        if (transaction.getAmount() >= 50000) {
            reason.append("High transaction amount. ");
        }

        if (transaction.isInternational()) {
            reason.append("International transaction. ");
        }

        if ("Online".equalsIgnoreCase(
                transaction.getTransactionType())) {
            reason.append("Online transaction. ");
        }

        if ("Unknown".equalsIgnoreCase(
                transaction.getLocation())) {
            reason.append("Unknown location. ");
        }

        if ("Unknown".equalsIgnoreCase(
                transaction.getDevice())) {
            reason.append("Unknown device. ");
        }

        if (reason.length() == 0) {
            return "No major suspicious indicators detected.";
        }

        return reason.toString();
    }

    public void validateTransaction(
            Transaction transaction)
            throws InvalidTransactionException {

        if (transaction.getAmount() <= 0) {
            throw new InvalidTransactionException(
                    "Transaction amount must be greater than zero."
            );
        }

        if (transaction.getLocation() == null ||
                transaction.getLocation().isBlank()) {

            throw new InvalidTransactionException(
                    "Location cannot be empty."
            );
        }

        if (transaction.getTransactionType() == null ||
                transaction.getTransactionType().isBlank()) {

            throw new InvalidTransactionException(
                    "Transaction type cannot be empty."
            );
        }
    }
}
