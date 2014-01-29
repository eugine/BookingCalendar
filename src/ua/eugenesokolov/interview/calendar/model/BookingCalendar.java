package ua.eugenesokolov.interview.calendar.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.joda.time.*;
import ua.eugenesokolov.interview.calendar.util.CalendarEventComparator;

public class BookingCalendar {
	
    private EventTime workingHours;
    private Map<LocalDate, Set<CalendarEvent>> events;

    public BookingCalendar(EventTime workingHours) {
        this.workingHours = workingHours;
        this.events = new HashMap<LocalDate, Set<CalendarEvent>>();
    }

    public EventTime getWorkingHours() {
        return workingHours;
    }

    public boolean addBooking(CalendarEvent event) {
        LocalDate date = event.getDay();
        Set<CalendarEvent> dayEvents = getBookingForDay(date);
        if (dayEvents == null) {
            addEvent(date, event);
            return true;
        }
        return false;
    }

    public Set<CalendarEvent> getBookingForDay(LocalDate date) {
        return events.get(date);
    }
    
    private void addEvent(LocalDate date, CalendarEvent event) {
        Set<CalendarEvent> dayEvents = getBookingForDay(date);
        if (dayEvents == null) {
            dayEvents = new TreeSet<CalendarEvent>(new CalendarEventComparator<CalendarEvent>());
            dayEvents.add(event);
            events.put(date, dayEvents);
        } else {
            dayEvents.add(event);
        }
    }
    
    public int getEventCountForDay(LocalDate date) {
        Set<CalendarEvent> events = getBookingForDay(date);
        if (events == null) {
            return 0;
        }
        return events.size();
    }

}
