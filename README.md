## Introduction
This is a game project for a coding course using android studio(kotlin) about a famous children's figure Yuval.

## Game description:
Yuval is the main character and he tries to avoid the sugar packets while gathering money bags.
The money bags are totalled to a score. The top ten scores are saved and the player can view the geographical places where they were set on a map.<br />
hitting a sugar packet causes vibration a toast pop up and a sound.


App Description: The app consists of three activities:

### Menu Activity: Includes four buttons:<br />
Slow- to start the game with a set speed set to slow with buttons to change the lane.<br />
Fast- to start the game with a set speed set to fast with buttons to change the lane.<br />
Tilt- to start the game with a changing speed that is conttroled by tillting the phone vertically(down to increase speed up to reduce speed) and horizontally to change the lanes corresponding to the way that its tillted.<br />
Scores- to go to the scoreScreen and see the top ten scores since the download and where they were achived.<br />

### Main Activity: This is where the game is played and includes:<br />
Grid Objects: Obstacles (Sugar sprite), Coins (Bags of money sprite).<br />
Score: Displays the player's current score and distance counter.<br />
Movement Buttons: Visible only if button mode was chosen, in that case controls Yuval's movement .<br />
Hearts Array: Represents Yuval’s remaining lives.<br />

### Record Activity: this is were the top ten records are presented.<br />
Fragments: High Score Fragment (the scores), Map Fragment (The google maps and location of record achived).<br />
Pressing each record button zooms in the map fragment and show where the record was achived.<br />
Pressing each pin of the map shows which record was achived there.<br />
