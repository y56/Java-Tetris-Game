package com.zetcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zetcode.ShapeL.TetrominoeL;

public class BoardL extends JPanel
  implements ActionListener {
  
  private final int BOARD_WIDTH_L = 50; // the number of boxes within a side
  private final int BOARD_HEIGHT = 20;
  private final int BOARD_WIDTH_L_TO_REMOVE_LINE = 10;
  private final int TETRIS_DELAY = 300;
  
  private Timer tetris_timer;
  private boolean isFallingFinished = false;
  private boolean isStarted = false;
  private boolean isPaused = false;
  private int numLinesRemoved = 0;
  private int curX = 0;
  private int curY = 0;
  private JLabel statusbar;
  private ShapeL curPiece;
  private TetrominoeL[] board;
  
  BoardL(Tetris parent) {
    
    initBoard(parent);
  }
  
  private void initBoard(Tetris parent) {
    
    setFocusable(true);
    curPiece = new ShapeL();
    tetris_timer = new Timer(TETRIS_DELAY, this);
    tetris_timer.start();
    
    statusbar = parent.getStatusBar();
    System.out.println("HAHAHA");
    System.out.println(statusbar);
    board = new TetrominoeL[BOARD_WIDTH_L * BOARD_HEIGHT];
    addKeyListener(new TAdapter());
    clearBoard(); // ???
  
    setBackground(Color.BLACK);
    setFocusable(true);
  
    setPreferredSize(new Dimension(SNAKE_B_WIDTH, SNAKE_B_HEIGHT));
    loadImages();
    initGame();
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (SNAKE_inGame) {
    
      checkApple();
      checkCollision();
      move();
    }
  
    repaint();
    
    if (isFallingFinished) {
      
      isFallingFinished = false;
      newPiece();
    } else {
      
      oneLineDown();
    }
  }
  
  private int squareWidth() {
    return (int) getSize().getWidth() / BOARD_WIDTH_L;
  } // I add /2
  
  private int squareHeight() {
    return (int) getSize().getHeight() / BOARD_HEIGHT;
  }
  
  private TetrominoeL shapeAt(int x, int y) {
    return board[(y * BOARD_WIDTH_L) + x];
  }
  
  
  void start() {
    
    if (isPaused)
      return;
    
    isStarted = true;
    isFallingFinished = false;
    numLinesRemoved = 0;
    clearBoard();
    
    newPiece();
    tetris_timer.start();
  }
  
  private void pause() {
    
    if (!isStarted)
      return;
    
    isPaused = !isPaused;
    
    if (isPaused) {
      
//      tetris_timer.stop();
      statusbar.setText("paused");
    } else {
      
      tetris_timer.start();
      statusbar.setText(String.valueOf(numLinesRemoved));
    }
    
    repaint();
  }
  
  private void doDrawing(Graphics g) {
    
    Dimension size = getSize();
    int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
    
    for (int i = 0; i < BOARD_HEIGHT; ++i) {
      
      for (int j = 0; j < BOARD_WIDTH_L; ++j) {
        
        TetrominoeL shape = shapeAt(j, BOARD_HEIGHT - i - 1);
        
        if (shape != TetrominoeL.NoShape)
          drawSquare(g, 0 + j * squareWidth(),
            boardTop + i * squareHeight(), shape);
      }
    }
    
    if (curPiece.getShape() != TetrominoeL.NoShape) {
      
      for (int i = 0; i < 4; ++i) {
        
        int x = curX + curPiece.x(i);
        int y = curY - curPiece.y(i);
        drawSquare(g, 0 + x * squareWidth(),
          boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
          curPiece.getShape());
      }
    }
  }
  
  @Override
  public void paintComponent(Graphics g) {
    
    super.paintComponent(g);
    doDrawing(g);
  }
  
  private void dropDown() {
    
    int newY = curY;
    
    while (newY > 0) {
      
      if (!tryMoveL(curPiece, curX, newY - 1))
        break;
      --newY;
    }
    
    pieceDropped();
  }
  
  private void oneLineDown() {
    
    if (!tryMoveL(curPiece, curX, curY - 1))
      pieceDropped();
  }
  
  
  private void clearBoard() {
    
    for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH_L; ++i)
      board[i] = TetrominoeL.NoShape;
  }
  
  private void pieceDropped() {
    
    for (int i = 0; i < 4; ++i) {
      
      int x = curX + curPiece.x(i);
      int y = curY - curPiece.y(i);
      board[(y * BOARD_WIDTH_L) + x] = curPiece.getShape();
    }
    
    removeFullLines();
    
    if (!isFallingFinished)
      newPiece();
  }
  
  private void newPiece() {
    
    curPiece.setRandomShape();
    curX = 6;
    curY = BOARD_HEIGHT - 1 + curPiece.minY();
    
    if (!tryMoveL(curPiece, curX, curY)) {
      
      curPiece.setShape(TetrominoeL.NoShape);
//      tetris_timer.stop();
      isStarted = false;
      statusbar.setText("game over");
      System.out.println(statusbar);
    }
  }
  
  private boolean tryMoveL(ShapeL newPiece, int newX, int newY) {
    
    for (int i = 0; i < 4; ++i) {
      
      int x = newX + newPiece.x(i);
      int y = newY - newPiece.y(i);
      
      if (x < 0 || x >= 10 || y < 0 || y >= BOARD_HEIGHT)
        return false;
      
      if (shapeAt(x, y) != TetrominoeL.NoShape)
        return false;
    }
    
    curPiece = newPiece;
    curX = newX;
    curY = newY;
    
    repaint();
    
    return true;
  }
  
  private void removeFullLines() {
    
    int numFullLines = 0;
    
    for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
      boolean lineIsFull = true;
      
      for (int j = 0; j < BOARD_WIDTH_L_TO_REMOVE_LINE; ++j) {
        if (shapeAt(j, i) == TetrominoeL.NoShape) {
          lineIsFull = false;
          break;
        }
      }
      
      if (lineIsFull) {
        ++numFullLines;
        for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
          for (int j = 0; j < BOARD_WIDTH_L; ++j)
            board[(k * BOARD_WIDTH_L) + j] = shapeAt(j, k + 1);
        }
      }
    }
    
    if (numFullLines > 0) {
      
      numLinesRemoved += numFullLines;
      SNAKE_dots += numFullLines;
      statusbar.setText(String.valueOf(numLinesRemoved));
      isFallingFinished = true;
      curPiece.setShape(TetrominoeL.NoShape);
      repaint();
    }
  }
  
  private void drawSquare(Graphics g, int x, int y, TetrominoeL shape) {
    
    Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
      new Color(102, 204, 102), new Color(102, 102, 204),
      new Color(204, 204, 102), new Color(204, 102, 204),
      new Color(102, 204, 204), new Color(218, 170, 0)
    };
    
    Color color = colors[shape.ordinal()];
    
    g.setColor(color);
    g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
    
    g.setColor(color.brighter());
    g.drawLine(x, y + squareHeight() - 1, x, y);
    g.drawLine(x, y, x + squareWidth() - 1, y);
    
    g.setColor(color.darker());
    g.drawLine(x + 1, y + squareHeight() - 1,
      x + squareWidth() - 1, y + squareHeight() - 1);
    g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
      x + squareWidth() - 1, y + 1);
    //==================
    if (SNAKE_inGame) {
    
      g.drawImage(SNAKE_apple, SNAKE_apple_x, SNAKE_apple_y, this);
    
      for (int z = 0; z < SNAKE_dots; z++) {
        if (z == 0) {
          g.drawImage(SNAKE_head, SNAKE_x[z], SNAKE_y[z], this);
        } else {
          g.drawImage(SNAKE_ball, SNAKE_x[z], SNAKE_y[z], this);
        }
      }
    
      Toolkit.getDefaultToolkit().sync();
    
    } else {
    
      gameOver(g);
    }
  }
  
  
  class TAdapter extends KeyAdapter {
    
    @Override
    public void keyPressed(KeyEvent e) {
      
      if (!isStarted || curPiece.getShape() == TetrominoeL.NoShape) {
        return;
      }
      
      int keycode = e.getKeyCode();
      
      if (keycode == 'P') {
        pause();
        return;
      }
      
      if (isPaused)
        return;
      
      switch (keycode) {
        
        case 'A':
          tryMoveL(curPiece, curX - 1, curY);
          break;
        
        case 'D':
          tryMoveL(curPiece, curX + 1, curY);
          break;
        
        case 'S':
          tryMoveL(curPiece.rotateRight(), curX, curY);
          break;
        
        case 'W':
          tryMoveL(curPiece.rotateLeft(), curX, curY);
          break;
        
        case 'Q':
          dropDown();
          break;
          
      }
      if ((keycode == KeyEvent.VK_LEFT) && (!SNAKE_rightDirection)) {
        SNAKE_leftDirection = true;
        SNAKE_upDirection = false;
        SNAKE_downDirection = false;
      }
  
      if ((keycode == KeyEvent.VK_RIGHT) && (!SNAKE_leftDirection)) {
        SNAKE_rightDirection = true;
        SNAKE_upDirection = false;
        SNAKE_downDirection = false;
      }
  
      if ((keycode == KeyEvent.VK_UP) && (!SNAKE_downDirection)) {
        SNAKE_upDirection = true;
        SNAKE_rightDirection = false;
        SNAKE_leftDirection = false;
      }
  
      if ((keycode == KeyEvent.VK_DOWN) && (!SNAKE_upDirection)) {
        SNAKE_downDirection = true;
        SNAKE_rightDirection = false;
        SNAKE_leftDirection = false;
      }
    }
  }
  //====================================================================================================================
  private final int RIGHT_SHiFT = 400;
  private final int SNAKE_B_WIDTH = 300+RIGHT_SHiFT;
  private final int SNAKE_B_HEIGHT = 300;
  private final int SNAKE_B_WIDTH_LEFT = 0+RIGHT_SHiFT;
  private final int SNAKE_B_HEIGHT_LOW = 0;
  private final int SNAKE_DOT_SIZE = 10;
  private final int SNAKE_ALL_DOTS = 900;
  private final int SNAKE_RAND_POS = 29;
  private final int SNAKE_DELAY = 300;
  
  private final int SNAKE_x[] = new int[SNAKE_ALL_DOTS];
  private final int SNAKE_y[] = new int[SNAKE_ALL_DOTS];
  
  private int SNAKE_dots;
  private int SNAKE_apple_x;
  private int SNAKE_apple_y;
  
  private boolean SNAKE_leftDirection = false;
  private boolean SNAKE_rightDirection = true;
  private boolean SNAKE_upDirection = false;
  private boolean SNAKE_downDirection = false;
  private boolean SNAKE_inGame = true;
  
  private Timer SNAKE_timer;
  private Image SNAKE_ball;
  private Image SNAKE_apple;
  private Image SNAKE_head;
  
  
  private void loadImages() {
    
    ImageIcon iid = new ImageIcon("src/resources/dot.png");
    SNAKE_ball = iid.getImage();
    
    ImageIcon iia = new ImageIcon("src/resources/apple.png");
    SNAKE_apple = iia.getImage();
    
    ImageIcon iih = new ImageIcon("src/resources/head.png");
    SNAKE_head = iih.getImage();
  }
  
  private void initGame() {
  
    SNAKE_dots = 3;
    
    for (int z = 0; z < SNAKE_dots; z++) {
      SNAKE_x[z] = 50 - z * 10 +RIGHT_SHiFT;
      SNAKE_y[z] = 50;
    }
    
    locateApple();
  
    SNAKE_timer = new Timer(SNAKE_DELAY, this);
    SNAKE_timer.start();
  }
  

  

  
  private void gameOver(Graphics g) {
    
    String msg = "Game Over";
    Font small = new Font("Helvetica", Font.BOLD, 30);
    FontMetrics metr = getFontMetrics(small);
    
    g.setColor(Color.white);
    g.setFont(small);
    g.drawString(msg, (SNAKE_B_WIDTH - metr.stringWidth(msg)) / 2 + 200, SNAKE_B_HEIGHT / 2);
  }
  
  private void checkApple() {
    
    if ((SNAKE_x[0] == SNAKE_apple_x) && (SNAKE_y[0] == SNAKE_apple_y)) {
  
      SNAKE_dots++;
      locateApple();
      
      dropDown();
    }
  }
  
  private void move() {
    
    for (int z = SNAKE_dots; z > 0; z--) {
      SNAKE_x[z] = SNAKE_x[(z - 1)];
      SNAKE_y[z] = SNAKE_y[(z - 1)];
    }
    
    if (SNAKE_leftDirection) {
      SNAKE_x[0] -= SNAKE_DOT_SIZE;
    }
    
    if (SNAKE_rightDirection) {
      SNAKE_x[0] += SNAKE_DOT_SIZE;
    }
    
    if (SNAKE_upDirection) {
      SNAKE_y[0] -= SNAKE_DOT_SIZE;
    }
    
    if (SNAKE_downDirection) {
      SNAKE_y[0] += SNAKE_DOT_SIZE;
    }
  }
  
  private void checkCollision() {
    
    for (int z = SNAKE_dots; z > 0; z--) {
      
      if ((z > 4) && (SNAKE_x[0] == SNAKE_x[z]) && (SNAKE_y[0] == SNAKE_y[z])) {
        SNAKE_inGame = false;
      }
    }
    
    if (SNAKE_y[0] >= SNAKE_B_HEIGHT) {
      SNAKE_inGame = false;
    }
    
    if (SNAKE_y[0] < SNAKE_B_HEIGHT_LOW) {
      SNAKE_inGame = false;
    }
    
    if (SNAKE_x[0] >= SNAKE_B_WIDTH) {
      SNAKE_inGame = false;
    }
    
    if (SNAKE_x[0] < SNAKE_B_WIDTH_LEFT) {
      SNAKE_inGame = false;
    }
    
    if (!SNAKE_inGame) {
//      SNAKE_timer.stop();
    }
  }
  
  private void locateApple() {
    
    int r = (int) (Math.random() * SNAKE_RAND_POS);
    SNAKE_apple_x = ((r * SNAKE_DOT_SIZE)) + RIGHT_SHiFT;
    
    r = (int) (Math.random() * SNAKE_RAND_POS);
    SNAKE_apple_y = ((r * SNAKE_DOT_SIZE));
  }
  
  
}

