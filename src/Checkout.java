import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Checkout {
    private ReentrantLock mutex = new ReentrantLock();
    private int counter = 0;
    private String name;
    private int queueLength = 0;

    public void increaseQueueLength() {
        queueLength++;
    }
    public void decreaseQueueLength() {
        queueLength--;
    }
    public int getQueueLength() {
        return queueLength;
    }

    public Checkout(String name) {
        this.name = name;
    }
    public void pay() {
        //System.out.println( threadName + " gets in line at " + name);
        String threadName = Thread.currentThread().getName();
        mutex.lock();
        try {
        System.out.println(threadName  + " is talking to cashier at " + name);
        counter++;

            Random rand = new Random();
            Thread.currentThread().sleep(5000);
            //TimeUnit.SECONDS.sleep(rand.nextInt(10));

        }catch (InterruptedException e) {
          System.out.println(threadName + " noticed while paying that the mensa is closing.");
          Thread.currentThread().interrupt(); //reset the interrupted flag
        }
        finally {
            System.out.println(threadName + " has payed.");
            decreaseQueueLength();
            mutex.unlock();
        }
    }


    public int getCounter() {
        return counter;
    }

    public String getName() {
        return name;
    }
}
