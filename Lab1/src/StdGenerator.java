import java.util.Random;

/**
 * Created by ioan on 3/3/18.
 */
public class StdGenerator implements Generator
{
    private Random randomGenerator;
    private int maxValue;
    StdGenerator(int maxValue){
        randomGenerator = new Random();
        this.maxValue = maxValue;
    }
    @Override
    public int next() {
        int value = randomGenerator.nextInt(maxValue);
        return value;
    }
}
