package com.zetcode;

import javax.swing.*;
import java.awt.*;

/*
Java Tetris game clone

Author: Jan Bodnar
Website: http://zetcode.com
 */
public class Tetris extends JFrame {
  
  public JLabel statusbar = new JLabel(" 0");
  
  public Tetris() { // constructor of Tetris (which is a JFrame)
    this.initUI();
    this.setLayout(null); /* Use it, otherwise the position of Board (extending JPanel)
      Using this will cause statusbar to disappear */
    this.setLocation(0, 0); // (0,0) is a left-up corner
  }
  
  public void initUI() {
//    BoardR boardR = new BoardR(this);
//
//    boardR.setBounds(530, 20, 500, 500);
//    add(boardR);
//    boardR.start();
  
    BoardL boardL = new BoardL(this);

    boardL.setBounds(0, 0, 1500, 600); // size of the board
    add(boardL);
    boardL.start();
  
    JPanel blankBar = new JPanel();
    blankBar.setBackground(Color.LIGHT_GRAY );
    blankBar.setBounds(300, 0, 90, 330);
    boardL.add(blankBar);
    boardL.setLayout(null);
    blankBar.setVisible(true);
  
    JPanel blankBar2 = new JPanel();
    blankBar2.setBackground(Color.LIGHT_GRAY );
    blankBar2.setBounds(300, 310, 800, 800);
    boardL.add(blankBar2);
    boardL.setLayout(null);
    blankBar2.setVisible(true);
  
    statusbar.setFont(
      new Font("Helvetica", Font.BOLD, 30)
    );
    statusbar.setForeground(Color.RED);
    blankBar2.add(statusbar);
    statusbar.setBounds(30,0, 700, 300);
    blankBar2.setLayout(null);
    statusbar.setVisible(true);
    
    setTitle("Tetris");
    setSize(728, 640); // the size of a window when the window first appear
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
