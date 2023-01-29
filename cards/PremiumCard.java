package com.company.cards;

import java.util.Date;

public class PremiumCard extends Card {
    // you can gain cashback if you transfer
    private double cashBack;

    public PremiumCard(long userUniqueId, String cardNumber, Date expirationDate, double amount, double cashBack) {
        super(userUniqueId, cardNumber, expirationDate, amount);
        this.cashBack = cashBack;
    }

    public double getCashBack() {
        return cashBack;
    }

    public void setCashBack(double cashBack) {
        this.cashBack = cashBack;
    }
}
