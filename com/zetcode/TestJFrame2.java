package com.zetcode;


import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;

public class TestJFrame2 {
  public static void main(String[] args) {
    // creating instance of JFrame
    JFrame f = new JFrame();
    
    JPanel p = new JPanel();
    p.setBounds(20, 20, 300, 80);
    p.setBackground(Color.BLACK);
    
    // creating instance of JButton
    JButton b = new JButton("Click me!");
    // x axis, y axis, width, height
    b.setBounds(30, 30, 50, 80); // matrix style indexing
    // add event listener
    b.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          System.out.println("Hello world!");
        }
      }
    );
    
    // add button to JFrame
    f.add(b);
    f.add(p);
    
    f.setSize(400, 500);
    f.setLayout(null);
    // make the frame visible
    f.setVisible(true);
  }
}