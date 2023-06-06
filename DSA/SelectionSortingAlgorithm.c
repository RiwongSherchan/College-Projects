#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

// Function to swap two integers
void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// Recursive implementation of selection sort
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

// Calculate the elapsed time in nanoseconds
double getElapsedTime(LARGE_INTEGER start, LARGE_INTEGER end, LARGE_INTEGER frequency) {
    return ((double)(end.QuadPart - start.QuadPart) * 1e9 / frequency.QuadPart);
}

// Print the memory usage of the process
void printMemoryUsage() {
    PROCESS_MEMORY_COUNTERS_EX pmc;
    GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
    SIZE_T virtualMemoryUsed = pmc.WorkingSetSize;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
}

// Print the result of each test case
void printCaseResult(const char* caseName, int foundIndex, int expectedIndex, double elapsedTime) {
    printf("%s Started\n", caseName);
    if (foundIndex != -1) {
        printf("Element found at index %d.\n", foundIndex);
    } else {
        printf("Element not found.\n");
    }
    printf("Expected index: %d\n", expectedIndex);
    printf("Time elapsed: %.0lf nanoseconds\n", elapsedTime);
    printf("%s Ended\n\n", caseName);
    printMemoryUsage();
}

// Print the elements of an array
void printArray(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main() {
    int i, j;

    int sizes[] = {10, 20, 30};
    int numSizes = sizeof(sizes) / sizeof(sizes[0]);

    for (i = 0; i < numSizes; i++) {
        int n = sizes[i];

        int arr[n];
        srand(time(0) + i);  // Use different seed for each test case
        printf("Case %d (Size: %d)\n", i + 1, n);

        // Best Case
        printf("Best Case:\n");
        printf("Original Array: ");
        for (j = 0; j < n; j++) {
            arr[j] = j;
            printf("%d ", arr[j]);
        }
        printf("\n");

        // Sort the original array
        LARGE_INTEGER start, end, frequency;
        double executionTime;
        QueryPerformanceFrequency(&frequency);
        QueryPerformanceCounter(&start);
        selectionSortRecursive(arr, n, 0);
        QueryPerformanceCounter(&end);
        executionTime = getElapsedTime(start, end, frequency);
        printf("Sorted Array: ");
        printArray(arr, n);
        printCaseResult("Sorting (Best Case)", n - 1, n - 1, executionTime);

        printf("\n");

        // Average Case
        printf("Average Case:\n");
        printf("Original Array: ");
        for (j = 0; j < n; j++) {
            arr[j] = rand() % 81; // Generating random integers between 0 and 80
            printf("%d ", arr[j]);
        }
        printf("\n");

        // Sort the original array
        QueryPerformanceCounter(&start);
        selectionSortRecursive(arr, n, n / 2);
        QueryPerformanceCounter(&end);
        executionTime = getElapsedTime(start, end, frequency);
        printf("Sorted Array: ");
        printArray(arr, n);
        printCaseResult("Sorting (Average Case)", n / 2, n / 2, executionTime);

        printf("\n");

        // Worst Case
        printf("Worst Case:\n");
        printf("Original Array: ");
        for (j = n - 1; j >= 0; j--) {
            arr[n - j - 1] = j;
            printf("%d ", arr[n - j - 1]);
        }
        printf("\n");

        // Sort the original array
        QueryPerformanceCounter(&start);
        selectionSortRecursive(arr, n, -1);
        QueryPerformanceCounter(&end);
        executionTime = getElapsedTime(start, end, frequency);
        printf("Sorted Array: ");
        printArray(arr, n);
        printCaseResult("Sorting (Worst Case)", -1, -1, executionTime);
  
        printf("\n");
    
        printf("Case %d Completed\n\n", i + 1);
    }  
 
    return 0;
}
