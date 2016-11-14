package threads2edition.ch053.AsyncInputStream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsyncInputStream extends FilterInputStream implements Runnable {

    BusyFlag lock;                        // Data lock
    CondVar empty, full;                  // Signal variables
    private Thread runner;                // Async reader thread
    private volatile byte result[];       // Buffer
    private volatile int reslen;          // Buffer length
    private volatile boolean EOF;         // End-of-file indicator
    private volatile IOException IOError; // I/O exceptions

    protected AsyncInputStream(InputStream in, int bufsize) {
        super(in);
        lock = new BusyFlag();              // Allocate sync variables.
        empty = new CondVar(lock);
        full = new CondVar(lock);

        EOF = false;
        IOError = null;

        result = new byte[bufsize];         // Allocate storage area
        reslen = 0;                         // and initialize variables.
        runner = new Thread(this);          // Start reader thread.
        runner.start();
    }

    protected AsyncInputStream(InputStream in) {
        this(in, 1024);
    }

    @Override
    public int read() throws IOException {
        try {
            lock.getBusyFlag();
            while (reslen == 0) {
                try {
                    if (EOF) {
                        return (-1);
                    }
                    if (IOError != null) {
                        throw IOError;
                    }
                    empty.cvWait();
                } catch (InterruptedException e) {
                }
            }
            return (int) getChar();
        } finally {
            lock.freeBusyFlag();
        }
    }

    @Override
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        try {
            lock.getBusyFlag();
            while (reslen == 0) {
                try {
                    if (EOF)
                        return (-1);
                    if (IOError != null)
                        throw IOError;
                    empty.cvWait();
                } catch (InterruptedException e) {
                }
            }
            int sizeread = Math.min(reslen, len);
            byte c[] = getChars(sizeread);
            System.arraycopy(c, 0, b, off, sizeread);
            return (sizeread);
        } finally {
            lock.freeBusyFlag();
        }
    }

    @Override
    public long skip(long n) throws IOException {
        try {
            lock.getBusyFlag();
            int sizeskip = Math.min(reslen, (int) n);
            if (sizeskip > 0) {
                byte c[] = getChars(sizeskip);
            }
            return ((long) sizeskip);
        } finally {
            lock.freeBusyFlag();
        }
    }

    @Override
    public int available() throws IOException {
        return reslen;
    }

    @Override
    public void close() throws IOException {
        try {
            lock.getBusyFlag();
            reslen = 0; // Clear buffer.
            EOF = true; // Mark end of file.
            empty.cvBroadcast(); // Alert all threads.
            full.cvBroadcast();
        } finally {
            lock.freeBusyFlag();
        }
    }

    @Override
    public void mark(int readlimit) {
    }

    @Override
    public void reset() throws IOException {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    public void run() {
        try {
            while (true) {
                int c = in.read();
                try {
                    lock.getBusyFlag();
                    if ((c == -1) || (EOF)) {
                        EOF = true;                     // Mark end of file.
                        in.close();                     // Close input source.
                        return;                         // End I/O thread.
                    } else {
                        putChar((byte) c);              // Store the byte read.
                    }
                    if (EOF) {
                        in.close();                     // Close input source.
                        return;                         // End I/O thread.
                    }
                } finally {
                    lock.freeBusyFlag();
                }
            }
        } catch (IOException e) {
            IOError = e; // Store exception.
            return;
        } finally {
            try {
                lock.getBusyFlag();
                empty.cvBroadcast(); // Alert all threads.
            } finally {
                lock.freeBusyFlag();
            }
        }
    }

    private void putChar(byte c) {
        try {
            lock.getBusyFlag();
            while ((reslen == result.length) && (!EOF)) {
                try {
                    full.cvWait();
                } catch (InterruptedException ie) {
                }
            }
            if (!EOF) {
                result[reslen++] = c;
                empty.cvSignal();
            }
        } finally {
            lock.freeBusyFlag();
        }
    }

    private byte getChar() {
        try {
            lock.getBusyFlag();
            byte c = result[0];
            System.arraycopy(result, 1, result, 0, --reslen);
            full.cvSignal();
            return c;
        } finally {
            lock.freeBusyFlag();
        }
    }

    private byte[] getChars(int chars) {
        try {
            lock.getBusyFlag();
            byte c[] = new byte[chars];
            System.arraycopy(result, 0, c, 0, chars);
            reslen -= chars;
            System.arraycopy(result, chars, result, 0, reslen);
            full.cvSignal();
            return c;
        } finally {
            lock.freeBusyFlag();
        }
    }
}
