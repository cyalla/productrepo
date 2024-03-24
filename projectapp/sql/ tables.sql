USE my_schema;

CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    address VARCHAR(255)
);
