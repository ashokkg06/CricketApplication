-- create table

use cricketapp;

CREATE TABLE ballRecord (
match_no INT,
ballnumber INT,
inningno INT,
overs FLOAT,
outcome VARCHAR(255),
batter VARCHAR(255),
bowler VARCHAR(255),
comment VARCHAR(255),
score INT
);

CREATE TABLE matchRecord22 (
id INT,
city varchar(255),
date varchar(255),
season INT,
matchnumber varchar(255),
team1 varchar(255),
team2 varchar(255),
venue varchar(255),
tosswinner varchar(255),
tossdecision varchar(255),
superover varchar(10),
winningteam varchar(255),
wonby varchar(255),
margin INT,
method varchar(255),
Player_of_Match varchar(255),
Umpire1 varchar(255),
Umpire2 varchar(255)
);

CREATE TABLE ballRecord22 (
match_id INT,
season INT,
match_no INT,
dates varchar(255),
venue varchar(255),
batting_team varchar(255),
bowling_team varchar(255),
innings INT,
overs FLOAT,
striker varchar(255),
bowler varchar(255),
batsman_runs INT,
extras INT,
wide INT,
legbyes INT,
byes INT,
noballs INT,
wicket_type varchar(255),
player_dismissed varchar(255),
fielder varchar(255)
);
-- drop table
use cricketapp;
drop table ballRecord;
drop table matchRecord;
drop table matchesdata;
drop table ballrecord22;

select * from matchesdata;

-- Allow client to access local data infile
use cricketapp;
SET GLOBAL local_infile = 'ON';

-- Add ballrecord data
use cricketapp;

LOAD DATA LOCAL INFILE 'C:/Users/kgash/OneDrive/Desktop/Java programs/Zoho/Incubation/CricketApp/cricketapp/src/main/resources/each_ball_records.csv'
INTO TABLE ballrecord
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS;

LOAD DATA LOCAL INFILE 'C:/Users/kgash/Downloads/IPL2022ballrecord/ipl_2022_deliveries.csv'
INTO TABLE ballrecord22
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS;

LOAD DATA LOCAL INFILE 'C:/Users/kgash/Downloads/ipl_matches_2022.csv'
INTO TABLE matchrecord22
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS;

-- Add matchrecord data
USE CricketApp; LOAD DATA LOCAL INFILE 'C:/Users/kgash/OneDrive/Desktop/Java programs/Zoho/Incubation/CricketApp/cricketapp/src/main/resources/each_match_records.csv' INTO TABLE matchRecord FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\r\n' IGNORE 1 ROWS;

-- select tables
select * from cricketapp.ballrecord;
select * from cricketapp.matchrecord;
select * from cricketapp.ballrecord22;
select * from cricketapp.matchrecord22;

-- ipl2022 ballrecord
select (match_id-202200) as match_no, ROW_NUMBER() OVER (PARTITION BY (match_id - 202200), innings ORDER BY overs, bowler, striker) AS ballnumber, innings as inningno, overs as overs, (CASE WHEN wide > 0 THEN CONCAT(batsman_runs + wide, 'wd') WHEN legbyes > 0 THEN CONCAT(batsman_runs + legbyes, 'lb') WHEN byes > 0 THEN CONCAT(batsman_runs + byes, 'b') WHEN noballs > 0 THEN CONCAT(batsman_runs + noballs, 'nb') WHEN wicket_type>'a' THEN 'w' ELSE batsman_runs END) AS outcome, striker as batter, bowler as bowler, (CASE WHEN wide>0 THEN batsman_runs + wide WHEN legbyes>0 THEN batsman_runs + legbyes WHEN byes>0 THEN batsman_runs + byes WHEN noballs>0 THEN batsman_runs + noballs ELSE batsman_runs END) as score
from ballrecord22;

