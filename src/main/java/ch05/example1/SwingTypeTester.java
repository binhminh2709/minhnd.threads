package ch05.example1;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch05.CharacterDisplayCanvasImpl;
import ch05.CharacterEventHandler;
import ch05.ICharacterListener;
import ch05.ICharacterSource;

public class SwingTypeTester extends JFrame implements ICharacterSource {
  
  protected RandomCharacterGenerator producer;
  private AnimatedCharacterDisplayCanvas displayCanvas;
  private CharacterDisplayCanvasImpl feedbackCanvas;
  private JButton quitButton;
  private JButton startButton;
  private JButton stopButton;
  private CharacterEventHandler handler;
  private ScoreLabel score;
  
  public SwingTypeTester() {
    initComponents();
  }
  
  private void initComponents() {
    handler = new CharacterEventHandler();
    producer = new RandomCharacterGenerator();
    producer.setDone(true);
    producer.start();
    displayCanvas = new AnimatedCharacterDisplayCanvas(producer);
    feedbackCanvas = new CharacterDisplayCanvasImpl(this);
    quitButton = new JButton();
    startButton = new JButton();
    stopButton = new JButton();
    score = new ScoreLabel(producer, this);
    
    Container pane = getContentPane();
    pane.add(displayCanvas, BorderLayout.NORTH);
    pane.add(feedbackCanvas, BorderLayout.CENTER);
    
    JPanel p1 = new JPanel();
    p1.setLayout(new BorderLayout());
    score.setText("     ");
    score.setFont(new Font("MONOSPACED", Font.BOLD, 30));
    p1.add(score, BorderLayout.CENTER);
    
    JPanel p2 = new JPanel();
    startButton.setLabel("Start");
    stopButton.setLabel("Stop");
    stopButton.setEnabled(false);
    quitButton.setLabel("Quit");
    p2.add(startButton);
    p2.add(stopButton);
    p2.add(quitButton);
    p1.add(p2, BorderLayout.EAST);
    pane.add(p1, BorderLayout.SOUTH);
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        quit();
      }
    });
    
    feedbackCanvas.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ke) {
        char c = ke.getKeyChar();
        if (c != KeyEvent.CHAR_UNDEFINED)
          newCharacter((int) c);
      }
    });
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        displayCanvas.setDone(false);
        producer.setDone(false);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        feedbackCanvas.setEnabled(true);
        feedbackCanvas.requestFocus();
        score.resetScore();
      }
    });
    stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        producer.setDone(true);
        displayCanvas.setDone(true);
        feedbackCanvas.setEnabled(false);
      }
    });
    quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        quit();
      }
    });
    
    pack();
  }
  
  private void quit() {
    System.exit(0);
  }
  
  public void addCharacterListener(ICharacterListener cl) {
    handler.addCharacterListener(cl);
  }
  
  public void removeCharacterListener(ICharacterListener cl) {
    handler.removeCharacterListener(cl);
  }
  
  public void newCharacter(int c) {
    handler.fireNewCharacter(this, c);
  }
  
  public void nextCharacter() {
    throw new IllegalStateException("We don't produce on demand");
  }
  
  public static void main(String args[]) {
    new SwingTypeTester().show();
  }
}
