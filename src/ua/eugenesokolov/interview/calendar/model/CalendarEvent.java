package ua.eugenesokolov.interview.calendar.model;

import org.joda.time.LocalDate;

public class CalendarEvent  {

    private LocalDate day;
    private Employee employee;
    private EventTime bookingTime;

    public CalendarEvent(LocalDate day, Employee employee, EventTime bookingTime) {
        this.day = day;
        this.employee = employee;
        this.bookingTime = bookingTime;
    }

    public EventTime getBookingTime() {
        return bookingTime;
    }

    public LocalDate getDay() {
        return day;
    }

    public Employee getEmployee() {
        return employee;
    }

}
