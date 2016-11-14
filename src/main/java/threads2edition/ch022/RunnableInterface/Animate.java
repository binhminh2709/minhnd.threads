package threads2edition.ch022.RunnableInterface;

import org.apache.log4j.Logger;
import threads2edition.util.IOUtil;
import threads2edition.util.NameImage;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class Animate extends Applet implements Runnable {

    private static final long serialVersionUID = 2059798146828365123L;

    private static final Logger _log = Logger.getLogger(Animate.class);
    // ///THREAD
    Component comp; // Component that needs repainting
    int timediff; // Time between repaints of the component
    volatile boolean shouldRun = true; // Set to false to stop thread
    Thread t;
    private int count, lastcount;
    private Image pictures[];

    // Method of Applet
    @Override
    public void init() {
        _log.info("====INIT===");
        // Thread
        timediff = 1000;

        // Applet
        lastcount = 10;
        count = 0;
        pictures = new Image[10];
        MediaTracker tracker = new MediaTracker(this);
        for (int a = 0; a < lastcount; a++) {
            // pictures[a] = getImage(getCodeBase(), new Integer(a).toString() +
            // ".jpeg");
            try {
                pictures[a] = ImageIO.read(IOUtil.getInputStream(NameImage.sImge[a]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tracker.addImage(pictures[a], 0);
        }
        tracker.checkAll(true);
    }

    // Method of Applet
    @Override
    public void start() {
        _log.info("====START===");
        // Thread(Runnable target).
        if (t == null) {
            t = new Thread(this, "MinhND");
            t.start();
        }
    }

    // Method of Applet
    @Override
    public void stop() {
        _log.info("====STOP===");
        shouldRun = false;
        t = null;
    }

    // Method of Container
    @Override
    public void paint(Graphics g) {
        g.drawImage(pictures[count++], 0, 0, null);
        _log.info("====PAINT===count===" + count);
        if (count == lastcount) {
            count = 0;
        }
    }

    // Method of Thread
    @Override
    public void run() {
        _log.info("====RUN===");
        // Example 1
        // while (shouldRun) {
        // try {
        // this.repaint();
        // Thread.sleep(timediff);
        // //or sleep(timediff);
        // } catch (Exception e) {
        // _log.error(e);
        // }
        // }

        // Example 2
        while (isActive()) {
            try {
                this.repaint();
                Thread.sleep(timediff);
            } catch (Exception e) {
                _log.error(e);
            }
            t = null;
        }
    }

    /**
     * After merging the classes, we now have a direct reference to the applet, so
     * we can call the repaint() method directly. Because the Animate class is not
     * of the Thread class, its run()method cannot call the sleep()method
     * directly. Fortunately, the sleep()method is a static method, so we can
     * still access it using the Thread class specifier
     *
     * As can be seen from this example, the threading interface model allows
     * classes that already have fixed inheritance structures to be threaded
     * without creating a new class. However, there is still one unanswered
     * question: when should you use the Runnable interface and when should you
     * create a new subclass of Thread?
     *
     * ======================The isActive() Method======================
     *
     * We used the isActive() method in the last example instead of stopping the
     * thread explicitly. This shows another technique you can use to stop your
     * threads; the benefit of this technique is that it allows the run() method
     * to terminate normally rather than through the immediate termination caused
     * by the stop() method. This allows the run() method to clean up after itself
     * before it terminates.
     *
     * The isActive() method is part of the Applet class and determines if an
     * applet is active. By definition, an applet is active between the periods of
     * the applet's start() and stop() methods. Don't confuse this method with the
     * isAlive() method of the Thread class, which we'll discuss later
     *
     * ======================Inheritance or Interfaces======================
     *
     * As noted, we will choose threading with inheritance or interfaces based on
     * personal preference and the clarity of the solution. However, those of you
     * who are object-oriented purists could argue that unless we are enhancing
     * the Thread class, we should not inherit from the Thread class
     *
     * Theorists could insert an entire chapter on this issue. Our main concern is
     * for the clarity of the code; any other reasons for choosing between
     * threading by inheritance or interfaces are beyond the scope of this book
     *
     * Chung ta se chon Threading voi inheritance hoac interface co ban tren the
     * hien moi~ ca nhan, va su' ro~ rang cua tung giai phap. Tuy nhien, nhung~
     * thu cua ban, nguoi theo huong doi tuong cu~, chi? ro~ dieu do, Tru' phi,
     * chung ta nang~ cao Thread, Chung ta ko nen ke thua tu Class Thread.
     *
     * Nha' ly' luan them mot toan bo chapter tren van^ de^' do. Lien quan chinh
     * chung ta la' su' ro~ rang cua? nhung~ dong code, mot vai nhung~ ly do cho
     * viec chon giua~ ingeritance or interfaces, la pham vi cua cuon sach nay.
     */
}
