/*CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName  VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    dateOfBirth DATE NOT NULL
);
*/
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL
    );

CREATE TABLE IF NOT EXISTS Item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_open BOOLEAN NOT NULL,
    status INT NOT NULL CHECK (status BETWEEN 1 AND 4),
    description VARCHAR(50) NOT NULL,
    "condition" INT NOT NULL CHECK ("condition" BETWEEN 1 AND 3)
    );

-- SQL statements to create the coworker table
CREATE TABLE IF NOT EXISTS coworker (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(50) NOT NULL,
    hourly_rate DOUBLE NOT NULL,
    available BOOLEAN DEFAULT TRUE
    );
UPDATE coworker SET available = TRUE WHERE available IS NULL;
ALTER TABLE coworker ADD COLUMN IF NOT EXISTS available BOOLEAN DEFAULT TRUE;

-- SQL statements to create the workingstation table
CREATE TABLE IF NOT EXISTS workingstation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL
    );
ALTER TABLE workingstation ADD COLUMN IF NOT EXISTS available BOOLEAN DEFAULT TRUE;

-- SQL statements to create the material table
CREATE TABLE IF NOT EXISTS material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    stock INT NOT NULL,
    price DOUBLE NOT NULL,
    unit VARCHAR(20) NOT NULL
    );

