DROP DATABASE IF EXISTS `eclinic`;
CREATE DATABASE  IF NOT EXISTS `eclinic` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `eclinic`;

DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `createdDateTime` datetime(6),
                             `updatedDateTime` datetime(6),
                             `version`         int(11),
                             `name` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_authority_name` (`name`)
);

DROP TABLE IF EXISTS `diagnosis`;
CREATE TABLE `diagnosis` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `createdDateTime` datetime(6),
                             `updatedDateTime` datetime(6),
                             `version`         int(11),
                             `name` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_authority_name` (`name`)
);

DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `createdDateTime` datetime(6),
                           `updatedDateTime` datetime(6),
                           `version`         int(11),
                           `fullName` varchar(255) DEFAULT NULL,
                           `insuranceNumber` varchar(255) DEFAULT NULL,
                           `patientStatus` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_patient_fullName_insuranceNumber` (`fullName`,`insuranceNumber`)
);

DROP TABLE IF EXISTS `timePattern`;
CREATE TABLE `timePattern` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `createdDateTime` datetime(6),
                           `updatedDateTime` datetime(6),
                           `version`         int(11),
                           `cycleLength` smallint(6) DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `isWeekCycle` bit(1) DEFAULT NULL,
                           PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `createdDateTime` datetime(6),
                             `updatedDateTime` datetime(6),
                             `version`         int(11),
                             `name` varchar(255) DEFAULT NULL,
                             `type` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_treatment_name` (`name`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `createdDateTime` datetime(6),
                        `updatedDateTime` datetime(6),
                        `version`         int(11),
                        `fullName` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK_user_username` (`username`),
                        UNIQUE KEY `UK_user_fullName` (`fullName`)
);

DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority` (
                                  `user_id` bigint(20) NOT NULL,
                                  `createdDateTime` datetime(6),
                                  `updatedDateTime` datetime(6),
                                  `version`         int(11),
                                  `authority_id` bigint(20) NOT NULL,
                                  PRIMARY KEY (`user_id`,`authority_id`),
                                  KEY `FK_authority` (`authority_id`),
                                  CONSTRAINT `FK_authority` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
                                  CONSTRAINT `FK_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `timePatternItem`;
CREATE TABLE `timePatternItem` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `createdDateTime` datetime(6),
                               `updatedDateTime` datetime(6),
                               `version`         int(11),
                               `dayOfCycle` smallint(6) DEFAULT NULL,
                               `time` time DEFAULT NULL,
                               `timePattern_id` bigint(20) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK_pattern` (`timePattern_id`),
                               CONSTRAINT `FK_pattern` FOREIGN KEY (`timePattern_id`) REFERENCES `timePattern` (`id`)
);

DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `createdDateTime` datetime(6),
                                `updatedDateTime` datetime(6),
                                `version`         int(11),
                                `dosage` float DEFAULT NULL,
                                `duration` smallint(6) DEFAULT NULL,
                                `doctor_id` bigint(20) DEFAULT NULL,
                                `patient_id` bigint(20) DEFAULT NULL,
                                `timePattern_id` bigint(20) DEFAULT NULL,
                                `treatment_id` bigint(20) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FK_doctor` (`doctor_id`),
                                KEY `FK_treatment` (`treatment_id`),
                                KEY `FK_timePattern` (`timePattern_id`),
                                CONSTRAINT `FK_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`id`),
                                CONSTRAINT `FK_timePattern` FOREIGN KEY (`timePattern_id`) REFERENCES `timePattern` (`id`),
                                CONSTRAINT `FK_treatment` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`)
);

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `createdDateTime` datetime(6),
                         `updatedDateTime` datetime(6),
                         `version`         int(11),
                         `dateTime` datetime(6) DEFAULT NULL,
                         `eventStatus` varchar(255) DEFAULT NULL,
                         `patient_id` bigint(20) DEFAULT NULL,
                         `prescription_id` bigint(20) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FK_patient` (`patient_id`),
                         KEY `FK_prescription` (`prescription_id`),
                         CONSTRAINT `FK_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
                         CONSTRAINT `FK_prescription` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`)
);

