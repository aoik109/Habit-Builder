package persistence;

import model.DailyMissionList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterEmptyDailyMissionsList() {
        try {
            DailyMissionList dailyMissions = new DailyMissionList("Allison");
            JsonWriter writer = new JsonWriter("./data/testEmptyDailyMissionList.json");
            writer.open();
            writer.write(dailyMissions);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyDailyMissionList.json");
            dailyMissions = reader.read();
            assertEquals("Allison", dailyMissions.getUserName());
            assertEquals(0, dailyMissions.getTotalPoints());
            assertEquals(0, dailyMissions.getMissionList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDailyMissionsListNoCompletedMissions() {
        try {
            DailyMissionList dailyMissions = new DailyMissionList("Allison");
            dailyMissions.addMission("exercise");
            dailyMissions.addMission("go to sleep early");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDailyMissionList.json");
            writer.open();
            writer.write(dailyMissions);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDailyMissionList.json");
            dailyMissions = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDailyMissionsListCompletedOneOfTwoMissions() {
        try {
            DailyMissionList dailyMissions = new DailyMissionList("Allison");
            dailyMissions.addMission("exercise");
            dailyMissions.addMission("go to sleep early");
            assertTrue(dailyMissions.completeMission(1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDailyMissionListOneCompleted.json");
            writer.open();
            writer.write(dailyMissions);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDailyMissionListOneCompleted.json");
            dailyMissions = reader.read();
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
