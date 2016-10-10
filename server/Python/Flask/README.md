Setup:

1. Have Python and Pip installed
2. pip install Flask
3. install mysql
5. CREATE DATABASE AuthParty;
6. CREATE TABLE `AuthParty`.`tbl_user` (
  `user_id` BIGINT UNIQUE AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL,
  `user_username` VARCHAR(45) NULL,
  `user_password` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`));

7. pip install flask-mysql



4.OSX:
	1. sudo easy_install virtualenv
	2. virtualenv [app name]
	3. virtualenv -p /usr/bin/python env
	4. source env/bin/activate
	5. touch requirements.txt
	6. nano requirements.txt
	7. Add "Flask" to top (no quotes)
	8. pip install -r requirements.txt
	9. python appname.py
