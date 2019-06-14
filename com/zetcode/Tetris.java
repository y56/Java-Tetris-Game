package com.zetcode;

import java.awt.*;
import javax.swing.*;

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

    boardL.setBounds(0, 0, 1100, 600); // size of the board
    add(boardL);
    boardL.start();
  
    JPanel blankBar = new JPanel();
    blankBar.setBackground(Color.LIGHT_GRAY );
    blankBar.setBounds(222, 0, 165, 700);
    boardL.add(blankBar);
    boardL.setLayout(null);
    blankBar.setVisible(true);
  
    JPanel blankBar2 = new JPanel();
    blankBar2.setBackground(Color.LIGHT_GRAY );
    blankBar2.setBounds(387, 310, 800, 800);
    boardL.add(blankBar2);
    boardL.setLayout(null);
    blankBar2.setVisible(true);
  
    
    System.out.println("54");
    System.out.println(statusbar);
    add(statusbar, BorderLayout.SOUTH);
    System.out.println("58");
    System.out.println(statusbar);
    add(statusbar);
    blankBar.setLayout(null);
    statusbar.setBounds(100,0,0,0);
    statusbar.setVisible(true);
    
    setTitle("Tetris");
    setSize(728, 700); // the size of a window when the window first appear
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  public JLabel getStatusBar() {
    System.out.println("66");
    System.out.println(statusbar);
  
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
