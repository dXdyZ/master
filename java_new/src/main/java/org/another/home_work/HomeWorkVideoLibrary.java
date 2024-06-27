package org.another.home_work;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

public class HomeWorkVideoLibrary {
    public static void main(String[] args) {
        Connection connection = null;
        try {
             connection = JDBCVideoLibConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MovieDAO movieDAO = new MovieDAO(connection);
        ActorDAO actorDAO = new ActorDAO(connection);
        System.out.println(actorDAO.findAll());
        System.out.println(movieDAO.findAll());
        System.out.println(movieDAO.deleteMoreAde('3'));
        AbstractDAO.closeConnection();

    }
}


class JDBCVideoLibConnection {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String url = resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}

abstract class AbstractDAO<K, T extends Entity> {
    protected static Connection connection;

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("connection failed closed: " + e.getMessage() + " " + e.getSQLState());
        }
    }

    public abstract List<T> findAll();
    public abstract T findById(K id);
    public abstract boolean addEntity(T entity);
    public abstract boolean dropEntityById(K id);
    public abstract boolean dropFullTable();

}

class MovieDAO extends AbstractDAO<Integer, Movie> {
    private static final String SQL_SELECT_MOVIE = "select * from movie";
    private static final String SQL_SELECT_MOVIE_BY_ID = "select * from movie where id=?";
    private static final String SQL_INSERT_MOVIE = "insert into movie (id, name, actor_id, create_date, create_country) values (?, ?, ?, ?, ?)";
    private static final String SQL_DROP_FROM_MOVIE = "delete from movie where id=?";
    private static final String SQL_DROP_FULL_TABLE = "delete from movie";
    private static final String SQL_SELECT_WHERE_ACTOR_ID = "select id from movie where actor_id=?";
    private static final String SQL_SELECT_ACTOR_WHERE_ID = "select * from actor where id=?";
    private static final String SQL_SELECT_WHERE_MOVIE_CREATE_DATE = "select * from movie where create_date=?";
    private static final String SQL_SELECT_WHERE_MOVIE = "select * from movie where name=?";
    private static final String SQL_DROP_WHERE_MORE_CREATE_DATE = "delete from movie where create_date=?";
    private static final String SQL_SELECT_ALL_CREATE_DATE = "select create_date from movie";

    public MovieDAO(Connection connection) {
        super(connection);
    }

