package ru.otus.repository;

import lombok.extern.java.Log;
import ru.otus.DataSource;
import ru.otus.Product;
import ru.otus.exeptions.NotFoundExeptions;
import ru.otus.exeptions.ApplicationInitializationException;
import ru.otus.annotations.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Log
public class AbstractRepository<T> {
    private final DataSource dataSource;
    private PreparedStatement psCreate;

    private List<Field> cachedFields;

    public AbstractRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(Product product) throws SQLException {
        String sql = "INSERT INTO products (id, title, price) VALUES (?, ?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getTitle());
            statement.setDouble(3, product.getPrice());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            } catch (SQLException e) {
                throw new NotFoundExeptions("Table product doesn't exist: " + e.getMessage());
            }
        }

        public List<Product> getAllProducts () {
            List<Product> result = new ArrayList<>();
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM products");
                while (rs.next()) {
                    result.add(new Product(rs.getInt("id"), rs.getString("title"), rs.getDouble("price")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Collections.unmodifiableList(result);
        }

        public void update (Product product){
            String sql = "UPDATE products SET title = ?, price = ? WHERE id = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getTitle());
                stmt.setDouble(2, product.getPrice());
                stmt.setInt(3, product.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void findById (Product product){
            String sql = "SELECT titles, prices from products  WHERE id = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getTitle());
                stmt.setDouble(2, product.getPrice());
                stmt.setInt(3, product.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new NotFoundExeptions("Error inserting product: " + e.getMessage());
            }
        }

        public void delete ( int id){
            String sql = "DELETE FROM products WHERE id = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new NotFoundExeptions("Error inserting product: " + e.getMessage());
            }
        }

    }
