package ch02.example7;

import java.awt.Dimension;
import java.awt.Graphics;

import ch02.CharacterDisplayCanvasImpl;
import ch02.CharacterEvent;
import ch02.ICharacterListener;
import ch02.ICharacterSource;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvasImpl implements ICharacterListener, Runnable {
  
  private volatile boolean done = false;
  private int curX = 0;
  
  public AnimatedCharacterDisplayCanvas() {
  }
  
  public AnimatedCharacterDisplayCanvas(ICharacterSource cs) {
    super(cs);
  }
  
  public synchronized void newCharacter(CharacterEvent ce) {
    curX = 0;
    tmpChar[0] = (char) ce.character;
    repaint();
  }
  
  protected synchronized void paintComponent(Graphics gc) {
    Dimension d = getSize();
    gc.clearRect(0, 0, d.width, d.height);
    if (tmpChar[0] == 0)
      return;
    int charWidth = fm.charWidth(tmpChar[0]);
    gc.drawChars(tmpChar, 0, 1, curX++, fontHeight);
  }
  
  public void run() {
    while (!done) {
      repaint();
      try {
        Thread.sleep(100);
      } catch (InterruptedException ie) {
        return;
      }
    }
  }
  
  public void setDone(boolean b) {
    done = b;
  }
}
