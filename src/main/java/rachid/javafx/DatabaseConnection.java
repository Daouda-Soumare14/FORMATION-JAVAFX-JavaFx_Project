package rachid.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    @SuppressWarnings("exports")
    public static Connection connection()
    {
        Connection connect = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/java_fx";
            String username = "root";
            String password = "root";

            connect = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connection a la base de donnee : " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            System.err.println("JDBC MYSQL DRIVER introuvable");
            e.printStackTrace();
        }
        return connect;
    }
}
