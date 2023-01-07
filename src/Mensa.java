import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Mensa {
    static final int NUM_OF_CHECKOUTS = 2;
    static final int NUM_OF_STUDENTS = 10;

    public static void main(String[] args) {
        //Generate checkouts and add to collection
        List<Checkout> checkouts = new ArrayList<>();
        for (int i=1; i<=NUM_OF_CHECKOUTS; i++) {
            checkouts.add(new Checkout(String.format("Checkout %d", i)));
        }

        //Generate students and add to collection
        List<Student> students = new ArrayList<>();
        for (int i=1; i<=NUM_OF_STUDENTS; i++) {
            Student student = new Student(checkouts);
            student.setName("Student " + i);
            students.add(student);
        }

        //Open mensa
        for (Student s : students) {
            s.start();
        }
        System.out.println("##### Mensa is open!");

        //Ask students to leave mensa
        try {
            TimeUnit.SECONDS.sleep(20);
            System.out.println("##### Mensa is closing!");
        } catch (InterruptedException e) {
            System.out.println("Mensa (main) was interrupted whilst open.");
        }
        for (Student s : students) {
            s.interrupt();
        }

        //Wait until every student has died
        try {
            for (Student s : students) {
                s.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Mensa (main) was interrupted before being able to close.");
        }

        //Collect numbers of purchases
        for (Checkout c : checkouts) {
            System.out.println(c.getName() + " - Counter: " + c.getCounter());
        }
    }
}
