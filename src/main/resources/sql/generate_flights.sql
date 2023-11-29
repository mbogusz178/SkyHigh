CREATE OR REPLACE PROCEDURE generate_flights() LANGUAGE SQL
AS $$
INSERT INTO flight(id, source, destination, departure_date, arrival_date, ticket_price_adult, ticket_price_child)
SELECT nextval('flight_sequence') id, s.iata source, d.iata destination, dep.departure departure_date, (dep.departure + INTERVAL '10 hours') arrival_date, dep.adult_price ticket_price_adult, (dep.adult_price / 2.0) ticket_price_child
FROM (SELECT generate_series(0, 2000) series) srs, airport s CROSS JOIN airport d CROSS JOIN generate_series(0, 2000), LATERAL (SELECT srs.series, (NOW() + (random() * (INTERVAL '120 days')) + INTERVAL '40 days') departure, (random() * (500 - 200) + 200) adult_price) dep ORDER BY random() LIMIT 200;
$$;