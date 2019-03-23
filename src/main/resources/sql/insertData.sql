-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO horse (PUBLIC_ID, NAME, BREED, MIN_SPEED, MAX_SPEED, CREATED, UPDATED )
VALUES (1, 'Joe', 'Cob', 40.1, 50.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (2, 'Lisa', 'Arab', 40.5, 50.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (3, 'Jim', 'Andalusian', 40.0, 60.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO jockey (PUBLIC_ID, NAME, SKILL, CREATED, UPDATED )
VALUES (1, 'Arthur', -20.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (2, 'Ford', 200.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (3, 'Gillian', 42.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
