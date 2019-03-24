INSERT INTO PUBLIC.HORSE(ID, PUBLIC_ID, NAME, BREED, MIN_SPEED, MAX_SPEED, CREATED, UPDATED, OBSOLETE) VALUES
(1, 1, 'Joe', 'Cob', 40.1, 50.0, TIMESTAMP '2019-03-24 21:17:27.785', TIMESTAMP '2019-03-24 21:17:27.785', FALSE),
(2, 2, 'Lisa', 'Arab', 40.5, 50.7, TIMESTAMP '2019-03-24 21:17:27.785', TIMESTAMP '2019-03-24 21:17:27.785', FALSE),
(3, 3, 'Jim', 'Andalusian', 40.0, 60.0, TIMESTAMP '2019-03-24 21:17:27.785', TIMESTAMP '2019-03-24 21:17:27.785', FALSE),
(4, 4, 'Thunder', '', 42.0, 51.0, TIMESTAMP '2019-03-24 21:19:01.348992', TIMESTAMP '2019-03-24 21:19:01.348992', FALSE),
(5, 5, 'Velvet', 'Lokai', 40.0, 42.0, TIMESTAMP '2019-03-24 21:20:07.690252', TIMESTAMP '2019-03-24 21:20:07.690252', FALSE),
(6, 6, 'Bruiser', 'Lipizzan', 55.0, 56.0, TIMESTAMP '2019-03-24 21:20:30.451965', TIMESTAMP '2019-03-24 21:20:30.451965', FALSE),
(7, 7, 'Maverick', 'Cob', 55.0, 60.0, TIMESTAMP '2019-03-24 21:20:47.448068', TIMESTAMP '2019-03-24 21:20:47.448068', FALSE),
(8, 8, 'Tally', 'Arab', 45.5, 50.8, TIMESTAMP '2019-03-24 21:21:51.249143', TIMESTAMP '2019-03-24 21:21:51.249143', FALSE),
(9, 9, 'Bingo', 'Gidran', 40.9, 59.8, TIMESTAMP '2019-03-24 21:22:40.21826', TIMESTAMP '2019-03-24 21:22:40.21826', FALSE),
(10, 10, 'Splash', 'Cob', 50.1, 54.2, TIMESTAMP '2019-03-24 21:23:23.771706', TIMESTAMP '2019-03-24 21:23:23.771706', FALSE);  

INSERT INTO PUBLIC.JOCKEY(ID, PUBLIC_ID, NAME, SKILL, CREATED, UPDATED, OBSOLETE) VALUES
(1, 1, 'Arthur', -20.0, TIMESTAMP '2019-03-24 21:17:27.788', TIMESTAMP '2019-03-24 21:17:27.788', FALSE),
(2, 2, 'Ford', 200.7, TIMESTAMP '2019-03-24 21:17:27.788', TIMESTAMP '2019-03-24 21:17:27.788', FALSE),
(3, 3, 'Gillian', 42.0, TIMESTAMP '2019-03-24 21:17:27.788', TIMESTAMP '2019-03-24 21:17:27.788', FALSE),
(4, 4, 'Tim', 7755.0, TIMESTAMP '2019-03-24 21:24:59.450164', TIMESTAMP '2019-03-24 21:24:59.450164', FALSE),
(5, 5, 'Arthur', 500.623, TIMESTAMP '2019-03-24 21:25:17.967517', TIMESTAMP '2019-03-24 21:25:17.967517', FALSE),
(6, 6, 'James', -3452.12, TIMESTAMP '2019-03-24 21:25:41.371744', TIMESTAMP '2019-03-24 21:25:41.371744', FALSE),
(7, 7, 'Lenie', -195.54, TIMESTAMP '2019-03-24 21:25:59.627988', TIMESTAMP '2019-03-24 21:25:59.627988', FALSE),
(8, 8, 'Charles', 645.17, TIMESTAMP '2019-03-24 21:26:16.553235', TIMESTAMP '2019-03-24 21:26:16.553235', FALSE),
(9, 9, 'Clarke', 8.45, TIMESTAMP '2019-03-24 21:26:33.176521', TIMESTAMP '2019-03-24 21:26:33.176521', FALSE),
(10, 10, 'John', 0.35, TIMESTAMP '2019-03-24 21:26:56.561927', TIMESTAMP '2019-03-24 21:26:56.561927', FALSE);        
INSERT INTO PUBLIC.SIMULATION(ID, NAME, CREATED) VALUES
(1, 'Sim1', TIMESTAMP '2019-03-24 21:17:27.79'),
(2, 'Sim2', TIMESTAMP '2019-03-24 21:17:27.79'),
(3, 'Sim3', TIMESTAMP '2019-03-24 21:17:27.79'),
(4, 'Sim1', TIMESTAMP '2019-03-24 21:27:19.39209'),
(5, 'Sim2', TIMESTAMP '2019-03-24 21:28:57.171312'),
(6, 'Sim3', TIMESTAMP '2019-03-24 21:30:37.019565'),
(7, 'Sim3', TIMESTAMP '2019-03-24 21:32:11.100734'),
(8, 'Sim3', TIMESTAMP '2019-03-24 21:33:06.653387'),
(9, 'Sim3', TIMESTAMP '2019-03-24 21:33:25.111188'),
(10, 'Sim3', TIMESTAMP '2019-03-24 21:33:47.447368');  

