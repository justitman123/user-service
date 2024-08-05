CREATE TABLE user_table (
                            id VARCHAR(255) NOT NULL,
                            ldap_login VARCHAR(255) NOT NULL,
                            name VARCHAR(255),
                            surname VARCHAR(255)
);

INSERT INTO user_table (id, ldap_login, name, surname) VALUES
                                                           (1, 'some_username1', 'John', 'some_surname1'),
                                                           (2, 'some_username1', 'Alice', 'some_surname'),
                                                           (3, 'bjones', 'some_name1', 'some_surname1'),
                                                           (4, 'cjohnson', 'Carol', 'Johnson'),
                                                           (5, 'dlee', 'David', 'Lee');