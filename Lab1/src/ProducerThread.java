import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerThread extends Thread
{
    private ProducerConsumer producerConsumer;
    private static StdGenerator generator;
    private static Lock lockQueue;
    ProducerThread(ProducerConsumer producerConsumer, Lock lockQueue)
    {
        this.producerConsumer = producerConsumer;
        ProducerThread.lockQueue = lockQueue;
        generator = new StdGenerator(Constants.maxNumberGenerated);
    }
    public void run()
    {
        while (true){
            int newItem = generator.next();
            try {
                lockQueue.lock();
                producerConsumer.add(newItem);
                producerConsumer.consoleWrite("Producer", this.getId());
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }finally {
                lockQueue.unlock();
            }
            try {
                sleep(Constants.sleepTimeProducer);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
