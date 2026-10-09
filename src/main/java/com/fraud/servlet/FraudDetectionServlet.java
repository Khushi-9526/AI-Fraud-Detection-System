package com.fraud.servlet;

import com.fraud.dao.TransactionDAO;
import com.fraud.exception.InvalidTransactionException;
import com.fraud.model.Transaction;
import com.fraud.service.FraudDetectionEngine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/analyze")
public class FraudDetectionServlet extends HttpServlet {

    private final FraudDetectionEngine fraudEngine =
            new FraudDetectionEngine();

    private final TransactionDAO transactionDAO =
            new TransactionDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Get data from the HTML form
            String amountText =
                    request.getParameter("amount");

            String location =
                    request.getParameter("location");

            String transactionType =
                    request.getParameter("transactionType");

            String internationalText =
                    request.getParameter("international");

            String device =
                    request.getParameter("device");

            // Convert amount
            double amount =
                    Double.parseDouble(amountText);

            // Convert international value
            boolean international =
                    "yes".equalsIgnoreCase(
                            internationalText
                    );

            // Create Transaction object
            Transaction transaction =
                    new Transaction(
                            amount,
                            location,
                            transactionType,
                            international,
                            device
                    );

            // Validate transaction
            fraudEngine.validateTransaction(
                    transaction
            );

            // Calculate fraud risk
            double riskScore =
                    fraudEngine.calculateRiskScore(
                            transaction
                    );

            String riskLevel =
                    fraudEngine.getRiskLevel(
                            riskScore
                    );

            String reason =
                    fraudEngine.getReason(
                            transaction
                    );

            // Determine prediction
            String prediction;

            if (riskScore >= 70) {
                prediction = "FRAUD";
            }
            else if (riskScore >= 40) {
                prediction = "SUSPICIOUS";
            }
            else {
                prediction = "SAFE";
            }

            // Save transaction
            int transactionId =
                    transactionDAO.saveTransaction(
                            transaction
                    );

            // Save fraud result
            transactionDAO.saveFraudResult(
                    transactionId,
                    riskScore,
                    riskLevel,
                    prediction,
                    reason
            );

            // Send result to JSP
            request.setAttribute(
                    "transactionId",
                    transactionId
            );

            request.setAttribute(
                    "score",
                    riskScore
            );

            request.setAttribute(
                    "riskLevel",
                    riskLevel
            );

            request.setAttribute(
                    "prediction",
                    prediction
            );

            request.setAttribute(
                    "reason",
                    reason
            );

            request.getRequestDispatcher(
                    "result.jsp"
            ).forward(request, response);

        }

        catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Please enter a valid transaction amount."
            );

            request.getRequestDispatcher(
                    "dashboard.html"
            ).forward(request, response);
        }

        catch (InvalidTransactionException e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "dashboard.html"
            ).forward(request, response);
        }

        catch (SQLException e) {

            throw new ServletException(
                    "Database error occurred.",
                    e
            );
        }
    }
}
