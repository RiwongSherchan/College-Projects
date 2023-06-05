#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

// Swaps two integers
void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// Partitions the array for QuickSort
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

// QuickSort algorithm
void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

// Prints memory usage information
void printMemoryUsage(PROCESS_MEMORY_COUNTERS_EX pmc) {
    SIZE_T virtualMemoryUsed = pmc.PrivateUsage;
    SIZE_T peakWorkingSetSize = pmc.PeakWorkingSetSize;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peakWorkingSetSize);
}

// Prints the elements of an array
void printArray(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main() {
    int sizes[] = {10, 20, 30}; // Sizes of the arrays to test
    int numSizes = sizeof(sizes) / sizeof(sizes[0]); // Number of sizes
    int numCases = 3; // Number of cases to test

    LARGE_INTEGER frequency, start, end;
    double elapsedSeconds;
    PROCESS_MEMORY_COUNTERS_EX pmc; // Declaration of the pmc variable

    for (int i = 0; i < numSizes; i++) {
        int size = sizes[i]; // Current size to test
        printf("Array size: %d\n", size);

        for (int j = 1; j <= numCases; j++) {
            printf("Case %d (Size: %d)\n", j, size);

            int numbers[size]; // Array to store random numbers
            srand(time(NULL));
            for (int k = 0; k < size; k++) {
                numbers[k] = rand() % 81;  // Generates random numbers between 0 and 80
            }

            printf("Original Array: ");
            printArray(numbers, size);

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

            printf("Sorted Array: ");
            printArray(numbers, size);

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

            printf("Sorted Array: ");
            printArray(numbers, size);

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

            printf("Sorted Array: ");
            printArray(numbers, size);

            printf("Case %d Completed\n\n", j);
        }
    }

    return 0;
}