select match_no, ballnumber, inningno, overs, outcome, batter, bowler, CONCAT(bowler, ' to ', batter, ', ', (CASE WHEN score=4 THEN "Four" WHEN score=6 THEN "Six" WHEN score=0 THEN "no" ELSE score END), (CASE WHEN outcome like "%lb" THEN " leg bye" WHEN outcome like "%nb" THEN " no ball" WHEN outcome like "%wd" THEN " wide" WHEN outcome like "%b" THEN " byes" WHEN score>1 THEN " runs" ELSE " run" END), (CASE WHEN outcome like "%w" THEN CONCAT(" Wicket -", (CASE WHEN fielder>'a' THEN CONCAT(" c ", fielder) ELSE '' END), " b ", bowler, " batsmen") ELSE '' END)) AS comment, score
from (
select (match_id-202200) as match_no, ROW_NUMBER() OVER (PARTITION BY (match_id - 202200), innings ORDER BY overs, bowler, striker) AS ballnumber, innings as inningno, overs as overs, (CASE WHEN wide > 0 THEN CONCAT(batsman_runs + wide, 'wd') WHEN legbyes > 0 THEN CONCAT(batsman_runs + legbyes, 'lb') WHEN byes > 0 THEN CONCAT(batsman_runs + byes, 'b') WHEN noballs > 0 THEN CONCAT(batsman_runs + noballs, 'nb') WHEN wicket_type>'a' THEN 'w' ELSE batsman_runs END) AS outcome, striker as batter, bowler as bowler, (CASE WHEN wide>0 THEN batsman_runs + wide WHEN legbyes>0 THEN batsman_runs + legbyes WHEN byes>0 THEN batsman_runs + byes WHEN noballs>0 THEN batsman_runs + noballs ELSE batsman_runs END) as score, fielder
from ballrecord22
) As ballrecord;
/*
INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/ballrecord22.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
*/

select * from ballrecord22;
-- ipl2022 matchrecord
select season, date, matchnumber as match_number, match_type, venue, location, team1, team2, toss_won, toss_decision, umpire1, umpire2, reserve_umpire, match_referee, winner, winner_runs, winner_wickets, man_of_match from matchrecord22;

select season, date, CAST(matchnumber AS UNSIGNED) as match_number, '' AS match_type,  venue, city as location, team1, team2, tosswinner as toss_won, tossdecision as toss_decision, umpire1, umpire2, '' as reserve_umpire, '' as match_referee, winningteam as winner, CAST((CASE WHEN wonby="Runs" THEN margin ELSE 0 END) AS unsigned) as winner_runs, (CASE WHEN wonby="Wickets" THEN margin ELSE 0 END) as winner_wickets, Player_of_Match as man_of_match INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/matchrecord22.csv' FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n' from matchrecord22 ORDER BY match_number asc;

select * from matchrecord22;
UPDATE cricketapp.matchrecord22 SET matchnumber=72 WHERE matchnumber="Eliminator";
UPDATE cricketapp.matchrecord22 SET matchnumber=73 WHERE matchnumber="Qualifier 2";
UPDATE cricketapp.matchrecord22 SET matchnumber=74 WHERE matchnumber="Final";

-- For exporting data values of select
-- INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/matchrecord22.csv' FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n'

SHOW VARIABLES LIKE 'secure_file_priv';

select comment from ballrecord;
select * from ballrecord where batter = "Wriddhiman Saha" and match_no=1;

-- clear tables
truncate cricketapp.ballrecord;
truncate cricketapp.matchrecord;

use cricketapp;
-- individual score from a match
select SUM(score) from ballrecord where batter = "Devon Conway" AND match_no = 1;

-- individual total score from tournament
select SUM(score) from ballrecord where batter = "Devon Conway";
-- First batting team score
select SUM(score) from ballrecord where match_no = 1 and inningno = 1;

-- Second batting team score
select SUM(score) from ballrecord where match_no = 1 and inningno = 2;

-- end of the over record
select overs from ballrecord where (match_no = 1 and inningno = 1) order by overs desc limit 1;

-- first inning wickets
select count(outcome) from ballrecord where outcome="w" and (match_no = 1 and inningno = 1);
-- second inning wickets
select count(outcome) from ballrecord where outcome="w" and (match_no = 1 and inningno = 2);

