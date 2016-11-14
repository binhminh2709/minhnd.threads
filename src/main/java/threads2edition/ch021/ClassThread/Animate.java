package threads2edition.ch021.ClassThread;

import org.apache.log4j.Logger;
import threads2edition.util.IOUtil;
import threads2edition.util.NameImage;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class Animate extends Applet {

    private static final long serialVersionUID = 4348632960758542678L;

    private static final Logger _log = Logger.getLogger(Animate.class);

    int count, lastcount;
    Image pictures[];
    AnimateTimerThread timer;

    @Override
    public void init() {
        _log.info("====INIT====");
        lastcount = 10;
        count = 0;
        pictures = new Image[10];
        MediaTracker tracker = new MediaTracker(this);
        for (int a = 0; a < lastcount; a++) {
            // pictures[a] = getImage(getCodeBase(), new Integer(a).toString() + ".jpeg");
            try {
                pictures[a] = ImageIO.read(IOUtil.getInputStream(NameImage.sImge[a]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tracker.addImage(pictures[a], 0);
        }
        tracker.checkAll(true);
    }

    @Override
    public void start() {
        _log.info("====START====");
        //timer = new AnimateTimerThread(this, 1000);
        timer = new AnimateTimerThread(this, 10);
        timer.start();
    }

    @Override
    public void stop() {

        _log.info("====STOP====");
        _log.info("====isAlive====" + timer.isAlive());

        timer.shouldRun = false;
        timer = null;
        try {
            _log.info("====isAlive====" + timer.isAlive());
        } catch (Exception e) {
            _log.info("====isAlive====FALSE");
        }


    }

    @Override
    public void paint(Graphics g) {
        _log.info("====PAINT====");
        g.drawImage(pictures[count++], 0, 0, null);
        if (count == lastcount) {
            count = 0;
        }
    }

    /**
     * we create and start the new thread in the applet's start() method.
     * This new thread is responsible only for informing the applet when to redraw the next frame;
     *
     * It is still the applet's thread that performs the redraw when the applet's paint() method is called.
     * The init() method in this case simply loads the image frames from the server.
     *
     * When the stop() method of the applet is called, we need to stop the timer
     * thread, since we do not need repaint() requests when the applet is no
     * longer running. To do this, we relied on the ability to set the shouldRun
     * variable of the TimerThread class to notify that class that it should
     * return from its run() method.
     *
     * When a thread returns from its run() method, it has completed its
     * execution, so in this case we also set the timer instance variable to null
     * to allow that thread object to be garbage collected.
     *
     * This technique is the preferred method for terminating a thread: threads
     * should always terminate by returning from their run() method. It's up to
     * the developer to decide how a thread should know when it's time to return
     * from the run() method; setting a flag, as we've done in this case, is
     * typically the easiest method to do that.
     *
     * Setting a flag means that my thread has to check the flag periodically.
     * Isn't there a cleaner way to stop the thread? And isn't there a way to
     * terminate the thread immediately, rather than waiting for it to check some
     * flag? Well, yes and no. The Thread class does contain a stop() method that
     * allows you to stop a thread immediately: no matter what the thread is
     * doing, it will be terminated. However, the stop() method is very dangerous.
     * In Java 2, the stop() method is deprecated; however, the reasons that led
     * it to become deprecated actually exist in all versions of Java, so you
     * should avoid using the stop() method in any release of Java. We'll discuss
     * the motivation for this in Chapter 6 after we understand a little more
     * about the details of threaded programming; for now, you'll have to accept
     * our word that using the stop() method is a dangerous thing. In addition,
     * calling the stop() method will sometimes result in a security exception, as
     * we'll explain in Chapter 10, so you cannot rely on it always working
     */
}
