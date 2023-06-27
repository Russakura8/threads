import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class first{
    static int N = 312;
    static int K = 400;
    static int M = 300;
    static int P = 8;
    static int[][] result = new int[N][M];

    public static void initialization(){
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                result[i][j] = 0;
            }
        }
    }

    public static void str_multiply(int[][] first, int[][] second, int str_num){
        str_num -= 1;
        for (int i = 0; i < M ; i++) {
            for (int j = 0; j < K; j++) {
                result[str_num][i] += first[str_num][j] * second[j][i];
            }
        }
    }
    public static void potok_multiply(int[][] first, int[][] second) {

        ExecutorService executor = Executors.newFixedThreadPool(P);
        for (int i = 0; i < N; i++) {
            int finalI = i;
            Runnable runnable = () -> str_multiply(first, second, finalI + 1);
            executor.execute(runnable);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

        /*ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int finalI = i;
            Runnable runnable = () -> str_multiply(first, second, finalI + 1);
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
         */

    public static void matrix_print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            String for_print = new String();
            for (int j = 0; j < matrix[0].length; j++) {
                for_print = for_print + matrix[i][j] + " ";
            }
            System.out.println(for_print);

        }
        System.out.println("\n");
    }

    public static void matrix_multiplication(int[][] first, int[][] second){

        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                for (int k = 0; k < K; k++){
                    result[i][j] += first[i][k] * second[k][j];
                }
            }
        }
    }

    public static void main(String[] args)
    {
        int[][] matrix1 = new int[N][K];
        int[][] matrix2 = new int[K][M];
        initialization();

        for (int i = 0; i < N; i++){
            for (int j = 0; j < K; j++){
                matrix1[i][j] = new Random().nextInt(100 + 1);
            }
        }
        for (int i = 0; i < K; i++){
            for (int j = 0; j < M; j++){
                matrix2[i][j] = new Random().nextInt(100 + 1);
            }
        }

        long startTime = System.currentTimeMillis();

        matrix_multiplication(matrix1, matrix2);

        long totalTime1 = System.currentTimeMillis() - startTime;


        initialization();

        startTime = System.currentTimeMillis();


        potok_multiply(matrix1, matrix2);
        long totalTime2 = System.currentTimeMillis() - startTime;

        matrix_print(matrix1);
        matrix_print(matrix2);
        matrix_print(result);
        System.out.println(totalTime1 + " " + totalTime2);

    }
}