CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Id，自增',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `nickName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `personalitySign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个性签名',
  `phoneNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `onlineState` int(1) unsigned zerofill DEFAULT '0' COMMENT '在线状态',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地区',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `historySessionList` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '历史会话列表',
  `outTradeNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'socket.id',
  `createTime` datetime(6) DEFAULT NULL COMMENT '账号创建时间',
  `accountStatus` int NOT NULL COMMENT '账号状态： 0： 注销，1：正常，2：禁用',
  PRIMARY KEY (`id`,`userName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

INSERT INTO `user`(`id`, `userName`, `nickName`, `password`, `personalitySign`, `phoneNumber`, `onlineState`, `region`, `avatar`, `email`, `historySessionList`, `outTradeNo`, `createTime`, `accountStatus`) VALUES (1, 'wanghao', '王浩', 'wanghao', '不知道是什么字段personalitySign', '13202456211', 0, '法国 巴黎', 'https://vkceyugu.cdn.bspapp.com/VKCEYUGU-e9420bcd-26cd-4faf-b47a-9949982f7c41/6dcf66fe-eb0f-42d8-9893-10dc48e67555.jpeg', 'wanghao', '', 'GWJ6-_vGt4f58V_NAAAB', '2022-12-21 00:00:00.000000', 0);
INSERT INTO `user`(`id`, `userName`, `nickName`, `password`, `personalitySign`, `phoneNumber`, `onlineState`, `region`, `avatar`, `email`, `historySessionList`, `outTradeNo`, `createTime`, `accountStatus`) VALUES (70, 'ceshi4', '测试4', 'doudou', '临渊羡鱼，不如退而结网！', '17302955696', 0, NULL, 'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', 'dumplingsir@gmail.com', NULL, NULL, '2022-12-21 00:59:57.000000', 1);
INSERT INTO `user`(`id`, `userName`, `nickName`, `password`, `personalitySign`, `phoneNumber`, `onlineState`, `region`, `avatar`, `email`, `historySessionList`, `outTradeNo`, `createTime`, `accountStatus`) VALUES (71, 'ceshi1', '测试1', 'doudou', '临渊羡鱼，不如退而结网！', '17302955696', 0, '陕西 西安', 'https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF', 'dumplingsir@gmail.com', NULL, NULL, '2022-12-21 01:02:55.000000', 1);
