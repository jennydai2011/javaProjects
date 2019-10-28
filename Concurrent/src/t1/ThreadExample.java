package t1;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadExample {


    public static void main(String[] args) {
        try {
            test7();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            if(!executor.isTerminated()){
                System.err.println("cancel non-finished taks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    public static void test4(){
        Callable<Integer> task = () -> {
          try {
              TimeUnit.MINUTES.sleep(1);
              return 123;
          }catch (InterruptedException e){
              throw  new IllegalStateException("task interrupted", e);
          }

        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);
        System.out.println("future done?"+ future.isDone());

        Integer result = null;
        try {
            result = future.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("future done?" + future.isDone());
        System.out.println("result is：" + result);

        executor.shutdown();
    }

    public static void test5() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3"
        );

        executor.invokeAll(callables)
                .stream()
                .map( future -> {

                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).forEach(System.out::println);

    }

    public static void test6() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                createCallable("result1", 1),
                createCallable("result2", 2),
                createCallable("result3", 3)
        );

        String actualResult = executor.invokeAny(callables);
        System.out.println("actualResult of invokeAny is:" + actualResult);

    }

    static Callable<String> createCallable (String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    public static void test7() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println(" Scheduling: " + System.nanoTime());

        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(1337);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);

        System.out.printf("Remaining Delay: %sms", remainingDelay);
    }
}
