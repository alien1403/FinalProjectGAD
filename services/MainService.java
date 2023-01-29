package com.company.services;

import com.company.user.Admin;
import com.company.user.Customer;
import com.company.user.User;

import java.io.FileNotFoundException;
import java.text.ParseException;

import java.util.*;

public class MainService {

    private static final List<String> loginCommands = Arrays.asList("1. Create Account", "2. Login", "3. Exit");
    private static final List<String> customerCommands = Arrays.asList("1. View account details", "2. Deposit cash", "3. Withdraw (in maintenance)", "4. New Card", "5. New Deposit (in maintenance)", "6. Logout", "7. Exit");
    private static final List<String> adminCommands = Arrays.asList( "1. View customer details", "2. Delete account (in maintenance)", "3. Delete Card (in maintenance)", "4. Print expired cards", "5. Logout", "6. Exit");

    private static MainService instance = null;
    UserService userService = UserService.getInstance();
    CardService cardService = CardService.getInstance();
    AuditService auditService = AuditService.getInstance();

    public static MainService getInstance(){
        if(instance == null){
            instance = new MainService();
        }
        return instance;
    }

    public void readFromCsv() throws ParseException {
        userService.readUsersFromCsv();
        cardService.readCardsFromCsv();

        // after reading go to log in menu
        loginMenu();
    }


    public void login() throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        // call the appropriate menu by user type after logged in

        System.out.println("Enter email address: ");
        String email = in.nextLine();
        System.out.println("Enter password: ");
        String password = in.nextLine();

        User loggedUser = userService.getUserByEmailAndPassword(email, password);

        while(loggedUser == null)
        {
            System.out.println("Email/Password not found. Try again.");
            System.out.println("Enter email address: ");
            email = in.nextLine();
            System.out.println("Enter password: ");
            password = in.nextLine();
            loggedUser = userService.getUserByEmailAndPassword(email, password);
        }
        auditService.writeActionInCsv("user logged in");
        if(Objects.equals(loggedUser.getTypeOfUser(), "customer"))
            customerMenu((Customer) loggedUser);
        else adminMenu((Admin) loggedUser);
    }

    public void depositCash(double amount, Customer loggedCustomer) throws FileNotFoundException {
        cardService.depositCash(amount, loggedCustomer);
        auditService.writeActionInCsv("deposit cash");
        // go back to customer menu
        customerMenu(loggedCustomer);
    }

    public void withdraw(double amount, Customer loggedCustomer) throws FileNotFoundException {
        auditService.writeActionInCsv("withdraw cash");
    }

    public void createCard(Customer customer) throws ParseException, FileNotFoundException {
        // choose what type of card to create:
        Scanner in = new Scanner(System.in);

        System.out.println("Type of card (1 - standard, 2 - premium): ");
        String command = in.nextLine();
        while(!Objects.equals(command, "1") || !Objects.equals(command, "2"))
        {
            if (Objects.equals(command, "1"))
            {
                cardService.createStandardCard_(customer.getUniqueId());
                break;
            }
            else if (Objects.equals(command, "2"))
            {
                cardService.createPremiumCard_(customer.getUniqueId());
                break;
            }
            else
            {
                System.out.println("Command unknown. Please try again");
                System.out.println("Type of card (1 - standard, 2 - premium): ");
                command = in.nextLine();
            }
        }
        auditService.writeActionInCsv("created card");
        // after the card creation, return to the customer menu
        customerMenu(customer);
    }

    public void deleteCard(){

    }

    public void createDeposit(){

    }

    public void deleteAccount(){

    }

    public void printExpiredCards(Admin loggedAdmin) throws FileNotFoundException {
        cardService.printExpiredCards();
        auditService.writeActionInCsv("print expired cards");
        adminMenu(loggedAdmin);
    }
    
    public void createAccount() throws ParseException, FileNotFoundException {
        // choose what type of accout to create:
        Scanner in = new Scanner(System.in);

        System.out.println("Type of account (1 - customer, 2 - admin): ");
        String command = in.nextLine();
        while(!Objects.equals(command, "1") || !Objects.equals(command, "2"))
        {
            if (Objects.equals(command, "1"))
            {
                userService.createCustomerAccount();
                break;
            }
            else if (Objects.equals(command, "2"))
            {
                userService.createAdminAccount();
                break;
            }
            else
            {
                System.out.println("Command unknown. Please try again");
                System.out.println("Type of account (1 - customer, 2 - admin): ");
                command = in.nextLine();
            }
        }
        auditService.writeActionInCsv("created account");
        // after the account creation, return to the login panel
        loginMenu();
    }

    public void viewCustomerDetails(Customer customer) throws FileNotFoundException {
        System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Identified by CNP: " + customer.getCnp());
        System.out.println("Email address: " + customer.getEmail());
        System.out.println("Phone number: " + customer.getPhoneNumber());

        cardService.viewCardDetails(customer);
        auditService.writeActionInCsv("customer show details");
        customerMenu(customer);
    }

    public void customerMenu(Customer loggedCustomer) {


        System.out.println("\nWelcome, " + loggedCustomer.getFirstName() + "!\n");

        for (String customerCommand : customerCommands) {
            System.out.println(customerCommand);
        }

        while(true)
        {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            try
            {
                switch (command)
                {
                    case "1" -> viewCustomerDetails(loggedCustomer);
                    case "2" -> {
                        System.out.println("Enter the amount to deposit: ");
                        Scanner in_amount = new Scanner(System.in);
                        double amount = Double.parseDouble(in_amount.nextLine());
                        depositCash(amount, loggedCustomer);
                    }
                    case "3" -> {
                        System.out.println("Enter the amount to withdraw: ");
                        Scanner in_amount = new Scanner(System.in);
                        double amount = Double.parseDouble(in_amount.nextLine());
                        withdraw(amount, loggedCustomer);
                    }
                    case "4" -> createCard(loggedCustomer);
                    case "5" -> createDeposit();
                    case "6" -> loginMenu();
                    case "7" -> System.exit(0);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }

        }
    }

    public void adminMenu(Admin loggedAdmin)
    {
        System.out.println("\nWelcome, " + loggedAdmin.getFirstName() + "!\n");
        for (String adminCommand : adminCommands) {
            System.out.println(adminCommand);
        }

        while(true)
        {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            try
            {
                switch (command)
                {
                    case "1" -> {
                        Customer customer = (Customer) userService.getUsers().get(0); // TEMPORAR
                        viewCustomerDetails(customer);
                    }
                    case "2" -> deleteAccount();
                    case "3" -> deleteCard();
                    case "4" -> printExpiredCards(loggedAdmin);
                    case "5" -> loginMenu();
                    case "6" -> System.exit(0);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }

        }


    }

    public void loginMenu()
    {

        boolean exit = false;

        System.out.println("\n~Genesis Bank~\n");

        for (String loginCommand : loginCommands)
        {
            System.out.println(loginCommand);
        }

        while(!exit)
        {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            try
            {
                switch (command)
                {
                    case "1" -> {
                        exit = true;
                        createAccount();
                    }
                    case "2" -> {
                        exit = true;
                        login();
                    }
                    case "3" -> System.exit(0);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }

        }
    }
}
