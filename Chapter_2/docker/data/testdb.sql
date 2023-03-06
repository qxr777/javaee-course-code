SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `FOO`;
CREATE TABLE `FOO` (
                            `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                            `BAR` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

INSERT INTO FOO (ID, BAR) VALUES (1, 'aaa');
INSERT INTO FOO (ID, BAR) VALUES (2, 'bbb');
