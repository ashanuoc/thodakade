package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeFormController {

    public Stage itemStage = new Stage();
    public Stage customerStage = new Stage();
    @FXML
    private Button btnCustomerManagement;

    @FXML
    private Button btnItemManagement;

    @FXML
    void btnCustomerManagementOnAction(ActionEvent event) {
        try {
            customerStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerManagementForm.fxml"))));
            customerStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnItemManagementOnAction(ActionEvent event) {
        try {
            itemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemManagementForm.fxml"))));
            itemStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
