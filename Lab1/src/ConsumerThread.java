import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ConsumerThread extends Thread
{
    private ProducerConsumer producerConsumer;
    private static Lock lockQueue;
    ConsumerThread(ProducerConsumer producerConsumer, Lock lockQueue)
    {
        this.producerConsumer = producerConsumer;
        ConsumerThread.lockQueue = lockQueue;
    }
    public void run()
    {
        while (true) {
            try {
                lockQueue.lock();
                producerConsumer.remove();
                producerConsumer.consoleWrite("Consumer", this.getId());
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }finally {
                lockQueue.unlock();
            }
            try {
                sleep(Constants.sleepTimeConsumer);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
