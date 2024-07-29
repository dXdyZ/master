-- Создание таблицы users
CREATE TABLE users (
                       id                    int AUTO_INCREMENT,
                       username              varchar(30) not null unique,
                       password              varchar(80) not null,
                       email                 varchar(50) unique,
                       primary key (id)
);

-- Создание таблицы roles
CREATE TABLE roles (
                       id                    int AUTO_INCREMENT,
                       name                  varchar(50) not null,
                       primary key (id)
);

-- Создание таблицы users_roles с совместимыми типами данных для внешних ключей
CREATE TABLE users_roles (
                             user_id               int not null,
                             role_id               int not null,
                             primary key (user_id, role_id),
                             foreign key (user_id) references users (id),
                             foreign key (role_id) references roles (id)
);

-- Вставка данных в таблицу roles
INSERT INTO roles (name)
VALUES
    ('ROLE_USER'), ('ROLE_ADMIN');

-- Вставка данных в таблицу users
INSERT INTO users (username, password, email)
VALUES
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

-- Вставка данных в таблицу users_roles
INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);