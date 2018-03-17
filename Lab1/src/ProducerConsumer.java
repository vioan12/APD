import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerConsumer
{
    private final LinkedList<Integer> queue;
    private final int size;
    ProducerConsumer(int size)
    {
        queue = new LinkedList<Integer>();
        this.size = size;
    }
    public void add(int item)
            throws Exception
    {
        if(queue.size() < size){
            queue.add(item);
        }else {
            throw new Exception("Queue is full!!");
        }
    }
    public void remove()
            throws Exception
    {
        if(queue.size() > 0){
            queue.remove();
        }else {
            throw new Exception("Queue is empty!!");
        }
    }
    public boolean isFull()
    {
        boolean retVal;
        synchronized (queue){
            retVal = (queue.size() == size);
        }
        return retVal;
    }
    public boolean isEmpty()
    {
        boolean retVal;
        synchronized (queue){
            retVal = (queue.size() == 0);
        }
        return retVal;
    }
    public void consoleWrite(String threadType, long threadId)
    {
        Iterator<Integer> iterator=queue.iterator();
        System.out.print(threadType+"Thread"+threadId+": |");
        while(iterator.hasNext()){
            System.out.print(iterator.next() + "|");
        }
        System.out.println("");
    }
}
