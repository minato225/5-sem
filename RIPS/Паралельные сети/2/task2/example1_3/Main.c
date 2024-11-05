#include <windows.h>
#include <process.h>
#include <stdio.h>
#define p 2
double pi[p];
int n = 100000000;

double f_1(double x) {
	return x * x;
}

double f_2(double x) {
	return x * x * x;
}

double f_3(double x) {
	return x * x * x * x;
}

typedef double (*f)(double);

typedef struct func_params {
	f f;
	int k;
} func_params;

DWORD WINAPI ThreadFunction(void* pvParam)
{
	func_params* func = (func_params*)pvParam;

	int i, start;
	double h, sum, x;
	h = 1. / n;
	sum = 0.;
	start = func->k;
	for (i = start; i < n; i += p)
	{
		x = h * i;
		sum += func->f(x);
	}
	pi[func->k] = h * sum;
	return 0;
}

int main()
{
	f func_array[] = { &f_1, &f_2, &f_3 };

	for (int j = 0; j < 3; j++)
	{
		LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
		QueryPerformanceFrequency(&liFrequency);
		QueryPerformanceCounter(&liStartTime);

		HANDLE hThreads[p];
		int k;
		double sum;

		for (k = 0; k < p; ++k)
		{
			func_params tmp_func = { .f = func_array[j], .k = k };
			void* tmp = &tmp_func;

			hThreads[k] = (HANDLE)_beginthreadex(NULL, 0, ThreadFunction, tmp, 0, NULL);
			if (hThreads[k] == NULL) {
				printf("Create Thread %d Error=%d\n", k, GetLastError());
				return -1;
			}
		}

		WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
		for (k = 0; k < p; ++k)
			CloseHandle(hThreads[k]);

		sum = 0.;
		for (k = 0; k < p; ++k)
			sum += pi[k];
		printf("integral = %.16f\t", sum);

		QueryPerformanceCounter(&liFinishTime);
		double dElapsedTime = 1000. * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
		printf("Time = %.16f\n", dElapsedTime);
	}

	return 0;
}