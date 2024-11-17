package ru.otus;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;


public class ItemsService {
    private ItemsDao itemsDao;

    public ItemsService() throws SQLException {
        this.itemsDao = new ItemsDao();
        itemsDao.createTableItem();
    }

    public void save100Items() throws SQLException {
        for (int i = 1; i <= 100; i++) {
            Item item = new Item(0, "Item " + i, i * 10.0);
            itemsDao.createItem(item);
        }
    }

    public void increasePrices() throws SQLException {
        List<Item> items = itemsDao.getAllItems();
        for (Item item : items) {
            item.setPrice(item.getPrice() * 2);
            itemsDao.createItem(item);
        }
    }
}
