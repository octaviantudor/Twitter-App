ALTER TABLE `replies` ADD COLUMN `user_id` BIGINT(20);
ALTER TABLE `replies` ADD FOREIGN KEY (user_id) REFERENCES users(id);