package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents all the missions the user must complete and keeps track of the points a user has
public class DailyMissionList implements Writable {
    private String userName;                    //name of the user
    private List<Mission> dailyMissionsList;    //list of all the Missions added by the user
    private int totalPoints;                    //total amount of points accumulated

    //EFFECTS: set this.userName to userName; instantiate a new empty Missions list
    public DailyMissionList(String userName) {
        this.userName = userName;
        dailyMissionsList = new ArrayList<>();
    }

    //REQUIRES: newMissionName must not be an empty string
    //MODIFIES: this
    //EFFECTS: adds a new Mission to the dailyMissionsList with the given name
    //          sets new Mission's missionNumber to its index in the dailyMissionsList + 1
    //          and logs the event into the EventLog
    public void addMission(String newMissionName) {
        Mission temp = new Mission(newMissionName);
        dailyMissionsList.add(temp);
        temp.setMissionNumber(dailyMissionsList.indexOf(temp) + 1);
        EventLog.getInstance().logEvent(new Event("Added '" + newMissionName + "'"));
    }

    //REQUIRES: only used when the Mission has already been assigned a missionNumber
    //MODIFIES: this
    //EFFECTS: adds a Mission to dailyMissionsList
    //          only used in addMission in JsonReader.java
    //          also logs the event into the EventLog
    public void addMission(Mission mission) {
        dailyMissionsList.add(mission);
        EventLog.getInstance().logEvent(new Event("Added '" + mission.getMissionName() + "'"));
    }

    //REQUIRES: missionNumber is between 1 and dailyMissionsList.size() inclusive
    //MODIFIES: this
    //EFFECTS: checks if the mission is already completed; if it is not,
    //          sets the Mission's field missionComplete to true and adds +1 to totalPoints and returns true
    //          returns false otherwise
    public boolean completeMission(int missionNumber) {
        int index = missionNumber - 1;
        if (!dailyMissionsList.get(index).getIsMissionCompleted()) {
            dailyMissionsList.get(index).setMissionToComplete();
            addPoints(1);
            return true;
        }
        return false;
    }

    //REQUIRES: there must exist a Mission in dailyMissionsList that has the given missionNumber
    //EFFECTS: returns the Mission with the given missionNumber
    public Mission getMission(int missionNumber) {
        int index = missionNumber - 1;
        return dailyMissionsList.get(index);
    }

    //EFFECTS: returns all the Missions in the dailyMissionsList
    public List<Mission> getMissionList() {
        return dailyMissionsList;
    }

    //EFFECTS: returns a list of all the not completed missions (Missions with missionCompleted = false)
    //          or null if the list is empty
    public List<Mission> getNotCompletedMissions() {
        List<Mission> notCompletedMissions = new ArrayList<>();
        for (Mission mission : dailyMissionsList) {
            if (!mission.getIsMissionCompleted()) {
                notCompletedMissions.add(mission);
            }
        }
        return notCompletedMissions;
    }

    //EFFECTS: returns the totalPoints the user has received so far
    public int getTotalPoints() {
        return totalPoints;
    }

    //MODIFIES: this
    //EFFECTS: checks if the total after adding points is less than zero, if it is, set totalPoints = 0
    //          else, adds the given number of points to totalPoints
    public void addPoints(int points) {
        int check = totalPoints + points;
        if (check < 0) {
            totalPoints = 0;
        } else {
            totalPoints = totalPoints + points;
        }
    }

    //EFFECTS: returns the userName of the user
    public String getUserName() {
        return userName;
    }

    //EFFECTS: creates a new JSONObject from the DailyMissionsList
    //CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", userName);
        json.put("totalPoints", totalPoints);
        json.put("missions", missionsToJson());
        return json;
    }

    //EFFECTS: returns all the Missions in this DailyMissionList as a JSON array
    //CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
    public JSONArray missionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Mission m : dailyMissionsList) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: concatenates all the events that were logged since opening the app
    public String getEventsLogged(EventLog eventLog) {
        String eventsLogged = "";

        for (Event nextEvent: eventLog) {
            eventsLogged = eventsLogged + nextEvent.toString() + "\n\n";
        }

        return eventsLogged;
    }
}
