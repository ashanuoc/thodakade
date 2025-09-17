package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManagementController implements CustomerManagementService{
    @Override
    public ObservableList<Customer> getAllCustomerDetails() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString("custID"),
                        resultSet.getString("Custtitle"),
                        resultSet.getString("Custname"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("Custaddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")

                );

                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer  WHERE custID=?");

            preparedStatement.setString(1, customer.getCustID());

            int rows = preparedStatement.executeUpdate();
            return rows;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
