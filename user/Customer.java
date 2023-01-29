package com.company.user;

public class Customer extends User{

    private String cnp;
    private String phoneNumber;


    public Customer(long uniqueId, String firstName, String lastName, String email, String password, String cnp, String phoneNumber) {
        super(uniqueId, "customer", firstName, lastName, email, password);
        this.cnp = cnp;
        this.phoneNumber = phoneNumber;
    }

    public String getCnp() {
        return cnp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
