package com.fraud.ml;

public class FraudPrediction {

    private final double fraudProbability;
    private final String prediction;
    private final String riskLevel;

    public FraudPrediction(
            double fraudProbability,
            String prediction,
            String riskLevel) {

        this.fraudProbability = fraudProbability;
        this.prediction = prediction;
        this.riskLevel = riskLevel;
    }

    public double getFraudProbability() {
        return fraudProbability;
    }

    public String getPrediction() {
        return prediction;
    }

    public String getRiskLevel() {
        return riskLevel;
    }
}
