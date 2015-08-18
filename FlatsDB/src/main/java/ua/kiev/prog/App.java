package ua.kiev.prog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return dbConnection;
    }

    public static void main(String[] args) {
        Connection conn = getDBConnection();
        if (conn == null) {
            System.out.println("Error creating connection!");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = "";
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Flats (district, address, square, roomsNumber, price) VALUES(?, ?, ?, ?, ?)");
            try {
                while (!s.equals("no")) {
                    System.out.print("Район: ");
                    String district = br.readLine();
                    System.out.print("Адрес: ");
                    String address = br.readLine();
                    System.out.print("Площадь: ");
                    Double square = Double.parseDouble(br.readLine());
                    System.out.print("Кол-во комнат: ");
                    Integer roomsNumber = Integer.parseInt(br.readLine());
                    System.out.print("Цена: ");
                    Double price = Double.parseDouble(br.readLine());
                    System.out.print("Продолжить? Введите no, чтобы выйти: ");

                    ps.setString(1, district);
                    ps.setString(2, address);
                    ps.setDouble(3, square);
                    ps.setInt(4, roomsNumber);
                    ps.setDouble(5, price);
                    ps.executeUpdate();
                    s = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ps = conn.prepareStatement("SELECT * FROM Flats");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.print("Район: " + rs.getString(0));
                System.out.print("Адрес: " + rs.getString(1));
                System.out.print("Площадь: " + rs.getDouble(2));
                System.out.print("Кол-во комнат: " + +rs.getInt(3));
                System.out.print("Цена: " + rs.getDouble(4));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
