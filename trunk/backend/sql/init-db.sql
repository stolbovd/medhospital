CREATE DATABASE `hospital` CHARACTER SET utf8;

USE hospital;

DROP TABLE IF EXISTS `hospital`.PATIENT;
DROP TABLE IF EXISTS `hospital`.PATIENT_ATTRIBUTE;
DROP TABLE IF EXISTS `hospital`.`PATIENT_ATTRIBUTE_VALUE`;

CREATE TABLE  `hospital`.`PATIENT` (
  `PK_ENTITY_ID` varchar(36) NOT NULL,
  PRIMARY KEY `PK_PATIENT_ID_INDEX` (`PK_ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `hospital`.`PATIENT_ATTRIBUTE` (
  `PK_ENTITY_ID` varchar(36) NOT NULL,
  `TYPE` varchar(100),
  `NAME` varchar(40) NOT NULL,
  `VALUE_TYPE` varchar(50),
  UNIQUE KEY `NAME_UNIQ_KEY` (`NAME`),
  PRIMARY KEY `PK_PATIENT_ATTRIBUTE_ID_INDEX` (`PK_ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `hospital`.`PATIENT_ATTRIBUTE_VALUE` (
  `FK_ATTRIBUTE_ID` varchar(36) NOT NULL,
  `FK_ENTITY_ID` varchar(36) NOT NULL,
  `VALUE` varchar(400) DEFAULT NULL,
  UNIQUE KEY `ENTITY_ATTRIBUTE_UNIQ_KEY` (`FK_ATTRIBUTE_ID`,`FK_ENTITY_ID`),
  CONSTRAINT `FK_ATTRIBUTE_ID_KEY` FOREIGN KEY (`FK_ATTRIBUTE_ID`) REFERENCES `patient_attribute` (`PK_ENTITY_ID`),
  CONSTRAINT `FK_ENTITY_ID_KEY` FOREIGN KEY (`FK_ENTITY_ID`) REFERENCES `patient` (`PK_ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `hospital`.`MESSAGE` (
  `PK_ENTITY_ID` varchar(36) NOT NULL,
  `JSON` varchar(4000) NOT NULL,
  PRIMARY KEY `PK_MESSAGE_ID_INDEX` (`PK_ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;