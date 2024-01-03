package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a single mission or habit the user would like to complete everyday
public class Mission implements Writable {
    private String missionName;         //the name of the mission
    private String missionDescription;  //optional description/extra details of the mission
    private Boolean isMissionCompleted;   //if the mission has been completed for the day, it is true
    //if not completed or need to reset to the next day, it is false
    private int missionNumber;          //the number automatically assigned to this mission when added to the
    //DailyMissionList

    //REQUIRES: name must not be an empty string
    //EFFECTS: missionName is set to name; missionDescription is set to an empty String because it is optional
    //          missionNumber is set to 0 for now; isMissionCompleted is set to false;
    //          and missionCounter is incremented
    public Mission(String name) {
        missionName = name;
        missionDescription = null;
        isMissionCompleted = false;
        missionNumber = 0;
    }

    //MODIFIES: this
    //EFFECTS: sets the missionDescription of a Mission to description
    public void setMissionDescription(String description) {
        missionDescription = description;
    }

    //MODIFIES: this
    //EFFECTS: sets isMissionCompleted to true and logs the event into the EventLog
    public void setMissionToComplete() {
        isMissionCompleted = true;
        EventLog.getInstance().logEvent(new Event("Completed '" + missionName + "'"));
    }

    //MODIFIES: this
    //EFFECTS: sets isMissionCompleted to false
    public void setMissionToNotComplete() {
        isMissionCompleted = false;
    }

    //EFFECTS: returns the name of the Mission
    public String getMissionName() {
        return missionName;
    }

    //EFFECTS: returns the missionDescription
    public String getMissionDescription() {
        return missionDescription;
    }

    //EFFECTS: returns if the mission is completed or not; true = completed, false = not completed
    public Boolean getIsMissionCompleted() {
        return isMissionCompleted;
    }

    //MODIFIES: this
    //EFFECTS: sets the missionNumber to the give number
    public void setMissionNumber(int num) {
        missionNumber = num;
    }

    //EFFECTS: returns the missionNumber of a Mission
    public int getMissionNumber() {
        return missionNumber;
    }

    //EFFECTS: returns the string representation of this Mission
    //CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("missionName", missionName);
        json.put("isMissionCompleted", isMissionCompleted);
        json.put("missionNumber", missionNumber);

        return json;
    }

}
