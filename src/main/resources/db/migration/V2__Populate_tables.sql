
insert into users (id, name, email, date_created, date_updated)
values (nextval('USER_ID_SEQ'), 'John', 'john.doe@gmail.com', current_timestamp, current_timestamp);

insert into users (id, name, email, date_created, date_updated)
values (nextval('USER_ID_SEQ'), 'Jane', 'maria_128@gmail.com', current_timestamp, current_timestamp);

insert into users (id, name, email, date_created, date_updated)
values (nextval('USER_ID_SEQ'), 'Mike', 'martin_123@gmail.com', current_timestamp, current_timestamp);

INSERT INTO projects (id, name, description,date_created, date_updated) VALUES
(nextval('project_id_seq'), 'Project A', 'A',current_timestamp, current_timestamp),
(nextval('project_id_seq'), 'Project B', 'B',current_timestamp, current_timestamp),
(nextval('project_id_seq'), 'Project C', 'C',current_timestamp, current_timestamp);

INSERT INTO project_users (project_id, user_id) VALUES
(1, 1), -- Project A - John
(1, 2), -- Project A - Jane
(2, 2), -- Project B - Jane
(3, 1), -- Project C - John
(3, 3); -- Project C - Mike
