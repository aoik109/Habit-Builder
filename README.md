# Gamified Habit Builder

## Overview

This application is centered around **daily missions, points, rewards, and punishments**.

- **Daily Missions**: the habits or _missions_ the user would like to complete every day, which are entered
  and personalized by the users.
- **Points**: for each mission the user completes, they will receive 1 point. if they complete all missions
  for the day, the user will receive 10 extra points. Additionally, if they do not complete all of their missions
  for the day, they will lose 5 points.These points can be used to purchase rewards whenever they want from the
  reward shop.
- **Rewards**: if the user completes all their missions for the day, they are presented with 3 random rewards,
  which they had personalized, and they get to pick one of them. Additionally, as the user saves up points,
  they will be able to purchase rewards from the reward shop.
- **Punishments**: if the user fails to complete all their missions for the day,
  they are presented with 3 random punishments that the user had personalized. Then, they must choose one of
  those random punishments.

Every day, the user will be presented with all of their daily missions. They will cross off their missions as
they complete them, and they will earn points to reinforce the habit. Additionally, their personalized
rewards and punishments will give them extra motivation to complete their missions. For example, they can add
a reward to the reward shop like "buy a new pair of shoes" for 100 points. To be able to purchase this reward,
the user will have to complete all their daily missions and save up their points for approximately 10 days
in a row.

## Purpose

I have been trying to find the best way to build good habits into my daily life, but I always find it
hard to follow through with it. I have tried bullet journal habit trackers, digital habit trackers, and
habit tracking apps, but they never gave me reliable motivation to do my habits. I realized that this is
because there were no short term rewards and goals in these methods of habit tracking; instead, all I had
was a little number, counting the days I had kept up with the habit.

Recently, I came across the concept of _"gamifying your life."_ Video games motivate us to keep playing
through missions, rewards, and goals, so people have begun implementing this concept into their daily lives.
When I kept up with my habits every day, I was the most productive and healthiest I had ever been,
but lately it has been difficult for me to do so. As a result, I am creating this application to make habit
building fun. I believe anyone would benefit from using this application as they can learn to build and
sustain good habits through a game-like system.

## User Stories

- As a user, I want to be able to add multiple missions to the list of my daily missions (daily mission list)
- As a user, I want to be able to view all of my daily missions
- As a user, I want to be able to cross off the missions that I complete
- As a user, I want to be able to see the total number of points I currently have
- As a user, I want to be able to select the quit option from the home screen and then be asked if I want to save my 
daily missions to file
- As a user, when I start the application, I want to be asked if I want to load my daily missions from file

## Instructions for Grader
- **You can generate the first required event related to adding Xs to a Y by...**
  - Press 'Start'
  - Enter your name and then click 'OK'
  - Press 'Yes' when asked if it is your first time opening the app then click 'OK'
  - Enter the name of your missions and press 'OK' for each mission (enter as many as you want)
      - this is one of the events of adding Missions to a Mission List

- **You can generate the second required event related to adding Xs to a Y by...**
  - Once you're done entering your missions, click 'Continue to Home Page'
  - Press the 'View not completed missions' button
      - this is one of the required events related to adding Xs to a Y
      - here, we only see the missions that you have not completed yet (filtered list)
  - Press the 'Complete Mission' button
      - this is another one of the required events
      - type in the number of the mission you would like to complete
      - then, if you press the 'View not completed missions' button, you will not see the mission you had just
        completed

- **You can locate my visual component by...**
  - after entering the number of the mission you would like to complete and clicking 'OK', an image will appear in the 
  frame
  - additionally, if you add a mission (similar process but you enter the name of the mission you want to add), a 
  different image will appear in the frame

- **You can save the state of my application by...**
  - Press the 'Quit' button and click 'Yes' when asked to save to file

- **You can reload the state of my application by...**
  - Restart the app after saving to file
  - This time, when asked if this is your first time after opening the app, click 'No'
  - Then, you will be asked if you would like to load from file, here, click 'Yes'
    - now the state of your application will be restored

## Phase 4: Task 2

Wed Nov 30 14:50:23 PST 2022
Added 'exercise'

Wed Nov 30 14:50:26 PST 2022
Added 'go to sleep early'

Wed Nov 30 14:50:31 PST 2022
Added 'study for 5 hours'

Wed Nov 30 14:50:41 PST 2022
Added 'no phone for 3 hours'

Wed Nov 30 14:50:48 PST 2022
Completed 'go to sleep early'

Wed Nov 30 14:50:54 PST 2022
Completed 'exercise'

Wed Nov 30 14:50:58 PST 2022
Completed 'study for 5 hours'

Wed Nov 30 14:51:16 PST 2022
Added 'meditate for 15 minutes'

## Phase 4: Task 3
Looking at my UML class diagram, there is some coupling.
 If I made a change in Mission or DailyMissionList,
 the changes would be reflected in DailyMissionAppGui. 
 For example, if I changed the implementation on adding Missions
to a DailyMissionList, it would be reflected most likely during runtime in the 
DailyMissionAppGui since DailyMissionAppGui depends on DailyMissionList. 

If I had more time to work on the project, the refactoring I would do is:

increase cohesion: 
- In DailyMissionAppGui, there are several methods just for creating buttons. I could create a new class
with the responsibility of creating buttons.
- Additionally, I could make a class just for creating the labels.
- Also, I could create a new methods in DailyMissionList that are responsible for viewing all the missions in a 
a DailyMissionApp in a certain format. In DailyMissionAppGui, there
are two methods, viewNotCompletedMissions() and viewAllMissionsPanel(), which loop through the missions in the 
DailyMissionList, which should not be DailyMissionListGui's responsibility
- Then, the DailyMissionAppGui would just have the single responsibility of putting together the panels, 
which would thus increase cohesion.

reduce duplication:

- In DailyMissionAppGui, there are two methods mentioned before, viewNotCompletedMissions() and viewAllMissionsPanel().
They both loop through a list of missions and format the list into a string, so there is some 
repetitive code. I can create a new method that takes in a list of missions and formats it into a String. This would 
reduce duplication.
- Additionally, in DailyMissionAppGui, there is a lot of duplicated code when creating 
and adding buttons. This could be fixed by, again, creating a class for creating buttons.

other fixes:

- increasing readability: it is really hard to understand what is happening in my code, especially in
DailyMissionListGui. Thus, I would organize the methods with more thought and utilize line spacing for better 
readability.

