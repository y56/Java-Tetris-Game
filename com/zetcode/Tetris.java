package com.zetcode;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
Java Tetris game clone

Author: Jan Bodnar
Website: http://zetcode.com
 */
public class Tetris extends JFrame {
  private JLabel statusbar;
  
  public Tetris() { // constructor of Tetris (which is a JFrame)
    
    initUI();
    this.setLocation(0, 0); // (0,0) is a left-up corner
    
  }
  
  private void initUI() {
    
    
    statusbar = new JLabel(" 0");
    add(statusbar, BorderLayout.SOUTH);
    
    Board board = new Board(this);
    Board board2 = new Board(this);

//        add(board);
//        board.setLocation(0,0);
//        board.setSize(100,100);
//        board.start();
    
    add(board2);
    board2.setLocation(0, 0);
    board2.setPreferredSize(new Dimension(40, 40));
    board2.start();
    
    
    setTitle("Tetris");
    setSize(300, 600); // the size of a window when the window first appear
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  public JLabel getStatusBar() {
    
    return statusbar;
  }
  
  public static void main(String[] args) {
    
    EventQueue.invokeLater(
      () -> {
        Tetris game = new Tetris();
        game.setVisible(true);
      }
    );
  }
}
