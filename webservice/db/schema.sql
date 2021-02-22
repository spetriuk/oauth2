CREATE TABLE IF NOT EXISTS user_roles
(
    id       int IDENTITY,
    name varchar(100)       NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_accounts
(
    id         int IDENTITY,
    login varchar(100)      NOT NULL UNIQUE,
    password varchar(255)   NOT NULL,
    email varchar(100)      NOT NULL UNIQUE,
    first_name varchar(100) NOT NULL,
    last_name varchar(100)  NOT NULL,
    birthday date,
    role_id int NOT NULL,
    FOREIGN KEY (role_id) REFERENCES user_roles (id) ON DELETE CASCADE
);

