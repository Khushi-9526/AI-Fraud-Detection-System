package com.fraud.dao;

import com.fraud.database.DBConnection;
import com.fraud.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository
        implements Repository<Transaction> {

    private final TransactionDAO transactionDAO =
            new TransactionDAO();

    @Override
    public void save(Transaction transaction)
            throws Exception {

        transactionDAO.saveTransaction(transaction);
    }

    @Override
    public Transaction findById(int id)
            throws Exception {

        String sql =
                "SELECT * FROM transactions " +
                "WHERE transaction_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (result.next()) {

                    return createTransaction(result);
                }
            }
        }

        return null;
    }

    @Override
    public List<Transaction> findAll()
            throws Exception {

        List<Transaction> transactions =
                new ArrayList<>();

        String sql =
                "SELECT * FROM transactions " +
                "ORDER BY transaction_id DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

                transactions.add(
                        createTransaction(result)
                );
            }
        }

        return transactions;
    }

    private Transaction createTransaction(
            ResultSet result)
            throws Exception {

        Transaction transaction =
                new Transaction();

        transaction.setTransactionId(
                result.getInt("transaction_id")
        );

        transaction.setUserId(
                result.getInt("user_id")
        );

        transaction.setAmount(
                result.getDouble("amount")
        );

        transaction.setLocation(
                result.getString("location")
        );

        transaction.setTransactionType(
                result.getString("transaction_type")
        );

        transaction.setInternational(
                result.getBoolean("international")
        );

        transaction.setDevice(
                result.getString("device")
        );

        return transaction;
    }
}
