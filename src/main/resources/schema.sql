create TABLE POSITION(
    position varchar(100) not null,
    PRIMARY KEY (position)
);

create TABLE TEAM(
    id int not null AUTO_INCREMENT,
    name varchar(100) not null unique,
    city varchar(100) not null,
    coach varchar(100) not null,
    arena varchar(100),
    founded varchar(100),
    owner varchar(100),
    active boolean,
    PRIMARY KEY (id)
);

create TABLE PLAYER(
    id int not null AUTO_INCREMENT,
    team_id int not null,
    name varchar(100) not null unique,
    age int,
    height varchar(100),
    position varchar(100) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (position) REFERENCES POSITION(position),
    FOREIGN KEY (team_id) REFERENCES TEAM(id)
);

CREATE TABLE PLAYERSTATS (
    id INT NOT NULL AUTO_INCREMENT,
    player_id INT NOT NULL,
    minpergame FLOAT,
    ppg FLOAT,
    rpg FLOAT,
    apg FLOAT,
    stealspergame FLOAT,
    fgpercent FLOAT,
    ftpercent FLOAT,
    season varchar(100),
    PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES PLAYER(id)
);

create TABLE TEAMSTATS(
    id int not null AUTO_INCREMENT,
    team_id int not null,
    played int,
    won int,
    lost int,
    ppg FLOAT,
    rpg FLOAT,
    apg FLOAT,
    fgpercent FLOAT,
    ftpercent FLOAT,
    season varchar(100),
    PRIMARY KEY (id),
    FOREIGN KEY (team_id) REFERENCES TEAM(id)
);