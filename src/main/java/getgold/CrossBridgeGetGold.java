package getgold;

import java.util.concurrent.atomic.AtomicInteger;

public class CrossBridgeGetGold {
    public static volatile Integer numberOfGold = 10;

    public static volatile Boolean holdingGate = true;

    // seraphone nhị phân tránh nhiều người cùng giữ cổng
    public static Object gateMutex = new Object();

    // seraphone nhị phân tránh nhiều người lên cầu cùng lúc
    public static final Object bridgeMutex = new Object();

    // biến kiểm tra còn thỏi vàng trên cầu không
    public static volatile Boolean goldRemaining = true;

    public synchronized static void startHoldingGate() {
        gateMutex = true;
    }
    public synchronized static void stopHoldingGate() {
        gateMutex = false;
    }
    public synchronized static boolean crossBridgeAndGetGold() {
        numberOfGold--;
        return numberOfGold != 0;
    }

    public static void createThreads(int numberOfPeople) {
        for (int i = 0; i < numberOfPeople; i++) {
            final int index = i;
            AtomicInteger count = new AtomicInteger();
            new Thread(() -> {

                // tiến trình chiếm cổng
                synchronized (gateMutex) {
                    // bắt đầu giữ cổng
                    System.out.println("Số " + (index + 1)+ " giữ cổng");

                    startHoldingGate();

                    // nếu cầu đang bị tiến trình khác chiếm thì không được ngừng giữ cổng
                    synchronized (bridgeMutex) {
                        System.out.println("Số " + (index + 1) + " ngừng giữ cổng");
                        stopHoldingGate();
                    }
                }
                System.out.println("Số " + (index + 1) + " đang đợi lấy vàng");

                // tiến trình chiếm cầu nếu trên cầu không có ai
                synchronized (bridgeMutex){

                    // kiểm tra còn vàng không
                    if(goldRemaining){

                        // nếu như không có ai giữ cổng thì đợi
                        while (!holdingGate);

                            // Lấy vàng và lưu kết quả còn vàng trên cầu hay không vào goldRemaining
                            goldRemaining = crossBridgeAndGetGold();
                            System.out.println("Số " + (index + 1) + " qua cầu và lấy được vàng");
                            count.getAndIncrement();
                    }
                    else{
                        // nếu không còn vàng thì in thông báo không qua cầu
                        System.out.println("Số " + (index + 1) + " không qua cầu");
                    }
                }
                // in thông báo kết thúc tiến trình
                System.out.println("Số " + (index + 1) + " hoàn thành công việc");

            }).start();
        }
    }
    public static void main(String[] args) {
        System.out.println("Tổng số vàng: " + numberOfGold);
        int numberOfPeople = numberOfGold + 3;
        System.out.println("Tổng số người: " + numberOfPeople);
        createThreads(numberOfPeople);
    }
}
