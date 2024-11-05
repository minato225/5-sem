// 1_4.cpp: определяет точку входа для консольного приложения.
//
#include "Windows.h"
#include <stdio.h>

#define p 1// количество дочерних потоков
int n = 1000000;
// тип параметра, передаваемого функции потока
struct SThreadParam
{
	int k;
	double sum;
};

unsigned int __stdcall ThreadFunction(void* pvParam)
{
	struct SThreadParam* param = (struct SThreadParam*)pvParam;
	int i, start;
	double h, sum, x;
	h = 1. / n;
	sum = 0.;
	start = param->k;
	for (i = start; i < n; i += p)
	{
		x = h * i;
		sum += 4. / (1. + x * x);
	}
	// к глобальной переменной не следует часто обращаться
	param->sum = h * sum;
	return 0;
}

int main()
{
	HANDLE hThreads[p]; // массив дескрипторов потоков
						// массив параметров потоковых функций
	struct SThreadParam params[p];
	int k;
	double sum;
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);

	// создание дочерних потоков
	for (k = 0; k < p; ++k)
	{
		params[k].k = k;
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0,
			ThreadFunction, (void*)&(params[k]), 0, NULL);
		if (hThreads[k] == NULL) // обработка ошибки
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}
	// ожидание завершения дочерних потоков
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k)
		CloseHandle(hThreads[k]);
	sum = 0.;
	for (k = 0; k < p; ++k)
		sum += params[k].sum;

	QueryPerformanceCounter(&liFinishTime);

	printf("PI = %.16f\n", sum);

	double dElapsedTime = 1000. * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
	printf("Time = %f\n", dElapsedTime);
	return 0;
}


