package com.pluralsight;

import java.sql.*;
import java.util.Scanner;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
public class mainApp {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        String username = "root";
        String password = System.getenv("MY_DB_PASSWORD");


        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);


        NorthwindDataManager dataManager = new NorthwindDataManager(dataSource);

        System.out.println("Welcome to Northwind Shippers!");


        System.out.println("What would you like to do?");
        System.out.println(" 1) add new shipper");
        System.out.println(" 2) update shipper phone number");
        System.out.println(" 3) remove/delete shipper");
        int selection = input.nextInt();

        switch(selection){
            case 1:
                PromptUserToAddShipper(dataManager);
            case 2:
                PromptUserToEditPhone(dataManager);
            case 3:
                PromptUserToDeleteShipper(dataManager);
            default:
                System.out.println("Not a valid selection.");
        }

    }

    public static void PromptUserToAddShipper(NorthwindDataManager dataManager) throws SQLException {


        System.out.println("Please enter a name and phone of a shipper you would like to add to our database: ");
        System.out.println("Enter shipper name: ");
        String name = input.nextLine();
        System.out.println("Name recorded.");
        System.out.println("Enter shipper phone (in the format (XXX) XXX-XXXX): ");
        String phone = input.nextLine();

        if (phone.matches("^\\(\\d{3}\\) \\d{3}-\\d{4}$")) {
            System.out.println("Valid phone number recorded: " + phone);
        } else {
            System.out.println("Invalid phone number. Please enter in the format (XXX) XXX-XXXX.");
        }
        dataManager.addShipper(name, phone);

    }

    public static void PromptUserToEditPhone(NorthwindDataManager dataManager) throws SQLException {
        System.out.println("You have opted to update a pre-existing phone number.");

        System.out.println("Please enter shipper ID: ");
        int shipperId = input.nextInt();

        input.nextLine();

        System.out.println("Please enter new phone: ");
        String phone = input.nextLine();

        dataManager.editPhone(shipperId,phone);

    }

    public static void PromptUserToDeleteShipper(NorthwindDataManager dataManager) throws SQLException {
        System.out.println("You have opted to delete a shipper.");
        System.out.println("Enter ID of shipper you would like to delete.");
        int shipperId = input.nextInt();

        dataManager.deleteShipper(shipperId);
    }



}
