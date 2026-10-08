package com.fraud.model;

public class Transaction {

    private int transactionId;
    private int userId;
    private double amount;
    private String location;
    private String transactionType;
    private boolean international;
    private String device;

    public Transaction() {
    }

    public Transaction(
            double amount,
            String location,
            String transactionType,
            boolean international,
            String device) {

        this.amount = amount;
        this.location = location;
        this.transactionType = transactionType;
        this.international = international;
        this.device = device;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isInternational() {
        return international;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
