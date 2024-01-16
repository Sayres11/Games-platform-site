package org.sayres.springmvc.dao;

import org.sayres.springmvc.models.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliaksei Karabelnikau
 */
@Component
@Repository
public class UserDAO {
    private static final String URL = "jdbc:sqlite:C:\\Users\\Aliaksei\\Desktop\\admin-panel\\Admin-Panel\\src\\main\\java\\org\\sayres\\springmvc\\dao\\identifier.sqlite";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public List<User> index() {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM Players";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeQuery(SQL);
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setPlayer_id(resultSet.getInt("player_id"));
                user.setPlayer_name(resultSet.getString("player_name"));
                user.setEmail(resultSet.getString("email"));
                String dateOfBirthStr = resultSet.getString("date_of_birth");
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
                user.setDate_of_birth(dateOfBirth);

                String createdAtStr = resultSet.getString("created_at");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);
                user.setCreated_at(createdAt);

                String lastModifiedStr = resultSet.getString("last_modified");
                LocalDateTime lastModified = LocalDateTime.parse(lastModifiedStr, formatter);
                user.setLast_modified(lastModified);

                user.setLocation(resultSet.getString("location"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User show(int id) {
        User user = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * FROM Players WHERE player_id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setPlayer_id(resultSet.getInt("player_id"));
                    user.setPlayer_name(resultSet.getString("player_name"));
                    user.setEmail(resultSet.getString("email"));
                    String dateOfBirthStr = resultSet.getString("date_of_birth");
                    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
                    user.setDate_of_birth(dateOfBirth);

                    String createdAtStr = resultSet.getString("created_at");
                    LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);
                    user.setCreated_at(createdAt);

                    String lastModifiedStr = resultSet.getString("last_modified");
                    LocalDateTime lastModified = LocalDateTime.parse(lastModifiedStr, formatter);
                    user.setLast_modified(lastModified);

                    user.setLocation(resultSet.getString("location"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void save(User user) {
        String SQL = "INSERT INTO Players(player_name, email, date_of_birth, created_at, last_modified, location,password) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, user.getPlayer_name());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getDate_of_birth().toString());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            preparedStatement.setString(4, formattedNow);
            preparedStatement.setString(5, formattedNow);
            preparedStatement.setString(6, "Warsaw, Poland");
            preparedStatement.setString(7,//FIXME // random password generator
                    "1234567890");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalUsers() {
        int totalUsers = 0;
        //sum records
        String sql = "SELECT COUNT(*) FROM Players";
        try (Connection conn = this.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                totalUsers = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return totalUsers;

    }

    public List<User> getUsers(int page, int size) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Players LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, offset);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setPlayer_id(resultSet.getInt("player_id"));
                    user.setPlayer_name(resultSet.getString("player_name"));
                    user.setEmail(resultSet.getString("email"));
                    String dateOfBirthStr = resultSet.getString("date_of_birth");
                    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
                    user.setDate_of_birth(dateOfBirth);

                    String createdAtStr = resultSet.getString("created_at");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);
                    user.setCreated_at(createdAt);

                    String lastModifiedStr = resultSet.getString("last_modified");
                    LocalDateTime lastModified = LocalDateTime.parse(lastModifiedStr, formatter);
                    user.setLast_modified(lastModified);

                    user.setLocation(resultSet.getString("location"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(int id, User updateUser) {
        String sql = "UPDATE Players SET player_name=?, email=?, date_of_birth=?, last_modified=? WHERE player_id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updateUser.getPlayer_name());
            preparedStatement.setString(2, updateUser.getEmail());
            preparedStatement.setString(3, updateUser.getDate_of_birth().toString());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            preparedStatement.setString(4, formattedNow);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(User user) {
        String sql = "INSERT INTO Players(player_name, email, date_of_birth, created_at, last_modified, location,password) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getPlayer_name());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getDate_of_birth().toString());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            preparedStatement.setString(4, formattedNow);
            preparedStatement.setString(5, formattedNow);
            preparedStatement.setString(6, "Warsaw, Poland");
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM Players WHERE player_id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM Players WHERE player_name=?";
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setPlayer_id(resultSet.getInt("player_id"));
                user.setPlayer_name(resultSet.getString("player_name"));
                user.setEmail(resultSet.getString("email"));
                String dateOfBirthStr = resultSet.getString("date_of_birth");
                LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
                user.setDate_of_birth(dateOfBirth);

                String createdAtStr = resultSet.getString("created_at");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);
                user.setCreated_at(createdAt);

                String lastModifiedStr = resultSet.getString("last_modified");
                LocalDateTime lastModified = LocalDateTime.parse(lastModifiedStr, formatter);
                user.setLast_modified(lastModified);

                user.setLocation(resultSet.getString("location"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
