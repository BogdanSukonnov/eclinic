
INSERT INTO authority (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_DOCTOR'),
       (3, 'ROLE_NURSE');

INSERT INTO patient (id, full_name, insurance_number, patient_status)
VALUES (1, 'Lennon John', '697-28-0304', 'DISCHARGED'),
       (2, 'Mccartney Paul', '763-33-2865', 'PATIENT'),
       (3, 'Jagger Mick', '133-11-7977', 'PATIENT'),
       (4, 'Dylan Bob', '110-04-2413', 'PATIENT'),
       (5, 'Mercury Freddie', '840-05-1085', 'PATIENT'),
       (6, 'Cocker Joe', '350-05-4685', 'DISCHARGED'),
       (7, 'Ray Charles', '350-06-4685', 'DISCHARGED');


INSERT INTO time_pattern (id, cycle_length, name, is_week_cycle)
VALUES (1, 7, 'two days a week: Tu Th', true),
       (2, 1, 'twice a day: morning evening', false),
       (3, 1, 'every day: 2PM', false);


INSERT INTO time_pattern_item (id, day_of_cycle, time, time_pattern_id)
VALUES (1, 2, '10:00', 1),
       (2, 4, '10:00', 1),
       (3, 1, '09:00', 2),
       (4, 1, '18:00', 2),
       (5, 1, '14:00', 3);


INSERT INTO treatment (id, name, type)
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

--  all passwords is 123
INSERT INTO app_user (id, username, full_name, password)
VALUES (1, 'perry', 'Cox Percival', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       (2, 'jd', 'Dorian John', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       (3, 'turk', 'Turk Christopher', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       (4, 'elly', 'Reid Elliot', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       (5, 'bob', 'Kelso Robert', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       (6, 'admin', 'admin', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe'),
       (7, 'carla', 'Espinosa Carla', '$2y$12$vsU2ML4xStW9aeNKKIdL..kJ46D2eLqdGaB8Dtv3ZqA2ktftfazAe');


INSERT INTO app_user_authority (app_user_id, authority_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 1),
       (7, 3);


INSERT INTO prescription (id, created_datetime, dosage, duration, doctor_id, patient_id, time_pattern_id, treatment_id)
VALUES (1, '2019-10-11 11:30:45', '1.3 g', 14, 1, 2, 2, 4),
       (2, '2019-10-13 12:07:28', null, 7, 2, 4, 3, 6),
       (3, '2019-10-15 13:43:19', '200 ml', 7, 4, 6, 1, 1);

