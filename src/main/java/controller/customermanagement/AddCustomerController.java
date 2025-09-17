package controller.customermanagement;

import db.DBConnection;
import model.dto.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCustomerController implements AddCustomerService {
    @Override
    public int addCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer " +
                    "(custID, CustTitle, CustName, dob, salary, CustAddress, city, province, postalCode) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)");

            preparedStatement.setObject(1, customer.getCustID());
            preparedStatement.setObject(2,  customer.getTitle());
            preparedStatement.setObject(3, customer.getName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setObject(5, customer.getSalary());
            preparedStatement.setObject(6, customer.getAddress());
            preparedStatement.setObject(7, customer.getCity());
            preparedStatement.setObject(8, customer.getProvince());
            preparedStatement.setObject(9, customer.getPostalCode());

            int rows = preparedStatement.executeUpdate();
            return rows;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
