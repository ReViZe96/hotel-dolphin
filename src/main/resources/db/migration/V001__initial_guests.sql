CREATE TABLE guests (
id serial,
name varchar(100),
phone varchar(25),
peoples_amount integer,
rooms_amount integer,
check_in timestamp,
check_out timestamp,
nights_amount integer,
info varchar(500),
booked varchar(1),
black_list varchar(1)
);