grant all privileges on *.* to bill@localhost identified by 'testpass' with grant option;
CREATE TABLE user_table (
                       ldap_login INT AUTO_INCREMENT PRIMARY KEY,
                       user_name VARCHAR(255) NOT NULL,
                       name VARCHAR(255),
                       surname VARCHAR(255)
);

INSERT INTO user_table (ldap_login, user_name, name, surname) VALUES
                                                ('11', 'user11', 'Name1', 'Surname1'),
                                                ('22', 'user2', 'Name2', 'Surname2');