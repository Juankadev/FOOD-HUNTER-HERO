package com.example.ffh_rep.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getConnection(){
        Connection con = null;

        try {
            Class.forName(DB_Env.DB_DRIVER);
            con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return con;
    }


    public static void closeConnection(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
