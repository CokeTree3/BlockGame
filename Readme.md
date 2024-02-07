# A yet to be named block game

A simple block match game made for the bonus assignment in Object Oriented and Functional Programming.
The game takes heavy inspiration(*mostly a copy*) from a mobile game called *"Blockudoku"*

## Description and game instructions

The game is infinite, there are no winning conditions, but the goal is to last for as long as possible and achive the biggest score.

This is done by placing randomly generated block shapes on a 9x9 field, in such a way so that full rows or columns are formed.When such a line is created it gets removed from the board opening up more space for the next shapes. The board is also divided into 9 3x3 blocks and filling one such block also results in it getting removed

Scoring is done by counting the removed blocks as well as placed ones.

The game is lost when the next shape can no longer be placed on the grid.

## Extras

Included in the game is a custom field gameMode, in which the starting grid already has placed blocks including double and triple blocks which requre multiple clearings to remove from the field.

More extras include:

* A global best score is stored and preserved and updated if a higher result is achieved.
* The settings menu allows for the global high score to be reset as well as loading a different custom level.
* Multiple colour themes changable in the settings menu.

## Demo

* Link to game demo video: [Demo for the A Yet To Be Named Block Game](https://youtu.be/JpUuyWMYQ6Y)
  
