import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ConsumerThread extends Thread
{
    private ProducerConsumer producerConsumer;
    private Lock lockQueue;
    ConsumerThread(ProducerConsumer producerConsumer, Lock lockQueue)
    {
        this.producerConsumer = producerConsumer;
        this.lockQueue = lockQueue;
    }
    public void run()
    {
        while (true) {
            try {
                lockQueue.lock();
                producerConsumer.Remove();
                producerConsumer.ConsoleWrite();
            }catch (Exception exception){
                System.out.println(exception);
            }finally {
                lockQueue.unlock();
            }
            try {
                sleep(Constants.sleepTimeConsumer);
            }catch (Exception exception){
                System.out.println(exception);
            }
        }
    }
}
