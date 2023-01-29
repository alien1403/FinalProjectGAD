package com.company.services;

import com.company.cards.Card;
import com.company.cards.PremiumCard;
import com.company.cards.StandardCard;
import com.company.user.Admin;
import com.company.user.Customer;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CardService {
    private static CardService instance;

    private final List<Card> cards = new ArrayList<>();

    CsvReaderService csvReaderService = CsvReaderService.getInstance();
    CsvWriterService csvWriterService = CsvWriterService.getInstance();

    public static CardService getInstance(){
        if(instance == null){
            instance = new CardService();
        }
        return instance;
    }


    public StandardCard createStandardCard(long userUniqueId, String cardNumber, Date expirationDate, double ammount, double withdrawFee) throws ParseException
    {
        return new StandardCard(userUniqueId, cardNumber, expirationDate, ammount, withdrawFee);
    }

    public PremiumCard createPremiumCard(long userUniqueId, String cardNumber, Date expirationDate, double ammount, double cashBack) throws ParseException
    {
        return new PremiumCard(userUniqueId, cardNumber, expirationDate, ammount, cashBack);
    }

    public void readCardsFromCsv() throws ParseException {
        List<String[]> standardCardList = csvReaderService.readStandardCardsFromCsv();
        for (String[] strings : standardCardList) {

            String sDate = strings[2];
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

            StandardCard newStandardCard = createStandardCard(Long.parseLong(strings[0]), strings[1], date, Double.parseDouble(strings[3]), Double.parseDouble(strings[4]));
            cards.add(newStandardCard);
        }

        List<String[]> premiumCardList = csvReaderService.readPremiumCardsFromCsv();
        for (String[] strings : premiumCardList) {

            String sDate = strings[2];
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

            StandardCard newStandardCard = createStandardCard(Long.parseLong(strings[0]), strings[1], date, Double.parseDouble(strings[3]), Double.parseDouble(strings[4]));
            cards.add(newStandardCard);
        }
    }


    public void depositCash(double amount, Customer loggedCustomer)
    {
        boolean hasCard = false;

        for (Card card : cards)
        {
            if (card.getUserUniqueId() == loggedCustomer.getUniqueId())
            {
                hasCard = true;
                break;
            }
        }
        if (!hasCard)
        {
            System.out.println("You don't have any cards in which to deposit money!\n");
            return;
        }
        int cardIndex = 1;
        System.out.println("Select the card in which to deposit:");
        for (Card card : cards)
        {
            if (card.getUserUniqueId() == loggedCustomer.getUniqueId())
            {
                System.out.println(cardIndex + ". Card number: " + card.getCardNumber());
                System.out.println("Current amount: " + card.getAmount() + "\n");
                cardIndex++;
            }
        }

        Scanner in = new Scanner(System.in);
        int command = Integer.parseInt(in.nextLine());
        while (command < 1 || command >= cardIndex)
        {
            System.out.println("Enter a valid number!");
            command = Integer.parseInt(in.nextLine());
        }

        cardIndex = 1;
        for (Card card : cards) {
            if (card.getUserUniqueId() == loggedCustomer.getUniqueId()) {
                if (cardIndex == command) {
                    // increment the amount
                    card.setAmount(card.getAmount() + amount);
                    break;
                } else cardIndex++;
            }
        }
    }


    public void createStandardCard_(long uniqueId) throws ParseException, FileNotFoundException {
        Random rand = new Random();
        int rand_month = rand.nextInt(1,13); // generate random month for exp date



        Scanner in = new Scanner(System.in);
        StringBuilder cardNumber = new StringBuilder();
        Date expirationDate = new Date(2025, rand_month, 1);

        double amount, withdrawFee;

        for(int i = 0; i < 16; i++)
        {
            int rand_number = rand.nextInt(9);
            cardNumber.append(rand_number);
        }

        System.out.println("Amount to deposit ($): ");
        amount = Double.parseDouble(in.nextLine());
        System.out.println("Withdrawal fee (%): ");
        withdrawFee = Double.parseDouble(in.nextLine());


        StandardCard newStandardCard = createStandardCard(uniqueId, String.valueOf(cardNumber), expirationDate, amount, withdrawFee);
        cards.add(newStandardCard);

        // add to csv
        csvWriterService.writeStandardCardInCsv(newStandardCard);
    }
    public void createPremiumCard_(long uniqueId) throws ParseException, FileNotFoundException {
        Random rand = new Random();
        int rand_month = rand.nextInt(1,13); // generate random month for exp date

        Scanner in = new Scanner(System.in);
        StringBuilder cardNumber = new StringBuilder();
        Date expirationDate = new Date(2025, rand_month, 1);

        double amount, cashBack;

        for(int i = 0; i < 16; i++)
        {
            int rand_number = rand.nextInt(9);
            cardNumber.append(rand_number);
        }

        System.out.println("Amount to deposit ($): ");
        amount = Double.parseDouble(in.nextLine());
        System.out.println("Cashback  (%): ");
        cashBack = Double.parseDouble(in.nextLine());

        PremiumCard newPremiumCard = createPremiumCard(uniqueId, String.valueOf(cardNumber), expirationDate, amount, cashBack);
        cards.add(newPremiumCard);

        // add to csv
        csvWriterService.writePremiumCardInCsv(newPremiumCard);
    }

    public void viewCardDetails(Customer customer)
    {
        for (Card card : cards) {
            if (card.getUserUniqueId() == customer.getUniqueId()) {
                System.out.println("\nCard details: ");
                System.out.println("Card number: " + card.getCardNumber());

                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
                String formattedDate = formatter.format(card.getExpirationDate());

                System.out.println("Exp. Date: " + formattedDate);
                System.out.println("Amount on card: " + card.getAmount());
            }
        }
    }

    public void printExpiredCards()
    {
        System.out.println("Expired cards:");
        cards.stream().filter(card -> card.isExpired()).forEach(card -> System.out.println("Card number: " + card.getCardNumber()));
    }


}
