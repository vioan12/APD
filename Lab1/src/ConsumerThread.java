import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ConsumerThread extends Thread
{
    private final ProducerConsumer producerConsumer;
    private final Object isEmpty, isFull;
    ConsumerThread(ProducerConsumer producerConsumer, Object isEmpty, Object isFull)
    {
        this.producerConsumer = producerConsumer;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
    }
    public void run()
    {
        while (true) {
            try {
                while (producerConsumer.isEmpty()){
                    synchronized (isEmpty) {
                        isEmpty.wait();
                    }
                }
                synchronized (producerConsumer) {
                    producerConsumer.remove();
                    producerConsumer.consoleWrite("Consumer", this.getId());
                }
            }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
            synchronized (isFull) {
                isFull.notify();
            }
            try {
                sleep(Constants.sleepTimeConsumer);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
