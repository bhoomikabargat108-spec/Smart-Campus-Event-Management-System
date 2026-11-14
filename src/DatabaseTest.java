
import database.DBConnection;
import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("Connection Test Passed!");

            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
