use eclinic;

DELETE
FROM authority;
INSERT INTO authority (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_DOCTOR'),
       (3, 'ROLE_NURSE');

DELETE
FROM diagnosis;
INSERT INTO diagnosis (id, name)
VALUES (1, 'Hypertension'),
       (2, 'Hyperlipidemia'),
       (3, 'Diabetes'),
       (4, 'Back pain'),
       (5, 'Anxiety'),
       (6, 'Obesity'),
       (7, 'Allergic rhinitis'),
       (8, 'Reflux esophagitis');

# DELETE FROM event;
# INSERT INTO event (id, dateTime, eventStatus, patient_id, prescription_id)

DELETE
FROM patient;
INSERT INTO patient (id, fullName, insuranceNumber, patientStatus)
VALUES (1, 'John Lennon', '697-28-0304', 'DISCHARGED'),
       (2, 'Paul Mccartney', 763 - 33 - 2865, 'PATIENT'),
       (3, 'Mick Jagger', '133-11-7977', 'PATIENT'),
       (4, 'Bob Dylan', '110-04-2413', 'PATIENT'),
       (5, 'Freddie Mercury', '840-05-1085', 'PATIENT'),
       (6, 'joe cocker', '350-05-4685', 'DISCHARGED');

DELETE
FROM pattern;
INSERT INTO pattern (id, cycleLength, description, isWeekCycle)
VALUES (1, 7, 'two days a week: Tu Th', true),
       (2, 1, 'twice a day: morning evening', false),
       (3, 1, 'every day: 2PM', false);

DELETE
FROM patternitem;
INSERT INTO patternitem (id, dayOfCycle, time, pattern_id)
VALUES (1, 2, '10:00', 1),
       (2, 4, '10:00', 1),
       (3, 1, '09:00', 2),
       (4, 1, '18:00', 2),
       (5, 1, '14:00', 3);

DELETE
FROM prescription;
INSERT INTO prescription (id, creationDateTime, dosage, duration, doctor_id, patient_id, pattern_id, treatment_id)
VALUES (1, '2019-10-11 11:30:45', 1.3, 14, 1, 2, 2, 4),
       (2, '2019-10-11 12:07:28', null, 7, 2, 4, 3, 6),
       (3, '2019-10-11 13:43:19', 200, 7, 4, 6, 1, 1);

DELETE
FROM treatment;
INSERT INTO treatment (id, treatmentName, treatmentType)
VALUES (1, 'levothyroxine', 'Medicine'),
       (2, 'rosuvastatin', 'Medicine'),
       (3, 'albuterol', 'Medicine'),
       (4, 'esomeprazole', 'Medicine'),
       (5, 'fluticasone', 'Medicine'),
       (6, 'ultrasound', 'Procedure'),
       (7, 'electrical stimulation', 'Procedure'),
       (8, 'traction', 'Procedure'),
       (9, 'laser or therapy', 'Procedure'),
       (10, 'hydrotherapy', 'Procedure');

# all passwords is 123
SET @pas123 = '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe';

DELETE
FROM user;
INSERT INTO user (id, password, username, fullName)
VALUES (1, @pas123, 'perry', 'Percival Cox'),
       (2, @pas123, 'jd', 'John Dorian'),
       (3, @pas123, 'turk', 'Christopher Turk'),
       (4, @pas123, 'elly', 'Elliot Reid'),
       (5, @pas123, 'bob', 'Robert Kelso'),
       (6, @pas123, 'admin', 'admin'),
       (7, @pas123, 'carla', 'Carla Espinosa');

DELETE
FROM user_authority;
INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 1),
       (7, 3);


