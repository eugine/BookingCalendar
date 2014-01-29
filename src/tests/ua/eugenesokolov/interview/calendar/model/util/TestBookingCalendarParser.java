package tests.ua.eugenesokolov.interview.calendar.model.util;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;

import ua.eugenesokolov.interview.calendar.manager.EmployeeManagerImpl;
import ua.eugenesokolov.interview.calendar.model.BookingCalendar;
import ua.eugenesokolov.interview.calendar.util.parser.BookingCalendarParser;

public class TestBookingCalendarParser {

	@Test
	public void calendarParser() {
		String data = "0900 1730\n" + "2011-03-17 10:17:06 EMP001"
				+ "2011-03-21 09:00 2\n" + "2011-03-16 12:34:56 EMP002\n"
				+ "2011-03-21 09:00 2\n" + "2011-03-16 09:28:23 EMP003\n"
				+ "2011-03-22 14:00 2\n" + "2011-03-17 10:17:06 EMP004\n"
				+ "2011-03-22 16:00 1\n" + "2011-03-15 17:29:12 EMP005\n"
				+ "2011-03-21 16:00 3";
		BookingCalendar calendar = new BookingCalendarParser(new EmployeeManagerImpl()).parse(data);
		
		assertEquals(new LocalTime(17, 30), calendar.getWorkingHours().getEndTime());
		assertEquals(new LocalTime(9, 00), calendar.getWorkingHours().getStartTime());
		
		assertEquals(2, calendar.getDayCountWithEvents());
		assertEquals(1, calendar.getEventCountForDay(new LocalDate(2011, 3, 21)));
		assertEquals(2, calendar.getEventCountForDay(new LocalDate(2011, 3, 22)));
	}

}
