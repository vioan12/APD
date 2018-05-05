import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class ReadFile
{
    private String fileName;
    private FileReader inputReader;
    private BufferedReader bufferReader;
    ReadFile(String fileName)
            throws Exception
    {
        this.fileName = fileName;
    }
    private void initialization()
            throws Exception
    {
        inputReader = new FileReader(fileName);
        bufferReader = new BufferedReader(inputReader);
    }
    private void close()
            throws Exception
    {
        inputReader.close();
        bufferReader.close();
    }
    public LinkedList<String> read()
            throws Exception
    {
        initialization();
        LinkedList<String> allLinesList = new LinkedList<String>();
        String line;
        while((line = bufferReader.readLine()) != null){
            allLinesList.add(line);
        }
        close();
        return allLinesList;
    }
}
