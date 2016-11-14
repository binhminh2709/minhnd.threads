package threads2edition.ch031.BankingExample;

public class Account {

    private float total;
    private BusyFlag flag = new BusyFlag();
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public synchronized boolean deduct(float t) {
        if (t <= total) {
            total -= t;
            return true;
        } else {
            return false;
        }
    }

    public synchronized float balance() {
        return total;
    }

    public void lock() {
        flag.getBusyFlag();
    }

    public void unlock() {
        flag.freeBusyFlag();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
