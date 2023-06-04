#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <psapi.h>
#include <time.h>

int linearSearch(int arr[], int n, int target) {
    for (int i = 0; i < n; i++) {
        if (arr[i] == target) {
            return i;  // Return the index where the target element is found
        }
    }
    return -1;  // Return -1 if the target element is not found
}

long long getCurrentTime() {
    LARGE_INTEGER frequency;
    QueryPerformanceFrequency(&frequency);

    LARGE_INTEGER start;
    QueryPerformanceCounter(&start);

    // Calculate time in nanoseconds
    long long nanoseconds = start.QuadPart * 1000000000LL / frequency.QuadPart;

    return nanoseconds;
}

void printMemoryUsage() {
    HANDLE process = GetCurrentProcess();
    PROCESS_MEMORY_COUNTERS_EX pmc;
    if (GetProcessMemoryInfo(process, (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc))) {
        SIZE_T virtualMemoryUsed = pmc.PrivateUsage;
        printf("Memory Usage: %zu bytes\n", virtualMemoryUsed);
    }
}

int main() {
    int n = 5000; // Size of the array
    int* arr = (int*)malloc(n * sizeof(int)); // Dynamically allocate memory for the array

    // Generate array elements
    srand(time(NULL));
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 10000001; // Generating random numbers between 0 and 10,000,000
    }

    // Best Case
    printf("Best Case:\n");
    int target = arr[0]; // Target element at the beginning of the array

    long long start = getCurrentTime(); // Start the clock

    int result = linearSearch(arr, n, target); // Perform linear search

    long long end = getCurrentTime(); // Stop the clock

    // Print the result and execution time
    if (result == -1) {
        printf("Element not found.\n");
    } else {
        printf("Element found at index %d.\n", result);
    }
    long long execution_time = end - start;
    printf("Execution time: %lld nanoseconds.\n", execution_time);
    printMemoryUsage();

    // Average Case
    printf("\nAverage Case:\n");
    target = arr[n / 2]; // Target element at the middle of the array

    start = getCurrentTime(); // Start the clock

    result = linearSearch(arr, n, target); // Perform linear search

    end = getCurrentTime(); // Stop the clock

    // Print the result and execution time
    if (result == -1) {
        printf("Element not found.\n");
    } else {
        printf("Element found at index %d.\n", result);
    }
    execution_time = end - start;
    printf("Execution time: %lld nanoseconds.\n", execution_time);
    printMemoryUsage();

    // Worst Case
    printf("\nWorst Case:\n");
    target = n + 1; // Target element not present in the array

    start = getCurrentTime(); // Start the clock

    result = linearSearch(arr, n, target); // Perform linear search

    end = getCurrentTime(); // Stop the clock

    // Print the result and execution time
    if (result == -1) {
        printf("Element not found.\n");
    } else {
        printf("Element found at index %d.\n", result);
    }
    execution_time = end - start;
    printf("Execution time: %lld nanoseconds.\n", execution_time);
    printMemoryUsage();

    free(arr); // Free dynamically allocated memory

    return 0;
}
