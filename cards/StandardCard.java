package com.company.cards;

import java.util.Date;

public class StandardCard extends Card{
    private double withdrawFee;

    public StandardCard(long userUniqueId, String cardNumber, Date expirationDate, double amount, double withdrawFee) {
        super(userUniqueId, cardNumber, expirationDate, amount);
        this.withdrawFee = withdrawFee;
    }

    public double getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(double withdrawFee) {
        this.withdrawFee = withdrawFee;
    }
}
