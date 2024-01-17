package org.sayres.springmvc.dao;

import org.sayres.springmvc.models.Game;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliaksei Karabelnikau
 */
@Component
@Repository
public class GameDAO {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";
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

    public List<Game> index() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game game = new Game();
                game.setGame_id(resultSet.getInt("game_id"));
                game.setGame_name(resultSet.getString("game_name"));
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public void save(Game game) {
        String sql = "INSERT INTO games (game_name) VALUES (?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, game.getGame_name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Game game) {
        String sql = "UPDATE games SET game_name=? WHERE game_id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, game.getGame_name());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM games WHERE game_id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game show(int id) {
        Game game = new Game();
        String sql = "SELECT * FROM games WHERE game_id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                game.setGame_id(resultSet.getInt("game_id"));
                game.setGame_name(resultSet.getString("game_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    public List<Game> findAllByPlayerId(int playerId) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT g.* FROM Games g " + "JOIN Game_Participation gp ON g.game_id = gp.game_id " + "WHERE gp.player_id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Game game = new Game();
                game.setGame_id(resultSet.getInt("game_id"));
                game.setGame_name(resultSet.getString("game_name"));
                games.add(game);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return games;
    }
}