CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE wallets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    balance DECIMAL(19,2) NOT NULL,
    CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transactions (
    id UUID AUTO_INCREMENT PRIMARY KEY,
    payer_id BIGINT NOT NULL,
    payee_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    status VARCHAR(10) NOT NULL,
    CONSTRAINT fk_tx_payer FOREIGN KEY (payer_id) REFERENCES users(id),
    CONSTRAINT fk_tx_payee FOREIGN KEY (payee_id) REFERENCES users(id)
);