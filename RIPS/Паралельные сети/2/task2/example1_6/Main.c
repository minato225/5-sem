// 1_6.cpp: ���������� ����� ����� ��� ����������� ����������.
//
#include "Windows.h"
//#include "stdafx.h"
#include <stdio.h>

#define p 1 // ���������� �������� �������
int n = 1000000;
double pi = 0.; // ��������� ����������������� ������

CRITICAL_SECTION cs;

unsigned int __stdcall ThreadFunction(void* pvParam)
{
	int nParam = (int)pvParam;
	int i, start;
	double h, sum, x;
	h = 1. / n;
	sum = 0.;
	start = nParam;
	for (i = start; i < n; i += p)
	{
		x = h * i;
		sum += 4. / (1. + x * x);
	}
	// ����������� ������
	EnterCriticalSection(&cs);
	pi += h * sum;
	LeaveCriticalSection(&cs);
	return 0;
}

int main()
{
	HANDLE hThreads[p];
	int k;
	InitializeCriticalSection(&cs);
	LARGE_INTEGER liFrequency, liStartTime, liFinishTime;
	QueryPerformanceFrequency(&liFrequency);
	QueryPerformanceCounter(&liStartTime);

	// �������� �������� �������
	for (k = 0; k < p; ++k)
	{
		hThreads[k] = (HANDLE)_beginthreadex(NULL, 0,
			ThreadFunction, (void*)k, 0, NULL);
		if (hThreads[k] == NULL) // ��������� ������
		{
			printf("Create Thread %d Error=%d\n", k, GetLastError());
			return -1;
		}
	}
	// �������� ���������� �������� �������
	WaitForMultipleObjects(p, hThreads, TRUE, INFINITE);
	for (k = 0; k < p; ++k)
		CloseHandle(hThreads[k]);
	// ������������ ��������, ������� ����������� �������
	DeleteCriticalSection(&cs);
	QueryPerformanceCounter(&liFinishTime);

	printf("PI = %.16f\n", pi);
	double dElapsedTime = 1000. * (liFinishTime.QuadPart - liStartTime.QuadPart) / liFrequency.QuadPart;
	printf("Time = %f\n", dElapsedTime);

	return 0;
}

