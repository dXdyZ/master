//package org.another;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import java.util.ResourceBundle;
//
//public class DAO {
//}
//
//abstract class AbstractDAO <T extends Entity> {
//    protected Connection connection;
//
//    public AbstractDAO(Connection connection) {
//        this.connection = connection;
//    }
//
//    public abstract List<T> findAll();
//
//    public abstract T findEntityById(K id);
//
//    public abstract boolean delete(K id);
//
//    public abstract boolean delete(T entity);
//
//    public abstract boolean create(T entity);
//
//    public abstract T update(T entity);
//
//    public void close(Statement statement) {
//        if (statement != null) {
//            try {
//                statement.close();;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//class Payment extends Entity{
//    private int id;
//    private String text;
//    public Payment() {}
//
//    public Payment(int id, String text) {
//        this.id = id;
//        this.text = text;
//    }
//
//    @Override
//    public int getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//}
//class AbonentDAO extends AbstractDAO<Abonent> {
//    public AbonentDAO(Connection connection) {
//        super(connection);
//    }
//    //реализация методов
//}
//class PaymentDAO extends AbonentDAO<Payment> {
//    public PaymentDAO(Connection connection) {
//        super(connection);
//    }
//}
//
///**
// * Наглядный пример
// */
//public class SomeLogic {
//    public void doLogic(int id) throws SQLException {
//        //1. Создаение-получени соединения
//        Connection connection = ConnectionPool.getConnection();
//        //2. Открытиые транзакции
//        connection.setAutoCommit(false);
//        //3. Инициализация необходимых экземпляров DAO
//        AbonentDAO abonentDAO = new AbonentDAO(connection);
//        PaymentDAO paymentDAO = new PaymentDAO(connection);
//        //4. Выполение запросов
//        abonentDAO.findAll();
//        paymentDAO.findEntityById(id);
//        //5. Закрытие транзакции
//        connection.commit();
//        //6. Закрытие-возвращаение соедиения
//        ConnctionPool.close(connection);
//    }
//}
//
//
//
