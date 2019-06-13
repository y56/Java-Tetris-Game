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
    
    this.initUI();
    this.setLayout(null); // Use it, otherwise the position of Board (extending JPanel)
    this.setLocation(100, 100); // (0,0) is a left-up corner
    
  }
  
  private void initUI() {
    
    
    statusbar = new JLabel(" 0");
    add(statusbar, BorderLayout.SOUTH);
    
    Board boardL = new Board(this);
    Board boardR = new Board(this);
  
    boardL.setBounds(20, 20, 500, 500);
    add(boardL);
    boardL.start();
    
    boardR.setBounds(530, 20, 500, 500);
    add(boardR);
    boardR.start();
    
    
    
    setTitle("Tetris");
    setSize(1200, 700); // the size of a window when the window first appear
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