-- To get noball and wide records
select match_no, inningno, COUNT(outcome)+score-1 from ballrecord where outcome like "%nb%" or outcome like "%wd%" or outcome like "%lb" or outcome like "%b";

-- noball count
select match_no, inningno, COUNT(outcome) As noball from ballrecord where outcome like "%nb%" GROUP BY match_no, inningno;
-- leg bye count
select match_no, inningno, SUM(score) As legbye from ballrecord where outcome like "%lb%" GROUP BY match_no, inningno;
-- wide count
select match_no, inningno, SUM(score) As wide from ballrecord where outcome like "%wd%" GROUP BY match_no, inningno;
-- byes count
SELECT match_no, inningno, SUM(score) As byes FROM ballrecord WHERE outcome REGEXP '^[0-9]+b$' GROUP BY match_no, inningno;

-- noball,legbye,wide
SELECT
matches_and_innings.match_no,
matches_and_innings.inningno,
COALESCE(noball_counts.noball, 0) AS noball,
COALESCE(legbye_counts.legbye, 0) AS legbye,
COALESCE(wide_counts.wide, 0) AS wide,
COALESCE(byes_counts.byes, 0) As byes
FROM
(
SELECT DISTINCT match_no, inningno
FROM ballrecord
WHERE match_no=1 and inningno=1
) AS matches_and_innings
LEFT JOIN
(
SELECT match_no, inningno, COUNT(outcome) AS noball
FROM ballrecord
WHERE outcome LIKE "%nb%"
GROUP BY match_no, inningno
) AS noball_counts
ON matches_and_innings.match_no = noball_counts.match_no AND matches_and_innings.inningno = noball_counts.inningno
LEFT JOIN
(
SELECT match_no, inningno, SUM(score) AS legbye
FROM ballrecord
WHERE outcome LIKE "%lb%"
GROUP BY match_no, inningno
) AS legbye_counts
ON matches_and_innings.match_no = legbye_counts.match_no AND matches_and_innings.inningno = legbye_counts.inningno
LEFT JOIN
(
SELECT match_no, inningno, SUM(outcome) AS wide
FROM ballrecord
WHERE outcome LIKE "%wd%"
GROUP BY match_no, inningno
) AS wide_counts
ON matches_and_innings.match_no = wide_counts.match_no AND matches_and_innings.inningno = wide_counts.inningno
LEFT JOIN
(
SELECT match_no, inningno, SUM(score) AS byes
FROM ballrecord
WHERE outcome REGEXP '^[0-9]+b$'
GROUP BY match_no, inningno
) AS byes_counts
ON matches_and_innings.match_no = byes_counts.match_no AND matches_and_innings.inningno = byes_counts.inningno;

-- 70th match wide wicket same time
select * from ballrecord where match_no=70 and inningno=1 and outcome like "%wd";
select overs, outcome, match_no, inningno from ballrecord where outcome REGEXP '[1-4]lb'; -- 0.2 14.3 19.2
SELECT
match_no,
inningno,
COUNT(*) as Byes_Count
FROM
ballrecord
WHERE
outcome LIKE '%b' AND outcome NOT LIKE "%nb" AND outcome NOT LIKE "%lb"
GROUP BY
match_no,
inningno;



-- To get individual player scores without extras
SELECT count(distinct(match_no)), SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" THEN score - 1 ELSE score END)
FROM ballrecord
WHERE batter = 'Rohit sharma';

Select distinct(match_no), SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" THEN score - 1 ELSE score END) from ballrecord where batter="Shikhar dhawan" GROUP BY match_no;

select * from ballrecord where match_no = 38;
-- https://github.com/Sankha1998/ipl2023/blob/main/each_ball_records.csv#L9181
select batter, bowler from ballrecord where batter="inis";
Update ballrecord set batter="Liam Livingstone" where batter="Liam Livings" LIMIT 1000;
SET SQL_SAFE_UPDATES = 1;
Update ballrecord set batter = "Prabhsimran Singh" Where (overs=0.1 and overs=0.2 and overs=0.4 and match_no = 38 and inningno=2);
-- SELECT
--     TRIM(REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(comment, ',', 1), 'to', ','), '"', '')) AS bowler_and_batter_names,
--     TRIM(REPLACE(SUBSTRING_INDEX(comment, ',', -1), '"', '')) AS runs_info
-- FROM
--     ballrecord
-- Where
-- 	match_no = 38 and inningno = 1 and batter="ne";

