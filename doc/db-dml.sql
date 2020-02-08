 
-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '性别', 'sex', '0', '女', '0', null, '0', null, null, null, null);
INSERT INTO `sys_dict` VALUES ('2', '性别', 'sex', '1', '男', '1', null, '0', null, null, null, null);
INSERT INTO `sys_dict` VALUES ('3', '性别', 'sex', '2', '未知', '2', null, '0', null, null, null, null);
INSERT INTO `sys_dict` VALUES ('4', '步骤', 'step', '0', '开始进行', '0', null, '0', null, null, null, null);
INSERT INTO `sys_dict` VALUES ('5', '步骤', 'step', '1', '正在处理', '1', null, '0', null, null, null, null);
INSERT INTO `sys_dict` VALUES ('6', '步骤', 'step', '2', '执行结束', '2', null, '0', null, null, null, null);
INSERT INTO `sys_dict` VALUES ('9AF87327-D5B8-4473-A6AE-7C754AC5B989', '步骤', 'step', '6', '测试过程0204', '99', '修改操作测试', '-1', 'admin', '2020-02-04 15:15:33', 'admin', '2020-02-04 15:33:20');
 
-- ----------------------------
-- Records of sys_user （密码初始化为123456，使用bCryptPasswordEncoder加密后存储，自动加盐处理）
-- ----------------------------
INSERT INTO `sys_user` VALUES ('7C6B7434-66AD-49E2-9BE4-1619F908ACF7', 'bbq', '$2a$10$gm9c0WEe3b2Kdxyz.vLve.60nFsPVYaZAC7yVgHm9.8YF5Uhw4rp.', null, '17765412345', '0001', 'admin', '2020-02-08 19:41:33', null, null);
INSERT INTO `sys_user` VALUES ('B6FDBE03-DEFF-4F29-AB03-72C02FC1E068', 'user1', '$2a$10$LKsQMbCRUmQGWH0iyPZxSueuN6ukilJczTIrQQBg1os7VUc/En4R.', null, null, '0001', 'admin', '2020-02-07 00:31:32', null, null);

 