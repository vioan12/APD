import java.util.LinkedList;

public class ConverterMatrix
{
    public int[][] convert(LinkedList<String> lines)
            throws Exception
    {
        int matrix[][], size;
        String splitLine[];
        size = Integer.parseInt(lines.get(0));
        matrix = new int[size][size];
        for (int i = 0; i < matrix.length; i++) {
            splitLine = lines.get(i+1).split(" ");
            for (int j = 0; j < matrix.length; j++) {
                if(splitLine[j].equals("INF")) {
                    matrix[i][j] = Constants.INFINITE;
                }else {
                    matrix[i][j] = Integer.parseInt(splitLine[j]);
                }
            }
        }
        return matrix;
    }
}
