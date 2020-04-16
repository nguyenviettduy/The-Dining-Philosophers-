package philosopher;

public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            /**
             * Nhà triết học có số hiệu lẻ lấy đũa bên trái, rồi bên phải.
             * Ngược lại đối với các nhà triết học có số hiệu chẵn
             */

            if(i % 2 == 0){
                philosophers[i] = new Philosopher(rightFork, leftFork);
            }
            else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }
            Thread t = new Thread(philosophers[i], "philosopher.Philosopher " + (i + 1));
            t.start();
        }
    }
}