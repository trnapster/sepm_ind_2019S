-- create table horse if not exists
CREATE TABLE IF NOT EXISTS horse (
  -- use auto incrementing IDs as primary key
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  public_id BIGINT AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  breed     TEXT,
  min_speed DOUBLE       NOT NULL,
  max_speed DOUBLE       NOT NULL,
  created   DATETIME     NOT NULL,
  updated   DATETIME     NOT NULL,
  obsolete  BOOLEAN      NOT NULL DEFAULT FALSE
);

-- create table jockey if not exists
CREATE TABLE IF NOT EXISTS jockey (
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  public_id BIGINT AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  skill     DOUBLE       NOT NULL,
  created   DATETIME     NOT NULL,
  updated   DATETIME     NOT NULL,
  obsolete  BOOLEAN      NOT NULL DEFAULT FALSE
);
