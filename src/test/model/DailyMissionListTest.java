package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//Tests for the DailyMissionList class
public class DailyMissionListTest {
    private DailyMissionList missions;

    @BeforeEach
    public void setUp() {
        missions = new DailyMissionList("Ryuu");
    }

    @Test
    void testGetEventsLogged() {
        EventLog.getInstance().clear();
        Date date0 = Calendar.getInstance().getTime();
        Mission exercise = new Mission("exercise");
        Mission goToSleepEarly = new Mission("go to sleep early");
        missions.addMission(exercise);
        Date date1 = Calendar.getInstance().getTime();
        missions.addMission(goToSleepEarly);
        Date date2 = Calendar.getInstance().getTime();
        exercise.setMissionToComplete();
        Date date3 = Calendar.getInstance().getTime();

        String eventsLogged = missions.getEventsLogged(EventLog.getInstance());
        assertEquals(date0.toString() + "\nEvent log cleared.\n\n" + date1.toString()
                + "\nAdded 'exercise'\n\n" + date2.toString()
                + "\nAdded 'go to sleep early'\n\n" + date3.toString() + "\nCompleted 'exercise'\n\n", eventsLogged);
    }

    @Test
    public void testConstructor() {
        assertEquals("Ryuu", missions.getUserName());
        assertEquals(0, missions.getMissionList().size());
    }

    @Test
    public void testAddOneMission() {
        missions.addMission("go to sleep early");
        assertEquals("go to sleep early", missions.getMission(1).getMissionName());
        assertEquals(1, missions.getMission(1).getMissionNumber());
        assertEquals(1, missions.getMissionList().size());
    }

    @Test
    public void testAddTwoMissions() {
        missions.addMission("go to sleep early");
        missions.addMission("wake up early");
        assertEquals("go to sleep early", missions.getMission(1).getMissionName());
        assertEquals(1, missions.getMission(1).getMissionNumber());
        assertEquals("wake up early", missions.getMission(2).getMissionName());
        assertEquals(2, missions.getMission(2).getMissionNumber());
        assertEquals(2, missions.getMissionList().size());
    }

    @Test
    public void testAddMissionGivenAMission() {
        Mission mission1 = new Mission("exercise");
        mission1.setMissionNumber(1);
        mission1.setMissionToNotComplete();
        missions.addMission(mission1);
        assertEquals(1, missions.getMissionList().size());
        assertEquals("exercise", missions.getMission(1).getMissionName());
        assertEquals(1, missions.getMission(1).getMissionNumber());
    }

    @Test
    public void testAddTwoMissionsGivenAMission() {
        Mission mission1 = new Mission("exercise");
        mission1.setMissionNumber(1);
        mission1.setMissionToNotComplete();

        Mission mission2 = new Mission("go to sleep early");
        mission2.setMissionNumber(2);
        mission2.setMissionToNotComplete(); //TODO: might have to test setting Mission to Complete

        missions.addMission(mission1);
        missions.addMission(mission2);
        assertEquals(2, missions.getMissionList().size());
        assertEquals("exercise", missions.getMission(1).getMissionName());
        assertEquals(1, missions.getMission(1).getMissionNumber());
        assertEquals("go to sleep early", missions.getMission(2).getMissionName());
        assertEquals(2, missions.getMission(2).getMissionNumber());
    }

    @Test
    public void testCompleteMissionListWithOneMission() {
        missions.addMission("go to sleep early");
        assertTrue(missions.completeMission(1));

        assertEquals(1, missions.getMissionList().size());
        assertEquals(0, missions.getNotCompletedMissions().size());

    }

    @Test
    public void testCompleteMissionListWithTwoMissionsCompleteFirstOneFirst() {
        missions.addMission("go to sleep early");
        missions.addMission("exercise");

        assertTrue(missions.completeMission(1));
        assertEquals(2, missions.getMissionList().size());
        assertEquals(1, missions.getNotCompletedMissions().size());
        assertEquals("exercise", missions.getNotCompletedMissions().get(0).getMissionName());

        assertTrue(missions.completeMission(2));
        assertEquals(0, missions.getNotCompletedMissions().size());
    }

    @Test
    public void testCompleteMissionListWithTwoMissionsCompleteSecondOneFirst() {
        missions.addMission("go to sleep early");
        missions.addMission("exercise");

        assertTrue(missions.completeMission(2));
        assertEquals(2, missions.getMissionList().size());
        assertEquals(1, missions.getNotCompletedMissions().size());
        assertEquals("go to sleep early", missions.getMission(1).getMissionName());

        assertTrue(missions.completeMission(1));
        assertEquals(0, missions.getNotCompletedMissions().size());
    }

    @Test
    public void testGetPointsAfterCompletingOneMission() {
        missions.addMission("go to sleep early");
        assertTrue(missions.completeMission(1));
        assertEquals(1, missions.getTotalPoints());
    }

    @Test
    public void testGetPointsAfterCompletingTwoMissions() {
        missions.addMission("go to sleep early");
        missions.addMission("exercise");

        assertTrue(missions.completeMission(1));
        assertTrue(missions.completeMission(2));

        assertEquals(2, missions.getTotalPoints());
    }

    @Test
    public void testCompleteMissionForSameMission() {
        missions.addMission("go to sleep early");
        assertTrue(missions.completeMission(1));
        assertFalse(missions.completeMission(1));
        assertTrue(missions.getMission(1).getIsMissionCompleted());
        assertEquals(1, missions.getTotalPoints());
        assertEquals(0, missions.getNotCompletedMissions().size());
    }

    @Test
    public void testTotalPointsLessThanZero() {
        missions.addMission("exercise");
        assertTrue(missions.completeMission(1));
        assertEquals(1, missions.getTotalPoints());
        missions.addPoints(-5);
        assertEquals(0, missions.getTotalPoints());
    }

    @Test
    public void testTotalPointsLessThanZeroTwice() {
        missions.addMission("exercise");
        assertTrue(missions.completeMission(1));
        assertEquals(1, missions.getTotalPoints());
        missions.addPoints(-5);
        assertEquals(0, missions.getTotalPoints());
        missions.addPoints(-5);
        assertEquals(0, missions.getTotalPoints());
    }

    @Test
    public void testTotalPointsLessThanZeroAndThenGreaterThanZero() {
        missions.addMission("exercise");
        assertTrue(missions.completeMission(1));
        assertEquals(1, missions.getTotalPoints());
        missions.addPoints(-5);
        assertEquals(0, missions.getTotalPoints());
        missions.addPoints(5);
        assertEquals(5, missions.getTotalPoints());
    }
}
