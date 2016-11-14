package threads2edition.ch031.BankingExample;

public class AutomatedTellerMachine extends Teller {
    Account a;

    public synchronized boolean login(String name, String password) {
        if (a != null) {
            throw new IllegalArgumentException("Already logged in");
        }
        a = verifyAccount(name, password);
        if (a == null) {
            return false;
        }
        a.lock();
        return true;
    }

    /**
     * Generate code
     */
    private Account verifyAccount(String name, String password) {
        return new Account("admin", "admin");
    }

    public void withdraw(float amount) {
        if (a.deduct(amount)) {
            dispense(amount);
        }
        printReceipt();
    }

    private void printReceipt() {

    }

    private void dispense(float amount) {

    }

    public void balanceInquiry() {
        printBalance(a.balance());
    }

    private void printBalance(float balance) {

    }

    public synchronized void logoff() {
        a.unlock();
        a = null;
    }
}
