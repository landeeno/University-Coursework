Welcome to Space Wars! This is the help button output.

Enter your name and the server address to connect your player to the game. You must have a name to play. These buttons disappear when you are connected.

Use the left and right arrow keys to change your angle, and use the up arrow key to move forward. To shoot, hit the space bar. You 
can also use "L", "R", "T", and "F" for the controls.

You get one point when you hit another player, and all the scores are displayed on the screen.

The structure of Space Wars follows the MVC model in that the logic is not at all in the WorldView form, the logic is in the GameController and the networking is only in the NetworkingController.

Basic Extra Features are a pop-out help menu and not allowing the player to connect without a player name. 




Welcome to SpaceWarsServer!

The server controls all the logic for the SpaceWarsGame. 

All of the settings used to control the server can be adjusted in the XML file. 
These settings include the world size, projectile fire rate, respawn delay, etc. 
(All the settings in the project description). All of the settings must be 
integers except for the star properties which can be doubles. 
Only one star can be made in the settings file. 

To enable the extra game mode, simply set the extra game mode text to “true”. 

Speaking of extra game modes…

The extra game mode that was implemented was to have multiple stars in the system. 
When this game mode is enabled, 1-3 additional stars will be added to the universe. 
These stars will have the same mass as the main star. For gravity purposes, 
just the main star will be accounted for. 

These extra stars will be randomly moving. Be careful not to get hit! Enjoy! :)