-- To get batters and bowlers
select distinct(batter) from ballrecord;
select distinct(bowler) from ballrecord;

select bowler from ballrecord where bowler = "kuldeep yadav";
select batter from ballrecord where batter = "kuldeep yadav";
-- batter stats
SELECT
batter AS Batter_Name,
COUNT(DISTINCT match_no) AS Matches,
SUM(match_score) AS Runs,
AVG(match_score) AS Average,
MAX(match_score) AS HS
FROM (
SELECT
batter,
match_no,
SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" or outcome Like "%lb" THEN score - 1 ELSE score END) AS match_score
FROM
ballrecord
GROUP BY
batter, match_no
) AS match_scores
where batter="Virat Kohli"
GROUP BY
batter
order by
runs desc;

-- bowler stats
SELECT
bowler AS Bowler,
COUNT(DISTINCT match_no) AS Matches,
SUM(total_wickets_taken) as Total_Wickets,
ROUND((SUM(total_overs_bowled)),1) as Overs,
SUM(total_runs_given) as Runs,
ROUND((AVG(economy)),2) as Average_Economy
FROM (
SELECT
bowler,
match_no,
SUM(CASE WHEN outcome = 'w' THEN 1 ELSE 0 END) AS Total_Wickets_Taken,
(COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) / 6) AS Total_Overs_Bowled,
SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" THEN score - 1 WHEN outcome LIKE "%lb" and score>3 THEN 0 ELSE score END) AS Total_Runs_Given,
ROUND(SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" or outcome LIKE "%lb%" THEN score - 1 ELSE score END) / (COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) / 6), 2) AS Economy
FROM
ballrecord
GROUP BY
bowler, match_no
) As each_bowler_stats
where bowler="Mohammed Shami"
GROUP BY bowler
order by Total_Wickets desc;

-- Each match batter stats
SELECT
batter as Batter,
match_no as Matches,
inningno as Innings,
SUM(CASE WHEN outcome LIKE "%nb%" or outcome LIKE "%wd%" THEN score - 1
WHEN outcome LIKE "%lb%" or outcome LIKE "%b" THEN 0
ELSE score END) as Runs,
COUNT(CASE WHEN outcome NOT LIKE "%wd%" THEN ballnumber END) as Ballsfaced,
COUNT(CASE WHEN score = 4 THEN 1 END) as 4s,
COUNT(CASE WHEN score = 6 THEN 1 END) as 6s,
ROUND((SUM(CASE WHEN outcome LIKE "%nb%" or outcome LIKE "%wd%" THEN score - 1
WHEN outcome LIKE "%lb%" or outcome LIKE "%b" THEN 0
ELSE score END) / COUNT(CASE WHEN outcome NOT LIKE "%wd%" THEN ballnumber END) * 100), 2) as StrikeRate
FROM
ballrecord
WHERE
match_no = 1 and inningno=1
GROUP BY
batter, match_no, inningno;


