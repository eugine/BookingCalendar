package tests.ua.eugenesokolov.interview.calendar.model;

import org.joda.time.LocalTime;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.eugenesokolov.interview.calendar.model.EventTime;

public class TestEventTime {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void durationConversion() {
        LocalTime start = new LocalTime(9, 0);
        int duration = 2;
        EventTime et = new EventTime(start, duration);
        assertEquals(et.getEndTime().getHourOfDay(), 11);
    }

    @Test
    public void wrongDuration() {
        thrown.expect(IllegalArgumentException.class);
        LocalTime start = new LocalTime(9, 0);
        int duration = -2;
        new EventTime(start, duration);
    }

    @Test
    public void startTimeBeforeEndTime() {
        LocalTime start = new LocalTime(17, 0);
        LocalTime end = new LocalTime(9, 0);

        thrown.expect(IllegalArgumentException.class);
        EventTime et = new EventTime(start, end);
    }

    @Test
    public void toStringConversion() {
        LocalTime start = new LocalTime(9, 0);
        LocalTime end = new LocalTime(17, 0);

        EventTime et = new EventTime(start, end);
        assertEquals("09:00 17:00", et.toString());
    }
    
    @Test
    public void intervalInclusion() {
        LocalTime start = new LocalTime(9, 0);
        LocalTime end = new LocalTime(17, 0);

        EventTime et = new EventTime(start, end);
        
        assertTrue(et.includeInterval(new EventTime(9, 0, 17, 0)));
        assertTrue(et.includeInterval(new EventTime(9, 0, 6)));
        assertTrue(et.includeInterval(new EventTime(10, 0, 17, 0)));
        assertTrue(et.includeInterval(new EventTime(14, 30, 16, 25)));
        
        assertFalse(et.includeInterval(new EventTime(8, 59, 6)));
        assertFalse(et.includeInterval(new EventTime(9, 01, 17, 01)));
    }
    
    @Test
    public void intervalOverlap() {
        LocalTime start = new LocalTime(9, 0);
        LocalTime end = new LocalTime(17, 0);

        EventTime et = new EventTime(start, end);
        
        assertTrue(et.overlapInterval(new EventTime(9, 0, 17, 0)));
        assertTrue(et.overlapInterval(new EventTime(9, 0, 6)));
        assertTrue(et.overlapInterval(new EventTime(10, 0, 17, 0)));
        assertTrue(et.overlapInterval(new EventTime(14, 30, 16, 25)));
        assertTrue(et.overlapInterval(new EventTime(8, 59, 6)));
        assertTrue(et.overlapInterval(new EventTime(9, 01, 17, 01)));
        
        assertFalse(et.overlapInterval(new EventTime(8, 59, 9, 0)));
        assertFalse(et.overlapInterval(new EventTime(17, 0, 17, 1)));
        assertFalse(et.overlapInterval(new EventTime(8, 58, 8, 59)));        
        assertFalse(et.overlapInterval(new EventTime(17, 1, 17, 2)));
        assertFalse(et.overlapInterval(new EventTime(6, 0, 1)));
        assertFalse(et.overlapInterval(new EventTime(18, 0, 1)));
        
    }
    
}
