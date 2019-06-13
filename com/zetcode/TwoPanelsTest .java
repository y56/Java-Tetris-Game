// https://stackoverflow.com/questions/10011564/how-to-make-two-jpanels-listen-to-the-same-event

package com.zetcode;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

class TwoPanelsTest extends JFrame {
  
  private MyPanel one = new MyPanel("One");
  private MyPanel two = new MyPanel("Two");
  private List<MyPanel> list = Arrays.asList(one, two);
  
  public TwoPanelsTest() {
    super("TwoPanelsTest");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panel.add(one);
    panel.add(two);
    panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
      .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
    panel.getActionMap().put("up", new AbstractAction() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        for (MyPanel panel : list) {
          panel.getAction().actionPerformed(e);
        }
      }
    });
    this.add(panel);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
  
  private static class MyPanel extends JPanel {
    
    private String string = " will be updated though its action.";
    private Action action = new UpdateAction(this);
    private String name;
    private JLabel label;
    
    public MyPanel(String name) {
      this.name = name;
      this.label = new JLabel(name + string, JLabel.CENTER);
      this.setLayout(new GridLayout());
      this.setFocusable(true);
      this.add(label);
    }
    
    public Action getAction() {
      return action;
    }
    
    private void update() {
      label.setText(name + ": " + System.nanoTime());
    }
    
    private static class UpdateAction extends AbstractAction {
      
      private MyPanel panel;
      
      public UpdateAction(MyPanel panel) {
        this.panel = panel;
      }
      
      @Override
      public void actionPerformed(ActionEvent ae) {
        panel.update();
      }
    }
  }
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      
      @Override
      public void run() {
        TwoPanelsTest t = new TwoPanelsTest();
      }
    });
  }
}
