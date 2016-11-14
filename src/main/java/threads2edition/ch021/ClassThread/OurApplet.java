package threads2edition.ch021.ClassThread;

import org.apache.log4j.Logger;

import java.applet.Applet;

public class OurApplet extends Applet {

    private static final long serialVersionUID = 8190782153045458605L;
    private static final Logger _log = Logger.getLogger(OurApplet.class);

    public void init() {
        _log.info("START========");
        long timeMillis = System.currentTimeMillis();
        // version 1
        OurClass oc = new OurClass();
        oc.run();

        // version 2
        // OurClassThread ocThread = new OurClassThread();
        // ocThread.start();

        _log.info("total excute: " + (System.currentTimeMillis() - timeMillis));
    }

}
