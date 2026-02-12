package utils;
import io.qameta.allure.Step;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlManager {

/*
    public static void main(String[] args) {
        try {
            var connection = DbConnection.getConnection();
            if (connection != null) {
                System.out.println("Conexión with database  Koel");
            }
        } catch (Exception e) {
            System.out.println("Error to connexion : " + e.getMessage());
        }
    }*/

    @Step("Get name all songs")
    public static List<String> getAllSongNames() {
        List<String> songs = new ArrayList<>();
        String query = "SELECT name FROM songs ORDER BY name ASC;";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                songs.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error name songs: " + e.getMessage());
        }
        return songs;
    }

    @Step("Get nam e artist with  ID: {artistId}")
    public static String getArtistName(int artistId) {
        String query = "SELECT name FROM artists WHERE id = ?;";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, artistId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("name");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Not Found";
    }

    @Step("Get artist name by song title: {songTitle}")
    public static String getArtistBySongTitle(String songTitle) {

        String query = "SELECT a.name FROM artists a " +
                "JOIN songs s ON a.id = s.artist_id " +
                "WHERE s.title = ?;";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, songTitle);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException e) {
            System.out.println("Error to connecto with  DB: " + e.getMessage());
        }
        return "Unknown Artist";
    }

    @Step("Get playlist name from DB for user ID: {userId}")
    public static String getPlaylistNameFromDb(int userId, String expectedName) {

        String query = "SELECT name FROM playlists WHERE user_id = ? AND name = ?;";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, expectedName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta de playlist: " + e.getMessage());
        }
        return "Not Found";
    }
}

