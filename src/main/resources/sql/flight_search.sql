CREATE OR REPLACE FUNCTION search_flights(sourceParam varchar, destinationParam varchar, departureAfter timestamptz, departureBefore timestamptz, arrivalAfter timestamptz, arrivalBefore timestamptz)
       RETURNS SETOF flight
       AS $$ SELECT f.id, f.source, f.destination, f.departure_date, f.arrival_date, f.ticket_price_adult, f.ticket_price_child, f.plane
            FROM flight f
            JOIN airport s ON f.source = s.iata
            JOIN airport d ON f.destination = d.iata
            WHERE (sourceParam IS NULL OR s.city = sourceParam)
            AND (destinationParam IS NULL OR d.city = destinationParam)
            AND (departureAfter IS NULL OR departureBefore IS NULL OR f.departure_date BETWEEN departureAfter AND departureBefore)
            AND (arrivalAfter IS NULL OR arrivalBefore IS NULL OR f.arrival_date BETWEEN arrivalAfter AND arrivalBefore) $$
            LANGUAGE SQL;