package controller;

import javafx.collections.ObservableList;
import model.dto.Customer;

public interface CustomerManagementService {
    public ObservableList<Customer> getAllCustomerDetails();

    public int deleteCustomer(Customer customer);
}
