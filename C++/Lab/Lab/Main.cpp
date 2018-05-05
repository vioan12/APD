#include<limits.h>
#include"mpi.h"
#include<stdio.h>
#define SIZE 5
#define INF INT_MAX

using namespace std;

int main(int argc, char *argv[])
{
	int myRank, buffer[3];
	int matrixGraph[SIZE][SIZE] = {
		{ 0, 2, INF, 10, INF },
		{ 2, 0, 3, INF, INF },
		{ INF, 3, 0, 1, 8 },
		{ 10, INF, 1, 0, INF },
		{ INF, INF, 8, INF, 0 } };
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &myRank);
	for (int k = 0; k < SIZE; k++) {
		MPI_Barrier(MPI_COMM_WORLD);
		//matrixGraph[buffer[1]][buffer[2]] = buffer[0];
		for (int j = 0; j < SIZE; j++) {
			if (matrixGraph[myRank][k] != INF && matrixGraph[k][j] != INF) {
				if (matrixGraph[myRank][j] > matrixGraph[myRank][k] + matrixGraph[k][j]) {
					matrixGraph[myRank][j] = matrixGraph[myRank][k] + matrixGraph[k][j];
					buffer[0] = matrixGraph[myRank][j];
					buffer[1] = myRank;
					buffer[2] = j;
					printf("\n[PROCESS %d ]: Start Bcast", myRank);
					MPI_Bcast(buffer, 3, MPI_INT, myRank, MPI_COMM_WORLD);
					printf("\n[PROCESS %d ]: End Bcast with %d (%d,%d)", myRank, buffer[0], buffer[1], buffer[2]);
				}
			}
		}
	}
	MPI_Barrier(MPI_COMM_WORLD);
	printf("\n[PROCESS %d ]: ", myRank);
	for (int i = 0; i < SIZE; i++) {
		printf("\n");
		for (int j = 0; j < SIZE; j++) {
			if (matrixGraph[i][j] != INF) {
				printf("%d ", matrixGraph[i][j]);
			}
			else {
				printf("INF ");
			}
		}
	}

	MPI_Finalize();
	return 0;
}