package dbConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String PROPERTY_FILE = "src/dbConfig/dbConfig.properties";

    private static String url;
    private static String username;
    private static String password;
    private static  DBConnection instance;
    private Connection connection;


    static{

        try(FileInputStream fs = new FileInputStream(PROPERTY_FILE)){
            Properties props = new Properties();
            props.load(fs);

            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
        }
        catch(IOException e){
            System.out.println("Error Could Not load the DBConfig... " + e.getMessage());
        }
    }

    private DBConnection(){

        try{
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e){
            System.out.println("Something wrong..." + e.getMessage());
        }
    }

    public static DBConnection getInstance(){

        if(instance == null){
            instance = new DBConnection();
        }

        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
    
}
