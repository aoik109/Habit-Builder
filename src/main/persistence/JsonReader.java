package persistence;

import model.DailyMissionList;
import model.Mission;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads a JSON representation of a Mission stored in file
// CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads DailyMissionList from file and returns it;
    // throws IOException if an error occurs while reading data from file
    public DailyMissionList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDailyMissionList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses DailyMissionList from JSON object and returns it
    private DailyMissionList parseDailyMissionList(JSONObject jsonObject) {
        String name = jsonObject.getString("username");
        int totalPoints = jsonObject.getInt("totalPoints");
        DailyMissionList dailyMissions = new DailyMissionList(name);
        dailyMissions.addPoints(totalPoints);
        addMissions(dailyMissions, jsonObject);
        return dailyMissions;
    }

    // MODIFIES: dailyMissions
    // EFFECTS: parses Missions from JSON object and adds them to DailyMissionList
    private void addMissions(DailyMissionList dailyMissions, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("missions");
        for (Object json : jsonArray) {
            JSONObject nextMission = (JSONObject) json;
            addMission(dailyMissions, nextMission);
        }
    }

    // MODIFIES: dailyMissions
    // EFFECTS: parses Mission from JSON object and adds it to DailyMissionList
    private void addMission(DailyMissionList dailyMissions, JSONObject jsonObject) {
        String missionName = jsonObject.getString("missionName");
        boolean isMissionCompleted = jsonObject.getBoolean("isMissionCompleted");
        int missionNumber = jsonObject.getInt("missionNumber");
        Mission mission = new Mission(missionName);
        if (isMissionCompleted) {
            mission.setMissionToComplete();
        } else {
            mission.setMissionToNotComplete();
        }
        mission.setMissionNumber(missionNumber);
        dailyMissions.addMission(mission);
    }
}
