import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerThread extends Thread
{
    private final ProducerConsumer producerConsumer;
    private final StdGenerator generator;
    private final Object isEmpty, isFull;
    ProducerThread(ProducerConsumer producerConsumer, Object isEmpty, Object isFull)
    {
        this.producerConsumer = producerConsumer;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
        generator = new StdGenerator(Constants.maxNumberGenerated);
    }
    public void run()
    {
        while (true){
            int newItem = generator.next();
            try {
                while (producerConsumer.isFull()){
                    synchronized (isFull) {
                        isFull.wait();
                    }
                }
                synchronized (producerConsumer) {
                    producerConsumer.add(newItem);
                    producerConsumer.consoleWrite("Producer", this.getId());
                }
            }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
            synchronized (isEmpty) {
                isEmpty.notify();
            }
            try {
                sleep(Constants.sleepTimeProducer);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
