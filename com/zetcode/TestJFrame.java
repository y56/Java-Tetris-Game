package com.zetcode;

import javax.swing.*;
import java.awt.*;

public class TestJFrame extends JFrame {
  
  public TestJFrame() {
    this.setLayout(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLocation(25, 25); // the position where a window appear, coordinating from up-left
    this.setSize(500, 500); // size of window
    
    JPanel panel = new JPanel();
//    panel.setLayout(null);
    panel.setBounds(100, 100, 200, 200);
    panel.setBackground(Color.BLACK);
  
    this.add(panel);
    
    panel.add(new JButton("Hello!"));
    
  }
  public static void main(String[] args) {
    
    EventQueue.invokeLater(
      () -> {
        TestJFrame game = new TestJFrame();
        game.setVisible(true);
      }
    );
  }
}
