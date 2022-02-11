package utilities;

//imports from java.sql -> JDBC
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {

    /*
    We will create a similar idea to Singleton -> is a design patter in which you only ever
    want One instance of the object to ever exist

    -Prevent additional Object creations by privatizing our constructor and create a
    public method that controls when if at all a new Object is created
     */
    private static Connection conn = null;

    static {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, IOException{

        /*
        To establish a new connection if one doesn't exist,
        otherwise return the current connection.

        Credentials: url (endpoint), username, password
         */

        if (conn == null){
            //Establish a new Connection

            Properties prop = new Properties();
            try{


                prop.load(ConnectionToDB.class.getClassLoader().getResourceAsStream("connection.properties"));
//                prop.load(new FileReader("C:\\Users\\Milanoboy\\Desktop\\CourseRegistration\\src\\main\\resources\\connection.properties"));

                String endpoint = prop.getProperty("endpoint");
                //URL Format (PostgreSQL JDBC)
                //jdbc:postgresql://[endpoint]/[database]
                String url = "jdbc:postgresql://" + endpoint + "/postgres";
                String username = prop.getProperty("username");
                String password = prop.getProperty("password");

                conn = DriverManager.getConnection(url, username, password);

            } catch (IOException | SQLException e){
                e.printStackTrace();
            }


        }
            //just return the current connection
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
