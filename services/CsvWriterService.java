package com.company.services;

import com.company.cards.PremiumCard;
import com.company.cards.StandardCard;
import com.company.user.Admin;
import com.company.user.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvWriterService {

    private static CsvWriterService instance;
    public static CsvWriterService getInstance(){
        if(instance == null){
            instance = new CsvWriterService();
        }
        return instance;
    }

    // Customer
    public void writeCustomerInCsv(Customer customer) throws FileNotFoundException
    {
        File csvOutputFile = new File("src\\com\\company\\resources\\customers.csv");
        String formattedCustomer = convertToCsvFormat(customer);

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true))) {
            pw.append(formattedCustomer);
        }
    }

    private static String convertToCsvFormat(Customer customer) {

        return  customer.getFirstName() +
                "," +
                customer.getLastName() +
                "," +
                customer.getEmail() +
                "," +
                customer.getPassword() +
                "," +
                customer.getCnp() +
                "," +
                customer.getPassword() + "\n";
    }

    // Admin
    public void writeAdminInCsv(Admin admin) throws FileNotFoundException
    {
        File csvOutputFile = new File("src\\com\\company\\resources\\admins.csv");
        String formattedAdmin = convertToCsvFormat(admin);

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true))) {
            pw.append(formattedAdmin);
        }
    }

    private static String convertToCsvFormat(Admin admin) {

        return  admin.getFirstName() +
                "," +
                admin.getLastName() +
                "," +
                admin.getEmail() +
                "," +
                admin.getPassword() + "\n";
    }

    public void writeStandardCardInCsv(StandardCard standardCard) throws FileNotFoundException
    {
        File csvOutputFile = new File("src\\com\\company\\resources\\standardcards.csv");
        String formattedStandardCard = convertToCsvFormat(standardCard);

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true))) {
            pw.append(formattedStandardCard);
        }
    }

    private static String convertToCsvFormat(StandardCard standardCard) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(standardCard.getExpirationDate());
        return  standardCard.getUserUniqueId() +
                "," +
                standardCard.getCardNumber() +
                "," +
                strDate +
                "," +
                standardCard.getAmount() +
                "," +
                standardCard.getWithdrawFee() + "\n";
    }


    public void writePremiumCardInCsv(PremiumCard premiumCard) throws FileNotFoundException
    {
        File csvOutputFile = new File("src\\com\\company\\resources\\premiumcards.csv");
        String formattedPremiumCard = convertToCsvFormat(premiumCard);

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true))) {
            pw.append(formattedPremiumCard);
        }
    }

    private static String convertToCsvFormat(PremiumCard premiumCard) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(premiumCard.getExpirationDate());
        return  premiumCard.getUserUniqueId() +
                "," +
                premiumCard.getCardNumber() +
                "," +
                strDate +
                "," +
                premiumCard.getAmount() +
                "," +
                premiumCard.getCashBack() + "\n";
    }
}
