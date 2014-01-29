package ua.eugenesokolov.interview.calendar.util;

import java.util.Comparator;
import ua.eugenesokolov.interview.calendar.model.CalendarEvent;

public class CalendarEventComparator<T extends CalendarEvent> implements Comparator<T> {

    public int compare(T o1, T o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("EventTimeComparator can't compare nulls.");
        }
        int dayCompare = o1.getDay().compareTo(o2.getDay());
        
        if (dayCompare == 0) {
            return o1.getBookingTime().getStartTime().compareTo(o2.getBookingTime().getStartTime());
        }
        return dayCompare;
    }
    
}
