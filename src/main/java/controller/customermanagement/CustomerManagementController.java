package controller.customermanagement;

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

    @Override
    public int updataCustomer(Customer customer) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET CustTitle=?, CustName=?, dob=?, salary=?, CustAddress=?, city=?, province=?, postalCode=? WHERE custID=?");
//            System.out.println(customer);
            preparedStatement.setString(1, customer.getTitle());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(customer.getDob()));
            preparedStatement.setDouble(4, customer.getSalary());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setString(7, customer.getProvince());
            preparedStatement.setString(8, customer.getPostalCode());
            preparedStatement.setString(9, customer.getCustID());

            int rows = preparedStatement.executeUpdate();
            return rows;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
