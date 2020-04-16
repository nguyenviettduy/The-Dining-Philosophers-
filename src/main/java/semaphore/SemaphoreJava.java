package semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreJava {
    private static Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) {
        System.out.println("Total available Semaphore permits: " + semaphore.availablePermits());
        for (int i = 0; i < 6; i++){
            WorkerThread worker = new WorkerThread(semaphore, "ATM " + (i + 1));
            worker.start();
        }
    }
}