-- Each match bowler stats
SELECT
bowler AS Bowler_Name,
match_no AS Match_Number,
inningno As Innings,
SUM(CASE WHEN outcome = 'w' THEN 1 ELSE 0 END) AS Total_Wickets_Taken,
-- ROUND((COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) / 6),1) AS Total_Overs_Bowled,
ROUND(FLOOR(COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) As Total_Overs_Bowled,
SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" THEN score - 1 WHEN outcome LIKE "%lb" and score>3 THEN 0 ELSE score END) AS Total_Runs_Given,
ROUND(SUM(CASE WHEN outcome LIKE '%nb%' or outcome Like "%wd%" THEN score - 1 WHEN outcome LIKE "%lb" and score>3 THEN 0 ELSE score END) / ROUND(FLOOR(COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10)),2) AS Economy
FROM
ballrecord
WHERE
match_no = 1 and inningno=1
GROUP BY
bowler, match_no, inningno;

-- SELECT date, season, match_no, venue, location, team1, team2, winner, winner_runs, winner_wickets, (select SUM(score) from ballrecord where inningno=1 GROUP BY inningno) as Team1_Score, (select SUM(score) from ballrecord where inningno=2 GROUP BY inningno) as Team2_Score, inningno, SUM(score), (select SUM(CASE WHEN outcome = 'w' THEN 1 ELSE 0 END) from ballrecord where inningno=1 GROUP BY inningno) as Team1_Wickets, (select SUM(CASE WHEN outcome = 'w' THEN 1 ELSE 0 END) from ballrecord where inningno=2 GROUP BY inningno) as Team2_Wickets from ballrecord LEFT JOIN matchrecord ON match_no=match_number WHERE match_no=match_number GROUP BY match_no;

-- ipl 2023 matches table
use cricketapp;
SELECT
MAX(m.date) as date,
MAX(m.season) as season,
m.match_number,
MAX(m.venue) as venue,
MAX(m.location) as location,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 2 THEN Overss END) as Overs,
MAX(m.winner_runs) as winner_runs,
MAX(m.winner_wickets) as winner_wickets
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as Overss
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number;

-- ipl2023 match no = 1 data
SELECT
MAX(m.date) as date,
MAX(m.season) as season,
m.match_number,
MAX(m.venue) as venue,
MAX(m.location) as location,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 2 THEN Overss END) as Overs,
MAX(m.winner_runs) as winner_runs,
MAX(m.winner_wickets) as winner_wickets
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as Overss
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number=1;

-- ipl2023 match no = 1 data
SELECT MAX(m.date) AS date, MAX(m.season) AS season, m.match_number AS matchNumber, MAX(m.venue) AS venue, MAX(m.location) AS location, MAX(m.team1) AS team1, MAX(m.team2) AS team2, MAX(m.winner) AS winner, MAX(CASE WHEN inningno = 1 THEN Team1_Score END) AS team1Score, MAX(CASE WHEN inningno = 2 THEN Team2_Score END) AS team2Score, MAX(CASE WHEN inningno = 1 THEN Team1_Wickets END) AS team1Wickets, MAX(CASE WHEN inningno = 2 THEN Team2_Wickets END) AS team2Wickets, SUM(CASE WHEN inningno = 2 THEN Overss END) AS overs, MAX(m.winner_runs) AS winnerRuns, MAX(m.winner_wickets) AS winnerWickets FROM ( SELECT match_no, inningno, SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) AS Team1_Score, SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) AS Team2_Score, SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) AS Team1_Wickets, SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) AS Team2_Wickets, ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as Overss FROM ballrecord GROUP BY match_no, inningno ) AS match_stats LEFT JOIN matchrecord AS m ON m.match_number = match_stats.match_no GROUP BY m.match_number HAVING match_number=1;

-- seperating comment
SELECT
batter,
wicket_details AS dismissals
FROM
(SELECT
batter,
CASE
WHEN comment LIKE '%Wicket%' THEN SUBSTRING_INDEX(SUBSTRING_INDEX(comment, 'Wicket - ', -1), ' batsmen', 1)
ELSE NULL
END AS wicket_details
FROM
ballrecord
WHERE match_no=1
GROUP BY
batter, wicket_details) As wickets
WHERE wicket_details IS NOT NULL;

select * from matchrecord;

-- runrate
select match_no, ROUND(AVG(SUM(score)/20),1) As Runrate from ballrecord where match_no=11 and inningno=2 group by match_no;

-- to get number of teams
select winner from matchrecord where winner!="No result" group by winner;

-- nrr for a team
SELECT
m.match_number,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (team1 = "mumbai indians" or team2 = "mumbai indians");

