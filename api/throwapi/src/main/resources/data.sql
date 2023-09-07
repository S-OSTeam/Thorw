DROP INDEX IF EXISTS location ON address;
ALTER TABLE address ADD SPATIAL INDEX(location);