CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      username VARCHAR(255),
                      email VARCHAR(255)
);

CREATE TABLE customer (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(255),
                          last_name VARCHAR(255),
                          email VARCHAR(255),
                          phone VARCHAR(255),
                          address VARCHAR(255),
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES user(id)
);