import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    private static Thread[] producerThread, consumerThread;
    private static Lock lock;
    private static Semaphore semaphoreFree, semaphoreFull;
    private static ProducerConsumer producerConsumer;

    public static void main(String[] args)
    {
        lock = new ReentrantLock();
        semaphoreFree = new Semaphore(Constants.queueSize);
        semaphoreFull = new Semaphore(Constants.queueSize);
        try {
            semaphoreFull.acquire(Constants.queueSize);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        producerConsumer = new ProducerConsumer(Constants.queueSize);
        producerThread = new Thread[Constants.numberOfProducer];
        consumerThread = new Thread[Constants.numberOfConsumers];
        for (int i = 0; i < producerThread.length; i++) {
            producerThread[i] = new ProducerThread(producerConsumer, lock, semaphoreFree, semaphoreFull);
        }
        for (int i = 0; i < consumerThread.length; i++) {
            consumerThread[i] = new ConsumerThread(producerConsumer, lock, semaphoreFree, semaphoreFull);
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
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
