package threads2edition.ch037.Synchronization;

public class Account {

    /**
     * defuct account
     * <p>
     * old method
     */
  
  /*
   * public boolean deduct(float amount) { return false; }
   */

    private float total;

    public synchronized boolean deduct(float t) {
        if (t <= total) {
            total -= t;
            return true;
        }
        return false;
    }

}
