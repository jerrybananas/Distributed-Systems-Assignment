-- Sample data for Pet Registry System
-- Author: it21542 - Antonis Rouseas

-- Insert sample users with consistent numeric ID format
-- ID Format: [1=Admin][2=Citizen][3=Vet] + 6-digit sequence
INSERT IGNORE INTO user (id_number, user_name, password, role) VALUES
('000000001', 'admin', 'pass', 'ADMIN'),
('000000002', 'citizen', 'pass', 'CITIZEN'),
('000000004', 'john_doe', 'pass', 'CITIZEN'),
('000000005', 'jane_smith', 'pass', 'CITIZEN'),
('000000003', 'vet', 'pass', 'VET'),
('000000006', 'dr_wilson', 'pass', 'VET');

-- Insert sample pets with updated numeric owner_id references
INSERT IGNORE INTO pet (serial_number, name, race, sex, birthday, owner_id, medical_history, vet_approved) VALUES
(0001, 'Buddy', 'Golden Retriever', 'M', '2020-05-15 00:00:00', '000000002', 'Vaccinated on 2023-01-15. Regular checkup on 2023-06-20.', true),
(0002, 'Whiskers', 'Persian Cat', 'F', '2019-03-22 00:00:00', '000000002', 'Spayed on 2022-08-10. Clean bill of health.', true),
(0003, 'Charlie', 'Beagle', 'M', '2021-11-30 00:00:00', '000000002', 'Minor ear infection treated in 2023-04-05.', true),
(0004, 'Luna', 'Siamese Cat', 'F', '2022-01-08 00:00:00', '000000004', 'Routine vaccination completed.', false),
(0005, 'Max', 'German Shepherd', 'M', '2018-09-12 00:00:00', '000000004', 'Hip dysplasia monitoring. Last checkup 2023-05-30.', true),
(0006, 'Bella', 'Labrador Mix', 'F', '2020-12-03 00:00:00', '000000005', 'Healthy. Annual vaccination due next month.', false);