package controller.itemmanagement;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.dto.Item;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    ItemManagementService itemManagementService = new ItemManagementController();

    @FXML
    private JFXButton btinView;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<Item, String> colDescription;

    @FXML
    private TableColumn<Item, String> colItemCode;

    @FXML
    private TableColumn<Item, String> colPackSize;

    @FXML
    private TableColumn<Item, Integer> colQtyOnHand;

    @FXML
    private TableColumn<Item, Double> colUnitPrice;


    @FXML
    private TableView<Item> tblItemManagement;

    private Stage addItemStage = new Stage();

    @FXML
    void btinViewOnAction(ActionEvent event) {
        loadItems();

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            addItemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddItem.fxml"))));
            addItemStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Item selected = tblItemManagement.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a row to update.").showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete the record.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            int rows = itemManagementService.deleteItem(selected);
            if (rows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Record Deleted.").showAndWait();
                loadItems();
            } else {
                new Alert(Alert.AlertType.WARNING, "Nothing Deleted.").showAndWait();
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item selected = tblItemManagement.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a row to update.").showAndWait();
        }

        int rows = itemManagementService.updateItem(selected);
        if (rows > 0) {
            new Alert(Alert.AlertType.INFORMATION, "Saved to database.").showAndWait();
            loadItems();
        } else {
            new Alert(Alert.AlertType.WARNING, "Nothing updated.").showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblItemManagement.setEditable(true);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        colDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        colPackSize.setCellFactory(TextFieldTableCell.forTableColumn());
        colUnitPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colQtyOnHand.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        colDescription.setOnEditCommit(itemStringCellEditEvent -> {
            Item item = itemStringCellEditEvent.getRowValue();
            item.setDescription(itemStringCellEditEvent.getNewValue());
        });

        colPackSize.setOnEditCommit(e -> {
            Item it = e.getRowValue();
            it.setPackSize(e.getNewValue());
        });

        colUnitPrice.setOnEditCommit(e -> {
            Item it = e.getRowValue();
            it.setUnitPrice(e.getNewValue());
        });

        colQtyOnHand.setOnEditCommit(e -> {
            Item it = e.getRowValue();
            it.setQtyOnHand(e.getNewValue());
        });


    }

    private void loadItems(){
        ObservableList<Item> items = itemManagementService.getAllItems();

        tblItemManagement.setItems(items);
    }
}
