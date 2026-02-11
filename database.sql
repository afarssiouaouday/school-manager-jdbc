CREATE DATABASE school_db;
USE school_db;

CREATE TABLE students (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          age INT NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_students_name ON students(name);
