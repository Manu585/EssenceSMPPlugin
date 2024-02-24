package me.manu.essencesmpplugin.database;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.entity.Player;

import java.sql.*;

public class EssencePlayerDB {

    private final Connection connection;

    public EssencePlayerDB(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS players (
                uuid TEXT PRIMARY KEY,
                username TEXT NOT NULL,
                activeessence STRING NOT NULL DEFAULT DefaultEssence)
                """);
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public boolean playerExists(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public void addPlayer(Player p) throws SQLException {
        if (!playerExists(p)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players(uuid, username) VALUES(?, ?)")) {
                    EssencePlayer newPlayer = new EssencePlayer(p.getUniqueId());
                    EssencePlayer.registerEssencePlayer(newPlayer);
                    preparedStatement.setString(1, newPlayer.getUuid().toString());
                    preparedStatement.setString(2, newPlayer.getPlayer().getName());
                    preparedStatement.executeUpdate();
            }
        }
    }

    public void updateEssence(Player p, Essence e) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET activeessence = ? WHERE uuid = ?")) {
            preparedStatement.setString(1, e.getEssenceName());
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    public String getPlayerEssence(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT activeessence FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("activeessence");
            } else {
                return "DefaultEssence";
            }
        }
    }
}
