-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2011 年 04 月 14 日 06:08
-- 服务器版本: 5.5.8
-- PHP 版本: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `elevator-managerial`
--

-- --------------------------------------------------------

--
-- 表的结构 `elevator`
--

CREATE TABLE IF NOT EXISTS `elevator` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `eId` int(11) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;




