CREATE TABLE `beer` (
	`id` bigint(19) NOT NULL AUTO_INCREMENT,
	`alcohol_content` DOUBLE(20) NOT NULL,
	`category` VARCHAR(150) NOT NULL,
	`ingredients` VARCHAR(200) NOT NULL,
	`name` VARCHAR(80) NOT NULL,
	`price` DOUBLE(20) NOT NULL,
	PRIMARY KEY(`id`)
)