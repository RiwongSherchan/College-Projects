#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
  
    for (int j = low; j <= high - 1; j++) {
        if (arr[j] <= pivot) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

void printMemoryUsage(PROCESS_MEMORY_COUNTERS_EX pmc) {
    SIZE_T virtualMemoryUsed = pmc.PrivateUsage;
    SIZE_T peakWorkingSetSize = pmc.PeakWorkingSetSize;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peakWorkingSetSize);
}

int main() {
    int sizes[] = {5000, 10000, 15000};
    int numSizes = sizeof(sizes) / sizeof(sizes[0]);
    int numCases = 3; // Number of cases to test

    LARGE_INTEGER frequency, start, end;
    double elapsedSeconds;
    PROCESS_MEMORY_COUNTERS_EX pmc; // Declaration of the pmc variable

    for (int i = 0; i < numSizes; i++) {
        int size = sizes[i];
        printf("Array size: %d\n", size);

        int numbers[size];
        srand(time(NULL));
        for (int k = 0; k < size; k++) {
            numbers[k] = rand() % 100;  // Generates random numbers between 0 and 99
        }

        for (int j = 1; j <= numCases; j++) {
            printf("Case %d (Size: %d)\n", j, size);

            // Reset variables for the case
            start.QuadPart = 0;
            end.QuadPart = 0;
            elapsedSeconds = 0;
            ZeroMemory(&pmc, sizeof(pmc));
            pmc.cb = sizeof(pmc);

            printf("Best Case Started\n");
            QueryPerformanceFrequency(&frequency);
            QueryPerformanceCounter(&start);
            quick_sort(numbers, 0, size - 1);
            QueryPerformanceCounter(&end);
            elapsedSeconds = (end.QuadPart - start.QuadPart) / (double)frequency.QuadPart;
            GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
            printMemoryUsage(pmc);
            printf("Time elapsed: %lld nanoseconds\n", (long long)((elapsedSeconds * 1e9)));

            // Reset variables for the case
            start.QuadPart = 0;
            end.QuadPart = 0;
            elapsedSeconds = 0;
            ZeroMemory(&pmc, sizeof(pmc));
            pmc.cb = sizeof(pmc);

            printf("Average Case Started\n");
            QueryPerformanceFrequency(&frequency);
            QueryPerformanceCounter(&start);
            quick_sort(numbers, 0, size - 1);
            QueryPerformanceCounter(&end);
            elapsedSeconds = (end.QuadPart - start.QuadPart) / (double)frequency.QuadPart;
            GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
            printMemoryUsage(pmc);
            printf("Time elapsed: %lld nanoseconds\n", (long long)((elapsedSeconds * 1e9)));

            // Reset variables for the case
            start.QuadPart = 0;
            end.QuadPart = 0;
            elapsedSeconds = 0;
            ZeroMemory(&pmc, sizeof(pmc));
            pmc.cb = sizeof(pmc);

            printf("Worst Case Started\n");
            QueryPerformanceFrequency(&frequency);
            QueryPerformanceCounter(&start);
            quick_sort(numbers, 0, size - 1);
            QueryPerformanceCounter(&end);
            elapsedSeconds = (end.QuadPart - start.QuadPart) / (double)frequency.QuadPart;
            GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
            printMemoryUsage(pmc);
            printf("Time elapsed: %lld nanoseconds\n", (long long)((elapsedSeconds * 1e9)));

            printf("Case %d Completed\n\n", j);
        }
    }

    return 0;
}
