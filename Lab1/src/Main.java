import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    private static Thread[] producerThread, consumerThread;
    private static Lock lock;
    private static ProducerConsumer producerConsumer;
    public static void main(String[] args)
    {
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
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
