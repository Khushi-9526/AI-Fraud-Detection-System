package com.fraud.dao;

import com.fraud.database.DBConnection;
import com.fraud.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAO {

    // Save transaction into database
    public int saveTransaction(Transaction transaction)
            throws SQLException {

        String sql =
                "INSERT INTO transactions " +
                "(user_id, amount, location, transaction_type, " +
                "international, device) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    transaction.getUserId()
            );

            statement.setDouble(
                    2,
                    transaction.getAmount()
            );

            statement.setString(
                    3,
                    transaction.getLocation()
            );

            statement.setString(
                    4,
                    transaction.getTransactionType()
            );

            statement.setBoolean(
                    5,
                    transaction.isInternational()
            );

            statement.setString(
                    6,
                    transaction.getDevice()
            );

            statement.executeUpdate();

            // Get generated transaction ID
            try (ResultSet result =
                         statement.getGeneratedKeys()) {

                if (result.next()) {

                    int id = result.getInt(1);

                    transaction.setTransactionId(id);

                    return id;
                }
            }
        }

        return -1;
    }


    // Save fraud detection result
    public void saveFraudResult(
            int transactionId,
            double riskScore,
            String riskLevel,
            String prediction,
            String reason)
            throws SQLException {

        String sql =
                "INSERT INTO fraud_results " +
                "(transaction_id, risk_score, risk_level, " +
                "prediction, reason) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    transactionId
            );

            statement.setDouble(
                    2,
                    riskScore
            );

            statement.setString(
                    3,
                    riskLevel
            );

            statement.setString(
                    4,
                    prediction
            );

            statement.setString(
                    5,
                    reason
            );

            statement.executeUpdate();
        }
    }
}
