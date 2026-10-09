CREATE DATABASE IF NOT EXISTS fraud_detection;

USE fraud_detection;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    amount DOUBLE NOT NULL,
    location VARCHAR(100),
    transaction_type VARCHAR(50),
    international BOOLEAN,
    device VARCHAR(50),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE fraud_results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_id INT,
    risk_score DOUBLE,
    risk_level VARCHAR(30),
    prediction VARCHAR(50),
    reason VARCHAR(500),

    FOREIGN KEY (transaction_id)
    REFERENCES transactions(transaction_id)
);
