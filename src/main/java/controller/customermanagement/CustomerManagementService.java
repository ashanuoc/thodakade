package controller.customermanagement;

import javafx.collections.ObservableList;
import model.dto.Customer;

public interface CustomerManagementService {
    public ObservableList<Customer> getAllCustomerDetails();

    public int deleteCustomer(Customer customer);

    public int updataCustomer(Customer customer);
}
