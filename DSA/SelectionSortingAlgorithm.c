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

SIZE_T getMemoryUsage() {
    PROCESS_MEMORY_COUNTERS_EX pmc;
    GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
    return pmc.PrivateUsage;
}

void checkExecutionTime(void (*sortingAlgorithm)(int[], int, int), int arr[], int n, int index) {
    LARGE_INTEGER frequency, start, end;
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);
    sortingAlgorithm(arr, n, index);
    QueryPerformanceCounter(&end);
    double executionTime = getElapsedTime(start, end, frequency);
    printf("Execution time: %.6lf seconds\n", executionTime);
}

void checkMemoryUsage() {
    SIZE_T memoryUsage = getMemoryUsage();
    printf("Memory usage: %llu bytes\n", memoryUsage);
}

int main() {
    int i, n;

    printf("Enter the size of the array: ");
    scanf("%d", &n);

    int arr[n];
    srand(time(0));
    printf("Generated random integers: ");
    for (i = 0; i < n; i++) {
        arr[i] = rand() % 100; // Generating random integers between 0 and 99
        printf("%d ", arr[i]);
    }
    printf("\n");

    printf("Sorting in progress...\n");
    checkExecutionTime(selectionSortRecursive, arr, n, 0);

    printf("Sorted array in ascending order: ");
    for (i = 0; i < n; i++)
        printf("%d ", arr[i]);
    printf("\n");

    printf("Checking memory usage...\n");
    checkMemoryUsage();

    return 0;
}
