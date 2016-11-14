package threads2edition.ch061.Overview;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.net.Socket;

public class AsyncReadSocket extends Thread {

    private Logger _log = Logger.getLogger(AsyncReadSocket.class);

    private Socket s;
    private StringBuffer result;
    // StringBuffer are thread-safe, StringBuider isn't

    public AsyncReadSocket(Socket s) {
        this.s = s;
        result = new StringBuffer();
    }

    @Override
    public void run() {
        DataInputStream input = null;
        try {
            input = new DataInputStream(s.getInputStream());
        } catch (Exception e) {
            _log.error(e);
        }

        while (true) {
            try {
                char readChar = input.readChar();
                appendResult(readChar);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    // Get the String already read from the socket so far.
    // this method is used by the applet thread to obtain the data in a synchronous manner.
    private synchronized String getResult() {
        String rs = result.toString();
        result = new StringBuffer();
        return rs;
    }

    // put the new data into the Stringbuffer to be returned by the getResult
    // method.
    private synchronized void appendResult(char readChar) {
        _log.info("reading======" + readChar);
        result.append(readChar);
    }
}

/**
 * Let's look at a complete example. One of the primary uses for threads within
 * a Java program is to read data asynchronously. In this section, we'll develop
 * a class to read a network socket asynchronously.
 * <p>
 * Why is threading important for I/O? Whether you are reading from orwriting to
 * a file or network socket, a common problem exists, namely, that the action of
 * reading or writing depends on other resources. These resources may be other
 * programs; they may be hardware, like the disk or the network; they may be the
 * operating system or browser. These resources may become temporarily
 * unavailable for a variety of reasons: reading from a network socket may
 * involve waiting until the data is available, writing large amounts of data to
 * a file may take a long period of time to complete if the disk is busy with
 * other requests, and so on. Unfortunately, the mechanism to check whether
 * these resources are available does not exist in the Java API. This is
 * particularly a problem for network sockets, where data is likely to take a
 * long time to be transmitted over the network; it is possible for a read from
 * a network socket to wait forever.
 * <p>
 * =======================Why Asynchronous I/O ? =======================
 * <p>
 * The driving force behind asynchronous I/O is to allow the program to continue
 * to do something useful while it is waiting for data to arrive. If I/O is not
 * asynchronous and not running in a thread separate from the applet thread, we
 * run into the problems we discussed in the previous chapter: mouse and
 * keyboard events will be delayed, and the program will appear to be
 * unresponsive to the user while the I/O completes.
 * <p>
 * The InputStream class does contain the available() method. However, not all
 * input streams support that method, and on a slow network, writing data to a
 * socket is also likely to take a long time. In general, checking for data via
 * the available() method is much less efficient (and much harder to program)
 * than creating a new thread to read the data.
 * <p>
 * The solution to this problem is to use another thread. Say that we use this
 * new thread in an applet: since this new thread is independent of the applet
 * thread, it can block without hanging the applet. Of course, this causes a new
 * problem: when this thread finally is able to read the data, this data must be
 * returned to the applet thread. Let's take a look at a possible implementation
 * of a generic socket reader class that will read the socket from another
 * thread:
 * <p>
 * ...... ...... ......
 * <p>
 * =============================When Is a Race Condition a
 * Problem?============================= A race condition occurs when the order
 * of execution of two or more threads may affect some variable or outcome in
 * the program. It may turn out that all the different possible thread orderings
 * have the same final effect on the application: the effect caused by the race
 * condition may be insignificant, and may not even be relevant. For example, a
 * character lost in the AsyncReadSocket may not affect the final outcome of the
 * program. Alternately, the timing of the threading system may be such that the
 * race condition never manifests itself, despite the fact that it exists in the
 * code.
 * <p>
 * A race condition is a problem that is waiting to happen. Simple changes in
 * the algorithm can cause race conditions to manifest themselves in problematic
 * ways. And, since different virtual machines will have different orderings of
 * thread execution, the developer should never let a race condition exist even
 * if it is currently not causing a problem on the development system.
 * <p>
 * race condition: die^u' kie^n tranh chap. xay ra khi hai hay nhieu Thread cung
 * chia se? du lieu, va khi chung cung doc, cung ghi du lieu chia se do dong
 * thoi. Ket qua nhan duoc se phu thuoc vao tac dong len bien chia se la cai j
 * vao luc nao. Va nguoi lap trinh khong the biet duoc chinh xac dieu j xay ra.
 * <p>
 * Synchronized khai niem dua ra, de giai quyet cho phuong thuc hay doan ma~ ma'
 * ban can bao ve.
 * <p>
 * die^u kie^n tranh cha^p xa^y? ra khi thu' tu.' thu'c thi cua hai hay nhieu
 * threads co the anh huong den mot va'i bien hoac da^u ra trong mot chuong
 * trinh. It (chuong trinh) turn out (chi viec san xuat ra mot mon hang hay san
 * pham) tat ca thread duoc sap xep theo thu tu co kha nang khac nhau co anh
 * huong cuoi cung mot cach tuong tu tren chuong trinh: anh huong boi vi' dieu
 * kien tranh chap la khong dang co, co the ko lien quan. Vi du, ki tu bi mat
 * trong AsyncReadSocket co the ko anh huong cuoi cung den dau ra cua chuong
 * trinh. Luan phien nhau, thoi gian cua thread he thong co the? such dieu kien
 * tranh chap khong bao gio hien? nhien, mac du thuc te no ton tai trong code.
 * <p>
 * How does synchronizing two different methods prevent the two threads calling
 * those methods from stepping on each other ?
 * <p>
 * Why do we need the appendResult() method ? Couldn't we simply put that code
 * into the run() method and synchronize the run() method ? We could do that,
 * but the result would be disastrous. Every lock has an associated scope; that
 * is, the amount of code for which the lock is valid. Synchronizing the run()
 * method creates a scope that is too large and prevents other methods from
 * being run at all
 * <p>
 * ============================Definition: Scope of a Lock============================
 * The scope of a lockis defined as the period of time between when the lock is grabbed and released. In our examples so far,
 * we have usedonly synchronized methods; this means that the scope of these locks is the period of time it takes to execute these methods.
 * This is referred to as method scope.
 * <p>
 * Later in this chapter, we'll examine locks that apply to any block of code
 * inside a method or that can be explicitly grabbed and released; these locks
 * have a different scope. We'll examine this concept of scope as locks of
 * various types are introduced
 * <p>
 * <p>
 * How does a synchronized method behave in conjunction with a nonsynchronized
 * method ? Simply put, a synchronized method tries to grab the object lock, and
 * a nonsynchronized method doesn't. This means it is possible for many
 * nonsynchronized methods to run in parallel with a synchronized method. Only
 * one synchronized method runs at a time.
 * <p>
 * Synchronizing a method just means the lock is grabbed when that method
 * executes. It is the developer's responsibility to ensure that the correct
 * methods are synchronized. Forgetting to synchronize a method can cause a race
 * condition: if we had synchronized only the getResult() method of the
 * AsyncReadSocket class and had forgotten to synchronize the appendResult()
 * method, we would not have solved the race condition, since any thread could
 * call the appendResult() method while the getResult() method was executing
 */
