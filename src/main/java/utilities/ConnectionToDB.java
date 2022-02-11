package utilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {

    private static Connection conn = null;

    static {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, IOException{



        if (conn == null){
            Properties prop = new Properties();
            try{

                prop.load(ConnectionToDB.class.getClassLoader().getResourceAsStream("connection.properties"));
                String endpoint = prop.getProperty("endpoint");
                String url = "jdbc:postgresql://" + endpoint + "/postgres";
                String username = prop.getProperty("username");
                String password = prop.getProperty("password");

                conn = DriverManager.getConnection(url, username, password);

            } catch (IOException | SQLException e){
                e.printStackTrace();
            }
        }
            return conn;
    }

    //To test the connection
    public static void main(String[] args) throws SQLException, IOException {
        Connection connect = getConnection();
        Connection connect2 = getConnection();
        System.out.println(connect);
        System.out.println(connect2);
    }
}
