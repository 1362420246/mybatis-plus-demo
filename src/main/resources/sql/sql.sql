--DROP TABLE IF EXISTS user;

CREATE TABLE If Not Exists user
(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user`
(`id`,`name`,`age`,`email`)
VALUES
	( 1, 'Jone', 18, 'test1@baomidou.com' ),
	( 2, 'Jack', 20, 'test2@baomidou.com' ),
	( 3, 'Tom', 28, 'test3@baomidou.com' ),
	( 4, 'Sandy', 21, 'test4@baomidou.com' ),
	( 5, 'Billie', 24, 'test5@baomidou.com' );
