package base.ch07.example2;

import base.ch07.CharacterDisplayCanvasImpl;
import base.ch07.CharacterEvent;
import base.ch07.ICharacterListener;
import base.ch07.ICharacterSource;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AnimatedCharacterDisplayCanvas extends CharacterDisplayCanvasImpl implements ICharacterListener, Runnable {

    private volatile boolean done = false;
    private int curX;
    private Lock lock = new ReentrantLock();
    private Condition cv = lock.newCondition();
    private Thread timer = null;

    public AnimatedCharacterDisplayCanvas(ICharacterSource cs) {
        super(cs);
    }

    public synchronized void newCharacter(CharacterEvent ce) {
        curX = 0;
        tmpChar[0] = (char) ce.character;
        repaint();
    }

    public synchronized void paintComponent(Graphics gc) {
        if (tmpChar[0] == 0)
            return;
        Dimension d = getSize();
        int charWidth = fm.charWidth(tmpChar[0]);
        gc.clearRect(0, 0, d.width, d.height);
        gc.drawChars(tmpChar, 0, 1, curX++, fontHeight);
        if (curX > d.width - charWidth)
            curX = 0;
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
