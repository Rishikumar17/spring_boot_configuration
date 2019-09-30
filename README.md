# spring_boot_configuration
This repository contains the configuration for connecting a spring boot application with MySQL database.

#Steps for using this repository
1) Build the project to download its dependencies.
2) Create the Database "Springboot"
3) Use the database "use Springboot"
4) Create the table 

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_id`)
)
5) Run Config.java 
