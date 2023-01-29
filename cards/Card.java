package com.company.cards;

import java.util.Date;

public class Card {
    private long userUniqueId;
    private String cardNumber;
    private Date expirationDate;
    private double amount;

    public Card(long userUniqueId, String cardNumber, Date expirationDate, double amount) {
        this.userUniqueId = userUniqueId;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }

    public long getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(long userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isExpired(){
        Date currentDateAndTime = new Date(System.currentTimeMillis());
        return currentDateAndTime.compareTo(expirationDate) > 0;
    }
}
