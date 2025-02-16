DROP DATABASE IF EXISTS `user_database`;
CREATE DATABASE `user_database`;
USE `user_database`;
DROP TABLE IF EXISTS `expense`;
CREATE TABLE `expense` (
                        `expenseID` int(11) NOT NULL AUTO_INCREMENT,
                        `title` varchar(50) NOT NULL,
                        `category` varchar(50) NOT NULL,
                        `amount` double NOT NULL,
                        `dateIncurred` DATE NOT NULL,
                        PRIMARY KEY  (`expenseID`)
);

DROP TABLE IF EXISTS `income`;

CREATE TABLE `income` (
                           `incomeID` int(11) NOT NULL AUTO_INCREMENT,
                           `title` varchar(50) NOT NULL,
                           `amount` double NOT NULL,
                           `dateEarned` DATE NOT NULL,
                           PRIMARY KEY  (`incomeID`)
);


-- Adding records to the expense table
INSERT INTO `expense` (`title`, `category`, `amount`, `dateIncurred`)
VALUES
    ('Grocery Shopping', 'Food', 150.75, '2025-02-10'),
    ('Monthly Rent', 'Housing', 1200.00, '2025-02-01');

-- Adding records to the income table
INSERT INTO `income` (`title`, `amount`, `dateEarned`)
VALUES
    ('Freelance Project', 2000.00, '2025-02-05'),
    ('Salary', 3500.00, '2025-02-01');
