package base.ch02.example2;

import base.ch02.CharacterEventHandler;
import base.ch02.ICharacterListener;
import base.ch02.ICharacterSource;

import java.util.Random;

public class RandomCharacterGenerator extends Thread implements ICharacterSource {

    static char[] chars;
    static String charArray = "abcdefghijklmnopqrstuvwxyz0123456789";

    static {
        chars = charArray.toCharArray();
    }

    Random random;
    CharacterEventHandler handler;

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
        for (; ; ) {
            nextCharacter();
            try {
                Thread.sleep(getPauseTime());
            } catch (InterruptedException ie) {
                return;
            }
        }
    }
}
