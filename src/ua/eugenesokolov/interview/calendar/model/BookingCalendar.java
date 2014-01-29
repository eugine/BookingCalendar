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
        if (!workingHours.includeInterval(event.getBookingTime())) {
                return false;
        }
                        
        Set<CalendarEvent> dayEvents = getBookingsForDay(date);
        if (dayEvents == null) {
            addEvent(date, event);
            return true;
        } else {
            EventTime bookingTime = event.getBookingTime();
            for(CalendarEvent dayEvent: dayEvents) {
                if (dayEvent.getBookingTime().overlapInterval(bookingTime)) {
                    return false;
                }
            }
            addEvent(date, event);
        }
        return true;
    }

    public Set<CalendarEvent> getBookingsForDay(LocalDate date) {
        return events.get(date);
    }
    
    public Set<LocalDate> getDaysWithEvents() {
        return events.keySet();
    }
    
    private void addEvent(LocalDate date, CalendarEvent event) {
        Set<CalendarEvent> dayEvents = getBookingsForDay(date);
        if (dayEvents == null) {
            dayEvents = new TreeSet<CalendarEvent>(new CalendarEventComparator<CalendarEvent>());
            dayEvents.add(event);
            events.put(date, dayEvents);
        } else {
            dayEvents.add(event);
        }
    }
    
    public int getDayCountWithEvents() {
        return events.size();
    }
    
    public int getEventCountForDay(LocalDate date) {
        Set<CalendarEvent> events = getBookingsForDay(date);
        if (events == null) {
            return 0;
        }
        return events.size();
    }

}
