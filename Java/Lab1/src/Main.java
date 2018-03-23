import mpi.*;
import static java.lang.StrictMath.floor;

public class Main
{
    public static void main(String[] args)
    {
        MPI.Init(args);
        int buffer[] = new int[2];
        double buffer2[] = new double[1];
        int myRank = MPI.COMM_WORLD.Rank();
        double sum = 0;
        if(myRank == Constants.MASTER){
            for(int k = 1; k < Constants.PROCESSORS_NUMBER; k ++) {
                buffer[0] = (int) floor((float) (k * Constants.myVect.length - k) / Constants.PROCESSORS_NUMBER) + 1;
                buffer[1] = (int) floor((float) ((k + 1) * Constants.myVect.length - (k + 1)) / Constants.PROCESSORS_NUMBER);
                MPI.COMM_WORLD.Send(buffer,0,2, MPI.INT, k,0);
            }
            int i,j;
            i = 0;
            j = (int) floor((float)(( Constants.myVect.length - 1)) / Constants.PROCESSORS_NUMBER);
            for(int k = i; k <= j; k++){
                sum = sum + Constants.myVect[k];
            }
            System.out.println("[MASTER]: The partial sum of " + i +" to " + j +" is " + sum);
        }
        if(myRank != Constants.MASTER) {
            MPI.COMM_WORLD.Recv(buffer, 0, 2, MPI.INT, 0, 0);
            int i,j;
            i = buffer[0];
            j = buffer[1];
            for(int k = i; k <= j; k++){
                sum = sum + Constants.myVect[k];
            }
            buffer2[0] = sum;
            System.out.println("[SLAVE"+ myRank +"]: The partial sum of " + i +" to " + j +" is " + buffer2[0]);
            MPI.COMM_WORLD.Send(buffer2,0,1, MPI.DOUBLE,0,0);
        }
        if(myRank == Constants.MASTER){
            for(int k = 1; k < Constants.PROCESSORS_NUMBER; k ++) {
                MPI.COMM_WORLD.Recv(buffer2, 0, 1, MPI.DOUBLE, k, 0);
                sum = sum + buffer2[0];
            }
            System.out.println("[MASTER]: The final sum is " + sum);
        }
        MPI.Finalize();
    }
}
