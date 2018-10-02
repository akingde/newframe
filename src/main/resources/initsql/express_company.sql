/*
Navicat MySQL Data Transfer

Source Server         : 172.16.100.30rent
Source Server Version : 50722
Source Host           : 172.16.100.30:3306
Source Database       : rent_phone

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-02 09:58:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for express_company
-- ----------------------------
DROP TABLE IF EXISTS `express_company`;
CREATE TABLE `express_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(30) NOT NULL,
  `company_code` varchar(30) NOT NULL COMMENT '轨迹查询',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='快递鸟支持的公司编码表';

-- ----------------------------
-- Records of express_company
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
