CREATE DATABASE database1;


\c database1

-- Создание таблицы
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       login VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255)
);

-- Вставка данных
INSERT INTO users (login, first_name, last_name) VALUES
                                                ('user1', 'Name1', 'Surname1'),
                                                ('user2', 'Name2', 'Surname2');