package ua.eugenesokolov.interview.calendar.util.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import ua.eugenesokolov.interview.calendar.manager.EmployeeManager;
import ua.eugenesokolov.interview.calendar.model.BookingCalendar;
import ua.eugenesokolov.interview.calendar.model.CalendarEvent;
import ua.eugenesokolov.interview.calendar.model.EventTime;

public class BookingCalendarParser {
    
	private static final String DATE_TIME_FORMAT = "YYYY-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT = "YYYY-MM-dd";
	private static final String TIME_WITH_SEPARATOR_FORMAT = "HH:mm";
	private static final String TIME_FORMAT = "HHmm";

	public final static String WORKING_HOURS_PATTERN = "^(\\d{4})\\s*(\\d{4})$"; //0600 0700
	public final static String REQUEST_DETAILS_PATTERN = "^(\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2})\\s*(\\w*)$";//2011-11-22 22:32:22 UserID
	public final static String EVENT_DETAILS_PATTERN = "^(\\d{4}-\\d{2}-\\d{2})\\s*(\\d{2}:\\d{2})\\s*(\\w*)$";//2011-11-22 22:32 2
	
	private EmployeeManager employeeManager;
	
	public BookingCalendarParser(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public BookingCalendar parse(String data) {
    	BookingCalendar calendar = null;
    	Scanner scanner = new Scanner(data);
    	EventTime workingHours = null; 
    	Pattern requestDetailsPattern = Pattern.compile(REQUEST_DETAILS_PATTERN);
    	Pattern eventDetailsPattern = Pattern.compile(EVENT_DETAILS_PATTERN);
    	
    	List<RecordDetails> parsedDetails = new ArrayList<RecordDetails>();
    		
    	if (scanner.hasNextLine()) {
    		Pattern workingHoursPattern = Pattern.compile(WORKING_HOURS_PATTERN);
    		String line = scanner.nextLine();
    		if (line.matches(WORKING_HOURS_PATTERN)) {
    			Matcher m = workingHoursPattern.matcher(line);
    			m.matches();
    			int groupCount = m.groupCount();
    			if (groupCount == 2) {
    				LocalTime startTime = LocalTime.parse(m.group(1), DateTimeFormat.forPattern(TIME_FORMAT));
    				LocalTime endTime = LocalTime.parse(m.group(2), DateTimeFormat.forPattern(TIME_FORMAT));
    				workingHours = new EventTime(startTime, endTime);
    			}
    		}
    	}
    	
    	if (workingHours == null) {
    		scanner.close();
    		throw new IllegalArgumentException("Incorrect Booking calendar data format - first line must be Working Hours");
    	}
    	
    	calendar = new BookingCalendar(workingHours);
    	
    	while (scanner.hasNextLine()) {
    		String line = scanner.nextLine();
    		
    		if (line.matches(REQUEST_DETAILS_PATTERN)) {
    			Matcher m = requestDetailsPattern.matcher(line);
    			m.matches();
    			int groupCount = m.groupCount();
    			if (groupCount == 2) {
    				DateTime timestamp = DateTime.parse(m.group(1), DateTimeFormat.forPattern(DATE_TIME_FORMAT));
    				String employeeId = m.group(2);
    				parsedDetails.add(new RequestDetails(timestamp, employeeId));
    			}
    		} else if (line.matches(EVENT_DETAILS_PATTERN)) {
    			Matcher m = eventDetailsPattern.matcher(line);
    			m.matches();
    			int groupCount = m.groupCount();
    			if (groupCount == 3) {
    				LocalDate date = LocalDate.parse(m.group(1), DateTimeFormat.forPattern(DATE_FORMAT));
    				LocalTime startTime = LocalTime.parse(m.group(2), DateTimeFormat.forPattern(TIME_WITH_SEPARATOR_FORMAT));
    				int duration = Integer.parseInt(m.group(3));
    				parsedDetails.add(new EventDetails(date, new EventTime(startTime, duration)));
    			}
    		}    		
    	}
    	scanner.close();
    	
    	Map<DateTime, CalendarEvent> events = new TreeMap<DateTime, CalendarEvent>();
    	
    	CalendarEventBuilder calendarEventBuilder = new CalendarEventBuilder(employeeManager);
    	for (RecordDetails details : parsedDetails) {
			calendarEventBuilder.addCalendarEvent(details);
			if (calendarEventBuilder.isCompleted()) {
				CalendarEvent event = calendarEventBuilder.build();
				DateTime dateTime = calendarEventBuilder.getDateTime();
				events.put(dateTime, event);
				calendarEventBuilder.clean();
			}
		}
    	
    	for (DateTime datetime: events.keySet()) {
    		calendar.addBooking(events.get(datetime));
    	}
    	
        return calendar;
    }
    
}


