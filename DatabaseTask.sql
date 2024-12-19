

/*
Database query (Should be done completely)
Given the below tables:
User table:
user_id username
1 John Doe
2 Jane Don
3 Alice Jones
4 Lisa Romero
Training_details table:
user_training_id user_id training_id training_date
1 1 1 "2015-08-02"
2 2 1 "2015-08-03"
3 3 2 "2015-08-02"
4 4 2 "2015-08-04"
5 2 2 "2015-08-03"
6 1 1 "2015-08-02"
7 3 2 "2015-08-04"
8 4 3 "2015-08-03"
9 1 4 "2015-08-03"
10 3 1 "2015-08-02"
11 4 2 "2015-08-04"
12 3 2 "2015-08-02"
13 1 1 "2015-08-02"
14 4 3 "2015-08-03"
Query to get the list of users who took the training lesson more than once at the same day,
grouped by user and training lesson, each ordered from the most recent lesson date to oldest
date.
Output:
user_id username training_id training_date count
4 Lisa Romero 2 2015-08-04 2
4 Lisa Romero 3 2015-08-03 2
1 John Doe 1 2015-08-02 3
3 Alice Jones 2 2015-08-02 2


 CREATE TABLE "User" (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);


 CREATE TABLE training_details (
    user_training_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    training_id INT NOT NULL,
    training_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "User"(user_id)
);


 INSERT INTO "User" (username) VALUES
('John Doe'),
('Jane Don'),
('Alice Jones'),
('Lisa Romero');


 INSERT INTO training_details (user_id, training_id, training_date) VALUES
(1, 1, '2015-08-02'),
(2, 1, '2015-08-03'),
(3, 2, '2015-08-02'),
(4, 2, '2015-08-04'),
(2, 2, '2015-08-03'),
(1, 1, '2015-08-02'),
(3, 2, '2015-08-04'),
(4, 3, '2015-08-03'),
(1, 4, '2015-08-03'),
(3, 1, '2015-08-02'),
(4, 2, '2015-08-04'),
(3, 2, '2015-08-02'),
(1, 1, '2015-08-02'),
(4, 3, '2015-08-03');


 */

SELECT u.user_id, u.username, td.training_id, td.training_date,count(*) AS count
FROM  training_details td  JOIN "User" u
ON td.user_id = u.user_id
GROUP BY  u.user_id, u.username, td.training_id, td.training_date
HAVING  COUNT (*)  > 1
ORDER BY   td.training_date DESC;
