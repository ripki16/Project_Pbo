/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.SQLException;
/**
 *
 * @author rifky
 */
public class Koneksi {
    public static Connection koneksi;
    
    public static Connection getKoneksiData() throws SQLException{
        if(koneksi == null) {
            try{
                String url = "jdbc:mysql://localhost:3306/akun";
                String username = "root";
                String password = "";
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(url, username, password);
                System.out.println("Connection Successfully");
            }catch (Exception e) {
                System.out.println("Error");
            }
        }
        return koneksi;
    }
}
