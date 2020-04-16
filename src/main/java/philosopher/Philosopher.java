package philosopher;

public class Philosopher implements Runnable {
    private Object leftFork;
    private Object rightFork;

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void doAction(String action){
        System.out.println(Thread.currentThread().getName() + " " + action);
        try {
            Thread.sleep((int)Math.random() * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            doAction(System.nanoTime() + ": Thinking");
            synchronized (leftFork) {
                doAction(System.nanoTime() + ": Picked up left fork");
                synchronized (rightFork) {
                    doAction(System.nanoTime() + ": Picked up right fork - eating");
                    doAction(System.nanoTime() + ": Put down right fork");
                }
                doAction(System.nanoTime() + ": Put down left fork. Back to thinking");
            }
        }
    }
}
