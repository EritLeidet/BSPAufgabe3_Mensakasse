import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Checkout {
    private ReentrantLock mutex = new ReentrantLock();
    private int counter = 0;
    private String name;

    public Checkout(String name) {
        this.name = name;
    }
    public void pay() {
        //System.out.println( threadName + " gets in line at " + name);
        mutex.lock();
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName  + " is talking to cashier at " + name);
        counter++;
        try {
            Random rand = new Random();
            TimeUnit.SECONDS.sleep(rand.nextInt(10));
        }catch (InterruptedException e) {
          System.out.println(threadName + " noticed while paying that the mensa is closing.");
          Thread.currentThread().interrupt(); //reset the interrupted flag
        }
        finally {
            System.out.println(threadName + " has payed.");
            mutex.unlock();
        }
    }


    public int getCounter() {
        return counter;
    }

    public int getQueueLength() {
        return mutex.getQueueLength();
    }

    public String getName() {
        return name;
    }
}
