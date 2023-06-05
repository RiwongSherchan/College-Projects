#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <psapi.h>
#include <time.h>

// Function to perform linear search in an array
// Arguments:
// - arr: The array to be searched
// - n: The size of the array
// - target: The element to be searched for
// Returns:
// - The index of the target element if found, or -1 if not found
int linearSearch(int arr[], int n, int target)
{
    for (int i = 0; i < n; i++)
    {
        if (arr[i] == target)
        {
            return i; // Return the index where the target element is found
        }
    }
    return -1; // Return -1 if the target element is not found
}

// Function to get the current time using the Windows performance counters
// Returns:
// - The current time in nanoseconds
long long getCurrentTime()
{
    LARGE_INTEGER frequency;
    QueryPerformanceFrequency(&frequency);

    LARGE_INTEGER start;
    QueryPerformanceCounter(&start);

    // Calculate time in nanoseconds
    long long nanoseconds = start.QuadPart * 1000000000LL / frequency.QuadPart;

    return nanoseconds;
}

// Function to print memory usage statistics
// Arguments:
// - pmc: The PROCESS_MEMORY_COUNTERS_EX structure containing memory usage information
void printMemoryUsage(PROCESS_MEMORY_COUNTERS_EX pmc)
{
    SIZE_T virtualMemoryUsed = pmc.PrivateUsage;
    SIZE_T peakWorkingSetSize = pmc.PeakWorkingSetSize;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peakWorkingSetSize);
}

int main()
{
    int sizes[] = {5000, 10000, 15000}; // Array sizes for analysis

    for (int i = 0; i < sizeof(sizes) / sizeof(sizes[0]); i++)
    {
        int n = sizes[i];
        printf("Case %d (Size: %d)\n", i + 1, n);

        // Allocate memory for the array
        int *arr = (int *)malloc(n * sizeof(int));

        // Generate array elements
        srand(time(NULL));
        for (int i = 0; i < n; i++)
        {
            arr[i] = rand() % 10000001; // Generating random numbers between 0 and 10,000,000
        }

        // Best Case
        printf("Best Case Started\n");
        // Perform linear search
        long long start = getCurrentTime();
        int target = arr[n - 1];
        int result = linearSearch(arr, n, target);
        long long end = getCurrentTime();
        long long executionTime = end - start;
        // Print result and execution time
        if (result == -1)
        {
            printf("Element not found.\n");
        }
        else
        {
            printf("Element found at index %d.\n", result);
        }
        printf("Time elapsed: %lld nanoseconds\n", executionTime);
        HANDLE process = GetCurrentProcess();
        PROCESS_MEMORY_COUNTERS_EX pmc;
        ZeroMemory(&pmc, sizeof(pmc));
        pmc.cb = sizeof(pmc);
        GetProcessMemoryInfo(process, (PROCESS_MEMORY_COUNTERS *)&pmc, sizeof(pmc));
        printMemoryUsage(pmc);
        printf("Best Case Ended\n\n");

        // Reset variables for the next case
        start = 0;
        end = 0;
        executionTime = 0;

        // Average Case
        printf("Average Case Started\n");
        // Perform linear search
        start = getCurrentTime();
        target = arr[n / 2];
        result = linearSearch(arr, n, target);
        end = getCurrentTime();
        executionTime = end - start;
        // Print result and execution time
        if (result == -1)
        {
            printf("Element not found.\n");
        }
        else
        {
            printf("Element found at index %d.\n", result);
        }
        printf("Time elapsed: %lld nanoseconds\n", executionTime);
        ZeroMemory(&pmc, sizeof(pmc));
        pmc.cb = sizeof(pmc);
        GetProcessMemoryInfo(process, (PROCESS_MEMORY_COUNTERS *)&pmc, sizeof(pmc));
        printMemoryUsage(pmc);
        printf("Average Case Ended\n\n");

        // Reset variables for the next case
        start = 0;
        end = 0;
        executionTime = 0;

        // Worst Case
        printf("Worst Case Started\n");
        // Perform linear search
        start = getCurrentTime();
        target = n + 1;
        result = linearSearch(arr, n, target);
        end = getCurrentTime();
        executionTime = end - start;
        // Print result and execution time
        if (result == -1)
        {
            printf("Element not found.\n");
        }
        else
        {
            printf("Element found at index %d.\n", result);
        }
        printf("Time elapsed: %lld nanoseconds\n", executionTime);
        ZeroMemory(&pmc, sizeof(pmc));
        pmc.cb = sizeof(pmc);
        GetProcessMemoryInfo(process, (PROCESS_MEMORY_COUNTERS *)&pmc, sizeof(pmc));
        printMemoryUsage(pmc);
        printf("Worst Case Ended\n\n");

        free(arr); // Free dynamically allocated memory
        printf("Case %d Completed\n\n", i + 1);
    }

    return 0;
}
