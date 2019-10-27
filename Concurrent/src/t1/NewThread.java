package t1;

import java.lang.management.ThreadInfo;

public class NewThread implements Runnable {

    @Override
    public synchronized void run() {

        while(true){
            System.out.println(" this is the child thread..."+ Thread.currentThread().getName()+"   time: " + System.currentTimeMillis());
            try {
               // Thread.sleep(1000);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(" thread started...");
        }


    }

    public static void main(String[] args) {
        NewThread n = new NewThread();
        Thread t = new Thread(n); //create thread
        t.start(); //start thread, into ready status, not in run status

//        Thread t2 = new Thread( new NewThread()); //create thread
//        t2.start(); //start thread, into ready status, not in run status

        while(true){
            synchronized (n){
                System.out.println(" this is main thread..." + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                n.notifyAll();
            }

        }


    }
}
