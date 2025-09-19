package controller.itemmanagement;

import javafx.collections.ObservableList;
import model.dto.Item;

public interface ItemManagementService {
    public ObservableList<Item> getAllItems();
}
