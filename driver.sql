CREATE TABLE driver (
    id     SERIAL PRIMARY KEY,
    name   varchar(200),
    date   date,
    car_id integer,
    FOREIGN KEY (car_id) REFERENCES car(id)
);

