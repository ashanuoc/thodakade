package controller.customermanagement;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.dto.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManagementFormController implements Initializable {

    CustomerManagementService customerManagementService = new CustomerManagementController();

    @FXML
    private JFXButton btinView;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, String> colCity;

    @FXML
    private TableColumn<Customer, String> colCustID;

    @FXML
    private TableColumn<Customer, LocalDate> colDob;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colPostalCode;

    @FXML
    private TableColumn<Customer, String> colProvince;

    @FXML
    private TableColumn<Customer, Double> colSalary;

    @FXML
    private TableColumn<Customer, String> colTitle;

    @FXML
    private TableView<Customer> tblCustomerManagement;

    private Stage addCustStage = new Stage();

    private void loadCustomers(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customers = customerManagementService.getAllCustomerDetails();
        tblCustomerManagement.setItems(customers);
    }
    @FXML
    void btinViewOnAction(ActionEvent event) {

        loadCustomers();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            addCustStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"))));
            addCustStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Customer selected = tblCustomerManagement.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a row to update.").showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the record.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            int rows = customerManagementService.deleteCustomer(selected);
            if (rows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Record Deleted.").showAndWait();
                loadCustomers(); // optional
            } else {
                new Alert(Alert.AlertType.WARNING, "Nothing Deleted.").showAndWait();
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer selected = tblCustomerManagement.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a row to update.").showAndWait();
            return;
        }
            int rows = customerManagementService.updataCustomer(selected);
            if (rows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Saved to database.").showAndWait();
//                tblCustomerManagement.refresh(); // optional
                loadCustomers();
            } else {
                new Alert(Alert.AlertType.WARNING, "Nothing updated.").showAndWait();
            }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblCustomerManagement.setEditable(true);

        tblCustomerManagement.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(ev -> {
                if (!row.isEmpty()) {
                    tv.getSelectionModel().select(row.getIndex());
                }
            });
            return row;
        });

        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        colCustID.setEditable(false);
        colDob.setEditable(false);

        colTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        colCity.setCellFactory(TextFieldTableCell.forTableColumn());
        colProvince.setCellFactory(TextFieldTableCell.forTableColumn());
        colPostalCode.setCellFactory(TextFieldTableCell.forTableColumn());
        colSalary.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));


        colTitle.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setTitle(e.getNewValue());
        });

        colName.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setName(e.getNewValue());
        });

        colAddress.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setAddress(e.getNewValue());
        });

        colCity.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setCity(e.getNewValue());
        });

        colProvince.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setProvince(e.getNewValue());
        });

        colPostalCode.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setPostalCode(e.getNewValue());
        });

        colSalary.setOnEditCommit(e -> {
            Customer c = e.getRowValue();
            c.setSalary(e.getNewValue());
        });

    }
}
