create schema critter;

create user 'sa'@'localhost' identified by 'Sa#12345'; -- Create the user
grant all on critter.* to 'sa'@'localhost'; -- Gives all privileges to that user on new db
