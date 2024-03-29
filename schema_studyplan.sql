DROP TABLE IF EXISTS goals CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS managements CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  user_id SERIAL NOT NULL,
  authority VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS books (
  id SERIAL NOT NULL,
  user_id SERIAL NOT NULL,
  title VARCHAR(255) NOT NULL,
  estimatedcompletion_date DATE NOT NULL,
  pages INT NOT NULL,
  starting_date DATE NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE books ADD CONSTRAINT FK_users_books FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE IF NOT EXISTS managements (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  completion_date DATE NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE managements ADD CONSTRAINT FK_managements_users FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE managements ADD CONSTRAINT FK_managements_books FOREIGN KEY (book_id) REFERENCES books;

CREATE TABLE IF NOT EXISTS goals (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  description VARCHAR(1000) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE goals ADD CONSTRAINT FK_users_goals FOREIGN KEY (user_id) REFERENCES users;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO studyplan;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO studyplan;