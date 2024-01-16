package org.sayres.springmvc.dao;

import org.sayres.springmvc.models.Participation;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aliaksei Karabelnikau
 */
@Component
public class ParticipationDAO {
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

    public List<Participation> index() {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT * FROM Game_Participation";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                participations.add(extractParticipation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participations;
    }

    public Participation show(int id) {
        String sql = "SELECT * FROM Game_Participation WHERE participation_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractParticipation(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void update(int id, Participation participation) {
        String sql = "UPDATE Game_Participation SET player_id=?, game_id=?,  is_banned=?, kills=?, death=?, rank=? WHERE participation_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, participation.getPlayer_id());
            preparedStatement.setInt(2, participation.getGame_id());
            preparedStatement.setBoolean(3, participation.isIs_banned());
            preparedStatement.setInt(4, participation.getKills());
            preparedStatement.setInt(5, participation.getDeath());
            preparedStatement.setInt(6, participation.getRank());
            ;
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Participation showDetails(int id) {
        String sql = "SELECT gp.*, p.*, g.* FROM Game_Participation gp " +
                "JOIN Player p ON gp.player_id = p.id " +
                "JOIN Game g ON gp.game_id = g.id " +
                "WHERE gp.participation_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractParticipation(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Participation> findAllByPlayerId(int playerId) {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT * FROM Game_Participation WHERE player_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, playerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    participations.add(extractParticipation(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participations;
    }

    public void save(Participation participation) {
        String sql = "INSERT INTO Game_Participation(player_id, game_id, hours_played, join_date, is_banned, kills, death, rank) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, participation.getPlayer_id());
            preparedStatement.setInt(2, participation.getGame_id());
            preparedStatement.setInt(3, participation.getHours_played());
            preparedStatement.setString(4, participation.getJoin_date());
            preparedStatement.setBoolean(5, participation.isIs_banned());
            preparedStatement.setInt(6, participation.getKills());
            preparedStatement.setInt(7, participation.getDeath());
            preparedStatement.setInt(8, participation.getRank());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Participation> findAllByGameId(int gameId) {
        List<Participation> participations = new ArrayList<>();
        String sql = "SELECT * FROM Game_Participation WHERE game_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    participations.add(extractParticipation(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participations;
    }

    //SELECT p.game, p.player FROM Participation p WHERE p.game = :game ORDER BY p.score DESC LIMIT 5")

    public List<Participation> findTop5ByGameId(int gameId) {
        List<Participation> participations = new ArrayList<>();
        //kils and rank
        String sql = "SELECT * FROM Game_Participation WHERE game_id=? ORDER BY rank DESC, kills DESC LIMIT 5";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    participations.add(extractParticipation(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participations;
    }

    //best player from each game
    public List<Participation> bestPlayersInGames() {
        List<Participation> participation = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM (\n" +
                "         SELECT *,\n" +
                "                ROW_NUMBER() OVER (PARTITION BY game_id ORDER BY rank DESC, kills DESC) AS rn\n" +
                "         FROM Game_Participation\n" +
                "     ) AS ranked\n" +
                "WHERE ranked.rn = 1;\n)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    participation.add(extractParticipation(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participation;
    }

    private Participation extractParticipation(ResultSet resultSet) throws SQLException {
        Participation participation = new Participation();
        participation.setParticipation_id(resultSet.getInt("participation_id"));
        participation.setPlayer_id(resultSet.getInt("player_id"));
        participation.setGame_id(resultSet.getInt("game_id"));
        participation.setHours_played(resultSet.getInt("hours_played"));
        participation.setJoin_date(resultSet.getString("join_date"));
        participation.setIs_banned(resultSet.getBoolean("is_banned"));
        participation.setKills(resultSet.getInt("kills"));
        participation.setDeath(resultSet.getInt("death"));
        participation.setRank(resultSet.getInt("rank"));
        return participation;
    }

    public void delete(int id) {
        String sql = "DELETE FROM Game_Participation WHERE participation_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
