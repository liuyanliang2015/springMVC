/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2018-04-19 18:23:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NAME` varchar(50) NOT NULL COMMENT '姓名',
  `AGE` int(3) NOT NULL DEFAULT '1' COMMENT '年龄',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '13');
INSERT INTO `user` VALUES ('2', '李四', '14');
INSERT INTO `user` VALUES ('3', '王五', '15');
INSERT INTO `user` VALUES ('4', '孙六', '16');
INSERT INTO `user` VALUES ('5', '周七', '17');
INSERT INTO `user` VALUES ('6', '王八', '18');
