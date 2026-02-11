package com.app;

import com.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) throws SQLException {
        System.out.println("test connexion MySQL");

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT 1");
            ResultSet rs = ps.executeQuery();
        ) {
            if(rs.next()){
                System.out.println("connexion réussie : " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
