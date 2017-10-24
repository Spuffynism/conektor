DROP SCHEMA IF EXISTS conektor_dev;
CREATE SCHEMA conektor_dev;
USE conektor_dev;

CREATE TABLE user_usr (
  usr_id INT AUTO_INCREMENT PRIMARY KEY,
  usr_username VARCHAR(32) NULL UNIQUE,
  usr_email VARCHAR(255) NULL UNIQUE,
  usr_password VARCHAR(255) NULL,
  usr_attempted_password_changes INT NOT NULL DEFAULT 0,
  usr_permission INT DEFAULT 1, -- Default user permission
  usr_date_created DATETIME DEFAULT CURRENT_TIMESTAMP,
  usr_date_modified DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

CREATE TABLE provider_pro (
  pro_id INT AUTO_INCREMENT PRIMARY KEY,
  pro_name VARCHAR(255) NOT NULL UNIQUE,
  pro_icon_path VARCHAR(255),
  pro_text_color VARCHAR(255),
  pro_panel_color VARCHAR(255),
  pro_date_created DATETIME DEFAULT CURRENT_TIMESTAMP,
  pro_date_modified DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;

CREATE TABLE account_acc (
  acc_id INT AUTO_INCREMENT PRIMARY KEY,
  acc_details TEXT NOT NULL,
  acc_token MEDIUMTEXT NOT NULL,
  acc_user_id INT NOT NULL,
  acc_provider_id INT NOT NULL,
  acc_date_created DATETIME DEFAULT CURRENT_TIMESTAMP,
  acc_date_modified DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_id FOREIGN KEY (acc_user_id)
  REFERENCES user_usr(usr_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT fk_provider_id FOREIGN KEY (acc_provider_id)
  REFERENCES provider_pro(pro_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE TABLE common_passwords_cpa (
  cpa_password VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

LOAD DATA LOCAL INFILE 'C:/g/conektor-api/conf/common_passwords/rockyou-50.txt'
INTO TABLE common_passwords_cpa
FIELDS TERMINATED BY ' '
ENCLOSED BY ''
LINES TERMINATED BY '\n'
(cpa_password);

CREATE INDEX cpa_password_idx ON common_passwords_cpa(cpa_password);

INSERT INTO provider_pro (pro_id, pro_name) VALUES
  (1, 'facebook'),
  (2, 'trello'),
  (3, 'twitter'),
  (4, 'imgur');

INSERT INTO account_acc (acc_details, acc_token, acc_user_id, acc_provider_id) VALUES
  ('{}', 'facebook_test_token', 1, 1),
  ('{}', 'trello_test_token', 1, 2),
  ('{}', 'twitter_test_token', 1, 3);

COMMIT;