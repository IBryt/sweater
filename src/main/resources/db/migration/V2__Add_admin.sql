INSERT INTO usr (id, username, password, active)
    VALUES (1, 'admin', '123', true);
insert into user_role(user_id, roles)
    VALUES (1, 'ADMIN'), (1,'USER');