-- nrr test total data
select
SUM(CASE WHEN team1="mumbai indians" THEN Team1_Score WHEN team2="mumbai indians" THEN Team2_Score END) teamtotalscore,
SUM(CASE WHEN team1!="mumbai indians" THEN Team1_Score WHEN team2!="mumbai indians" THEN Team2_Score END) opteamtotalscore,
SUM(CASE WHEN team1="mumbai indians" THEN Team1_Overs WHEN team2="mumbai indians" THEN Team2_Overs END) teamtotalovers,
SUM(CASE WHEN team1!="mumbai indians" THEN Team1_Overs WHEN team2!="mumbai indians" THEN Team2_Overs END) opteamtotalovers
from
(SELECT
m.match_number,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (team1 = "mumbai indians" or team2 = "mumbai indians")) As TeamScorecard;

-- nrr
select
((teamtotalscore/teamtotalovers) - (opteamtotalscore/opteamtotalovers)) As Nrr
from
(select
SUM(CASE WHEN team1="mumbai indians" THEN Team1_Score WHEN team2="mumbai indians" THEN Team2_Score END) teamtotalscore,
SUM(CASE WHEN team1!="mumbai indians" THEN Team1_Score WHEN team2!="mumbai indians" THEN Team2_Score END) opteamtotalscore,
SUM(CASE WHEN team1="mumbai indians" THEN Team1_Overs WHEN team2="mumbai indians" THEN Team2_Overs END) teamtotalovers,
SUM(CASE WHEN team1!="mumbai indians" THEN Team1_Overs WHEN team2!="mumbai indians" THEN Team2_Overs END) opteamtotalovers
from
(SELECT
m.match_number,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (team1 = "mumbai indians" or team2 = "mumbai indians")) As TeamScorecard) As datas;

-- nrr results
select
((teamtotalscore/teamtotalovers) - (opteamtotalscore/opteamtotalovers)) As Nrr
from
(select
SUM(CASE WHEN team1="mumbai indians" THEN Team1_Score WHEN team2="mumbai indians" THEN Team2_Score END) teamtotalscore,
SUM(CASE WHEN team1!="mumbai indians" THEN Team1_Score WHEN team2!="mumbai indians" THEN Team2_Score END) opteamtotalscore,
SUM(CASE WHEN team1="mumbai indians" THEN 20 WHEN team2="mumbai indians" THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) teamtotalovers,
SUM(CASE WHEN team1!="mumbai indians" THEN 20 WHEN team2!="mumbai indians" THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) opteamtotalovers
from
(SELECT
m.match_number,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (team1 = "mumbai indians" or team2 = "mumbai indians")) As TeamScorecard) As datas;

-- nrr result table
select
winner,
((teamtotalscore/teamtotalovers) - (opteamtotalscore/opteamtotalovers)) As Nrr
from
(select
winner,
SUM(CASE WHEN team1=winner THEN Team1_Score WHEN team2=winner THEN Team2_Score END) teamtotalscore,
SUM(CASE WHEN team1!=winner THEN Team1_Score WHEN team2!=winner THEN Team2_Score END) opteamtotalscore,
SUM(CASE WHEN team1=winner THEN 20 WHEN team2=winner THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) teamtotalovers,
SUM(CASE WHEN team1!=winner THEN 20 WHEN team2!=winner THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) opteamtotalovers
from
(SELECT
m.match_number,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (team1 = winner or team2 = winner)) As TeamScorecard
GROUP by winner) As datas;

-- points table
select winner As team, count(winner) As Wins, (CASE WHEN winner LIKE "%NO%" THEN 13-count(winner) ELSE 14-count(winner) END) As Lose, (count(winner)*2) As PTS from matchrecord WHERE match_number<71 GROUP BY winner order by Wins desc;


