package com.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NorthwindDataManager {

    private DataSource dataSource;

    static List<Shipper> shipperList = new ArrayList<>();

    public NorthwindDataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void addShipper(String name, String phone) throws SQLException {
        String insert = "INSERT INTO shippers (CompanyName, Phone) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);

            // Execute the insert
            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows updated: %d\n", rows);

            // Retrieve the auto-generated keys
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int shipperID = generatedKeys.getInt(1);
                    System.out.println("Generated ShipperID: " + shipperID);
                } else {
                    System.out.println("Failed to retrieve the generated ShipperID.");
                }
            }
        }
    }

    public void editPhone(int shipperId, String phone) throws SQLException {

        String update = "UPDATE shippers " +
                "SET Phone = ? " +
                "WHERE ShipperID = ? ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {

            preparedStatement.setString(1, phone);
            preparedStatement.setInt(2, shipperId);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows updated %d\n", rows);

            // If the update was successful, fetch and display the updated phone number
            if (rows > 0) {
                String selectQuery = "SELECT Phone FROM shippers WHERE ShipperID = ?";
                try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                    selectStatement.setInt(1, shipperId);
                    ResultSet resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        String updatedPhoneNumber = resultSet.getString("Phone");
                        System.out.println("Updated Phone Number: " + updatedPhoneNumber);
                    }
                }
            }

        }
    }

    public void deleteShipper(int shipperId) throws SQLException {

        String delete = "DELETE FROM shippers WHERE ShipperID = ?";

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(delete)){

            preparedStatement.setInt(1, shipperId);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows deleted %d\n", rows);
        }

    }




    public List<Shipper> getAllShippers() throws SQLException {


        String sql = "SELECT * FROM shippers";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("ShipperID");
                String CompanyName = resultSet.getString("CompanyName");
                String phone = resultSet.getString("Phone");

                Shipper shipper = new Shipper(id,CompanyName,phone);

                shipperList.add(shipper);

            }

        }

        return shipperList;


    }

}
