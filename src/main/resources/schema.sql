create TABLE POSITION(
    position varchar(100) not null,
    PRIMARY KEY(position)
);

create TABLE TEAM(
    id int not null AUTO_INCREMENT,
    name varchar(100) not null,
    city varchar(100) not null,
    coach varchar(100) not null,
    arena varchar(100),
    founded varchar(100),
    owner varchar(100),
    PRIMARY KEY(id)
);

create TABLE PLAYER(
    id int not null AUTO_INCREMENT,
    team_id int not null,
    name varchar(100) not null,
    age int,
    height varchar(100),
    position varchar(100) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (position) REFERENCES POSITION(position),
    FOREIGN KEY (team_id) REFERENCES TEAM(id)
);

