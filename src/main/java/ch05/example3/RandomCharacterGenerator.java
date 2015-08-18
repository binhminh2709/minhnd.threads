package ch05.example3;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import ch05.*;

public class RandomCharacterGenerator extends Thread implements ICharacterSource {
  private static char[] chars;
  private static String charArray = "abcdefghijklmnopqrstuvwxyz0123456789";
  static {
    chars = charArray.toCharArray();
  }
  
  private Random random;
  private CharacterEventHandler handler;
  private AtomicBoolean done = new AtomicBoolean(true);
  
  public RandomCharacterGenerator() {
    random = new Random();
    handler = new CharacterEventHandler();
  }
  
  public int getPauseTime(int minTime, int maxTime) {
    return (int) (minTime + ((maxTime - minTime) * random.nextDouble()));
  }
  
  public int getPauseTime() {
    return getPauseTime(2000, 5500);
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
    while (true) {
      try {
        if (done.get()) {
          Thread.sleep(100);
        } else {
          nextCharacter();
          Thread.sleep(getPauseTime());
        }
      } catch (InterruptedException ex) {
        return;
      }
    }
  }
  
  public void setDone(boolean b) {
    done.set(b);
  }
}
