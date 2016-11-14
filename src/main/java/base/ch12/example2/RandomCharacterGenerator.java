package base.ch12.example2;

import base.ch12.CharacterEventHandler;
import base.ch12.CharacterListener;
import base.ch12.CharacterSource;
import base.ch12.TypeServerConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RandomCharacterGenerator extends Thread implements CharacterSource {
    private char[] chars;
    private int curChar;
    private Random random = new Random();
    private CharacterEventHandler handler;
    private boolean done = true;
    private Lock lock = new ReentrantLock();
    private Condition cv = lock.newCondition();
    private String host;
    private int port;

    public RandomCharacterGenerator(String host, int port) {
        handler = new CharacterEventHandler();
        this.host = host;
        this.port = port;
    }

    private synchronized void getString() throws IOException {
        Socket sock = new Socket(host, port);
        DataInputStream reader = new DataInputStream(sock.getInputStream());
        reader.read(); // Welcome
        DataOutputStream writer = new DataOutputStream(sock.getOutputStream());
        byte b = TypeServerConstants.GET_STRING_REQUEST;
        writer.write(b);
        writer.flush();
        b = (byte) reader.readByte();
        if (b != TypeServerConstants.GET_STRING_RESPONSE)
            throw new IllegalStateException("Bad recv state " + b);
        String s = reader.readUTF();
        chars = s.toCharArray();
        curChar = 0;
        sock.close();
    }

    public int getPauseTime(int minTime, int maxTime) {
        return (int) (minTime + ((maxTime - minTime) * random.nextDouble()));
    }

    public int getPauseTime() {
        return getPauseTime(2000, 5500);
    }

    public void addCharacterListener(CharacterListener cl) {
        handler.addCharacterListener(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        handler.removeCharacterListener(cl);
    }

    public void nextCharacter() {
        try {
            if (chars == null)
                getString();
            handler.fireNewCharacter(this, (int) chars[curChar++]);
            if (curChar == chars.length)
                getString();
        } catch (IOException ioe) {
            // Put up a dialog box to alert user of error
        }
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
