drop table if exists `order_message`;
CREATE TABLE `order_message` (
  `id` bigint(20) NOT NULL,
	exchange_name varchar(100) DEFAULT NULL COMMENT '交换机名称',
	routing_key varchar(100) DEFAULT NULL COMMENT '绑定key',
	queue_name varchar(100) DEFAULT NULL COMMENT '队列名称',
	message varchar(100) DEFAULT NULL COMMENT '消息json格式化',
	`status_id` int(11) not null DEFAULT '0' COMMENT '投递状态：0投递失败、1投递成功、2已消费、3投递中',
	`try_count` int(11) not null DEFAULT '0' COMMENT '重试次数',
	`creat_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';