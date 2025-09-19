package controller.itemmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.dto.Item;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {
    ItemManagementService itemManagementService = new ItemManagementController();

    private String  itemCode;

    @FXML
    private Button btnAddItem;

    @FXML
    private Label lblItemCode;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtPackSize;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        Item item = new Item(itemCode,txtAreaDescription.getText(),
                txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));

        int rows = itemManagementService.addItem(item);
        if (rows > 0) {
            new Alert(Alert.AlertType.INFORMATION, "Item added successfully").showAndWait();
            itemCode = "P"+ String.format("%03d", Integer.parseInt(itemCode.substring(1)) + 1);
            lblItemCode.setText(itemCode);
            txtAreaDescription.clear();
            txtPackSize.clear();
            txtUnitPrice.clear();
            txtQtyOnHand.clear();

        } else {
            new Alert(Alert.AlertType.WARNING, "Couldn’t add the item.").showAndWait();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1 ");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                itemCode = "P"+ String.format("%03d", Integer.parseInt(resultSet.getString("itemCode").substring(1)) + 1);
                lblItemCode.setText(itemCode);

            }else {
                itemCode = "P001";
                lblItemCode.setText(itemCode);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
