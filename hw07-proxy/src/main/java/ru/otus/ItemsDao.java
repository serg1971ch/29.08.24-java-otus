package ru.otus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDao {
    private Connection connection;

    public ItemsDao() {
        this.connection = DataSource.getInstance().getConnection();
    }

    public void createTableItem() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS items (id SERIAL PRIMARY KEY, " +
                "title varchar(256) NOT NULL, price int NOT NULL); ";

        connection.createStatement().execute(sql);
    }


    public void createItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (title, price) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getTitle());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
        }
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, price FROM items";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                items.add(new Item(id, title, price));
            }
        }
        return items;
    }
}