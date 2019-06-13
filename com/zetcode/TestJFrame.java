package com.zetcode;

import javax.swing.*;
import java.awt.*;

public class TestJFrame extends JFrame {
  
  public TestJFrame() {
    
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLocation(250, 250);
    this.setSize(300, 300);
    this.setVisible(true);
    
    JPanel panel = new JPanel();
    this.add(panel);
    panel.setLocation(150, 150);
    panel.add(new JButton("Hello!")); // just to show JPanel
    
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
