package ui;

import model.DailyMissionList;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Daily Missions App GUI
// CITATION: inspired by TextSamplerDemo.java in
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class DailyMissionAppGui extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/dailyMissionsGui.json";
    private String userName;
    private DailyMissionList dailyMissions;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private final JPanel welcomePanel;
    private final JPanel homePage;
    private final JPanel allMissionsPanel;
    private JPanel checkBoxPanel;
    private JPanel completeAndAddPanel;

    private final ImageIcon youGotThisIcon;
    private final JLabel youGotThisIconLabel;
    private final ImageIcon youGotThisIcon2AddMission;
    private final JLabel youGotThisIconLabel2AddMission;

    private String viewNotCompletedDialogue;
    private final CardLayout cardLayout = new CardLayout();

    //EFFECTS: instantiates the jsonWriter, jsonReader, the welcome panel, and other JPanel components
    public DailyMissionAppGui() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setLayout(cardLayout);
        welcomePanel = new JPanel();
        JLabel welcome = new JLabel("Welcome to the Daily Mission App");
        welcome.setFont(new Font("Monospaced", Font.PLAIN, 25));
        JButton welcomeButton = new JButton("Start");
        welcomeButton.setPreferredSize(new Dimension(200,60));
        welcomeButton.setActionCommand("welcomeButton");
        welcomeButton.addActionListener(this);
        welcomePanel.add(welcome);
        welcomePanel.add(welcomeButton);

        homePage = new JPanel();
        allMissionsPanel = new JPanel();

        youGotThisIcon = new ImageIcon("src/main/images/youGotThisBaby.jpg");
        youGotThisIconLabel = new JLabel(youGotThisIcon);

        youGotThisIcon2AddMission = new ImageIcon("src/main/images/youGotThisClipArt.jpg");
        youGotThisIconLabel2AddMission = new JLabel(youGotThisIcon2AddMission);

        add("Welcome Panel", welcomePanel);
        add("Home Page", homePage);
        add("All Missions", allMissionsPanel);

    }

    //EFFECTS: how the DailyMissionAppGui behaves when an event occurs
    @Override
    public void actionPerformed(ActionEvent event) {
        if ("welcomeButton".equals(event.getActionCommand())) {
            welcomeAndSetUp();
        } else if ("toHomePage".equals(event.getActionCommand())) {
            cardLayout.show(this, "Home Page");
            homePage();
        } else if ("not completed missions".equals(event.getActionCommand())) {
            viewNotCompletedMissions();
            viewNotCompletedMissionsDialogue();
        } else if ("Complete Mission".equals(event.getActionCommand())) {
            completeMission();
            displayMotivationalIcon();
        } else if ("Add Mission".equals(event.getActionCommand())) {
            addMission();
        } else if ("View Points".equals(event.getActionCommand())) {
            viewPoints();
        } else if ("View All Missions".equals(event.getActionCommand())) {
            cardLayout.show(this, "All Missions");
            viewAllMissionsPanel();
        } else if ("Quit".equals(event.getActionCommand())) {
            askToSaveToFile();
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the user's username, displays the overview, and asks if this is the user's first time opening the
    // app
    public void welcomeAndSetUp() {
        userName = JOptionPane.showInputDialog("Please enter your name: ");
        JOptionPane.showMessageDialog(null, "Hello " + userName + ", I hope you are ready to "
                + "build good habits!!"
                + "\nWe will now go over the overview of this app. "
                + "\nFirst, you will enter your missions, the habits that you want to complete everyday"
                + "\nSecond, we have a point system: "
                + "\n\t+1 point - if you complete a mission"
                + "\n\t+10 points - if you complete all your missions for the day"
                + "\n\t-5 points - if you do not complete all your missions for the day"
                + "\nNote: you will never have less than 0 points");
        int userResponseFirstTime = JOptionPane.showConfirmDialog(this,
                "Is this your first time opening this app?", null, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (userResponseFirstTime == JOptionPane.YES_OPTION) {
            setUp();
        } else if (userResponseFirstTime == JOptionPane.NO_OPTION) {
            loadFromFile();
        }
    }

    //MODIFIES: this
    //EFFECTS: instantiates DailyMissionList, and asks the user to add at least one mission
    public void setUp() {
        dailyMissions = new DailyMissionList(userName);
        JOptionPane.showMessageDialog(null, userName
                + ", you will now be adding your missions");
        boolean addMoreMissionsSetUp = true;
        int addMoreMissionsInt;
        while (addMoreMissionsSetUp) {
            String missionName = JOptionPane.showInputDialog("Enter the name of the mission: ");
            dailyMissions.addMission(missionName);
            addMoreMissionsInt = JOptionPane.showConfirmDialog(null,
                    "Would you like to enter another mission?", null, JOptionPane.YES_NO_OPTION);
            addMoreMissionsSetUp = addMoreMissionsInt == JOptionPane.YES_OPTION;

            JButton setUpToHomePageButton = new JButton("Continue to Home Page");
            setUpToHomePageButton.setPreferredSize(new Dimension(200,60));
            setUpToHomePageButton.setActionCommand("toHomePage");
            setUpToHomePageButton.addActionListener(this);
            welcomePanel.remove(1);
            welcomePanel.add(setUpToHomePageButton);
            welcomePanel.revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks the user if they would like to load their application from file
    // and creates a button to continue to home page
    public void loadFromFile() {
        int userResponse = JOptionPane.showConfirmDialog(this,
                "Would you like to load your daily missions from file?",
                null, JOptionPane.YES_NO_OPTION);
        if (userResponse == JOptionPane.YES_OPTION) {
            loadDailyMissionList();
        } else {
            JOptionPane.showMessageDialog(null, "Please redo setup");
            setUp();
        }

        JButton setUpToHomePageButton = new JButton("Continue to Home Page");
        setUpToHomePageButton.setPreferredSize(new Dimension(200,60));
        setUpToHomePageButton.setActionCommand("toHomePage");
        setUpToHomePageButton.addActionListener(this);
        welcomePanel.remove(1);
        welcomePanel.add(setUpToHomePageButton);
        welcomePanel.revalidate();
    }

    //MODIFIES: this
    //EFFECTS: loading the state of the application from the top list
    public void loadDailyMissionList() {
        try {
            dailyMissions = jsonReader.read();
            JOptionPane.showConfirmDialog(null, "Loaded " + dailyMissions.getUserName() + "'s "
                    + "progress and daily missions from "
                    + JSON_STORE, null, JOptionPane.DEFAULT_OPTION);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
        EventLog.getInstance().clear();
    }

    //MODIFIES: this
    //EFFECTS: creates the home page, adding all of its components
    public void homePage() {
        homePage.setLayout(new BorderLayout());
        checkBoxPanel = new JPanel(new GridLayout(0, 1));

        JLabel dailyMissionsLabel = new JLabel("Daily Missions");
        addDailyMissionsLabel(dailyMissionsLabel);

        JButton viewNotCompletedMissionsButton = new JButton("View not completed missions");
        addViewNotCompletedMissionsButton(viewNotCompletedMissionsButton);

        completeAndAddPanel = new JPanel();
        addCompleteMissionButton();

        JButton addMission = new JButton("Add Mission");
        addMissionButton(addMission);

        JButton viewAllMissionsButton = new JButton("View All Missions");
        addViewAllMissionsButton(viewAllMissionsButton);

        JButton viewPoints = new JButton("View Points");
        addViewPointsButton(viewPoints);

        JButton quitButton = new JButton("Quit");
        addQuitButton(quitButton);

        checkBoxPanel.add(viewNotCompletedMissionsButton);
        homePage.add(checkBoxPanel, BorderLayout.NORTH);
        homePage.add(completeAndAddPanel, BorderLayout.SOUTH);
    }

    //EFFECTS: adds a quit button and sets its action command
    public void addQuitButton(JButton quitButton) {
        quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        completeAndAddPanel.add(quitButton);
    }

    //EFFECTS: adds a points button and sets its action command
    public void addViewPointsButton(JButton viewPoints) {
        viewPoints.setActionCommand("View Points");
        viewPoints.addActionListener(this);
        completeAndAddPanel.add(viewPoints);
    }

    //MODIFIES: this
    //EFFECTS: creates a button to view all missions
    public void addViewAllMissionsButton(JButton viewAllMissionsButton) {
        viewAllMissionsButton.setActionCommand("View All Missions");
        viewAllMissionsButton.addActionListener(this);
        completeAndAddPanel.add(viewAllMissionsButton);
    }

    //MODIFIES: this
    //EFFECTS: creates a button to add a mission from the home page
    public void addMissionButton(JButton addMission) {
        addMission.setActionCommand("Add Mission");
        addMission.addActionListener(this);
        completeAndAddPanel.add(addMission);
    }

    //MODIFIES: this
    //EFFECTS: creates a button to complete a mission from the home page
    public void addCompleteMissionButton() {
        JButton completeMission = new JButton("Complete Mission");
        completeMission.setActionCommand("Complete Mission");
        completeMission.addActionListener(this);
        completeAndAddPanel.add(completeMission);
    }

    //MODIFIES: this
    //EFFECTS: creates a button to view my not completed missions
    public void addViewNotCompletedMissionsButton(JButton viewNotCompletedMissionsButton) {
        viewNotCompletedMissionsButton.setActionCommand("not completed missions");
        viewNotCompletedMissionsButton.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: creates a Daily Missions Label, that says that the missions you see when you press the view not
    // completed missions, these are the missions you still need to complete
    public void addDailyMissionsLabel(JLabel dailyMissionsLabel) {
        dailyMissionsLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        checkBoxPanel.add(dailyMissionsLabel);
        JLabel notCompletedMissionsLabel = new JLabel("Here are the missions you still need to complete: ");
        checkBoxPanel.add(notCompletedMissionsLabel);
    }

    //EFFECTS: sets the viewNotCompletedDialogue to a string of all the missions that are still not completed
    public void viewNotCompletedMissions() {
        viewNotCompletedDialogue = "";
        for (int i = 0; i < dailyMissions.getNotCompletedMissions().size(); i++) {
            int missionNumber = dailyMissions.getNotCompletedMissions().get(i).getMissionNumber();
            String missionName = dailyMissions.getNotCompletedMissions().get(i).getMissionName();
            viewNotCompletedDialogue = viewNotCompletedDialogue + "\n" + missionNumber + "    " + missionName;
        }
    }

    //EFFECTS: creates a message dialog that shows the viewNotCompletedDialogue
    private void viewNotCompletedMissionsDialogue() {
        JOptionPane.showMessageDialog(null, viewNotCompletedDialogue);
    }

    //MODIFIES: this
    //EFFECTS: asks the user to input the mission number they would like to complete and completes it
    public void completeMission() {
        viewNotCompletedMissions();
        int missionNumber = Integer.parseInt(JOptionPane.showInputDialog(this,
                "Enter the number of the mission you would like to complete: " + "\n"
                        + viewNotCompletedDialogue));
        boolean isSuccessfulCompletion = dailyMissions.completeMission(missionNumber);
        checkIfAllMissionsAreComplete(isSuccessfulCompletion);
    }

    //MODIFIES: this
    //EFFECTS: checks to see if all the missions had been completed
    public void checkIfAllMissionsAreComplete(boolean isCompleteSuccess) {
        if (dailyMissions.getNotCompletedMissions().size() == 0 && isCompleteSuccess) {
            JOptionPane.showMessageDialog(null, "Good work! You have completed all your "
                    + "missions for the day +1 point and an extra +10 points!!");
            dailyMissions.addPoints(10);
            JOptionPane.showMessageDialog(null, "You now have " + dailyMissions.getTotalPoints()
                    + " points.");
        } else if (isCompleteSuccess) {
            JOptionPane.showMessageDialog(null, "Nice!! +1 points!! You now have "
                    + dailyMissions.getTotalPoints() + " points");
        } else {
            JOptionPane.showMessageDialog(null, "You have already completed this mission!! "
                    + "You have" + dailyMissions.getTotalPoints() + " point.");
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a mission with the inputted name and changes the image to a motivational quote image
    public void addMission() {
        String missionName = JOptionPane.showInputDialog("Enter the name of the mission: ");
        dailyMissions.addMission(missionName);
        youGotThisIconLabel2AddMission.setPreferredSize(new Dimension(50, 50));
        homePage.add(youGotThisIconLabel2AddMission, BorderLayout.CENTER);
        youGotThisIconLabel.setVisible(false);
        youGotThisIconLabel2AddMission.setVisible(true);
        homePage.revalidate();
    }

    //EFFECTS: creates a message dialog with the number of points the user currently has
    public void viewPoints() {
        JOptionPane.showMessageDialog(null, "You have " + dailyMissions.getTotalPoints()
                + " points. Good work!!");
    }

    //MODIFIES: this
    //EFFECTS: creates the view all mission plane which simply displays all the missions the user has added no matter
    // if they are completed or not
    public void viewAllMissionsPanel() {
        JLabel allMissionLabel = new JLabel("Here are all your missions: ");
        allMissionLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
        JPanel allMissions = new JPanel();
        allMissions.setLayout(new GridLayout(0, 1));
        allMissions.add(allMissionLabel);
        allMissionsPanel.setLayout(new BorderLayout());
        for (int i = 0; i < dailyMissions.getMissionList().size(); i++) {
            int missionNumber = dailyMissions.getMissionList().get(i).getMissionNumber();
            String missionName = dailyMissions.getMissionList().get(i).getMissionName();
            JLabel missionLabel = new JLabel(missionNumber + "    " + missionName);
            missionLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
            allMissions.add(missionLabel);
        }
        JButton backToHomeScreen = new JButton("Back To Home Page");
        backToHomeScreen.setActionCommand("toHomePage");
        backToHomeScreen.addActionListener(this);
        allMissionsPanel.add(backToHomeScreen, BorderLayout.SOUTH);
        allMissionsPanel.add(allMissions, BorderLayout.NORTH);
    }

    //EFFECTS: asks if the user would like to save the application to file
    //          also prints the EventLog to console
    public void askToSaveToFile() {
        int userResponse = JOptionPane.showConfirmDialog(null,
                "Would you like to save your progress?", null, JOptionPane.YES_NO_OPTION);
        if (userResponse == JOptionPane.YES_OPTION) {
            saveToFile();
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Your progress was not saved. Goodbye!!");
            setVisible(false);
        }

        System.out.println("\n" + dailyMissions.getEventsLogged(EventLog.getInstance()));
    }

    //EFFECTS: saves the state of the application to file
    public void saveToFile() {
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

    //EFFECTS: creates a label with the icon of the motivational kid
    public void displayMotivationalIcon() {
        youGotThisIconLabel.setPreferredSize(new Dimension(50, 50));
        homePage.add(youGotThisIconLabel, BorderLayout.CENTER);
        youGotThisIconLabel2AddMission.setVisible(false);
        youGotThisIconLabel.setVisible(true);
        homePage.revalidate();
    }

}