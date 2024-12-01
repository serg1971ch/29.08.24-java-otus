package ru.otus;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws SQLException {
        final Logger logger = Logger.getLogger(ItemsService.class.getName());

        ItemsServiceProxy serviceProxy = new ItemsServiceProxy();

        serviceProxy.save100Items();
        logger.info("100 items saved successfully.");

        serviceProxy.increasePrices();
        logger.info("Prices increased successfully.");
    }
}