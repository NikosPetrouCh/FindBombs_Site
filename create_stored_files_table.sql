-- SQL Script to create stored_files table
-- Run this in MySQL Workbench

USE findbombsdb;

CREATE TABLE IF NOT EXISTS `stored_files` (
    `file_id` BIGINT NOT NULL AUTO_INCREMENT,
    `original_filename` VARCHAR(255) NOT NULL,
    `content_type` VARCHAR(100) NOT NULL,
    `file_size` BIGINT NOT NULL,
    `file_data` MEDIUMBLOB NOT NULL,
    `uploaded_at` DATETIME NOT NULL,
    `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME,
    PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

