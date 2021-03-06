package base.ch05.example1;

import base.ch05.CharacterDisplayCanvasImpl;
import base.ch05.CharacterEvent;
import base.ch05.ICharacterListener;
import base.ch05.ICharacterSource;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvasImpl implements ICharacterListener, Runnable {

    private boolean done = true;
    private int curX = 0;
    private Lock lock = new ReentrantLock();
    private Condition cv = lock.newCondition();
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

    public void run() {
        try {
            lock.lock();
            while (true) {
                try {
                    if (done) {
                        cv.await();
                    } else {
                        repaint();
                        cv.await(100, TimeUnit.MILLISECONDS);
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

            if (timer == null) {
                timer = new Thread(this);
                timer.start();
            }
            if (!done)
                cv.signal();
        } finally {
            lock.unlock();
        }
    }
}
