-- This script inserts initial data into the 'role', 'application_status',and 'person' tables

DELETE FROM person;
DELETE FROM role;
DELETE FROM application_status;

INSERT INTO role (role_id, name) VALUES (1, 'recruiter');
INSERT INTO role (role_id, name) VALUES (2, 'applicant');

INSERT INTO application_status (application_status_id, status) VALUES (1, 'accepted');
INSERT INTO application_status (application_status_id, status) VALUES (2, 'rejected');
INSERT INTO application_status (application_status_id, status) VALUES (3, 'unhandled');

INSERT INTO person (name, surname, pnr, email, password, username, role_id, application_status_id) VALUES
    ('applicant1', 'validApplicantUser', '19909090-9090', 'email@kth.se', '$2a$10$ZVeEAO5d9j5SyUmUzIfiiuHE/UpKkeFrY5qut6lKUH3cGPBP1u2iq', 'validApplicantUser', 2, 3);
                                                                        --'validApplicantPassword'
INSERT INTO person (name, surname, pnr, email, password, username, role_id, application_status_id) VALUES
    ('applicant2', 'validApplicantUser', '19909090-9090', 'email@kth.se', 'Icantlogin', 'applicant2', 2, 2);
INSERT INTO person (name, surname, pnr, email, password, username, role_id, application_status_id) VALUES
    ('applicant3', 'validApplicantUser', '19909090-9090', 'email@kth.se', 'Ialsocantlogin', 'applicant3', 2, 1);

INSERT INTO person (name, surname, pnr, email, password, username, role_id, application_status_id) VALUES
    ('validRecruiterUser', 'validRecruiterUser', '19909090-9090', 'email@kth.se', '$2a$10$9u.byUPFFXkM.GRdzZ5.1eIXuag5fXDkPq3jjSvUh3Azrqt23k9/G', 'validRecruiterUser', 1, null);
                                                                                --'validRecruiterPassword'
