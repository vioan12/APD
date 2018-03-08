import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ConsumerThread extends Thread
{
    private ProducerConsumer producerConsumer;
    private static Lock lockQueue;
    private static Semaphore semaphoreFree, semaphoreFull;
    ConsumerThread(ProducerConsumer producerConsumer, Lock lockQueue, Semaphore semaphoreFree, Semaphore semaphoreFull)
    {
        this.producerConsumer = producerConsumer;
        ConsumerThread.lockQueue = lockQueue;
        ConsumerThread.semaphoreFree = semaphoreFree;
        ConsumerThread.semaphoreFull = semaphoreFull;
    }
    public void run()
    {
        while (true) {
            semaphoreFull.acquireUninterruptibly(1);
            try {
                lockQueue.lock();
                producerConsumer.Remove();
                producerConsumer.ConsoleWrite("Consumer", this.getId());
            }catch (Exception exception){
                System.out.println(exception);
            }finally {
                lockQueue.unlock();
            }
            semaphoreFree.release(1);
            try {
                sleep(Constants.sleepTimeConsumer);
            }catch (Exception exception){
                System.out.println(exception);
            }
        }
    }
}
