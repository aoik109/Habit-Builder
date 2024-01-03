package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//CITATION: inspired byEventLogTest from AlarmSystem by CPSC 210 Instructors
public class EventLogTest {

    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    void setUp() {
        event1 = new Event("event1");
        event2 = new Event("event2");
        event3 = new Event("event3");
        EventLog el = EventLog.getInstance();
        el.logEvent(event1);
        el.logEvent(event2);
        el.logEvent(event3);
    }

    @Test
    void testLogEvent() {
        List<Event> l = new ArrayList<>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(event1));
        assertTrue(l.contains(event2));
        assertTrue(l.contains(event3));
    }

    @Test
    public void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
