insert into POSITION (position) VALUES ('FORWARD');
insert into POSITION (position) VALUES ('GUARD');
insert into POSITION (position) VALUES ('CENTER');

insert into TEAM (name,city,coach) VALUES('Golden State Warriors', 'San Francisco', 'Steve Kerr');
insert into TEAM (name,city,coach) VALUES('LA Lakers', 'LA', 'Tom');
insert into TEAM (name,city,coach) VALUES('Miami Heat', 'Miami', 'Dick');
insert into TEAM (name,city,coach) VALUES('Mavericks', 'Dallas', 'Harry');

insert into PLAYER (team_id, name, position) VALUES(1,'Steph Curry', 'GUARD');
insert into PLAYER (team_id, name, position) VALUES(1,'Draymond Green', 'FORWARD');
insert into PLAYER (team_id, name, position) VALUES(1,'Klay Thompson', 'GUARD');
insert into PLAYER (team_id, name, position) VALUES(1,'Iggy', 'CENTER');
insert into PLAYER (team_id, name, position) VALUES(2,'Lebron James', 'GUARD');
insert into PLAYER (team_id, name, position) VALUES(2,'Shaq', 'CENTER');

INSERT INTO PLAYERSTATS (player_id, minpergame, ppg, rpg, apg, stealspergame, fgpercent, ftpercent, season)
VALUES (1, 34.7, 29.9, 5.3, 6.2, 1.5, 0.489, 0.432, '2014');

INSERT INTO PLAYERSTATS (player_id, minpergame, ppg, rpg, apg, stealspergame, fgpercent, ftpercent, season) 
VALUES (2, 32.4, 25.6, 7.1, 5.4, 1.7, 0.478, 0.840, '2014');

INSERT INTO PLAYERSTATS (player_id, minpergame, ppg, rpg, apg, stealspergame, fgpercent, ftpercent, season) 
VALUES (3, 30.2, 22.8, 6.5, 4.8, 1.3, 0.452, 0.812, '2017');

INSERT INTO PLAYERSTATS (player_id, minpergame, ppg, rpg, apg, stealspergame, fgpercent, ftpercent, season) 
VALUES (4, 35.1, 31.2, 8.0, 7.5, 2.1, 0.501, 0.854, '2018');

INSERT INTO PLAYERSTATS (player_id, minpergame, ppg, rpg, apg, stealspergame, fgpercent, ftpercent, season) 
VALUES (5, 29.9, 18.4, 5.9, 3.6, 1.0, 0.443, 0.785, '2019');

INSERT INTO PLAYERSTATS (player_id, minpergame, ppg, rpg, apg, stealspergame, fgpercent, ftpercent, season) 
VALUES (6, 31.5, 24.3, 6.8, 5.9, 1.8, 0.469, 0.823, '2020');

INSERT INTO TEAMSTATS (team_id, played, won, lost, ppg, rpg, apg, fgpercent, ftpercent, season) 
VALUES (1, 82, 58, 24, 114.7, 44.2, 27.4, 48.9, 78.3, '2021');

INSERT INTO TEAMSTATS (team_id, played, won, lost, ppg, rpg, apg, fgpercent, ftpercent, season) 
VALUES (1, 82, 49, 33, 112.7, 46.2, 30.5, 48.9, 78.3, '2023');

INSERT INTO TEAMSTATS (team_id, played, won, lost, ppg, rpg, apg, fgpercent, ftpercent, season) 
VALUES (2, 82, 52, 30, 112.5, 46.1, 25.3, 47.3, 79.1, '2022');

INSERT INTO TEAMSTATS (team_id, played, won, lost, ppg, rpg, apg, fgpercent, ftpercent, season) 
VALUES (3, 82, 45, 37, 109.4, 48.2, 24.5, 46.2, 77.8, '2015');

INSERT INTO TEAMSTATS (team_id, played, won, lost, ppg, rpg, apg, fgpercent, ftpercent, season) 
VALUES (4, 82, 60, 22, 116.3, 43.7, 28.9, 50.1, 80.5, '2016');





