package controller.itemmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

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

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?,?)");

            preparedStatement.setObject(1, itemCode);
            preparedStatement.setObject(2, txtAreaDescription.getText());
            preparedStatement.setObject(3, txtPackSize.getText());
            preparedStatement.setObject(4, Double.parseDouble(txtUnitPrice.getText()));
            preparedStatement.setObject(5, Integer.parseInt(txtQtyOnHand.getText()));

            int rows = preparedStatement.executeUpdate();
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
