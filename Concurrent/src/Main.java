public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        Runtime r = Runtime.getRuntime();
        r.gc();
        long startMem = r.freeMemory();

        for(int i=0; i<100; i++){
            Thread t = new MyThread();
            t.start();
            System.out.println(" thread started:"+ t.getName());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }





        long orz = startMem - r.freeMemory();
        System.out.println(" orz:" + orz);

    }


}
class MyThread extends Thread{
    public void run(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}