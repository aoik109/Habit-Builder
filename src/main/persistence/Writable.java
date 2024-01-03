package persistence;

import org.json.JSONObject;

// An interface with a method toJson; for classes to implement and return the string representation
// of it
// CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
public interface Writable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
