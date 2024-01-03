package persistence;

import model.Mission;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
public class JsonTest {
    protected void checkMission(String missionName, Boolean isMissionCompleted, int missionNumber, Mission mission) {
        assertEquals(missionName, mission.getMissionName());
        assertEquals(isMissionCompleted, mission.getIsMissionCompleted());
        assertEquals(missionNumber, mission.getMissionNumber());
    }
}
