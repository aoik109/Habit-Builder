package persistence;

import model.DailyMissionList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Tests for the JsonReader class
// CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/invalidFile.json");
        try {
            DailyMissionList dailyMissions = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyDailyMission() {
        JsonReader reader = new JsonReader("./data/testEmptyDailyMissionList.json");
        try {
            DailyMissionList dailyMissions = reader.read();
            assertEquals("Allison", dailyMissions.getUserName());
            assertEquals(0, dailyMissions.getTotalPoints());
            assertEquals(0, dailyMissions.getMissionList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDailyMissionList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDailyMissionList.json");
        try {
            DailyMissionList dailyMissions = reader.read();
            assertEquals("Allison", dailyMissions.getUserName());
            assertEquals(0, dailyMissions.getTotalPoints());
            assertEquals(2, dailyMissions.getMissionList().size());
            assertEquals("exercise", dailyMissions.getMission(1).getMissionName());
            assertEquals(1, dailyMissions.getMission(1).getMissionNumber());
            assertFalse(dailyMissions.getMission(1).getIsMissionCompleted());
            assertEquals("go to sleep early", dailyMissions.getMission(2).getMissionName());
            assertEquals(2, dailyMissions.getMission(2).getMissionNumber());
            assertFalse(dailyMissions.getMission(2).getIsMissionCompleted());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneCompletedMission() {
        JsonReader reader = new JsonReader("./data/testReaderDailyMissionListOneCompleted.json");
        try {
            DailyMissionList dailyMissions = reader.read();
            assertEquals("Allison", dailyMissions.getUserName());
            assertEquals(1, dailyMissions.getTotalPoints());
            assertEquals(2, dailyMissions.getMissionList().size());

            assertEquals("exercise", dailyMissions.getMission(1).getMissionName());
            assertEquals(1, dailyMissions.getMission(1).getMissionNumber());
            assertTrue(dailyMissions.getMission(1).getIsMissionCompleted());

            assertEquals("go to sleep early", dailyMissions.getMission(2).getMissionName());
            assertEquals(2, dailyMissions.getMission(2).getMissionNumber());
            assertFalse(dailyMissions.getMission(2).getIsMissionCompleted());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
