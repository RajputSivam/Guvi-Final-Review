create database userappdb;

use userappdb;

create table users
(
id integer auto_increment primary key,
uname varchar (100) not null,
email varchar (50) not null unique ,
country varchar (50 not null ,
passwd varchar (20) not null 
);

--Login Attempts Table:
CREATE TABLE login_attempts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    attempt_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    success BOOLEAN NOT NULL,
    ip_address VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--Transactions Table:
CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_amount DECIMAL(10, 2) NOT NULL,
    transaction_type VARCHAR(50),  -- e.g., deposit, withdrawal, etc.
    status VARCHAR(20),  -- e.g., completed, pending, failed
    transaction_details TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
--Suspicious Activities Table:
CREATE TABLE suspicious_activities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    activity_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activity_type VARCHAR(100),  -- e.g., multiple failed logins, high-value transactions
    description TEXT,
    resolved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
--Fraud Detection Rules Table:
CREATE TABLE fraud_detection_rules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(100) NOT NULL,
    rule_description TEXT,
    rule_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--Session History Table:
CREATE TABLE session_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP,
    device_type VARCHAR(50),  -- e.g., Desktop, Mobile
    ip_address VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
--