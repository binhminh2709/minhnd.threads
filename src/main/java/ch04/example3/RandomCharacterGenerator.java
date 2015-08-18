package ch04.example3;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

import ch04.*;

public class RandomCharacterGenerator extends Thread implements ICharacterSource {
  private static char[] chars;
  private static String charArray = "abcdefghijklmnopqrstuvwxyz0123456789";
  static {
    chars = charArray.toCharArray();
  }
  
  private Random random;
  private CharacterEventHandler handler;
  private boolean done = true;
  private Lock lock = new ReentrantLock();
  private Condition cv = lock.newCondition();
  
  public RandomCharacterGenerator() {
    random = new Random();
    handler = new CharacterEventHandler();
  }
  
  public int getPauseTime() {
    return (int) (Math.max(1000, 5000 * random.nextDouble()));
  }
  
  public void addCharacterListener(ICharacterListener cl) {
    handler.addCharacterListener(cl);
  }
  
  public void removeCharacterListener(ICharacterListener cl) {
    handler.removeCharacterListener(cl);
  }
  
  public void nextCharacter() {
    handler.fireNewCharacter(this, (int) chars[random.nextInt(chars.length)]);
  }
  
  public void run() {
    try {
      lock.lock();
      while (true) {
        try {
          if (done) {
            cv.await();
          } else {
            nextCharacter();
            cv.await(getPauseTime(), TimeUnit.MILLISECONDS);
          }
        } catch (InterruptedException ie) {
          return;
        }
      }
    } finally {
      lock.unlock();
    }
  }
  
  public void setDone(boolean b) {
    try {
      lock.lock();
      done = b;
      
      if (!done)
        cv.signal();
    } finally {
      lock.unlock();
    }
  }
}