INSERT INTO PUBLIC.SIMULATION_PARTICIPANT(ID, SIMULATION_ID, HORSE_ID, JOCKEY_ID, RANK, AVG_SPEED, HORSE_SPEED, SKILL, LUCK_FACTOR) VALUES
(1, 2, 1, 2, 2, 50.0, 55.0, 20.0, 1.0),
(2, 1, 2, 1, 1, 40.0, 42.0, 24.0, 1.1),
(3, 1, 3, 3, 1, 60, 50, -1.0, 0.9),
(4, 4, 3, 1, 2, 46.835, 50.0, 0.9367, 1.0),
(5, 4, 1, 2, 3, 40.9064, 40.1, 1.0738, 0.95),
(6, 4, 2, 3, 1, 56.9242, 50.7, 1.0693, 1.05),
(7, 5, 7, 2, 1, 62.9032, 58.0, 1.0738, 1.01),
(8, 5, 9, 5, 6, 41.7497, 40.9, 1.0745, 0.95),
(9, 5, 1, 3, 7, 41.5995, 40.694, 1.0693, 0.956),
(10, 5, 2, 1, 5, 49.8652, 50.7, 0.9367, 1.05),
(11, 5, 4, 8, 2, 57.5448, 51.0, 1.0746, 1.05),
(12, 5, 10, 7, 4, 52.71, 54.2, 0.9262, 1.05),
(13, 5, 8, 10, 3, 53.516, 50.8, 1.0033, 1.05),
(14, 6, 1, 4, 3, 50.3023, 46.238, 1.075, 1.012),
(15, 6, 2, 5, 4, 47.4222, 44.58, 1.0745, 0.99),
(16, 6, 3, 6, 6, 42.8275, 47.0, 0.9251, 0.985),
(17, 6, 4, 7, 5, 45.6302, 48.3, 0.9262, 1.02),
(18, 6, 5, 8, 7, 41.471, 40.2, 1.0746, 0.96),
(19, 6, 6, 9, 1, 58.9357, 55.6, 1.0495, 1.01),
(20, 6, 7, 10, 2, 57.6898, 57.5, 1.0033, 1.0),
(21, 7, 5, 3, 4, 44.168, 41.1, 1.0693, 1.005),
(22, 7, 6, 7, 3, 48.3939, 55.0, 0.9262, 0.95),
(23, 7, 4, 4, 2, 49.2562, 46.05, 1.075, 0.995),
(24, 7, 7, 6, 1, 54.1924, 58.0, 0.9251, 1.01),
(25, 7, 3, 5, 5, 43.3238, 42.0, 1.0745, 0.96),
(26, 8, 4, 4, 3, 43.5802, 42.45, 1.075, 0.955),
(27, 8, 8, 8, 1, 57.3192, 50.8, 1.0746, 1.05),
(28, 8, 10, 10, 2, 56.611, 53.995, 1.0033, 1.045),
(29, 9, 3, 4, 2, 42.0916, 41.0, 1.075, 0.955),
(30, 9, 1, 2, 1, 56.3745, 50.0, 1.0738, 1.05),
(31, 10, 3, 1, 2, 41.0977, 45.0, 0.9367, 0.975),
(32, 10, 10, 2, 1, 57.0033, 52.56, 1.0738, 1.01);      


