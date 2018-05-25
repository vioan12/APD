#include<stdio.h>
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <cstdlib>

const long int INF = 99999999;
const int N = 4;

__global__ void ComputeMinPath(int *d_Matrix) {
	int row = blockIdx.x;
	int col = threadIdx.x;

	for (int j = 0; j < N; j++) {

		d_Matrix[row * N + col] = d_Matrix[row * N + j] + d_Matrix[j * N + col] < d_Matrix[row * N + col] ? 
			d_Matrix[row * N + j] + d_Matrix[j * N + col]
		: d_Matrix[row * N + col];
	}
}

void RoyFloyd(int *h_Matrix, int N) {

	size_t size = N * N * sizeof(int);
	int *d_Matrix;

	cudaMalloc(&d_Matrix, size);
	cudaMemcpy(d_Matrix, h_Matrix, size, cudaMemcpyHostToDevice);

	for (int k = 0; k < N; k++) {
		ComputeMinPath<<<N, N>>>(d_Matrix);
	}

	cudaMemcpy(h_Matrix, d_Matrix, size, cudaMemcpyDeviceToHost);

	cudaFree(d_Matrix);
}

int main() {

	int matrix[N*N] = { 
		0,   5,  INF, 10, 
		INF,  0,  3,  INF,
		INF, INF, 0,   1 ,
		INF, INF, INF, 0
	};

	RoyFloyd(matrix, N);

	for (int i = 0; i < N * N; i++) {
		if(matrix[i] == INF)
		{
			printf("INF  ");
		}
		else {
			printf("%d  ", matrix[i]);
		}
		if ((i + 1) % N == 0) {
			printf("\n");
		}
	}

	getchar();
	return 0;
}