import mpi.*;

public class Main
{
    private static int matrixGraph[][];
    public static void main(String[] args)
    {
        try {
            ReadFile readFile = new ReadFile(Constants.FileNameGraph);
            ConverterMatrix converterMatrix = new ConverterMatrix();
            matrixGraph = converterMatrix.convert(readFile.read());
            int myRank, size, buffer[];
            buffer = new int[3];
            MPI.Init(args);
            size = MPI.COMM_WORLD.Size();
            myRank = MPI.COMM_WORLD.Rank();
            for (int k = 0; k < matrixGraph.length; k++) {
                MPI.COMM_WORLD.Barrier();
                for (int j = 0; j < matrixGraph.length; j++) {
                    if (matrixGraph[myRank][k] != Constants.INFINITE && matrixGraph[k][j] != Constants.INFINITE) {
                        if (matrixGraph[myRank][j] > matrixGraph[myRank][k] + matrixGraph[k][j]) {
                            matrixGraph[myRank][j] = matrixGraph[myRank][k] + matrixGraph[k][j];
                            buffer[0] = matrixGraph[myRank][j];
                            buffer[1] = myRank;
                            buffer[2] = j;
                            System.out.println("\n[PROCESS" + myRank + "]: Start Bcast");
                            MPI.COMM_WORLD.Bcast(buffer, 0, 3, MPI.INT, myRank);
                            System.out.println("\n[PROCESS" + myRank + "]: End Bcast with " + buffer[0]);
                        }
                    }
                }
            }
            if(myRank == size-1) {
                System.out.println("\n[PROCESS" + myRank + "]: ");
                for (int i = 0; i < matrixGraph.length; i++) {
                    System.out.println();
                    for (int j = 0; j < matrixGraph.length; j++) {
                        if (matrixGraph[i][j] != Constants.INFINITE) {
                            System.out.print(matrixGraph[i][j] + " ");
                        } else {
                            System.out.print("INF ");
                        }
                    }
                }
            }
            MPI.Finalize();
        }catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
