package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

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
    blankBar.setBackground(Color.LIGHT_GRAY);
    blankBar.setBounds(300, 0, 90, 330);
    boardL.add(blankBar);
    boardL.setLayout(null);
    blankBar.setVisible(true);
    
    JPanel blankBar2 = new JPanel();
    blankBar2.setBackground(Color.LIGHT_GRAY);
    blankBar2.setBounds(300, 310, 800, 800);
    boardL.add(blankBar2);
    boardL.setLayout(null);
    blankBar2.setVisible(true);
    
    statusbar.setFont(
      new Font("Helvetica", Font.BOLD, 30)
    );
    statusbar.setForeground(Color.RED);
    blankBar2.add(statusbar);
    statusbar.setBounds(30, 0, 700, 300);
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
  
  public static class Git {
    
    // example of usage
    protected static void initAndAddFile() throws IOException, InterruptedException {
      Path directory = Paths.get("c:\\temp\\example");
      Files.createDirectories(directory);
      gitInit(directory);
      Files.write(directory.resolve("example.txt"), new byte[0]);
      gitStage(directory);
      gitCommit(directory, "Add example.txt");
    }
    
    // example of usage
    private static void cloneAndAddFile() throws IOException, InterruptedException {
      String originUrl = "https://github.com/Crydust/TokenReplacer.git";
      Path directory = Paths.get("c:\\temp\\TokenReplacer");
      gitClone(directory, originUrl);
      Files.write(directory.resolve("example.txt"), new byte[0]);
      gitStage(directory);
      gitCommit(directory, "Add example.txt");
      gitPush(directory);
    }
    
    public static void gitInit(Path directory) throws IOException, InterruptedException {
      runCommand(directory, "git", "init");
    }
    
    public static void gitStage(Path directory) throws IOException, InterruptedException {
      runCommand(directory, "git", "add", "-A");
    }
    
    public static void gitCommit(Path directory, String message) throws IOException, InterruptedException {
      runCommand(directory, "git", "commit", "-m", message);
    }
    
    public static void gitPush(Path directory) throws IOException, InterruptedException {
      runCommand(directory, "git", "push");
    }
    
    public static void gitClone(Path directory, String originUrl) throws IOException, InterruptedException {
      runCommand(directory.getParent(), "git", "clone", originUrl, directory.getFileName().toString());
    }
    
    public static void runCommand(Path directory, String... command) throws IOException, InterruptedException {
      Objects.requireNonNull(directory, "directory");
      if (!Files.exists(directory)) {
        throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
      }
      ProcessBuilder pb = new ProcessBuilder()
                            .command(command)
                            .directory(directory.toFile());
      Process p = pb.start();
      StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
      StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
      outputGobbler.start();
      errorGobbler.start();
      int exit = p.waitFor();
      errorGobbler.join();
      outputGobbler.join();
      if (exit != 0) {
        throw new AssertionError(String.format("runCommand returned %d", exit));
      }
    }
    
    private static class StreamGobbler extends Thread {
      
      private final InputStream is;
      private final String type;
      
      private StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
      }
      
      @Override
      public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
          String line;
          while ((line = br.readLine()) != null) {
            System.out.println(type + "> " + line);
          }
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
    
  }
  
  public static void main(String[] args) throws IOException, InterruptedException {
    
    EventQueue.invokeLater(
      () -> {
        Tetris game = new Tetris();
        
        game.setVisible(true);
      }
    );

    BufferedWriter writer = null;
    try {
      //create a temporary file
      String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
      File logFile = new File("/home/y56/github/test-java-git/" + "y56-beat-you-" + timeLog + ".XD");
      
      // This will output the full path where the file will be written to...
      System.out.println(logFile.getCanonicalPath());
      
      writer = new BufferedWriter(new FileWriter(logFile));
      writer.write("Hello world!");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // Close the writer regardless of what happens...
        writer.close();
      } catch (Exception e) {
      }
    }
    
    
    Path directory = Paths.get("/home/y56/github/test-java-git");
    System.out.println(directory);
    Git.gitStage(directory);
    Git.gitCommit(directory, "お前はもう死んでいる");
    Git.gitPush(directory);
  }
}
