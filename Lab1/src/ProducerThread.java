import java.util.concurrent.locks.Lock;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerThread extends Thread
{
    private final ProducerConsumer producerConsumer;
    private final StdGenerator generator;
    private final Lock lockQueue;
    private final Object isEmpty, isFull;
    ProducerThread(ProducerConsumer producerConsumer, Lock lockQueue, Object isEmpty, Object isFull)
    {
        this.producerConsumer = producerConsumer;
        this.lockQueue = lockQueue;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
        generator = new StdGenerator(Constants.maxNumberGenerated);
    }
    public void run()
    {
        while (true){
            int newItem = generator.next();
            try {
                if(producerConsumer.isFull()){
                    isFull.wait();
                }
                lockQueue.lock();
                producerConsumer.add(newItem);
                producerConsumer.consoleWrite("Producer", this.getId());
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }finally {
                lockQueue.unlock();
            }
            isEmpty.notify();
            try {
                sleep(Constants.sleepTimeProducer);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
