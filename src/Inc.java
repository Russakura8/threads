import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Inc {
    static int m = 0;
    static int N = 10;



    static void break_the_limit(){
        ArrayList<Thread> threads = new ArrayList<>();
        var mt = new ReentrantLock();

        for (int i = 0; i < N; i++) {

            Runnable runnable = () -> {
                for (int j = 0; j < 10000; j++) {
                    mt.lock();
                    m++;
                    mt.unlock();
                }
            };

            Thread dva_potok = new Thread(runnable);
            threads.add(dva_potok);
            dva_potok.start();

        }

        for (Thread th: threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    static void break_the_limit_atom(){
        ArrayList<Thread> threads = new ArrayList<>();
        var atom = new AtomicInteger();

        for (int i = 0; i < N; i++) {

            Runnable runnable = () -> {
                for (int j = 0; j < 10000; j++) {
                    m = atom.incrementAndGet();
                }
            };

            Thread dva_potok = new Thread(runnable);
            threads.add(dva_potok);
            dva_potok.start();

        }

        for (Thread th: threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        break_the_limit();
        long totalTime1 = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        break_the_limit_atom();
        long totalTime2 = System.currentTimeMillis() - startTime;
        System.out.println(m);
        System.out.println(totalTime1 + " " + totalTime2);

    }


}
