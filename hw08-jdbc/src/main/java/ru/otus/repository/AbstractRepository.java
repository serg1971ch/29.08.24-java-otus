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

    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;
        prepare(cls);
    }

    public void create(T entity) {
        try {
            for (int i = 0; i < cachedFields.size(); i++) {
                psCreate.setObject(i + 1, cachedFields.get(i).get(entity));
            }
            psCreate.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationInitializationException("Невозможно инициализировать столбцы таблицы");
        }
    }

    private void prepare(Class<T> cls) {
        StringBuilder query = new StringBuilder("insert into ");
        String tableName = cls.getAnnotation(RepositoryTable.class).title();
        query.append(tableName).append(" (");
        // 'insert into users ('
        cachedFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> !f.isAnnotationPresent(RepositoryIdField.class))
                .collect(Collectors.toList());
        for (Field f : cachedFields) { // TODO Сделать использование геттеров
            f.setAccessible(true);
        }
        for (Field f : cachedFields) {
            query.append(f.getName()).append(", ");
        }
        // 'insert into users (login, password, nickname, '
        query.setLength(query.length() - 2);
        // 'insert into users (login, password, nickname'
        query.append(") values (");
        for (Field f : cachedFields) {
            query.append("?, ");
        }
        // 'insert into users (login, password, nickname) values (?, ?, ?, '
        query.setLength(query.length() - 2);
        // 'insert into users (login, password, nickname) values (?, ?, ?'
        query.append(");");
        try {
            psCreate = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ApplicationInitializationException("Impossible to prepare statement");
        }
    }

    public List<Product> getAllProducts() {
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

    public void update(Product product) {
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

    public void findById(Product product) {
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

    public void delete(int id) {
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
