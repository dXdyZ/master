package org.another;

import java.awt.print.PrinterException;
import java.io.Serializable;
import java.rmi.server.UID;
import java.sql.*;
import java.util.*;

public class JDBCHomeWork {
    public static void main(String[] args) {

    }
}



class DataBaseSelect {
    public static List<Weather> getWeather() {
        List<Weather> weatherList = null;
        try(Connection connection = JDBCConnect.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM weather");
            ResultSetMetaData setMetaData = resultSet.getMetaData();
            int getColumn = setMetaData.getColumnCount();
            weatherList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                double latitude = resultSet.getDouble(2);
                double longitude = resultSet.getDouble(3);
                double generationtime_ms = resultSet.getDouble(4);
                int utc_offset_seconds = resultSet.getInt(5);
                String timezone = resultSet.getString(6);
                String timezone_abbreviation = resultSet.getString(7);
                int elevation = resultSet.getInt(8);
                weatherList.add(new Weather(id, latitude, longitude,
                        generationtime_ms, utc_offset_seconds, timezone,
                        timezone_abbreviation, elevation));
            }
            if (!weatherList.isEmpty()) {
                System.out.println(weatherList);
            } else {
                System.err.println("В листе пусто");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    public static Weather getWeatherId(int idWeather) {
        try(Connection connection = JDBCConnect.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM weather");
            ResultSetMetaData setMetaData = resultSet.getMetaData();
            int getColumn = setMetaData.getColumnCount();
            List<Weather> weatherList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                double latitude = resultSet.getDouble(2);
                double longitude = resultSet.getDouble(3);
                double generationtime_ms = resultSet.getDouble(4);
                int utc_offset_seconds = resultSet.getInt(5);
                String timezone = resultSet.getString(6);
                String timezone_abbreviation = resultSet.getString(7);
                int elevation = resultSet.getInt(8);
                weatherList.add(new Weather(id, latitude, longitude,
                        generationtime_ms, utc_offset_seconds, timezone,
                        timezone_abbreviation, elevation));
            }
            if (!weatherList.isEmpty()) {
                System.out.println(weatherList.get(idWeather));
                return weatherList.get(idWeather);
            } else {
                throw new NullPointerException("Такого элемента нет в листе");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


class DatabaseCreateInsert {
    private final static String SQL_INSERT_WEATHER =
            "INSERT INTO weather(id, latitude, longitude, generationtime_ms, utc_offset_seconds, timezone, timezone_abbreviation, elevation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private Connection connection;

    public DatabaseCreateInsert() throws SQLException {
        connection = JDBCConnect.getConnection();
    }

    public PreparedStatement getPreparedStatement() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_WEATHER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public boolean insertWeather(PreparedStatement preparedStatement, Weather weather) {
        boolean flag = false;
        try {
            preparedStatement.setInt(1, weather.getId());
            preparedStatement.setDouble(2, weather.getLatitude());
            preparedStatement.setDouble(3, weather.getLongitude());
            preparedStatement.setDouble(4, weather.getGenerationtime_ms());
            preparedStatement.setInt(5, weather.getUtc_offset_seconds());
            preparedStatement.setString(6, weather.getTimezone());
            preparedStatement.setString(7, weather.getTimezone_abbreviation());
            preparedStatement.setInt(8, weather.getElevation());
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.err.println("Close not success");
            }
        }
    }
}

abstract class EntityWeather implements Serializable, Cloneable {
    private int id;

    public EntityWeather(int id) {
        this.id = id;
    }
    public EntityWeather() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class JDBCConnect {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String url = resourceBundle.getString("db.url");
        String password = resourceBundle.getString("db.password");
        String user = resourceBundle.getString("db.user");
        return DriverManager.getConnection(url, user, password);
    }
}

class Weather extends EntityWeather{
    private int id;
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private int elevation;
    public Weather() {}

    public Weather(int id, double latitude, double longitude,
                   double generationtime_ms, int utc_offset_seconds, String timezone,
                   String timezone_abbreviation, int elevation) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.generationtime_ms = generationtime_ms;
        this.utc_offset_seconds = utc_offset_seconds;
        this.timezone = timezone;
        this.timezone_abbreviation = timezone_abbreviation;
        this.elevation = elevation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(double generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }

    public int getUtc_offset_seconds() {
        return utc_offset_seconds;
    }

    public void setUtc_offset_seconds(int utc_offset_seconds) {
        this.utc_offset_seconds = utc_offset_seconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_abbreviation() {
        return timezone_abbreviation;
    }

    public void setTimezone_abbreviation(String timezone_abbreviation) {
        this.timezone_abbreviation = timezone_abbreviation;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", generationtime_ms=" + generationtime_ms +
                ", utc_offset_seconds=" + utc_offset_seconds +
                ", timezone='" + timezone + '\'' +
                ", timezone_abbreviation='" + timezone_abbreviation + '\'' +
                ", elevation=" + elevation +
                '}' + "\n";
    }
}


