INSERT INTO roles (id, denomination) values (1, 'ADMIN')
INSERT INTO roles (id, denomination) values (2, 'RECRUTEUR')
INSERT INTO roles (id, denomination) values (3, 'CANDIDAT')

INSERT INTO utilisateur(id, first_name, last_name, user_login, user_password, mail_address, global_score, roles_id) values(UNHEX(REPLACE(UUID(),'-','')), 'defaultAdmin', 'admin', 'admin', '$2y$10$WghpjCg7.EMQY2969mdJm.z7e3MQk4aC5NMwMsXRFa4Rz5sFSeTH.', 'admin@nomail', 0, 1)