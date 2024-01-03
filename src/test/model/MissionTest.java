package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//tests for the Mission class
public class MissionTest {
    private Mission mission;

    @BeforeEach
    public void setUp() {
        mission = new Mission("exercise");
    }

    @Test
    public void testConstructorOfOneMission() {
        assertEquals("exercise", mission.getMissionName());
        assertNull(mission.getMissionDescription());
        assertFalse(mission.getIsMissionCompleted());
        assertEquals(0, mission.getMissionNumber());
    }

    @Test
    public void testSetMissionDescription() {
        mission.setMissionDescription("Workout for 1 hour");
        assertEquals("Workout for 1 hour", mission.getMissionDescription());
    }

    @Test
    public void testSetMissionToComplete() {
        mission.setMissionToComplete();
        assertTrue(mission.getIsMissionCompleted());
    }

    @Test
    public void testSetMissionToNotComplete() {
        mission.setMissionToNotComplete();
        assertFalse(mission.getIsMissionCompleted());
    }

    @Test
    public void testSetMissionNumber() {
        mission.setMissionNumber(1);
        assertEquals(1, mission.getMissionNumber());
    }

}