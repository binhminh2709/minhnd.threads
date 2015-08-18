package ch02.example2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch02.CharacterDisplayCanvasImpl;
import ch02.CharacterEventHandler;
import ch02.ICharacterListener;
import ch02.ICharacterSource;

public class SwingTypeTester extends JFrame implements ICharacterSource {
  
  protected RandomCharacterGenerator producer;
  private CharacterDisplayCanvasImpl displayCanvas;
  private CharacterDisplayCanvasImpl feedbackCanvas;
  private JButton quitButton;
  private JButton startButton;
  private CharacterEventHandler handler;
  
  public SwingTypeTester() {
    initComponents();
  }
  
  private void initComponents() {
    handler = new CharacterEventHandler();
    displayCanvas = new CharacterDisplayCanvasImpl();
    feedbackCanvas = new CharacterDisplayCanvasImpl(this);
    quitButton = new JButton();
    startButton = new JButton();
    add(displayCanvas, BorderLayout.NORTH);
    add(feedbackCanvas, BorderLayout.CENTER);
    JPanel p = new JPanel();
    startButton.setLabel("Start");
    quitButton.setLabel("Quit");
    p.add(startButton);
    p.add(quitButton);
    add(p, BorderLayout.SOUTH);
    
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
        producer = new RandomCharacterGenerator();
        displayCanvas.setCharacterSource(producer);
        producer.start();
        startButton.setEnabled(false);
        feedbackCanvas.setEnabled(true);
        feedbackCanvas.requestFocus();
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