    public boolean deleteMoreAde(char date) {
        PreparedStatement preparedStatement = null;
        List<String> list = new ArrayList<>();
        boolean flag = false;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_CREATE_DATE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("create_date"));
            }

            preparedStatement = null;
            preparedStatement = connection.prepareStatement(SQL_DROP_WHERE_MORE_CREATE_DATE);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).charAt(9) < date) {
                    preparedStatement.setString(1, list.get(i));
                    preparedStatement.executeUpdate();
                }
            }

            flag = true;
        } catch (SQLException e) {
            System.err.println("request create filed: " + e.getMessage() + " " + e.getSQLState());
        }
        return flag;
    }

    public Integer getMovieByName(String name) {
        PreparedStatement preparedStatement = null;
        Integer resultActorId = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_WHERE_MOVIE);
            try {
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultActorId = resultSet.getInt("actor_id");
                }
            } catch (SQLException ex) {
                System.err.println("request failed: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("connection failed: " + e.getMessage() + " " + e.getSQLState());
        }
        return resultActorId;
    }

    public List<Movie> getMovieCreateDate(String age) {
        PreparedStatement preparedStatement = null;
        List<Movie> movies = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_WHERE_MOVIE_CREATE_DATE);
            try {
                preparedStatement.setString(1, age);
                ResultSet resultSet = preparedStatement.executeQuery();
                connection.setAutoCommit(false);
                while (resultSet.next()) {
                    Movie movie = new Movie();
                    movie.setId(resultSet.getInt("id"));
                    movie.setName(resultSet.getString("name"));
                    movie.setActorId(resultSet.getInt("actor_id"));
                    movie.setCreateDate(resultSet.getString("create_date"));
                    movie.setCreateCountry(resultSet.getString("create_country"));
                    movies.add(movie);
                }

                //получения нового значения
                char oldAge = age.charAt(9);
                Integer ageInt = Integer.parseInt(String.valueOf(oldAge));
                ageInt--;
                String newAge = String.valueOf(ageInt);
                oldAge = newAge.charAt(0);

                //замена последнего символа на измененый
                String result = age.substring(0, 9) + oldAge + age.substring(9 + 1);

                //запрос с новыми данными
                preparedStatement.setString(1, result);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Movie movie = new Movie();
                    movie.setId(resultSet.getInt("id"));
                    movie.setName(resultSet.getString("name"));
                    movie.setActorId(resultSet.getInt("actor_id"));
                    movie.setCreateDate(resultSet.getString("create_date"));
                    movie.setCreateCountry(resultSet.getString("create_country"));
                    movies.add(movie);
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                System.err.println("request failed: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("connection failed: " + e.getMessage() + " " + e.getSQLState());
        }
        return movies;
    }

    public List<Actor> getQuantityNActor(int actorQuantity) {
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        Map<Integer, Integer> quantityActorMovie = new HashMap<>();
        Map<Integer, Integer> quantity = new HashMap<>();
        List<Actor> actors = new ArrayList<>();
        List<Integer> listActorId = new ArrayList<>();
        int quantityActorId = 0;
        try {
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ACTOR_WHERE_ID);
            try {
                connection.setAutoCommit(false);
                //блок получения кол-во астеров снималось в фильмах
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_MOVIE);
                while (resultSet.next()) {
                    quantityActorMovie.put(resultSet.getInt("id"), resultSet.getInt("actor_id"));
                }
                //Подсчет повторяющихся значений
                for (Integer value : quantityActorMovie.values()) {
                    quantity.put(value, quantity.getOrDefault(value, 0) + 1);
                }
                for (Map.Entry<Integer, Integer> entry : quantity.entrySet()) {
                    if (entry.getValue().equals(actorQuantity)) {
                        listActorId.add(entry.getKey());
                    }
                }
                //блок получения актера
                for (int i = 0; i < listActorId.size(); i++) {
                    preparedStatement.setInt(1, listActorId.get(i));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Actor actor = new Actor();
                        actor.setId(resultSet.getInt("id"));
                        actor.setActorFullName(resultSet.getString("actor_full_name"));
                        actor.setDateOfBirth(resultSet.getString("date_of_birth"));
                        actors.add(actor);
                    }
                }
                connection.commit();
            } catch (SQLException exe) {
                try {
                    connection.rollback();
                } catch (SQLException sqlExceptione) {
                    System.err.println("trans rollback: " + sqlExceptione.getMessage() + " " + sqlExceptione.getSQLState());
                }
                System.err.println("request failed: " + exe.getMessage() + " " + exe.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("connection failed: " + e.getMessage() + " " + e.getSQLState());
        }
        return actors;
    }

    @Override
    public boolean addEntity(Movie entity) {
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_MOVIE);
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getActorId());
            preparedStatement.setString(4, entity.getCreateDate());
            preparedStatement.setString(5, entity.getCreateCountry());
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public List<Integer> getMovieId(int id) {
        PreparedStatement preparedStatement = null;
        List<Integer> listInt = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_WHERE_ACTOR_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listInt.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " " + e.getSQLState());
        }
        return listInt;
    }

    @Override
    public boolean dropEntityById(Integer id) {
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        try {
            preparedStatement = connection.prepareStatement(SQL_DROP_FROM_MOVIE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            System.err.println("failed request: " + ex.getMessage() + " " + ex.getSQLState());
        }

        return flag;
    }

    @Override
    public boolean dropFullTable() {
        Statement statement = null;
        boolean flag = false;
        try {
            statement = connection.createStatement();
            try {
                int resultSet = statement.executeUpdate(SQL_DROP_FULL_TABLE);
                flag = true;
            } catch (SQLException ex) {
                System.err.println("failed full drop table: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("failed connection: " + e.getMessage() + " " + e.getSQLState());
        }
        return flag;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_MOVIE);
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setName(resultSet.getString("name"));
                movie.setActorId(resultSet.getInt("actor_id"));
                movie.setCreateDate(resultSet.getString("create_date"));
                movie.setCreateCountry(resultSet.getString("create_country"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie findById(Integer id) {
        Movie movie = new Movie();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_MOVIE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            movie.setId(resultSet.getInt("id"));
            movie.setName(resultSet.getString("name"));
            movie.setActorId(resultSet.getInt("actor_id"));
            movie.setCreateDate(resultSet.getString("create_date"));
            movie.setCreateCountry(resultSet.getString("create_county"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }


}

class ActorDAO extends AbstractDAO<Integer, Actor> {
    private static final String SQL_SELECT_ACTOR = "select * from actor";
    private static final String SQL_SELECT_ACTOR_BY_ID = "select * from actor where id=?";
    private static final String SQL_INSERT_ACTOR = "insert into actor (id, actor_full_name, date_of_birth) values (?, ?, ?)";
    private static final String SQL_DROP_FROM_ACTOR = "delete from actor where id=?";
    private static final String SQL_DROP_FULL_TABLE = "delete  from actor";

    public ActorDAO(Connection connection) {
        super(connection);
    }

    public Actor getActorByMovieName(String movieName) {
        PreparedStatement preparedStatement = null;
        Actor actor = new Actor();
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL_SELECT_ACTOR_BY_ID);
            MovieDAO movieDAO = new MovieDAO(connection);
            preparedStatement.setInt(1, movieDAO.getMovieByName(movieName));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            actor.setId(resultSet.getInt("id"));
            actor.setActorFullName(resultSet.getString("actor_full_name"));
            actor.setDateOfBirth(resultSet.getString("date_of_birth"));
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.err.println("connection failed: " + e.getMessage() + " " + e.getSQLState());
        }
        return actor;
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            try {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ACTOR);
                while (resultSet.next()) {
                    Actor actor = new Actor();
                    actor.setId(resultSet.getInt("id"));
                    actor.setActorFullName(resultSet.getString("actor_full_name"));
                    actor.setDateOfBirth(resultSet.getString("date_of_birth"));
                    actors.add(actor);
                }
            } catch (SQLException ex) {
                System.err.println("failed request: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("failed connection: " + e.getMessage() + " " + e.getSQLState());
        }
        return actors;
    }

    @Override
    public Actor findById(Integer id) {
        Actor actor = new Actor();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_ACTOR_BY_ID);
            preparedStatement.setInt(1, id);
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                actor.setId(resultSet.getInt("id"));
                actor.setActorFullName(resultSet.getString("actor_full_name"));
                actor.setDateOfBirth(resultSet.getString("date_of_birth"));
            } catch (SQLException ex) {
                System.err.println("failed request: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("failed connection: " + e.getMessage() + " " + e.getSQLState());
        }
        return actor;
    }

    @Override
    public boolean addEntity(Actor entity) {
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_ACTOR);
            try {
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.setString(2, entity.getActorFullName());
                preparedStatement.setString(3, entity.getDateOfBirth());
                preparedStatement.executeUpdate();
                flag = true;
            } catch (SQLException ex) {
                System.err.println("failed request: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("failed connection: " + e.getMessage() + " " + e.getSQLState());
        }
        return flag;
    }

    @Override
    public boolean dropEntityById(Integer id) {
        PreparedStatement preparedStatement = null;
        MovieDAO movieDAO = new MovieDAO(connection);
        boolean flag = false;
        try {
            connection.setAutoCommit(false);
            List<Integer> list = movieDAO.getMovieId(id);
            for (int i = 0; i < list.size(); i++) {
                movieDAO.dropEntityById(list.get(i));
            }
            preparedStatement = connection.prepareStatement(SQL_DROP_FROM_ACTOR);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            flag = true;
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException exe) {
                System.err.println("transaction rollback: " + exe.getMessage() + " " + exe.getSQLState());
            }
            System.err.println("failed request: " + ex.getMessage() + " " + ex.getSQLState());
        }
        return flag;
    }

    @Override
    public boolean dropFullTable() {
        Statement statement = null;
        MovieDAO movieDAO = new MovieDAO(connection);
        boolean flag = false;
        try {
            statement = connection.createStatement();
            try {
                connection.setAutoCommit(false);
                movieDAO.dropFullTable();
                statement.executeUpdate(SQL_DROP_FULL_TABLE);
                flag = true;
                connection.commit();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException exe) {
                    System.err.println("request rollback: " + exe.getMessage() + " " + exe.getSQLState());
                }
                System.err.println("failed request: " + ex.getMessage() + " " + ex.getSQLState());
            }
        } catch (SQLException e) {
            System.err.println("failed connection: " + e.getMessage() + " " + e.getSQLState());
        }
        return flag;
    }
}


class Movie extends Entity {
    private int id;
    private String name;
    private int actorId;
    private String createDate;
    private String createCountry;

    public Movie() {}

    public Movie(int id, String name, int actorId, String createDate, String createCountry) {
        this.id = id;
        this.name = name;
        this.actorId = actorId;
        this.createDate = createDate;
        this.createCountry = createCountry;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateCountry() {
        return createCountry;
    }

    public void setCreateCountry(String createCountry) {
        this.createCountry = createCountry;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actorId=" + actorId +
                ", createDate='" + createDate + '\'' +
                ", createCountry='" + createCountry + '\'' +
                '}' + "\n";
    }
}

class Actor extends Entity {
    private int id;
    private String actorFullName;
    private String dateOfBirth;

    public Actor() {}

    public Actor(int id, String actorFullName, String dateOfBirth) {
        this.id = id;
        this.actorFullName = actorFullName;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getActorFullName() {
        return actorFullName;
    }

    public void setActorFullName(String actorFullName) {
        this.actorFullName = actorFullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", actorFullName='" + actorFullName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}' + "\n";
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
class CreateTable {
    private static final String SQL_CREATE_TABLE_ACTOR = "CREATE TABLE actor (" +
            "    id INT PRIMARY KEY," +
            "    actor_full_name VARCHAR(255) NOT NULL," +
            "    date_of_birth VARCHAR(255) NOT NULL" +
            ")";
    private static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE movie (" +
            "    id INT PRIMARY KEY," +
            "    name VARCHAR(255) NOT NULL," +
            "    actor_id INT NOT NULL," +
            "    create_date VARCHAR(255) NOT NULL," +
            "    create_country VARCHAR(255) NOT NULL," +
            "    FOREIGN KEY (actor_id) REFERENCES actor(id)" +
            ")";
    private Connection connection;

    public CreateTable() {
        try {
            this.connection = JDBCVideoLibConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creteTables() {
        try {
            connection.setAutoCommit(false);
            createTableActor();
            createTableMovie();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private boolean createTableActor() {
        boolean flag = false;
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_CREATE_TABLE_ACTOR);
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " " + e.getSQLState());
        }
        return flag = true;
    }

    private boolean createTableMovie() {
        boolean flag = false;
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_CREATE_TABLE_MOVIE);
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " " + e.getSQLState());
        }
        return flag = true;
    }
}