import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ConsumerThread extends Thread
{
    private final ProducerConsumer producerConsumer;
    private final Lock lockQueue;
    private final Object isEmpty, isFull;
    ConsumerThread(ProducerConsumer producerConsumer, Lock lockQueue, Object isEmpty, Object isFull)
    {
        this.producerConsumer = producerConsumer;
        this.lockQueue = lockQueue;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
    }
    public void run()
    {
        while (true) {
            try {
                if(producerConsumer.isEmpty()){
                    isEmpty.wait();
                }
                lockQueue.lock();
                producerConsumer.remove();
                producerConsumer.consoleWrite("Consumer", this.getId());
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }finally {
                lockQueue.unlock();
            }
            isFull.notify();
            try {
                sleep(Constants.sleepTimeConsumer);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
