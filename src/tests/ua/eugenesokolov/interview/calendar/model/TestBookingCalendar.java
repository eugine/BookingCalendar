package tests.ua.eugenesokolov.interview.calendar.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ua.eugenesokolov.interview.calendar.model.BookingCalendar;
import ua.eugenesokolov.interview.calendar.model.CalendarEvent;
import ua.eugenesokolov.interview.calendar.model.Employee;
import ua.eugenesokolov.interview.calendar.model.EventTime;

public class TestBookingCalendar {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EventTime getWorkingHours() {
        return new EventTime(9, 0, 17, 0);
    }
    
    private BookingCalendar getBookingCalendar() {
        return new BookingCalendar(getWorkingHours());
    }

    @Test
    public void bookingEvent() {
        BookingCalendar calendar = getBookingCalendar();
        //event
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Employee employee = new Employee("BookingEvent");
        EventTime bookingTime = new EventTime(14, 0, 2);
        
        assertNull(calendar.getBookingsForDay(today));
        assertTrue(calendar.addBooking(new CalendarEvent(today, employee, bookingTime)));
        assertTrue(calendar.addBooking(new CalendarEvent(tomorrow, employee, bookingTime)));
        assertEquals(1, calendar.getEventCountForDay(today));
        assertEquals(1, calendar.getEventCountForDay(tomorrow));
        
        assertEquals(2, calendar.getDayCountWithEvents());
    }
    
    @Test
    public void shouldntAddBookingInNotWorkingHours() {
        BookingCalendar calendar = getBookingCalendar();
        //event
        LocalDate today = LocalDate.now();
        Employee employee = new Employee("BookingEvent");
        EventTime bookingTime1 = new EventTime(6, 0, 1);
        EventTime bookingTime2 = new EventTime(8, 0, 2);
        EventTime bookingTime3 = new EventTime(16, 0, 2);
        EventTime bookingTime4 = new EventTime(18, 0, 2);
        
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime1)));
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime2)));
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime3)));
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime4)));

        assertNull(calendar.getBookingsForDay(today));
    }
    
    @Test
    public void shouldntAddBookingForAlreadyBookedTime() {
        BookingCalendar calendar = getBookingCalendar();
        //event
        LocalDate today = LocalDate.now();
        Employee employee = new Employee("BookingEvent");
        EventTime bookingTime = new EventTime(14, 0, 2);
        
        EventTime bookingTime1 = new EventTime(13, 0, 2);
        EventTime bookingTime2 = new EventTime(14, 0, 2);
        EventTime bookingTime3 = new EventTime(14, 0, 2);
        EventTime bookingTime4 = new EventTime(14, 0, 2);
        
        EventTime bookingTime5 = new EventTime(10, 0, 2);
        
        assertTrue(calendar.addBooking(new CalendarEvent(today, employee, bookingTime)));

        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime1)));
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime2)));
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime3)));
        assertFalse(calendar.addBooking(new CalendarEvent(today, employee, bookingTime4)));

        assertTrue(calendar.addBooking(new CalendarEvent(today, employee, bookingTime5)));

        assertEquals(2, calendar.getEventCountForDay(today));
    }
    
}
