ConnectFour 

Class: Space
Space is an extension of JPanel that has some extra instance variable to represent the color and owner 
of the connect four tiles. It uses an enum of SpaceType with three values (empty, player1, and player2) in
order to represent who owns the tile.

Class: CustomButton
A simple extension of the JButton class. Made specifically so that each button can store the column that it 
is associated with.

Class: GameCourt
The GameCourt class is the heart of the game. It contains instance variables 
to represent the board, save file, the current player, and the column chosen by the player.
The constructor fills in a 2d array of spaces with grey spaces which are owned by neither player (the board array).
It is an extension of JPanel. It implements a grid layout as this is best suited to represent a 
connect four board. The spaces saved in the grid layout are the same as those saved in the 2d array of spaces.
This allows the graphics to be updated without having to remove and add new panels to GameCourt.
It contains a method for placing pieces, checking if the last piece placed causes a win, resetting the board,
saving the state to a file after each move, and reloading the last saved state. CustomButtons on the grid layout are used to choose columns.
They have action listeners attached that call the place piece method. Since custom buttons have an associated column, the instance variable
for column is updated when the button is clicked.

Class:Game
The Game class simply sets up a frame for a GameCourt object object to be added to.

Class: GameWinTest
Carries out some unit tests to make sure the winning condition check works properly and the save function works properly.
