CREATE OR REPLACE FUNCTION search_flights(sourceParam varchar, destinationParam varchar, departureAfter timestamptz, departureBefore timestamptz, arrivalAfter timestamptz, arrivalBefore timestamptz, adultTicketMinPrice double precision, adultTicketMaxPrice double precision, childTicketMinPrice double precision, childTicketMaxPrice double precision)
       RETURNS SETOF flight
       AS $$ SELECT f.id, f.source, f.destination, f.departure_date, f.arrival_date, f.ticket_price_adult, f.ticket_price_child
            FROM flight f
            JOIN airport s ON f.source = s.iata
            JOIN airport d ON f.destination = d.iata
            WHERE (sourceParam IS NULL OR s.iata = sourceParam)
            AND (destinationParam IS NULL OR d.iata = destinationParam)
            AND (departureAfter IS NULL OR departureBefore IS NULL OR f.departure_date BETWEEN departureAfter AND departureBefore)
            AND (arrivalAfter IS NULL OR arrivalBefore IS NULL OR f.arrival_date BETWEEN arrivalAfter AND arrivalBefore)
            AND (adultTicketMinPrice IS NULL OR adultTicketMaxPrice IS NULL OR f.ticket_price_adult BETWEEN adultTicketMinPrice AND adultTicketMaxPrice)
            AND (childTicketMinPrice IS NULL OR childTicketMaxPrice IS NULL OR f.ticket_price_child BETWEEN childTicketMinPrice AND childTicketMaxPrice) $$
            LANGUAGE SQL;