package t1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

import util.ConcurrentUtils;

import static util.ConcurrentUtils.sleep;

public class SynchronizeLockExample {
    int count = 0;
    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        SynchronizeLockExample mainThread = new SynchronizeLockExample();
        mainThread.test7();
    }

    void increment() {
        count = count + 1;
    }

    synchronized void incrementSync() {
        count = count + 1;
    }

    void incrementSync2() {
        synchronized (this) {
            count = count + 1;
        }
    }

    void incrementLock() {
        lock.lock();
        try {
            count = count + 1;
        } finally {
            lock.unlock();
        }
    }



    public void test1() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::increment));

        stop(executor);
        System.out.println("count result is :" + count);
    }

    public void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::incrementSync));

        stop(executor);
        System.out.println("count result is :" + count);
    }

    //use ReentrantLock
    public void test3() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::incrementSync2));

        stop(executor);
        System.out.println("count result is :" + count);
    }

    //lock details
    public void test4() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            lock.lock();

            try {
                sleep(1);
            } finally {
                lock.unlock();
            }


        });

        //submit 2nd thread
        executor.submit(() -> {
            System.out.println("Locked:  " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());

            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);

        });
        stop(executor);
        System.out.println("count result is :" + count);
    }

    private void stop(ExecutorService executor) {
        ConcurrentUtils.stop(executor);
    }

    //ReadWriteLock
    public void test5(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit( () -> {
            lock.writeLock().lock();

            try{
                sleep(1);
                map.put("foo", "bar");
                System.out.println(" write thread called...");
            }finally {
                lock.writeLock().unlock();
            }

        } );
        //sleep(2);

        Runnable readTask = () -> {
            lock.readLock().lock();

            try{

                System.out.println(map.get("foo"));
                System.out.println(" read thread called...");
            }finally{
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);
        stop(executor);
    }

    //stampedlock
    public void test6(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        executor.submit( () -> {
           long stamp = lock.writeLock();

           try{
               sleep(1);
               map.put("foo", "bar");
           }finally {
               lock.unlockWrite(stamp);
           }
        });

        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try{
                System.out.println(map.get("foo"));
                sleep(1);
            }finally {
                lock.unlockRead(stamp);
            }

        };

        executor.submit(readTask);
        executor.submit(readTask);
        stop(executor);
    }

    public void test7(){
        ExecutorService executors = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try{
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if(permit){
                    System.out.println("Semaphore acquired");
                    sleep(5);
                }else{
                    System.out.println("could not acquire semaphore");
                }
            }catch (InterruptedException e){
                throw new IllegalStateException(e);
            }finally{
                if(permit){
                    semaphore.release();
                }
            }

        };

        IntStream.range(0, 10)
                .forEach( i -> executors.submit(longRunningTask));

        stop(executors);
    }
}
