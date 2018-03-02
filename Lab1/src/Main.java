import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    static private Thread[] producerThread, consumerThread;
    static private Lock lock;
    static private ProducerConsumer producerConsumer;

    public static void main(String[] args) {
        lock = new ReentrantLock();
        producerConsumer = new ProducerConsumer(Constants.queueSize);
        producerThread = new Thread[Constants.numberOfProducer];
        consumerThread = new Thread[Constants.numberOfConsumers];
        for (int i = 0; i < producerThread.length; i++) {
            producerThread[i] = new ProducerThread(producerConsumer, lock);
        }
        for (int i = 0; i < consumerThread.length; i++) {
            consumerThread[i] = new ConsumerThread(producerConsumer, lock);
        }
        for (int i = 0; i < producerThread.length; i++) {
            producerThread[i].start();
        }
        for (int i = 0; i < consumerThread.length; i++) {
            consumerThread[i].start();
        }
        try {
            for (int i = 0; i < producerThread.length; i++) {
                producerThread[i].join();
            }
            for (int i = 0; i < consumerThread.length; i++) {
                consumerThread[i].join();
            }
        }catch (Exception exception){
            System.out.println(exception);
        }
    }
}
