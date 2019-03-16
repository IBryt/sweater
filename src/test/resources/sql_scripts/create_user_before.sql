delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
  (1, 'admin', '0', true),
  (2, 'mike', '0', true);

insert into user_role(user_id, roles) values
  (1, 'ADMIN'), (1, 'USER'),
  (2, 'USER');