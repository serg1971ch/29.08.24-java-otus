package ru.otus;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemsServiceProxy {
    private final ItemsService itemsService;
    private final Connection connection;

    public ItemsServiceProxy() throws SQLException {
        this.itemsService = new ItemsService();
        this.connection = DataSource.getInstance().getConnection();
    }

    public void save100Items() {
        try {
            connection.setAutoCommit(false);
            itemsService.save100Items();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void increasePrices() {
        try {
            connection.setAutoCommit(false);
            itemsService.increasePrices();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
