package ui;

import model.DailyMissionList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Daily Mission App
public class DailyMissionApp {
    private static final String JSON_STORE = "./data/dailyMissions1.json";
    private String userName;
    private DailyMissionList dailyMissions;
    private Scanner scan;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //CITATION: inspired by TellerApp() by CPSC 210 instructors
    //EFFECTS: runs the DailyMissionApp
    public DailyMissionApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDailyMissionApp();

    }

    //CITATION: inspired by runTeller() by CPSC 210 instructors
    //MODIFIES: this
    //EFFECTS: first, walks the user through the overview and set up
    //          then, leads the user to the home screen, where the user can select different actions or menus
    public void runDailyMissionApp() {
        boolean continueApp;
        boolean keepGoing;
        String userCommand;
        if (welcomePageNewOrNotToApp()) {
            keepGoing = true;
            appOverview();
            userCommand = scan.next();
            continueToSetUp(userCommand);
            setUp();
        } else {
            keepGoing = askToLoadFile();
        }
        if (!keepGoing) {
            System.out.println("ERROR: please restart the app and redo setup");
            continueApp = false;
        } else {
            continueApp = true;
            userCommand = scan.next();
            continueToHomeScreen(userCommand);
        }
        continueApp(continueApp);
    }

    // EFFECTS: keeps the app running until the user enters 'quit'
    public void continueApp(Boolean continueApp) {
        String userCommand;
        while (continueApp) {
            displayHomeScreen();
            userCommand = scan.next();
            if (userCommand.equals("quit")) {
                askToSaveProgress();
                continueApp = false;
            } else if (userCommand.equals("new day")) {
                newDay();
            } else {
                readCommand(userCommand);
            }
        }
        System.out.println("Thank you for using the Daily Missions App!!");
    }

    //REQUIRES: userCommand is not null
    //EFFECTS: brings the user to the home screen
    private void continueToHomeScreen(String userCommand) {
        while (!userCommand.equals("continue")) {
            System.out.println("To go to the Home Screen please type 'continue': ");
            userCommand = scan.next();
        }
    }

    //REQUIRES: userCommand is not null
    //EFFECTS: brings the user to the setUp stage
    private void continueToSetUp(String userCommand) {
        while (!userCommand.equals("y")) {
            System.out.println("Please type <y> to continue to the setting up portion.");
            userCommand = scan.next();
        }
    }

    //MODIFIES: this
    //EFFECTS: displays the welcome menu, asks the user to input their name,
    //         instantiates the DailyMissionlist and the Scanner
    //          returns true if the user is new to the app
    //          returns false if the user is not new to the app
    public boolean welcomePageNewOrNotToApp() {
        String answer;
        boolean newToApp;
        scan = new Scanner(System.in).useDelimiter("\\n");
        System.out.println("Welcome to the Daily Mission App!!");
        System.out.print("Is this your first time opening this app? Enter (y/n): ");
        answer = scan.next();
        while (!answer.equals("n") && !answer.equals("y")) {
            System.out.print("Is this your first time opening this app? Enter (y/n): ");
            answer = scan.next();
        }
        if (answer.equals("n")) {
            newToApp = false;
        } else {
            System.out.print("Please enter your name: ");
            userName = scan.next();
            System.out.println();
            newToApp = true;
        }
        return newToApp;
    }

    //MODIFIES: this
    //EFFECTS: where we set the name of the user and tell the user about the app
    public void appOverview() {
        System.out.println("Hello " + userName + ", I hope you are ready to build good habits!!");
        System.out.println("We will now go over the overview of this app.");
        System.out.println();
        System.out.println("First, you will enter your missions, rewards, and punishments");
        System.out.println("\t Missions - the habits that you want to complete everyday");
        System.out.print("\t Rewards - what you will receive if you finish all missions for the day ");
        System.out.println("or purchase from the reward shop");
        System.out.println("\t Punishments - what you will receive if you fail to finish all missions for the day");
        System.out.println();
        System.out.println("Second, we have a point system, and you can use these points to purchase rewards.");
        System.out.println("\t +1 point - if you complete a mission");
        System.out.println("\t +10 points - if you complete all your missions for the day");
        System.out.println("\t -5 points - if you do not complete all your missions for the day");
        System.out.println("Note: you will never have less than 0 points");
        System.out.println();
        System.out.println("Please type <y> to continue to the setting up portion.");
    }

    //MODIFIES: this
    //EFFECTS: where the user adds Missions, adds Rewards, and adds Punishments
    public void setUp() {
        System.out.println("-------------------------------- SET UP ----------------------------------");
        dailyMissions = new DailyMissionList(userName);
        setUpMissions();
        //setUpRewards();
        //setUpPunishments
        System.out.println();
        System.out.println("To go to the Home Screen please type 'continue': ");
    }

    //EFFECTS: displays all the user's not completed Missions and the different menus they can go to:
    //          complete mission, missions menu, rewards menu, punishments menu, and points menu
    //          and the options to go to a new day or quit the app
    public void displayHomeScreen() {
        System.out.println("-------------------------- HOME SCREEN ----------------------------");
        System.out.println("Here are the missions you still have to complete for the day");
        viewNotCompletedDailyMissionList();
        System.out.println();
        System.out.println("Select from the following: ");
        System.out.println("'complete' -> to complete a mission  ");
        System.out.println("'missions' -> to view all missions");
        System.out.println("'rewards' -> to view the rewards menu and shop");
        System.out.println("'punishments' -> to view the punishments menu");
        System.out.println("'points' -> to view the total number of points you have");
        System.out.println();
        System.out.println("Enter 'new day' if it is the next day");
        System.out.println("Enter 'quit' if you would like to quit and save your progress");
        System.out.println();
        System.out.print("Enter here: ");
    }

    //MODIFIES: this
    //EFFECTS: the dailyMissions will be reset (all Missions will be set to missionCompleted = false)
    public void newDay() {
        System.out.println("STILL UNDER CONSTRUCTION");
    }

    //MODIFIES: this
    //EFFECTS: reads the user input and sends the user to the correct menu/option
    public void readCommand(String userCommand) {
        switch (userCommand) {
            case "complete":
                completeAMission();
                break;
            case "missions":
                displayMissionMenu();
                break;
            case "rewards":
                displayRewardsMenu();
                break;
            case "punishments":
                displayPunishmentsMenu();
                break;
            case "points":
                displayTotalPoints();
                break;
            case "new day":
                newDay();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds Missions to dailyMissions.getMissionList()
    public void setUpMissions() {
        boolean moreMissions = true;
        String missionName;
        String response;
        System.out.println("Now, please enter your first mission!");
        while (moreMissions) {
            System.out.print("\nWhat is the name of the mission? ");
            missionName = scan.next();
            dailyMissions.addMission(missionName);
            System.out.println("\t Awesome!! You just added mission: " + missionName);
            System.out.println();
            System.out.print("Would you like to add another mission? Enter (y/n): ");
            response = scan.next();
            while (!response.equals("n") && !response.equals("y")) {
                System.out.print("Would you like to add another mission? Enter (y/n): ");
                response = scan.next();
            }
            if (response.equals("n")) {
                moreMissions = false;
            }
        }
        System.out.println("\nHere are all of your missions: ");
        viewDailyMissionList();
    }

    //EFFECTS: presents all the missions in dailyMissions.getMissionList()
    public void viewDailyMissionList() {
        for (int i = 1; i <= dailyMissions.getMissionList().size(); i++) {
            System.out.print("\t \t" + dailyMissions.getMission(i).getMissionNumber() + "    ");
            System.out.println("\t" + dailyMissions.getMission(i).getMissionName());
        }
    }

    //EFFECTS: presents only the missions that has missionCompleted = false
    public void viewNotCompletedDailyMissionList() {
        for (int i = 0; i < dailyMissions.getNotCompletedMissions().size(); i++) {
            System.out.print("\t \t" + dailyMissions.getNotCompletedMissions().get(i).getMissionNumber() + "    ");
            System.out.println("\t" + dailyMissions.getNotCompletedMissions().get(i).getMissionName());
        }
    }

    //REQUIRES: missionNumber is between 1 and dailyMissionsList.size()
    //MODIFIES: this
    //EFFECTS: checks if the Mission is complete or not
    //          if not, sets the selected Mission to missionCompleted = true and adds 1 point to totalPoints
    //          if true, then doesn't do anything
    //          also has the option to go back to the home screen
    public void completeAMission() {
        int missionNumber;
        boolean completeAMissionMenu = true;
        String userCommand;
        while (completeAMissionMenu) {
            completeAMissionMenuDisplay();
            userCommand = scan.next();
            if (userCommand.equals("complete")) {
                System.out.print("Please enter the number of the mission you want to complete: ");
                missionNumber = scan.nextInt();
                while (!(missionNumber <= dailyMissions.getMissionList().size() && missionNumber >= 1)) {
                    System.out.print("Please enter the number of the mission you want to complete: ");
                    missionNumber = scan.nextInt();
                }
                boolean isSuccessfulCompletion = dailyMissions.completeMission(missionNumber);
                checkIfAllMissionsAreComplete(isSuccessfulCompletion);
            } else if (userCommand.equals("home screen")) {
                completeAMissionMenu = false;
            }
        }
    }

    //EFFECTS: presents the options in the complete (a mission) menu
    private void completeAMissionMenuDisplay() {
        System.out.println();
        System.out.println("Here are the missions you still have to complete for the day");
        viewNotCompletedDailyMissionList();
        System.out.println();
        System.out.println("Please select one of the following: ");
        System.out.println("\t'complete' -> to choose a mission to complete");
        System.out.println("\t'home screen' -> to return to the Home Screen");
    }

    //MODIFIES: this
    //EFFECTS: checks if all Missions in dailyMissions are complete
    //          if they are, adds +1 and +5 points to totalPoints
    //          else if the Mission is already complete, do nothing
    //          else, lets the user know that they gained +1 point to totalPoints
    public void checkIfAllMissionsAreComplete(boolean isCompleteSuccess) {
        if (dailyMissions.getNotCompletedMissions().size() == 0 && isCompleteSuccess) {
            System.out.println();
            System.out.print("Good work! You have completed all your missions for the day +1 point and ");
            System.out.println("an extra +10 points!!");
            dailyMissions.addPoints(10);
            System.out.println("You now have " + dailyMissions.getTotalPoints() + " points.");
            System.out.print("Once it is the next day, please enter 'next day' in the Home Screen and ");
            System.out.println("daily missions will be reset.");
            System.out.println("--------------------------------------------------------------------");
        } else if (isCompleteSuccess) {
            System.out.println("\nNice!! +1 points!! You now have " + dailyMissions.getTotalPoints() + " point");
        } else {
            System.out.print("\nYou have already completed this mission!! You have ");
            System.out.println(dailyMissions.getTotalPoints() + " point.");
        }
    }

    //EFFECTS: displays all the missions and the options to add a Mission or go back to the home screen
    public void displayMissionMenu() {
        String missionCommand;
        boolean displayMissionMenu = true;
        while (displayMissionMenu) {
            System.out.println();
            System.out.println("Here are all of your Missions");
            viewDailyMissionList();
            System.out.println();
            System.out.println("Select from the following: ");
            System.out.print("add -> add a new mission ");
            System.out.println("(WARNING: ONLY ADD A NEW MISSION BEFORE YOU COMPLETE ALL OF THEM FOR THE DAY)");
            System.out.println("description -> set the description of a mission");
            System.out.println("home screen -> to return to the home screen");
            missionCommand = scan.next();
            if (missionCommand.equals("home screen")) {
                displayMissionMenu = false;
            }
            readMissionMenuCommands(missionCommand);
        }
    }

    //REQUIRES: dailyMissions.getNotCompleteMissions() is not empty
    //EFFECTS: reads the user input
    //          if they type add, they will be asked to enter the name of Mission and the Mission will be added to
    //          dailyMissions
    //          if they type description, they will be asked to enter the missionNumber of the mission they would
    //          like to add the mission to and then they will be asked to enter the description for that mission
    public void readMissionMenuCommands(String userCommand) {
        String missionName;
        //int missionNumber;
        if (userCommand.equals("add")) {
            System.out.println();
            System.out.print("What is the name of the mission? ");
            missionName = scan.next();
            dailyMissions.addMission(missionName);
            System.out.println("\tAwesome!! You just added mission: " + missionName);
            //WILL ADD OPTION TO SET DESCRIPTION IN THE FUTURE
        } else if (userCommand.equals("description")) {
            System.out.println("STILL UNDER CONSTRUCTION");
           /* System.out.print("Enter the number of the mission you would like to set a description for: ");
            missionNumber = scan.nextInt();
            dailyMissions.getMission(missionNumber).setMissionDescription();*/
        }
    }

    //EFFECTS: displays the rewards menu with its options
    public void displayRewardsMenu() {
        String rewardsMenuCommand;
        boolean displayRewardsMenu = true;
        while (displayRewardsMenu) {
            System.out.println("STILL UNDER CONSTRUCTION FOR NEXT PHASES");
            System.out.println("home screen -> to return to the home screen");
            rewardsMenuCommand = scan.next();
            if (rewardsMenuCommand.equals("home screen")) {
                displayRewardsMenu = false;
            }
        }
    }

    //EFFECTS: displays the punishments menu with its options
    public void displayPunishmentsMenu() {
        System.out.println("STILL UNDER CONSTRUCTION FOR NEXT PHASES");
    }

    //EFFECTS: displays the totalPoints the user currently has and the option to return to the home screen
    public void displayTotalPoints() {
        boolean displayTotalPoints = true;
        String userCommand;
        while (displayTotalPoints) {
            System.out.println("\nTotal Points: " + dailyMissions.getTotalPoints());
            System.out.println("'home screen' -> to return to the Home Screen");
            userCommand = scan.next();
            if (userCommand.equals("home screen")) {
                displayTotalPoints = false;
            }
        }
    }

    public void askToSaveProgress() {
        System.out.print("Would you like to save your progress? Enter (y/n): ");
        String answer = scan.next();
        while (!answer.equals("n") && !answer.equals("y")) {
            System.out.print("Would you like to save your progress? Enter (y/n): ");
            System.out.println("WARNING!! if you enter 'n', your progress will be deleted");
            answer = scan.next();
        }
        if (answer.equals("y")) {
            saveDailyMissionsList();
            System.out.println("See you later!!");
        } else {
            System.out.println("Your progress was not saved. Goodbye!!");
        }
    }

    // EFFECTS: asks if user would like to load DailyMissionList to file
    public boolean askToLoadFile() {
        boolean keepGoing;
        boolean exceptionOrNot;
        System.out.print("Would you like to load your daily missions from file? Enter (y/n): ");
        String answer = scan.next();
        while (!answer.equals("n") && !answer.equals("y")) {
            System.out.print("Would you like to load your daily missions from file? Enter (y/n): ");
            answer = scan.next();
        }
        if (answer.equals("y")) {
            exceptionOrNot = loadDailyMissionList();
            if (exceptionOrNot) {
                keepGoing = false;
            } else {
                keepGoing = true;
                System.out.println("Enter continue if you would like to continue to the home screen");
            }
        } else {
            keepGoing = false;
        }
        return keepGoing;
    }

    // MODIFIES: this
    // EFFECTS: saves the DailyMissionList to file
    // CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
    private void saveDailyMissionsList() {
        try {
            jsonWriter.open();
            jsonWriter.write(dailyMissions);
            jsonWriter.close();
            System.out.println("Saved " + dailyMissions.getUserName() + "'s progress and Daily Missions to "
                    + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads DailyMissionList from file
    private boolean loadDailyMissionList() {
        boolean exceptionCaught;
        try {
            dailyMissions = jsonReader.read();
            System.out.println("Loaded " + dailyMissions.getUserName() + "'s progress and daily missions from "
                    + JSON_STORE);
            exceptionCaught = false;
        } catch (IOException e) {
            exceptionCaught = true;
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        return exceptionCaught;
    }

    //FOR FUTURE PHASES
    /*public void setUpRewards() {

    }

    public void setUpPunishments() {

    }*/
}