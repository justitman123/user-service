CREATE TABLE users (
                       user_id VARCHAR(255) NOT NULL,
                       login VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255)
);

INSERT INTO users (user_id, login, first_name, last_name) VALUES
                                                              ('212asdd', 'some__username', 'some_name1', 'some_surname1'),
                                                              ('21231', 'some_username1', 'some_name1', 'some_surname'),
                                                              ('21233', 'some_username1', 'some_name', 'some_surname1'),
                                                              ('212asdd1', 'some_username1', 'some_name1', 'some_surname1'),
                                                              ('212312', 'some_username2', 'some_name1', 'some_surname');