// package com.company;

import java.sql.*;

public class Main {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE Clients (" +
        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
        "group_id INT DEFAULT NULL," +
        "name VARCHAR(128) DEFAULT NULL," +
        "surname VARCHAR(128) DEFAULT NULL," +
        "email VARCHAR(128) DEFAULT NULL," +
        "phone VARCHAR(12) NOT NULL)";


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
            try {
                // #1
//                Statement st = conn.createStatement();
//                try {
//                    st.execute(CREATE_TABLE_SQL);
//                } finally {
//                    if (st != null) st.close();
//                }

                // #2
//                Statement st = conn.createStatement();
//                try {
//                    st.executeUpdate("INSERT INTO Clients (phone) VALUES('0505551112')"); // for INSERT, UPDATE & DELETE
//                } finally {
//                    if (st != null) st.close();
//                }
//
//                // #3
//                long groupId = -1;
//
//                st = conn.createStatement();
//                try {
//                    ResultSet rs = st.executeQuery("SELECT id FROM Groups WHERE name='Group-1'");
//                    if (rs.next()) {
//                        groupId = rs.getLong(1);
//                    } else {
//                        System.out.println("Group not found!");
//                        return;
//                    }
//                } finally {
//                    if (st != null) st.close();
//                }
//
//                // #4
//                PreparedStatement ps = conn.prepareStatement("INSERT INTO Clients (name, phone, group_id) VALUES(?, ?, ?)");
//                try {
//                    ps.setString(1, "Ivan");
//                    ps.setString(2, "0441234567");
//                    ps.setLong(3, groupId);
//
//                    ps.executeUpdate();
//                } finally {
//                    if (ps != null) ps.close();
//                }
//
//                // #4a
//                conn.setAutoCommit(false); // !!!
//
//                try {
//                    ps = conn.prepareStatement("INSERT INTO Clients (name, phone, group_id) VALUES(?, ?, ?)");
//                    try {
//                        for (int i = 0; i < 10; i++) {
//                            ps.setString(1, "Ivan");
//                            ps.setString(2, "044123456" + i);
//                            ps.setLong(3, groupId);
//
//                            ps.executeUpdate();
//                        }
//
//                        conn.commit();
//                    } finally {
//                        if (ps != null) ps.close();
//                    }
//                } catch (Exception ex) {
//                    conn.rollback();
//                }
//
//                conn.setAutoCommit(true); // !!!

                // #5
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clients");
                try {
                    // table of data representing a database result set,
                    ResultSet rs = ps.executeQuery();
                    try {
                        // can be used to get information about the types and properties of the columns in a ResultSet object
                        ResultSetMetaData md = rs.getMetaData(); //

                        for (int i = 1; i <= md.getColumnCount(); i++)
                            System.out.print(md.getColumnName(i) + "\t\t");
                        System.out.println();

                        while (rs.next()) {
                            for (int i = 1; i <= md.getColumnCount(); i++) {
                                System.out.print(rs.getString(i) + "\t\t");
                            }
                            System.out.println();
                        }
                    } finally {
                        rs.close(); // rs can't be null according to the docs
                    }
                } finally {
                    if (ps != null) ps.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