-- points table calculation wrong
select
winner,
count(winner) As Wins,
(CASE WHEN winner LIKE "%NO%" THEN 13-count(winner) ELSE 14-count(winner) END) As Lose, (count(winner)*2) As PTS,
((teamtotalscore/teamtotalovers) - (opteamtotalscore/opteamtotalovers)) As Nrr
from
(select
winners,
SUM(CASE WHEN teams1=winners THEN Team1_Score WHEN teams2=winners THEN Team2_Score END) teamtotalscore,
SUM(CASE WHEN teams1!=winners THEN Team1_Score WHEN teams2!=winners THEN Team2_Score END) opteamtotalscore,
SUM(CASE WHEN teams1=winners THEN 20 WHEN teams2=winners THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) teamtotalovers,
SUM(CASE WHEN teams1!=winners THEN 20 WHEN teams2!=winners THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) opteamtotalovers
from
(SELECT
m.match_number,
MAX(m.team1) as teams1,
MAX(m.team2) as teams2,
MAX(m.winner) as winners,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (teams1 = winners or teams2 = winners)) As TeamScorecard
GROUP by winners) As datas,
matchrecord
GROUP BY winner,Nrr
order by Wins desc;



select
SUM(CASE WHEN team1=winner THEN Team1_Score WHEN team2=winner THEN Team2_Score END) teamtotalscore,
SUM(CASE WHEN team1!=winner THEN Team1_Score WHEN team2!=winner THEN Team2_Score END) opteamtotalscore,
SUM(CASE WHEN team1=winner THEN 20 WHEN team2=winner THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) teamtotalovers,
SUM(CASE WHEN team1!=winner THEN 20 WHEN team2!=winner THEN (CASE WHEN (Team2_Overs%1)>0 THEN ((Team2_Overs - (Team2_Overs%1) ) + ((Team2_Overs%1)*10/6)) ELSE Team2_Overs END) END) opteamtotalovers
from
(SELECT
m.match_number,
MAX(m.team1) as team1,
MAX(m.team2) as team2,
MAX(m.winner) as winner,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) as Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) as Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) as Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) as Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) as Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) as Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) as T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) as T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) as T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) as T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) as T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord AS m ON m.match_number = match_stats.match_no
GROUP BY
m.match_number
HAVING match_number<=70 and (team1 = winner or team2 = winner)) As TeamScorecard;

-- wrong
SELECT
points_table.winner AS Team,
COUNT(points_table.winner) AS Wins,
(CASE WHEN points_table.winner LIKE "%NO%" THEN 13 - COUNT(points_table.winner) ELSE 14 - COUNT(points_table.winner) END) AS Losses,
(COUNT(points_table.winner) * 2) AS PTS,
nrr_data.Nrr AS Nrr
FROM (
SELECT
winner,
COUNT(winner) AS Wins
FROM
matchrecord
WHERE
match_number < 71
GROUP BY
winner
) AS points_table
LEFT JOIN (
SELECT
winners AS team,
((teamtotalscore / teamtotalovers) - (opteamtotalscore / opteamtotalovers)) AS Nrr
FROM (
SELECT
winners,
SUM(CASE WHEN team1 = winners THEN Team1_Score ELSE Team2_Score END) AS teamtotalscore,
SUM(CASE WHEN team1 != winners THEN Team1_Score ELSE Team2_Score END) AS opteamtotalscore,
SUM(CASE WHEN team1 = winners THEN Team1_Overs ELSE Team2_Overs END) AS teamtotalovers,
SUM(CASE WHEN team1 != winners THEN Team1_Overs ELSE Team2_Overs END) AS opteamtotalovers
FROM
(
SELECT
MAX(team1) AS team1,
MAX(team2) AS team2,
MAX(winner) AS winners,
MAX(CASE WHEN inningno = 1 THEN T1_Score END) AS Team1_Score,
MAX(CASE WHEN inningno = 2 THEN T2_Score END) AS Team2_Score,
MAX(CASE WHEN inningno = 1 THEN T1_Wickets END) AS Team1_Wickets,
MAX(CASE WHEN inningno = 2 THEN T2_Wickets END) AS Team2_Wickets,
MAX(CASE WHEN inningno = 1 THEN T1_Overs END) AS Team1_Overs,
MAX(CASE WHEN inningno = 2 THEN T2_Overs END) AS Team2_Overs
FROM (
SELECT
match_no,
inningno,
SUM(CASE WHEN inningno = 1 THEN score ELSE 0 END) AS T1_Score,
SUM(CASE WHEN inningno = 2 THEN score ELSE 0 END) AS T2_Score,
SUM(CASE WHEN inningno = 1 AND outcome = 'w' THEN 1 ELSE 0 END) AS T1_Wickets,
SUM(CASE WHEN inningno = 2 AND outcome = 'w' THEN 1 ELSE 0 END) AS T2_Wickets,
ROUND(FLOOR(COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=1 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) AS T1_Overs,
ROUND(FLOOR(COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END)/6) + (COUNT(CASE WHEN inningno=2 AND outcome NOT LIKE '%nb%' AND outcome NOT LIKE '%wd%' THEN ballnumber END) % 6 / 10),1) AS T2_Overs
FROM
ballrecord
GROUP BY
match_no, inningno
) AS match_stats
LEFT JOIN
matchrecord ON matchrecord.match_number = match_stats.match_no
GROUP BY
matchrecord.match_number
HAVING
matchrecord.match_number <= 70
) AS TeamScorecard
group by winners
) AS TeamNrr
group by winners, nrr
) AS nrr_data ON nrr_data.team = points_table.winner
GROUP BY
points_table.winner, team
ORDER BY
Wins DESC;

