package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionPET {

    private static DbConnectionPET dbConnectionPET;

    private Connection connection;

    private DbConnectionPET() throws SQLException {
        connection = DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/pet_empire_animal_hospital",
                "root",
                "Ijse@1234"
        );
    }

    public static DbConnectionPET getInstance() throws SQLException {
        if(dbConnectionPET == null){
            dbConnectionPET = new DbConnectionPET();
        }
        return dbConnectionPET;
    }

    public Connection getConnection() {
        return connection;
    }
}
