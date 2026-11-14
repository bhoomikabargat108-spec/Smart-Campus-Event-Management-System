
package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean authenticate(
            String username,
            String password,
            String role) {

        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            try (PreparedStatement pst = con.prepareStatement(query)) {

                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, role);

                try (ResultSet rs = pst.executeQuery()) {
                    return rs.next();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
