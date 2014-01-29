package ua.eugenesokolov.interview.calendar.model;

import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EventTime {

    private LocalTime startTime;
    private LocalTime endTime;

    public EventTime(int startHour, int startMinute, int endHour, int endMinute) {
        this(new LocalTime(startHour, startMinute), new LocalTime(endHour, endMinute));
    }
    
    public EventTime(int startHour, int startMinute, int durationInHours) {
        this(new LocalTime(startHour, startMinute), durationInHours);
    }
    
    public EventTime(LocalTime startTime, int durationInHours) {
        this.startTime = startTime;
        this.endTime = startTime.plus(Period.hours(durationInHours));
        verifyTime();
    }

    public EventTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        verifyTime();
    }

    private void verifyTime() {
        if (startTime.getMillisOfDay() >= endTime.getMillisOfDay()) throw new IllegalArgumentException("Start time can't be after end time.");
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    
    public boolean includeInterval(EventTime interval) {
        return startTime.getMillisOfDay() <= interval.startTime.getMillisOfDay() 
                && endTime.getMillisOfDay() >= interval.endTime.getMillisOfDay();
    }
    
    public boolean overlapInterval(EventTime interval) {
        return (startTime.getMillisOfDay() <= interval.startTime.getMillisOfDay() 
                && endTime.getMillisOfDay() > interval.startTime.getMillisOfDay())
                || (startTime.getMillisOfDay() < interval.endTime.getMillisOfDay() 
                && endTime.getMillisOfDay() >= interval.endTime.getMillisOfDay());
    }
    
    @Override
    public String toString() {
        return toString("HH:mm", " ");
    }
    
    public String toString(String pattern, String separator) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return startTime.toString(fmt) +  separator + endTime.toString(fmt);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventTime other = (EventTime) obj;
        if (this.startTime != other.startTime && (this.startTime == null || !this.startTime.equals(other.startTime))) {
            return false;
        }
        if (this.endTime != other.endTime && (this.endTime == null || !this.endTime.equals(other.endTime))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.startTime != null ? this.startTime.hashCode() : 0);
        hash = 59 * hash + (this.endTime != null ? this.endTime.hashCode() : 0);
        return hash;
    }
    
    
}
