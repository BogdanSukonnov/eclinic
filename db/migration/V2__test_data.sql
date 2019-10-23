SET SCHEMA 'clinic';

INSERT INTO authority (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_DOCTOR'),
       ('ROLE_NURSE');

INSERT INTO patient (full_name, insurance_number, patient_status, diagnosis, created_datetime)
VALUES ('Lennon John', '697-28-0304', 'DISCHARGED', 'Hypertension', '2019-10-11 11:30:45'),
       ('Mccartney Paul', '763-33-2865', 'PATIENT', 'Hyperlipidemia', '2019-10-11 11:30:45'),
       ('Jagger Mick', '133-11-7977', 'PATIENT', 'Diabetes', '2019-10-11 11:30:45'),
       ('Dylan Bob', '110-04-2413', 'PATIENT', 'Back pain', '2019-10-11 11:30:45'),
       ('Mercury Freddie', '840-05-1085', 'PATIENT', 'Anxiety', '2019-10-11 11:30:45'),
       ('Cocker Joe', '350-05-4685', 'DISCHARGED', 'Obesity', '2019-10-11 11:30:45'),
       ('Ray Charles', '350-06-4685', 'DISCHARGED', 'Allergic rhinitis', '2019-10-11 11:30:45');


INSERT INTO time_pattern (cycle_length, name, is_week_cycle)
VALUES (7, 'two days a week: Tu Th', true),
       (1, 'twice a day: morning evening', false),
       (1, 'every day: 2PM', false);


INSERT INTO time_pattern_item (day_of_cycle, time, time_pattern_id)
VALUES (2, '10:00', 1),
       (4, '10:00', 1),
       (1, '09:00', 2),
       (1, '18:00', 2),
       (1, '14:00', 3);


INSERT INTO treatment (name, type)
VALUES ('levothyroxine', 'Medicine'),
       ('rosuvastatin', 'Medicine'),
       ('albuterol', 'Medicine'),
       ('esomeprazole', 'Medicine'),
       ('fluticasone', 'Medicine'),
       ('ultrasound', 'Procedure'),
       ('electrical stimulation', 'Procedure'),
       ('traction', 'Procedure'),
       ('laser or therapy', 'Procedure'),
       ('hydrotherapy', 'Procedure');

--  all passwords is 123
INSERT INTO app_user (username, full_name, password)
VALUES ('perry', 'Cox Percival', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       ('jd', 'Dorian John', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       ('turk', 'Turk Christopher', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       ('elly', 'Reid Elliot', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       ('bob', 'Kelso Robert', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       ('admin', 'admin', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       ('carla', 'Espinosa Carla', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe');


INSERT INTO app_user_authority (app_user_id, authority_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 1),
       (7, 3);


INSERT INTO prescription (created_datetime, dosage, duration, doctor_id, patient_id, time_pattern_id, treatment_id)
VALUES ('2019-10-11 11:30:45', '1.3 g', 14, 1, 2, 2, 4),
       ('2019-10-13 12:07:28', null, 7, 2, 4, 3, 6),
       ('2019-10-15 13:43:19', '200 ml', 7, 4, 6, 1, 1);

