package controller.itemmanagement;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public int deleteItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Item  WHERE itemCode=?");

            preparedStatement.setString(1, item.getItemCode());

            int rows = preparedStatement.executeUpdate();
            return rows;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
