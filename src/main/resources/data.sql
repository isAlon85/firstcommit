INSERT INTO role(name, description) VALUES ('USER' , 'User role');
INSERT INTO role(name, description) VALUES ('ADMIN' , 'Admin role');
INSERT INTO ob_user(username, password, email) VALUES ('Admin' , '$2a$10$bPkErVt0Zii5mRrN4wfU2OeMGxzvvD3CgWzFdOiQgSOGMiknJUfam' , 'admin@ob.com');
INSERT INTO user_roles(user_id, role_id) VALUES (1 , 2);
INSERT INTO user_roles(user_id, role_id) VALUES (1 , 1);