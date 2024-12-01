package ru.otus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableRepository {
    private final Connection connection;

    public TableRepository(Connection connection) {
        this.connection = connection;
    }

    public void executeSQL(String sql)  {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        } catch (ExeptionJDBC | SQLException e) {
            e.printStackTrace();
        }
    }

    public Product read(int id)  {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("id"), rs.getString("title"), rs.getDouble("price"));
            }
        }  catch (ExeptionJDBC | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create(Product product)  {
        String sql = "INSERT INTO products (id, title, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getTitle());
            stmt.setDouble(3, product.getPrice());
            stmt.executeUpdate();
        }  catch (ExeptionJDBC | SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Product product)  {
        String sql = "UPDATE products SET title = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getTitle());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getId());
            stmt.executeUpdate();
        } catch (ExeptionJDBC | SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (ExeptionJDBC | SQLException e) {
            e.printStackTrace();
        }
    }
}
