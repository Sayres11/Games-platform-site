package org.sayres.springmvc.dao;

import org.sayres.springmvc.models.LinkedAccount;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliaksei Karabelnikau
 */
@Component
public class LinkedAccountDAO {

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

    public List<LinkedAccount> show(int id) {
        String sql = "SELECT * FROM Linked_Accounts WHERE player_id=?";
        List<LinkedAccount> linkedAccounts = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    linkedAccounts.add(extractLinkedAccount(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return linkedAccounts;
    }

    private LinkedAccount extractLinkedAccount(ResultSet resultSet) throws SQLException {
        LinkedAccount linkedAccount = new LinkedAccount();
        linkedAccount.setId(resultSet.getInt("linked_id"));
        linkedAccount.setPlayer_id(resultSet.getInt("player_id"));
        linkedAccount.setAccount_details(resultSet.getString("account_details"));
        return linkedAccount;
    }

}
