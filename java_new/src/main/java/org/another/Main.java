package org.another;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        ArrayList<Abonent> list = new ArrayList<>() {
            {
                add(new Abonent(3, 90934525));
                add(new Abonent(4, 903232325));
                add(new Abonent(5, 90931325));
                add(new Abonent(6, 90134525));
            }
        };
        DataBaseHelper helper = null;
        PreparedStatement statement = null;
        try {
            helper = new DataBaseHelper();
            statement = helper.getPreparedStatement();
            for (Abonent abonent : list) {
                helper.insertAbonent(statement, abonent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.closeStatment(statement);
        }
    }
}

class DataBaseHelper {
    private final static String SQL_INSERT =
            "INSERT INTO phonebook(id, phone) VALUES(?, ?)";
    private Connection connection;

    public DataBaseHelper() throws SQLException{
        connection = ConnectDB.getConnection();
    }

    public PreparedStatement getPreparedStatement() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public boolean insertAbonent(PreparedStatement ps, Abonent abonent) {
        boolean flag = false;
        try {
            ps.setInt(1, abonent.getId());
            ps.setLong(2, abonent.getPhone());
            ps.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void closeStatment(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConnectDB {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String url = resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String pass = resourceBundle.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}

abstract class Entity implements Serializable, Cloneable {
    private int id;
    public Entity() {}
    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class Abonent extends Entity {
    private int id;
    private long phone;
    public Abonent(){}

    public Abonent(int id, long phone) {
        this.id = id;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Abonent{" +
                "id=" + id +
                ", phone=" + phone +
                '}';
    }
}



