package mbogusz.spring.skyhigh.testUtils.mappers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class TimestampTestProvider implements Function<String, Timestamp> {
    @Override
    public Timestamp apply(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
