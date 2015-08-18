package ch07;

import java.util.Vector;

public class CharacterEventHandler {
  private Vector listeners = new Vector();
  
  public void addCharacterListener(ICharacterListener cl) {
    listeners.add(cl);
  }
  
  public void removeCharacterListener(ICharacterListener cl) {
    listeners.remove(cl);
  }
  
  public void fireNewCharacter(ICharacterSource source, int c) {
    CharacterEvent ce = new CharacterEvent(source, c);
    ICharacterListener[] cl = (ICharacterListener[]) listeners.toArray(new ICharacterListener[0]);
    for (int i = 0; i < cl.length; i++)
      cl[i].newCharacter(ce);
  }
}
