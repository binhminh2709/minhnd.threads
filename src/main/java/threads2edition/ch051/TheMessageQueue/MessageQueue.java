package threads2edition.ch051.TheMessageQueue;

import java.util.Vector;

public class MessageQueue {

    Vector<Object> queue = new Vector<Object>();

    public synchronized void send(Object obj) {
        queue.addElement(obj);
    }

    public synchronized Object recv() {
        if (queue.size() == 0) {
            return null;
        }
        Object obj = queue.firstElement();
        queue.removeElementAt(0);
        return obj;
    }
}
