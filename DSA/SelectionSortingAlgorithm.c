#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

void selectionSortRecursive(int arr[], int n, int index) {
    if (index >= n - 1)
        return;

    int minIndex = index;
    int i;
    for (i = index + 1; i < n; i++) {
        if (arr[i] < arr[minIndex])
            minIndex = i;
    }

    if (minIndex != index)
        swap(&arr[minIndex], &arr[index]);

    selectionSortRecursive(arr, n, index + 1);
}

double getElapsedTime(LARGE_INTEGER start, LARGE_INTEGER end, LARGE_INTEGER frequency) {
    return ((double)(end.QuadPart - start.QuadPart) / frequency.QuadPart);
}

void printMemoryUsage(PROCESS_MEMORY_COUNTERS_EX pmc) {
    SIZE_T virtualMemoryUsed = pmc.PrivateUsage;
    SIZE_T peakWorkingSetSize = pmc.PeakWorkingSetSize;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peakWorkingSetSize);
}

void printCaseResult(const char* caseName, int index, double elapsedTime, PROCESS_MEMORY_COUNTERS_EX pmc) {
    printf("%s Started\n", caseName);
    if (index != -1) {
        printf("Element found at index %d.\n", index);
    } else {
        printf("Element not found.\n");
    }
    printf("Time elapsed: %.0lf nanoseconds\n", elapsedTime * 1e9);
    printMemoryUsage(pmc);
    printf("%s Ended\n\n", caseName);
}

int main() {
    int i, j;

    int sizes[] = {5000, 10000, 15000};
    int numSizes = sizeof(sizes) / sizeof(sizes[0]);

    for (i = 0; i < numSizes; i++) {
        int n = sizes[i];

        int arr[n];
        srand(time(0));
        printf("Case %d (Size: %d)\n", i + 1, n);

        PROCESS_MEMORY_COUNTERS_EX pmc;
        LARGE_INTEGER frequency, start, end;
        QueryPerformanceFrequency(&frequency);

        // Best Case
        printf("Best Case Started\n");
        for (j = 0; j < n; j++) {
            arr[j] = j;
        }
        QueryPerformanceCounter(&start);
        selectionSortRecursive(arr, n, -1);
        QueryPerformanceCounter(&end);
        double executionTime = getElapsedTime(start, end, frequency);
        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
        printCaseResult("Best Case", -1, executionTime, pmc);

        // Reset variables for the next case
        start.QuadPart = 0;
        end.QuadPart = 0;
        executionTime = 0;
        ZeroMemory(&pmc, sizeof(pmc));
        pmc.cb = sizeof(pmc);

        // Average Case
        printf("Average Case Started\n");
        for (j = 0; j < n; j++) {
            arr[j] = rand() % 100; // Generating random integers between 0 and 99
        }
        QueryPerformanceCounter(&start);
        selectionSortRecursive(arr, n, n / 2);
        QueryPerformanceCounter(&end);
        executionTime = getElapsedTime(start, end, frequency);
        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
        printCaseResult("Average Case", n / 2, executionTime, pmc);

        // Reset variables for the next case
        start.QuadPart = 0;
        end.QuadPart = 0;
        executionTime = 0;
        ZeroMemory(&pmc, sizeof(pmc));
        pmc.cb = sizeof(pmc);

        // Worst Case
        printf("Worst Case Started\n");
        for (j = n - 1; j >= 0; j--) {
            arr[n - j - 1] = j;
        }
        QueryPerformanceCounter(&start);
        selectionSortRecursive(arr, n, -1);
        QueryPerformanceCounter(&end);
        executionTime = getElapsedTime(start, end, frequency);
        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
        printCaseResult("Worst Case", -1, executionTime, pmc);

        // Reset variables for the next case
        start.QuadPart = 0;
        end.QuadPart = 0;
        executionTime = 0;
        ZeroMemory(&pmc, sizeof(pmc));
        pmc.cb = sizeof(pmc);

        printf("Case %d Completed\n\n", i + 1);
    }

    return 0;
}
