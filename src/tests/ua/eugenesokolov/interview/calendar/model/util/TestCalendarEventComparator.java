package tests.ua.eugenesokolov.interview.calendar.model.util;

import org.joda.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.eugenesokolov.interview.calendar.model.CalendarEvent;
import ua.eugenesokolov.interview.calendar.model.EventTime;
import ua.eugenesokolov.interview.calendar.util.CalendarEventComparator;

public class TestCalendarEventComparator {

    @Test
    public void compareDifferentDays() {
        LocalDate date1 = new LocalDate(2014, 1, 1);
        LocalDate date2 = new LocalDate(2014, 1, 2);
        
        CalendarEvent event1 = new CalendarEvent(date1, null, null);
        CalendarEvent event2 = new CalendarEvent(date2, null, null);
        
        CalendarEventComparator<CalendarEvent> cmp = new CalendarEventComparator<CalendarEvent>();
        
        assertTrue(cmp.compare(event1, event2) < 0);
        assertTrue(cmp.compare(event2, event1) > 0);
    }
    
    @Test
    public void compareDifferentTime() {
        LocalDate date1 = new LocalDate(2014, 1, 1);
        LocalDate date2 = new LocalDate(2014, 1, 1);
        EventTime time1 = new EventTime(9, 0, 1);
        EventTime time2 = new EventTime(10, 0, 1);
        
        CalendarEvent event1 = new CalendarEvent(date1, null, time1);
        CalendarEvent event2 = new CalendarEvent(date2, null, time2);
        
        CalendarEventComparator cmp = new CalendarEventComparator();
        
        assertTrue(cmp.compare(event1, event2) < 0);
        assertTrue(cmp.compare(event2, event1) > 0);
    }
    
     @Test
    public void compareSameTime() {
        LocalDate date1 = new LocalDate(2014, 1, 1);
        LocalDate date2 = new LocalDate(2014, 1, 1);
        EventTime time1 = new EventTime(9, 0, 1);
        EventTime time2 = new EventTime(9, 0, 1);
        
        CalendarEvent event1 = new CalendarEvent(date1, null, time1);
        CalendarEvent event2 = new CalendarEvent(date2, null, time2);
        
        CalendarEventComparator cmp = new CalendarEventComparator();
        
        assertTrue(cmp.compare(event1, event2) == 0);
        assertTrue(cmp.compare(event1, event1) == 0);
        assertTrue(cmp.compare(event2, event2) == 0);
    }
}
