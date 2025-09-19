package controller.itemmanagement;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Item;

import java.sql.*;

public class ItemManagementController implements ItemManagementService{

    @Override
    public ObservableList<Item> getAllItems() {
        try {
            ObservableList<Item> items = FXCollections.observableArrayList();

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Item item = new Item(
                        resultSet.getString("itemCode"),
                        resultSet.getString("description"),
                        resultSet.getString("packSize"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("qtyOnHand")

                );

                items.add(item);

            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
