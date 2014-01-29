package ua.eugenesokolov.interview.calendar.util;

import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ua.eugenesokolov.interview.calendar.model.BookingCalendar;
import ua.eugenesokolov.interview.calendar.model.CalendarEvent;
import ua.eugenesokolov.interview.calendar.model.EventTime;

public class PlainTextCalendarPrinter implements CalendarPrinter {

    public String print(BookingCalendar calendar) {
    	StringBuilder result = new StringBuilder();
//    	EventTime workingHours = calendar.getWorkingHours();
    	Set<LocalDate> daysWithEvents = new TreeSet<LocalDate>(calendar.getDaysWithEvents());
    	for (LocalDate day : daysWithEvents) {
    		result.append(day.toString(DateTimeFormat.forPattern("YYYY-MM-dd")));
    		result.append("\n");
			Set<CalendarEvent> dayEvents = calendar.getBookingsForDay(day);
			for (CalendarEvent event : dayEvents) {
				EventTime eventTime = event.getBookingTime();
				DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
				result.append(String.format("%s %s %s\n", eventTime.getStartTime().toString(formatter), 
						eventTime.getEndTime().toString(formatter), event.getEmployee().getId()));
			}
		}
        return result.toString();
    }
    
}
