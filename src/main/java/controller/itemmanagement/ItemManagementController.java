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

    @Override
    public int updateItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?");

            preparedStatement.setString(1, item.getDescription());
            preparedStatement.setString(2, item.getPackSize());
            preparedStatement.setDouble(3, item.getUnitPrice());
            preparedStatement.setInt(4, item.getQtyOnHand());
            preparedStatement.setString(5, item.getItemCode());

            int rows = preparedStatement.executeUpdate();
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?,?)");

            preparedStatement.setObject(1, item.getItemCode());
            preparedStatement.setObject(2, item.getDescription());
            preparedStatement.setObject(3, item.getPackSize());
            preparedStatement.setObject(4, item.getUnitPrice());
            preparedStatement.setObject(5, item.getQtyOnHand());

            int rows = preparedStatement.executeUpdate();
            return rows;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
