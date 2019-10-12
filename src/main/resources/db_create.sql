
DROP DATABASE eclinic;

CREATE DATABASE eclinic;

use eclinic;

CREATE TABLE `authority`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `diagnosis`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `event`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `dateTime`        datetime     DEFAULT NULL,
    `eventStatus`     varchar(255) DEFAULT NULL,
    `patient_id`      bigint(20)   DEFAULT NULL,
    `prescription_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_patient` (`patient_id`),
    KEY `FK_prescription` (`prescription_id`)
);

CREATE TABLE `patient`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `fullName`        varchar(255) DEFAULT NULL,
    `insuranceNumber` varchar(255) DEFAULT NULL,
    `patientStatus`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `pattern`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `cycleLength` smallint(6)  DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `isWeekCycle` bit(1)       DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `patternitem`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `dayOfCycle` smallint(6) DEFAULT NULL,
    `time`       time        DEFAULT NULL,
    `pattern_id` bigint(20)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_pattern` (`pattern_id`)
);

CREATE TABLE `prescription`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `creationDateTime` datetime    DEFAULT NULL,
    `dosage`           float       DEFAULT NULL,
    `duration`         smallint(6) DEFAULT NULL,
    `doctor_id`        bigint(20)  DEFAULT NULL,
    `patient_id`       bigint(20)  DEFAULT NULL,
    `pattern_id`       bigint(20)  DEFAULT NULL,
    `treatment_id`     bigint(20)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_doctor` (`doctor_id`),
    KEY `FK_patient` (`patient_id`),
    KEY `FK_pattern` (`pattern_id`),
    KEY `FK_treatment` (`treatment_id`)
);

CREATE TABLE `treatment`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `treatmentName` varchar(255) DEFAULT NULL,
    `treatmentType` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `password` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `fullName` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user_authority`
(
    `user_id`      bigint(20) NOT NULL,
    `authority_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`, `authority_id`),
    KEY `FK_authority` (`authority_id`)
);