-- squads
SELECT team1
FROM
(
(SELECT batter AS team1 FROM ballrecord WHERE match_no=2 AND inningno=1)
UNION
(SELECT bowler AS team1 FROM ballrecord WHERE match_no=2 AND inningno=2)
) AS team1_players;

SELECT team2
FROM
(
(SELECT batter AS team2 FROM ballrecord WHERE match_no=2 AND inningno=2)
UNION
(SELECT bowler AS team2 FROM ballrecord WHERE match_no=2 AND inningno=1)
) AS team2_players;

-- commentary
select overs, outcome, comment from ballrecord where match_no=1 and inningno=1 order by ballnumber desc;
select overs, outcome, comment from ballrecord where match_no=1 and inningno=2 order by ballnumber desc;

-- team stats
select * from ballrecord;
select * from matchrecord;

-- filter by team
select match_number, team1, team2 from matchrecord where team1="chennai super kings" or team2="chennai super kings";

-- team full stats
select * from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings");

-- number of matches played


-- total number of runs scored by a team
select SUM(score) from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=1 WHEN team2="chennai super kings" THEN inningno=2 END);

-- total number of runs conceded
select SUM(score) from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=2 WHEN team2="chennai super kings" THEN inningno=1 END);

-- total number of wickets taken
select COUNT(CASE WHEN outcome="w" THEN 1 END) from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=2 WHEN team2="chennai super kings" THEN inningno=1 END);

-- total number of outs
select COUNT(CASE WHEN outcome="w" THEN 1 END) from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=1 WHEN team2="chennai super kings" THEN inningno=2 END);

-- team stats 1
select count(match_number) as matches, count(CASE WHEN winner="chennai super kings" THEN 1 END) as won, count(CASE WHEN winner!="chennai super kings" and winner!="no result" THEN 1 END) as lose, count(CASE WHEN winner="no result" THEN 1 END) as NR, count(CASE WHEN winner="tie" THEN 1 END) as tie
from matchrecord
where team1="chennai super kings" or team2="chennai super kings";

-- team stats 2
select runs, runconced, wickettake, wicketgot
from (
(select SUM(score) as runs from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=1 WHEN team2="chennai super kings" THEN inningno=2 END)) as runscored,
(select SUM(score) as runconced from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=2 WHEN team2="chennai super kings" THEN inningno=1 END)) as runsconceded,
(select COUNT(CASE WHEN outcome="w" THEN 1 END) as wickettake from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=2 WHEN team2="chennai super kings" THEN inningno=1 END)) as wicketstaken,
(select COUNT(CASE WHEN outcome="w" THEN 1 END) as wicketgot from ballrecord
inner join matchrecord on ballrecord.match_no = matchrecord.match_number and (matchrecord.team1="chennai super kings" or matchrecord.team2="chennai super kings")
where (CASE WHEN team1="chennai super kings" THEN inningno=1 WHEN team2="chennai super kings" THEN inningno=2 END)) as wicktetsgot
)