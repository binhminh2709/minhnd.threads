package threads2edition.ch037.Synchronization;

public class AutomatedTellerMachine extends Teller {

    public void withDraw(float amount) {
        Account a = getAccount();
        if (a.deduct(amount)) {
            dispence(amount);
        }
        printReceipt();
    }

    /**
     * method print information of receipt
     */
    private void printReceipt() {

    }

    /**
     * dispence: rut tien
     */
    private void dispence(float amount) {

    }

    private Account getAccount() {
        return null;
    }
}
/**
 * In the previous chapter, we covered a lot of ground: we examined how to
 * create and start threads, how to arrange for them to terminate, how to name
 * them, how to monitor their life cycles, and so on. In the examples of that
 * chapter, however, the threads that we examined were more or less independent:
 * they did not need to share any data between them
 * <p>
 * This chapter, we look at the issue of sharing data between threads. Sharing
 * data between threads is often hampered due to what is known as a race
 * condition between the threads attempting to access the same data more or less
 * simultaneously. In this chapter, we'll look at the concept of a race
 * condition as well as examining a mechanism that solves race conditions. We
 * will see how this mechanism can be used not only to coordinate access to
 * data, but also for many problems in which synchronization is needed between
 * threads. Before we start, let's introduce a few concepts.
 * <p>
 * --hampered
 * <p>
 * Of course, we are assuming that the Teller class and the getAccount() ,
 * dispense() , and printReceipt() methods have already been implemented. For
 * our purposes, we are simply examining this algorithm at a high level, so
 * these methods will not be implemented here.
 * <p>
 * During our testing, we run a few simple and short tests of the routine. These
 * tests involve withdrawing some cash. In certain cases, we withdraw a small
 * amount. In other cases, we withdraw a large amount. We withdraw with enough
 * cash in the account to cover the transaction, and we withdraw without enough
 * cash in the account to cover the transaction. In each case, the code works as
 * desired. Being proud of our routine, we send it to a local branch for beta
 * testing.
 * <p>
 * As it turns out, it is possible for two people to have access to the same
 * account (e.g., a joint account). One day, a husband and wife both decide to
 * empty the same account, and purely by chance, they empty the account at the
 * same time. We now have a race condition: if the two users withdraw from the
 * bank at the same time, causing the methods to be called at the same time, it
 * is possible for the two ATMs to confirm that the account has enough cash and
 * dispense it to both parties. In effect, the two users are causing two threads
 * to access the account database at the same time.
 * <p>
 * Do do, chung ta gia dinh rang, Teller class va nhung method getAccount(),
 * dispense(), printReceipt() da san sang thuc thu roi. Muc dich cua? chung,
 * chung ta don gian vi du thuat toan muc level cao hown, nhung~ method se ko
 * implement o day.
 * <p>
 * Trai? qua qua' trinh testing, chung ta chay don gian few va nhung~ doan test
 * ngan cua routine. Nhung~ doan test xu? ly rut tien. Trong truong hop certain
 * (dai loai la hop ly), chung ta rut mot luong tien nho. Trong truong hop khac,
 * chung ta rut mot so tien >. CHung ta rut vua du so tien trong tai khoan?
 * cover(vo? be^' ngoai) transaction, va chung ta rut ko dung so tien vua du?
 * tron tai khoan cover the transaction. In moi truong hop do, trong viec code
 * dc thiet ke san~. Being prout cua our routine, chung ta gui no den local
 * branch cho test thu? nghiem.
 * <p>
 * Nhu la it turns out, co kha? nang 2 nguoi co cung mot tai khoan tuong tu, Mot
 * ngay, nguoi chong^ va nguoi vo deu chi ra ton tai mot tai khoan? tuong tu, va
 * purely by change, ....
 * <p>
 * =======================Definition: Atomic=======================
 * <p>
 * The term atomic is related to the atom, once considered the smallest possible
 * unit of matter, unable to be broken into separate parts. When a routine is
 * considered atomic, it cannot be interrupted during its execution. This can
 * either be accomplished in hardware or simulated in software. In general,
 * atomic instructions are provided in hardware that is used to implement atomic
 * routines in software.
 * <p>
 * In our case, we define an atomic routine as one that can't be found in an
 * intermediate state. In our banking example, if the acts of
 * "checking on the account" and "changing the account status" were atomic, it
 * would not be possible for another thread to check on the same account until
 * the first thread had finished changing the account status.
 * <p>
 * - atomic: adj - thuoc ve nguyen tu? - term: thoi han, ki han. - atom: nguyen
 * tu? - routine: n - doan chuong trinh - intermediate: adj - trung gian, o?
 * giua~ - race: dua xe, racing, raced - certain: chac chan. -
 * <p>
 * There is a race condition because the action of checking the account and
 * changing the account status is not atomic. Here we have the husband thread
 * and the wife thread competing for the account:
 * <p>
 * 1. The husband thread begins to execute the deduct() method. 2. The husband
 * thread confirms that the amount to deduct is less than or equal to the total
 * in the account. 3. The wife thread begins to execute the deduct() method. 4.
 * The wife thread confirms that the amount to deduct is less than or equal to
 * the total in the account. 5. The wife thread performs the subtraction
 * statement to deduct the amount, returns true , and the ATM dispenses her
 * cash. 6. The husband thread performs the subtraction statement to deduct the
 * amount, returns true , and the ATM dispenses his cash.
 * <p>
 * <p>
 * The Java specification provides certain mechanisms that deal specifically
 * with this problem. The Java language provides the synchronized keyword; in
 * comparison with other threading systems, this keyword allows the programmer
 * access to a resource that is very similar to a mutex lock. For our purposes,
 * it simply prevents two or more threads from calling our deduct() method at
 * the same time.
 * <p>
 * public class Account { private float total; public synchronized boolean
 * deduct(float t) { if (t <= total) { total -= t; return true; } return false;
 * } }
 * <p>
 * By declaring the method as synchronized, if two users decide to withdraw cash
 * from the ATM at the same time, the first user executes the deduct() method
 * while the second user waits until the first user completes the deduct()
 * method. Since only one user may execute the deduct() method at a time, the
 * race condition is eliminated.
 * <p>
 * =======================Definition: Mutex Lock=======================
 * <p>
 * A mutex lock is also known as a mutually exclusive lock. This type of lock is
 * provided by many threading systems as a means of synchronization. Basically,
 * it is only possible for one thread to grab a mutex at a time: if two threads
 * try to grab a mutex, only one succeeds. The other thread has to wait until
 * the first thread releases the lock; it can then grab the lock and continue
 * operation.
 * <p>
 * With Java, there is a lock created in every object in the system. When a
 * method is declared synchronized, the executing thread must grab the lock
 * assigned to the object before it can continue. Upon completion of the method,
 * the mechanism automatically releases the lock.
 * <p>
 * Under the covers, the concept of synchronization is simple: when a method is
 * declared as synchronized, it must have a token, which we call a lock. Once
 * the method has acquired this lock (we may also say the lock has been checked
 * out or grabbed ), it executes the method and releases (we may also say
 * returns) the lock once the method is finished. No matter how the method
 * returns—including via an exception—the lock is released. There is only one
 * lock per object, so if two separate threads try to call synchronized methods
 * of the same object, only one can execute the method immediately; the other
 * thread has to wait until the first thread releases the lock before it can
 * execute the method
 */
