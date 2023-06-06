<<<<<<< HEAD
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

// Function to measure time using QueryPerformanceCounter
long long measureTime(void (*func)(int[], int), int arr[], int size) {
    LARGE_INTEGER frequency, start, end;
    long long elapsed_time;

    // Get the frequency of the performance counter
    QueryPerformanceFrequency(&frequency);

    // Get the starting time
    QueryPerformanceCounter(&start);

    // Call the sorting function
    func(arr, size);

    // Get the ending time
    QueryPerformanceCounter(&end);

    // Calculate the elapsed time in nanoseconds
    elapsed_time = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;

    // Return the elapsed time
    return elapsed_time;
}

// Function to measure memory using GetProcessMemoryInfo
void measureMemory(SIZE_T* peak_memory) {
    PROCESS_MEMORY_COUNTERS_EX pmc;
    ZeroMemory(&pmc, sizeof(pmc));
    pmc.cb = sizeof(pmc);

    // Get the process memory information
    GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));

    // Store the peak working set size in the provided variable
    *peak_memory = pmc.PeakWorkingSetSize;
}

// Function to print memory usage
void printMemoryUsage(SIZE_T peak_memory) {
    SIZE_T virtualMemoryUsed = peak_memory;

    // Print the memory usage in bytes
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);

    // Print the peak working set size in bytes
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peak_memory);
}

// Function to merge two sorted subarrays
void merge(int arr[], int left[], int left_size, int right[], int right_size) {
    int i = 0, j = 0, k = 0;

    // Merge the two subarrays while maintaining the sorted order
    while (i < left_size && j < right_size) {
        if (left[i] <= right[j]) {
            arr[k] = left[i];
            i++;
        } else {
            arr[k] = right[j];
            j++;
        }
        k++;
    }

    // Copy the remaining elements of the left subarray, if any
    while (i < left_size) {
        arr[k] = left[i];
        i++;
        k++;
    }

    // Copy the remaining elements of the right subarray, if any
    while (j < right_size) {
        arr[k] = right[j];
        j++;
        k++;
    }
}

// Recursive function to perform merge sort
void merge_sort(int arr[], int size) {
    // Base case: if the size is 1 or less, the array is already sorted
    if (size <= 1)
        return;

    // Divide the array into two halves
    int mid = size / 2;
    int left[mid];
    int right[size - mid];

    // Copy the elements into the left and right subarrays
    for (int i = 0; i < mid; i++)
        left[i] = arr[i];
    for (int i = mid; i < size; i++)
        right[i - mid] = arr[i];
   
    // Recursively sort the left and right subarrays
    merge_sort(left, mid);
    merge_sort(right, size - mid);
 
    // Merge the sorted subarrays
    merge(arr, left, mid, right, size - mid);
}
 
int main() {
    int sizes[] = {10, 25, 60};
    int num_sizes = sizeof(sizes) / sizeof(sizes[0]);

    // Loop over the different array sizes
    for (int i = 0; i < num_sizes; i++) {
        int size = sizes[i];
        printf("Case %d (Size: %d)\n", i + 1, size);

        // Create an array of the given size
        int arr[size];

        // Seed the random number generator with the current time
        srand(time(0));

        printf("Original Array: ");
        // Generate random numbers between 0 and 80 (inclusive) and print the array
        for (int j = 0; j < size; j++) {
            arr[j] = rand() % 81;
            printf("%d ", arr[j]);
        }
        printf("\n");

        // Reset variables for the next case
        LARGE_INTEGER frequency;
        LARGE_INTEGER start, end;
        long long executionTime = 0;
        SIZE_T peak_memory = 0;

        // Get the frequency of the performance counter
        QueryPerformanceFrequency(&frequency);

        // Best Case
        printf("\nBest Case Started\n");
        // Measure the memory usage before sorting
        measureMemory(&peak_memory);
        // Get the starting time
        QueryPerformanceCounter(&start);
        // Perform merge sort
        merge_sort(arr, size);
        // Get the ending time
        QueryPerformanceCounter(&end);
        // Calculate the elapsed time
        executionTime = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;
        // Print the memory usage
        printMemoryUsage(peak_memory);
        // Print the elapsed time
        printf("Time elapsed: %lld nanoseconds\n", executionTime);
        printf("Sorted Array: ");
        // Print the sorted array
        for (int j = 0; j < size; j++) {
            printf("%d ", arr[j]);
        }
        printf("\n");

        // Average Case
        printf("\nAverage Case Started\n");
        // Measure the memory usage before sorting
        measureMemory(&peak_memory);
        // Get the starting time
        QueryPerformanceCounter(&start);
        // Perform merge sort
        merge_sort(arr, size);
        // Get the ending time
        QueryPerformanceCounter(&end);
        // Calculate the elapsed time
        executionTime = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;
        // Print the memory usage
        printMemoryUsage(peak_memory);
        // Print the elapsed time
        printf("Time elapsed: %lld nanoseconds\n", executionTime);
        printf("Sorted Array: ");
        // Print the sorted array
        for (int j = 0; j < size; j++) {
            printf("%d ", arr[j]);
        }
        printf("\n");

        // Worst Case
        printf("\nWorst Case Started\n");
        // Measure the memory usage before sorting
        measureMemory(&peak_memory);
        // Get the starting time
        QueryPerformanceCounter(&start);
        // Perform merge sort
        merge_sort(arr, size);
        // Get the ending time
        QueryPerformanceCounter(&end);
        // Calculate the elapsed time
        executionTime = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;
        // Print the memory usage
        printMemoryUsage(peak_memory);
        // Print the elapsed time
        printf("Time elapsed: %lld nanoseconds\n", executionTime);
        printf("Sorted Array: ");
        // Print the sorted array
        for (int j = 0; j < size; j++) {
            printf("%d ", arr[j]);
        }
        printf("\n");

        printf("Case %d Completed\n\n", i + 1);
    }

    return 0;
}
=======
>>>>>>> feee262d3ca9326b298a5e3e2badd2252980d27b
