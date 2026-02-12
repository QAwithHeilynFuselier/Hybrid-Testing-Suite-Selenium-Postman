package utils;
import io.qameta.allure.Step;

import java.sql.*;



public class SqlQueries {
    @Step("name artist ID: {id} database")
    public static String getArtistNameById(int id) {
        String query = "SELECT name FROM artists WHERE id = ?;";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error with s in  SQL: " + e.getMessage());
        }
        return "Artist no found";
    }


    public static String getArtistBySongTitle(String songTitle) throws SQLException {
        String query = "SELECT a.name FROM artists a " +
                "JOIN songs s ON a.id = s.artist_id " +
                "WHERE s.title = ? LIMIT 1;";

        try(Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, songTitle);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }
        }
        return "Unknown Artist";
    }


}







