CREATE  TABLE IF NOT EXISTS`question` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `question` VARCHAR(255) NOT NULL ,
  `desciption` VARCHAR(255) NULL ,
  `option_a` VARCHAR(255) NOT NULL ,
  `option_b` VARCHAR(255) NOT NULL ,
  `option_c` VARCHAR(255) NOT NULL ,
  `option_d` VARCHAR(255) NOT NULL ,
  `answer` VARCHAR(45) NOT NULL ,
  `creat_time` DATETIME NULL ,
  `update_time` DATETIME NULL ,
  `delete` TINYINT(1) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci