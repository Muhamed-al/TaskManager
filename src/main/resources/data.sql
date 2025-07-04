INSERT INTO roles (name) VALUES ( 'ROLE_ADMIN' );
INSERT INTO roles (name) VALUES ( 'ROLE_USER' );
INSERT INTO users (username, email) VALUES ( 'admin', 'admin@example.com' );
INSERT INTO users (username, email) VALUES ( 'user','user@example.com' );
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO project (name, description, start_date, end_date, status, creator_id)
VALUES ( 'Projet Alpha', 'Description du projet Alpha', '2023-01-01', '2023-12-31', 'EN_COURS', 1 );
INSERT INTO project (name, description, start_date, end_date, status, creator_id)
VALUES ( 'Projet Beta', 'Description du projet Beta', '2023-06-01', '2024-06-01', 'PLANIFIE', 2 );
INSERT INTO task (name, description, due_date, status, priority, project_id)
VALUES ( 'Tâche 1 Alpha', 'Première tâche du projet Alpha', '2023-03-15', 'A_FAIRE', 'HAUTE', 1 );
INSERT INTO task (name, description, due_date, status, priority, project_id)
VALUES ( 'Tâche 2 Alpha', 'Deuxième tâche du projet Alpha','2023-04-01', 'EN_COURS', 'MOYENNE', 1 );
INSERT INTO task (name, description, due_date, status, priority, project_id)
VALUES ( 'Tâche 1 Beta', 'Première tâche du projet Beta', '2023-07-01', 'A_FAIRE', 'BASSE', 2 );
INSERT INTO task_assigned_users (task_id, user_id) VALUES (1, 1);
INSERT INTO task_assigned_users (task_id, user_id) VALUES (1, 2);
INSERT INTO task_assigned_users (task_id, user_id) VALUES (2, 1);
INSERT INTO task_assigned_users (task_id, user_id) VALUES (3, 2);
INSERT INTO comment (content, task_id, user_id) VALUES ('Premier commentaire sur la Tâche 1 Alpha', 1, 1 );
INSERT INTO comment (content, task_id, user_id) VALUES ('Deuxième commentaire sur la Tâche 1 Alpha', 1, 2 );