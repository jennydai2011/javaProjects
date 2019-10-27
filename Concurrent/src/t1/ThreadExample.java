package t1;

import sun.jvm.hotspot.debugger.ThreadAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadExample {


    public static void main(String[] args) {
        test3();
    }

    //difference between method call and thread run
    public static void test1() {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();

            System.out.println(" Hello " + threadName);

        };

        task.run(); //here is the main thread run

        Thread thread = new Thread(task);
        thread.start(); //here is the forked thread run
    }

    //demo sleep
    public static void test2() {
        Runnable runnable = () -> {
            String name = Thread.currentThread().getName();
            System.out.println("Foo " + name);

            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" Bar " + name);
        };

        Thread thread = new Thread(runnable); //not recommended to new thread
        thread.start();

    }

    //demo ExecutorService
    public static void test3(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit( () -> {
           String threadName = Thread.currentThread().getName();
            System.out.println(" Hello " + threadName);
        });
    }


    public static void test() {
        List<String> test = Arrays.asList("1", "2", "3", "4");
        String result = String.join("", test);
        System.out.println(result);


    }
}
