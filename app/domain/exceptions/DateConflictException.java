package domain.exceptions;

import org.joda.time.DateTime;

public class DateConflictException extends Throwable {

    public DateConflictException(DateTime startDate, DateTime endDate) {
        super("endDate (" + startDate.toString() + ") must occur after StartDate (" + startDate.toString() + ")");
    }

}
