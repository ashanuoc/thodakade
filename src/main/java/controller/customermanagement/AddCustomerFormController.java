package controller.customermanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dto.Customer;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {
    public AddCustomerService addCustomerService = new AddCustomerController();
    private String  custID;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Label lblCustID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TextField txtProvince;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTitle;



    @FXML
    void btnAddItemOnAction(ActionEvent event) {

            Customer customer = new Customer(custID,
                    txtTitle.getText(),
                    txtName.getText(),
                    LocalDate.parse(txtDob.getText()),
                    Double.parseDouble(txtSalary.getText()),
                    txtAddress.getText(),
                    txtCity.getText(),
                    txtProvince.getText(),
                    txtPostalCode.getText()
            );


            int rows = addCustomerService.addCustomer(customer);
            if (rows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item added successfully").showAndWait();
                custID = "C"+ String.format("%03d", Integer.parseInt(custID.substring(1)) + 1);
                lblCustID.setText(custID);
                txtTitle.clear();
                txtName.clear();
                txtDob.clear();
                txtSalary.clear();
                txtCity.clear();
                txtProvince.clear();
                txtPostalCode.clear();

            } else {
                new Alert(Alert.AlertType.WARNING, "Couldn’t add the item.").showAndWait();
            }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT custID FROM customer ORDER BY custID DESC LIMIT 1 ");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                custID = "C"+ String.format("%03d", Integer.parseInt(resultSet.getString("custID").substring(1)) + 1);
                lblCustID.setText(custID);

            }else {
                custID = "C001";
                lblCustID.setText(custID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
