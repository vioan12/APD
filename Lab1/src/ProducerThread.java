import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerThread extends Thread
{
    private ProducerConsumer producerConsumer;
    private static StdGenerator generator;
    private static Lock lockQueue;
    private static Semaphore semaphoreFree, semaphoreFull;
    ProducerThread(ProducerConsumer producerConsumer, Lock lockQueue, Semaphore semaphoreFree, Semaphore semaphoreFull)
    {
        this.producerConsumer = producerConsumer;
        ProducerThread.lockQueue = lockQueue;
        generator = new StdGenerator(Constants.maxNumberGenerated);
        ProducerThread.semaphoreFree = semaphoreFree;
        ProducerThread.semaphoreFull = semaphoreFull;
    }
    public void run()
    {
        while (true){
            int newItem = generator.next();
            semaphoreFree.acquireUninterruptibly(1);
            try {
                lockQueue.lock();
                producerConsumer.Add(newItem);
                producerConsumer.ConsoleWrite("Producer", this.getId());
            }catch (Exception exception){
                System.out.println(exception);
            }finally {
                lockQueue.unlock();
            }
            semaphoreFull.release(1);
            try {
                sleep(Constants.sleepTimeProducer);
            }catch (Exception exception){
                System.out.println(exception);
            }
        }
    }
}
