package ch04.example1;

import java.awt.Dimension;
import java.awt.Graphics;

import ch04.CharacterDisplayCanvasImpl;
import ch04.CharacterEvent;
import ch04.ICharacterListener;
import ch04.ICharacterSource;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvasImpl implements ICharacterListener, Runnable {
  
  private boolean done = true;
  private int curX = 0;
  private Thread timer = null;
  
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
  
  public synchronized void run() {
    while (true) {
      try {
        if (done) {
          wait();
        } else {
          repaint();
          wait(100);
        }
      } catch (InterruptedException ie) {
        return;
      }
    }
  }
  
  public synchronized void setDone(boolean b) {
    done = b;
    
    if (timer == null) {
      timer = new Thread(this);
      timer.start();
    }
    if (!done)
      notify();
  }
}
