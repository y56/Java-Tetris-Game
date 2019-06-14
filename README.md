# Tetris-vs-Snake
## Reference
http://zetcode.com/tutorials/javagamestutorial/tetris/ 


https://github.com/janbodnar/Java-Tetris-Game 


http://zetcode.com/tutorials/javagamestutorial/snake/ 


https://github.com/janbodnar/Java-Snake-Game


https://gist.github.com/Crydust/fd1b94afc52cd0f7dd4c


## Features
### Tetris attacks Snake
When Snake eats an apple, Tetris will suffer a hard-drop.

### Snake attacks Tetris
When Tetris cleans a line, Snake will grow a node.

## Implementation
### Two-player controll
Use KeyListener for all keystrokes from both players in a single JPanel. Key Binding can support two Jpanels in a JFrame to receive keystrokes individually. Yet I need KeyListender for a Swing.Timer to count time for stepwise operation of the game.
