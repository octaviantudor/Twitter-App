ALTER TABLE `users` ADD COLUMN `is_deleted` BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE `posts` ADD COLUMN `is_deleted` BOOLEAN NOT NULL DEFAULT false;;

ALTER TABLE `replies` ADD COLUMN `is_deleted` BOOLEAN NOT NULL DEFAULT false;;

ALTER TABLE `mentions` ADD COLUMN `is_deleted` BOOLEAN NOT NULL DEFAULT false;;

ALTER TABLE `likes` ADD COLUMN`is_deleted` BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE `follows` ADD COLUMN `is_deleted` BOOLEAN NOT NULL DEFAULT false;

