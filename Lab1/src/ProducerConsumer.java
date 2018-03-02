import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ioan on 3/3/18.
 */
public class ProducerConsumer
{
    private LinkedList<Integer> queue;
    private int size;
    ProducerConsumer(int size)
    {
        queue = new LinkedList<Integer>();
        this.size = size;
    }
    public void Add(int item)
            throws Exception
    {
        if(queue.size() < size){
            queue.add(item);
        }else {
            throw new Exception("Queue is full!!");
        }
    }
    public void Remove()
            throws Exception
    {
        if(queue.size() > 0){
            queue.remove();
        }else {
            throw new Exception("Queue is empty!!");
        }
    }
    public void ConsoleWrite()
    {
        Iterator<Integer> iterator=queue.iterator();
        System.out.print("|");
        while(iterator.hasNext()){
            System.out.print(iterator.next() + "|");
        }
        System.out.println("");
    }
}
