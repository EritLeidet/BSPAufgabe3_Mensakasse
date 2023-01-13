import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Student extends Thread {
    //private final int numOfStudents = 5;
    private List<Checkout> kassen;
    Random rand = new Random();
    private final int maxTimeWaiting = 10;

    public Student(List<Checkout> kassen) {
        this.kassen = kassen;
    }


    private void eat() throws InterruptedException {
        Thread.currentThread().sleep(200);
    }

    private void gotoMensa() throws InterruptedException {
        Thread.currentThread().sleep(200);
    }

    @Override
    public void run() {
        //System.out.println(getName() + " started to run.");
        try {
            while (!isInterrupted()) {
                Checkout kasse = shortestCheckout(kassen);
                kasse.pay();
                eat();
                gotoMensa();
            }
            System.out.println(getName() + " was interrupted without throwing InterruptedException");
        } catch(InterruptedException e){
            System.out.println(getName() + " was interrupted whilst eating or going to mensa.");
        }
    }

    public synchronized static Checkout shortestCheckout(List<Checkout> cls) {
        Checkout kasse = cls.stream().min(Comparator.comparingInt(Checkout::getQueueLength)).orElse(null);
        kasse.increaseQueueLength();
        return kasse;
    }
}
