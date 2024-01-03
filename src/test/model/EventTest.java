package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//CITATION: inspired byEventTest from AlarmSystem by CPSC 210 Instructors
public class EventTest {

    private Event event;
    private Date date;

    @BeforeEach
    void setUp() {
        event = new Event("Mission was added");
        date = Calendar.getInstance().getTime();
    }

    @Test
    void testEvent() {
        assertEquals("Mission was added", event.getDescription());
        assertTrue(date.toString().equals(event.getDate().toString()));
    }

    @Test
    void testToString() {
        assertEquals(date.toString() + "\n" + "Mission was added", event.toString());
    }

    @Test
    void testEqualsWhenObjectIsNull() {
        Object object = null;
        assertFalse(event.equals(object));
    }

    @Test
    void testEqualsWhenObjectIsDifferentClass() {
        Mission mission = new Mission("exercise");
        assertFalse(event.equals(mission));
    }

    @Test
    void testEqualsWhenSameClassSameDescriptionButDifferentTimeLogged() {
        Event differentEventWithSameDescription = new Event("Mission was not added");
        assertFalse(event.equals(differentEventWithSameDescription));
    }

    @Test
    void testEqualsWhenSameClassSameTimeButDifferentDescription() {
        Event differentEventWithSameDescription = new Event("Mission was completed");
        assertFalse(event.equals(differentEventWithSameDescription));
    }

    @Test
    void testEqualsWhenEqual() {
        assertTrue(event.equals(event));
    }

    @Test
    void testHashCode() {
        int hashCode = 13 * event.getDate().hashCode() + event.getDescription().hashCode();
        assertEquals(hashCode, event.hashCode());
    }
}
