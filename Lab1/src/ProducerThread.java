import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerThread extends Thread
{
    private ProducerConsumer producerConsumer;
    private Lock lockQueue;
    private StdGenerator generator;
    ProducerThread(ProducerConsumer producerConsumer, Lock lockQueue)
    {
        this.producerConsumer = producerConsumer;
        this.lockQueue = lockQueue;
        generator = new StdGenerator(Constants.maxNumberGenerated);
    }
    public void run()
    {
        while (true){
            int newItem = generator.next();
            try {
                lockQueue.lock();
                producerConsumer.Add(newItem);
                producerConsumer.ConsoleWrite();
            }catch (Exception exception){
                System.out.println(exception);
            }finally {
                lockQueue.unlock();
            }
            try {
                sleep(Constants.sleepTimeProducer);
            }catch (Exception exception){
                System.out.println(exception);
            }
        }
    }
}
