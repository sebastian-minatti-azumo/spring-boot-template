
create table users (
    id int not null PRIMARY KEY,
    name varchar(100) not null,
    email varchar(255) NOT NULL UNIQUE,
    date_created timestamp not null,
    date_updated timestamp not null,
    primary key (id)
);

create sequence user_id_seq START WITH 1 INCREMENT BY 1;

create index user_date_created_idx on users(date_created);

create table projects (
    id int not null PRIMARY KEY,
    name varchar(100) not null,
    description varchar(255),
    date_created timestamp not null,
    date_updated timestamp not null,
    primary key (id)
);

create sequence project_id_seq START WITH 1 INCREMENT BY 1;

create index project_date_created_idx on projects(date_created);

CREATE TABLE project_users (
  project_id INT,
  user_id INT,
  PRIMARY KEY (project_id, user_id),
  FOREIGN KEY (project_id) REFERENCES projects(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);
