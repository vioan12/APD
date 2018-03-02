import java.util.Random;

/**
 * Created by ioan on 3/3/18.
 */
public class StdGenerator implements Generator
{
    private Random randomGenerator;
    private int max;

    StdGenerator(int valueofmax){
        randomGenerator= new Random();
        max=valueofmax;
    }
    @Override
    public int next() {
        int x=randomGenerator.nextInt(max);
        return x;
    }
}
