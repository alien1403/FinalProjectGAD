package com.company.user;

import com.company.services.UserService;

import java.util.List;
import java.util.Objects;

public class Admin extends User {

    UserService userService = UserService.getInstance();

    public Admin(long uniqueId, String firstName, String lastName, String email, String password) {
        super(uniqueId, "admin", firstName, lastName, email, password);
    }

    public void viewAllCustomers()
    {
        List<User> users = userService.getUsers();
        for (User user : users) {
            if (Objects.equals(user.getTypeOfUser(), "customer")) {
                System.out.println("First name: " + user.getFirstName());
                System.out.println("Last name: " + user.getLastName());
            }
        }
    }

}
