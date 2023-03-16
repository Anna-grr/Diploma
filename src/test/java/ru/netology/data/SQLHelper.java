package ru.netology.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLHelper {

    private static QueryRunner runner = new QueryRunner();

    private static String url = System.getProperty("dbUrl");
    private static String username = System.getProperty("dbUsername");
    private static String password = System.getProperty("dbPassword");

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public static String getPaymentStatus(String status) {
        Connection conn = getConn();
        var SQLQuery = "SELECT status FROM payment_entity JOIN order_entity ON transaction_id = payment_id where status = ?";
        return runner.query(conn, SQLQuery, new ScalarHandler<String>(), status);
    }

    @SneakyThrows
    public static void assertPaymentStatus(String status) {
        assertEquals(status, getPaymentStatus(status));
    }

    @SneakyThrows
    public static String getCreditStatus(String status) {
        Connection conn = getConn();
        var SQLQuery = "SELECT status FROM credit_request_entity JOIN order_entity ON bank_id = credit_id where status = ?";
        return runner.query(conn, SQLQuery, new ScalarHandler<String>(), status);
    }

    @SneakyThrows
    public static void assertCreditStatus(String status) {
        assertEquals(status, getCreditStatus(status));
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentEntity {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditRequestEntity {
        private String id;
        private String bank_id;
        private String created;
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderEntity {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }
}
