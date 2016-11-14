package threads2edition.ch026.JoiningThreads;

import org.apache.log4j.Logger;
import threads2edition.util.IOUtil;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class JoiningAnimate extends Applet {
    // ...same code

    private static final Logger _log = Logger.getLogger(JoiningAnimate.class);

    String[] nameImage = new String[]{"Chrysanthemum", "Desert", "Hydrangeas", "Jellyfish", "Koala", "Lighthouse", "Penguins", "Spring",
            "TripleChildrens", "Tulips"};
    private int count, lastcount;
    private Image pictures[];
    private JoiningTimer t;
    private long startTime;

    // Method of applet
    @Override
    public void init() {

        _log.info("====INIT===");

        lastcount = 10;
        count = 0;
        pictures = new Image[10];
        MediaTracker tracker = new MediaTracker(this);
        for (int a = 0; a < lastcount; a++) {
            // pictures[a] = getImage(getCodeBase(), new Integer(a).toString() +
            // ".jpeg");
            try {
                pictures[a] = ImageIO.read(IOUtil.getInputStream(nameImage[a]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tracker.addImage(pictures[a], 0);
        }
        tracker.checkAll(true);
    }

    // Method of applet
    @Override
    public void start() {
        _log.info("====START===");
        t = new JoiningTimer(this, 1000);
        t.start();
        startTime = System.currentTimeMillis();
        _log.info("====" + startTime);
    }

    // Method of applet
    @Override
    public void stop() {
        t.shouldRun = false;
        // after line code, Thread is live, code in statement while don't run,
        try {
            t.join();
            // after line code, returns as soom as thread is considered "not alive"
            _log.info("====isAlive()====" + t.isAlive());
        } catch (InterruptedException e) {
            _log.equals(e);
        }
    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(pictures[count++], 0, 0, null);

        _log.info("====PAINT====" + count + "=" + t.isAlive());

        if (count == lastcount) {
            count = 0;
        }
    }
}
// ...same code
/**
 * The isAlive() method can be thought of as a crude form of communication. We
 * are waiting for information: the indication that the other thread has
 * completed. As another example, if we start a couple of threads to do a long
 * calculation, we are then free to do other tasks. Assume that sometime later
 * we have completed all other secondary tasks and need to deal with the results
 * of the long calculation: we need to wait until the calculations are finished
 * before continuing on to process the results
 * <p>
 * We could accomplish this task by using the looping isAlive()technique we've
 * just discussed, but there are other techniques in the Java API that are more
 * suited to this task. This act of waiting is called a thread join. We are
 * "joining" with the thread that was "forked" off from us earlier when we
 * started the thread. So, modifying our last example,
 * <p>
 * =========void join()====================================== Waits for the
 * completion of the specified thread. By definition, join() returns as soon as
 * the thread is considered "not alive." This includes the case in which the
 * join()method is called on a thread that has not been started.
 * <p>
 * =========void join(long timeout)========================== Waits for the
 * completion of the specified thread, but no longer than the timeout specified
 * in milliseconds. This timeout value is subject to rounding based on the
 * capabilities of the underlying platform.
 * <p>
 * =========void join(long timeout, int nanos)=============== Waits for the
 * completion of the specified thread, but no longer than a timeout specified in
 * milliseconds and nanoseconds. This timeout value is subject to rounding based
 * on the capabilities of the underlying platform.
 * <p>
 * When the join() method is called, the current thread will simply wait until
 * the thread it is joining with is no longer alive. This can be caused by the
 * thread not having been started, or having been stopped by yet another thread,
 * or by the completion of the thread itself. The join()method basically
 * accomplishes the same task as the combination of the sleep()and isAlive()
 * methods we used in the earlier example. However, by using the join() method,
 * we accomplish the same task with a single method call. We also have better
 * control over the timeout interval, and we don't waste CPU cycles by polling.
 * <p>
 * Another interesting point about both the isAlive()method and the join()method
 * is that we are actually not affecting the thread on which we called the
 * method. That thread will run no differently whether the join()method is
 * called or not; instead, it is the calling thread that is affected. The
 * isAlive()method simply returns the status of a thread, and the join()method
 * simply waits for a certain status on the thread
 * <p>
 * Khi method join() duoc goi,the thread hien tai se~ don gian doi cho den khi
 * the thread, no joining voi is no dai hon song (thoi gian song cua thread do
 * tac dong nhu la huy may ao jvm, joint() tu dong duoc goi). Do co the nguyen
 * ngan boi Thread ko duoc start tu truoc hoac duoc stopped boi mot thread
 * khach, hoac hoan thanh chinh ban than Thread no. Method join() co ban hoan
 * thanh nhim vu tuong tu nhu ket hop cua The sleep() va isAlive() methods, da
 * duoc su dung trong vi du gan nhat. Tuy nhien, bang cach su dung join()
 * method, chung ta hoan thanh The same task bang cach call method don gian.
 * Chung ta cung~ co dieu khien tot nhat over timeou noi, va chung ta ko lang
 * phi vong CPU bang polling.
 * <p>
 * Diem? thu vi khac ve ca? hai method isAlive() and join() la chung thuc thi
 * khong anh? huong? The Thread on wwich ma chung ta da called method. Nhung
 * Thread do se run ko khac biet so method join() duoc goi, hoac ko thay the. No
 * duoc calling thread kia la anh? huong, Method isAlive() don gian tra? ve
 * trang thai cua thread, va method join() don gian doi for mot thang thai chac
 * chan tren the thread.
 * <p>
 * ===============join(), isAlive(), and the Current Thread=============== The
 * concept of a thread calling the isAlive() or the join() method on itself does
 * not make sense. There is no reason to check if the current thread is alive
 * since it would not be able to do anything about it if it were not alive. As a
 * matter of fact, isAlive() can only return true when it checks the status of
 * the thread calling it. If the thread were stopped during the isAlive()
 * method, the isAlive() method would not be able to return. So a thread that
 * calls the isAlive() method on itself will always receive true as the result.
 * <p>
 * Dinh nghia~ cua thread goi the isAlive() hoac the join() trong ban? than
 * chinh no ko tao ra nhieu y' nghia~. Chung ko co ly do kiem? tra neu Thread
 * hien tai la song tu thoi diem no ko lam mot cai j ve no, neu no ko song. Nhu
 * la' chu? de thuc te, isAlive() co the chi tra? ve ket qua true khi no kiem
 * tra trang thai cua The thread dang duoc goi. Neu thread do da stopped trong
 * luc method isAlive(), isAlive() co the ko co kha nang return. So mot thread,
 * call isALive() method tren itself, se~ luon luon tra ve true nhu la ket qua
 * <p>
 * <p>
 * The concept of a thread joining itself does not make sense, but let's examine
 * what happens when one tries. It turns out that the join() method uses the
 * isAlive() method to determine when to return from the join() method. In the
 * current implementation, it also does not check to see if the thread is
 * joining itself. In other words, the join() method returns when and only when
 * the thread is nolonger alive. This will have the effect of waiting forever.
 * <p>
 * Dinh nghia cua thread joining chinh ban than no, ko tao ra nhieu y' nghia~.
 * nhung cung' kham xet dieu^' j xay? ra khi co gang mot lan^'. No' tao ra
 * method join() su dung the isALive() cho den khi xac dinh khi nao return
 * method join(). Trong thuc thi current, no ko kiem tra xem neu^ thread la
 * joining itselt(chinh ban than no). trong mot other words, method join()
 * return khi va chi chi thread la ko qua song dai, dieu do se~ anh? huong cua
 * cho' doi' mai~ mai~.
 */
