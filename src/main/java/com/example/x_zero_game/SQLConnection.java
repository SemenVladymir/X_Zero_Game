package com.example.x_zero_game;

import java.sql.*;

public class SQLConnection  {
    private static Connection connection;
    private static Statement statement;
    private static SQLConnection _instance;
    private SQLConnection() throws SQLException {
        connection  = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/games",
                "root", "");
        statement = connection.createStatement();
    }

    public static SQLConnection Start() throws SQLException {
        if (_instance==null)
            _instance = new SQLConnection();
        return _instance;
    }

    public static void Save(String name, String symb, String field, String datetime) throws SQLException {
        statement.execute("INSERT INTO `x_0_game_steps`(`name`, `symbol`, `field`, `datetime`) VALUES ('"+name+"', '"+symb+"', '"+field+"','"+datetime+"')");

    }

    public static  void Stop() throws SQLException {
        connection.close();
    }


}
