package com.pluralsight;

public class Shipper {

    protected int shipperId;

    protected String companyName;

    protected String phoneNumber;

    public Shipper(int shipperId, String companyName, String phoneNumber) {
        this.shipperId = shipperId;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Shipper ID: " + shipperId + "\n----------\nCompany Name: " + companyName + "\nPhone Number: " + phoneNumber;
    }

}
