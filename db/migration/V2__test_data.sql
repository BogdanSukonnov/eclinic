SET SCHEMA 'clinic';

INSERT INTO authority (name, version)
VALUES ('ROLE_ADMIN', 1),
       ('ROLE_DOCTOR', 1),
       ('ROLE_NURSE', 1);

INSERT INTO patient (full_name, insurance_number, patient_status, diagnosis, created_datetime, version)
VALUES ('Lennon John', '697-28-0304', 'DISCHARGED', 'Hypertension', '2019-10-11 11:30:45', 1),
       ('Mccartney Paul', '763-33-2865', 'PATIENT', 'Hyperlipidemia', '2019-10-11 11:30:45', 1),
       ('Jagger Mick', '133-11-7977', 'PATIENT', 'Diabetes', '2019-10-11 11:30:45', 1),
       ('Dylan Bob', '110-04-2413', 'PATIENT', 'Back pain', '2019-10-11 11:30:45', 1),
       ('Mercury Freddie', '840-05-1085', 'PATIENT', 'Anxiety', '2019-10-11 11:30:45', 1),
       ('Cocker Joe', '350-05-4685', 'DISCHARGED', 'Obesity', '2019-10-11 11:30:45', 1),
       ('Ray Charles', '350-06-4685', 'DISCHARGED', 'Allergic rhinitis', '2019-10-11 11:30:45', 1);


INSERT INTO time_pattern (cycle_length, name, is_week_cycle, version)
VALUES (7, 'two days a week: Tu Th', true, 1),
       (1, 'twice a day: morning evening', false, 1),
       (1, 'every day: 2PM', false, 1);


INSERT INTO time_pattern_item (day_of_cycle, time, time_pattern_id, version)
VALUES (1, '10:00', 1, 1),
       (3, '10:00', 1, 1),
       (0, '09:00', 2, 1),
       (0, '18:00', 2, 1),
       (0, '14:00', 3, 1);


INSERT INTO treatment (name, type, version)
VALUES ('levothyroxine', 'Medicine', 1),
       ('rosuvastatin', 'Medicine', 1),
       ('albuterol', 'Medicine', 1),
       ('esomeprazole', 'Medicine', 1),
       ('fluticasone', 'Medicine', 1),
       ('ultrasound', 'Procedure', 1),
       ('electrical stimulation', 'Procedure', 1),
       ('traction', 'Procedure', 1),
       ('laser or therapy', 'Procedure', 1),
       ('hydrotherapy', 'Procedure', 1);

--  all passwords is 123
INSERT INTO app_user (username, full_name, password, version)
VALUES ('perry', 'Cox Percival', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1),
       ('jd', 'Dorian John', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1),
       ('turk', 'Turk Christopher', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1),
       ('elly', 'Reid Elliot', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1),
       ('bob', 'Kelso Robert', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1),
       ('admin', 'admin', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1),
       ('carla', 'Espinosa Carla', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe', 1);


INSERT INTO app_user_authority (app_user_id, authority_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 1),
       (7, 3);


INSERT INTO prescription (created_datetime, dosage, duration, doctor_id, patient_id, time_pattern_id, treatment_id, version, prescription_status)
VALUES ('2019-10-11 11:30:45', '1.3 g', 14, 1, 2, 2, 4, 1, 'ACTIVE'),
       ('2019-10-13 12:07:28', null, 7, 2, 4, 3, 6, 1, 'ACTIVE'),
       ('2019-10-15 13:43:19', '200 ml', 7, 4, 6, 1, 1, 1, 'ACTIVE');

