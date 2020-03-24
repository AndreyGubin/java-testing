package com.terrazor.addressbook;

public class ContactInfo {
    private final String firstName;
    private final String secondName;
    private final String address;

    public ContactInfo(String firstName, String secondName, String address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getAddress() {
        return address;
    }
}
