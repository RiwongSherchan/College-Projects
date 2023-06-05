#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

void insertion_sort(int arr[], int size) {
    for (int i = 1; i < size; ++i) {
        int key = arr[i];
        int j = i - 1;

        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            --j;
        }

        arr[j + 1] = key;
    }
}

int interpolation_search(int arr[], int size, int target) {
    int low = 0;
    int high = size - 1;

    while (low <= high && target >= arr[low] && target <= arr[high]) {
        if (low == high) {
            if (arr[low] == target)
                return low;
            return -1;
        }

        int position = low + (((double)(target - arr[low]) / (arr[high] - arr[low])) * (high - low));

        if (arr[position] == target)
            return position;
        else if (arr[position] < target)
            low = position + 1;
        else
            high = position - 1;
    }

    return -1;
}

long long measureElapsedTime(int arr[], int size, int target) {
    LARGE_INTEGER frequency, start, end;
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

    insertion_sort(arr, size); // Sort the array

    interpolation_search(arr, size, target);

    QueryPerformanceCounter(&end);
    long long elapsed_time = (end.QuadPart - start.QuadPart) * 1000000000 / frequency.QuadPart;

    return elapsed_time;
}

void printMemoryUsage(PROCESS_MEMORY_COUNTERS_EX pmc) {
    SIZE_T virtualMemoryUsed = pmc.PrivateUsage;
    SIZE_T peakWorkingSetSize = pmc.PeakWorkingSetSize;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peakWorkingSetSize);
}


int main() {
    srand(time(NULL));  // Seed the random number generator

    // Define different array sizes for each case
    int sizes[] = {5000, 10000, 15000};

    for (int i = 0; i < sizeof(sizes) / sizeof(sizes[0]); ++i) {
        int size = sizes[i]; // Size of the array

        printf("// Case %d (Size: %d)\n", i + 1, size);

        // Best Case: Already Sorted Array
        printf("Best Case Started\n");

        int* best_arr = (int*)malloc(size * sizeof(int));
        if (best_arr == NULL) {
            printf("Memory allocation failed. Exiting...\n");
            return 1;
        }

        for (int j = 0; j < size; ++j) {
            best_arr[j] = j; // Assigning numbers in sorted order
        }

        int best_target = size - 1; // Target element is the last element

        long long best_elapsed_time = measureElapsedTime(best_arr, size, best_target);
        PROCESS_MEMORY_COUNTERS_EX best_pmc;
        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&best_pmc, sizeof(best_pmc));

        insertion_sort(best_arr, size); // Sort the array

        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&best_pmc, sizeof(best_pmc));
        SIZE_T best_memory_usage = best_pmc.PrivateUsage;

        int best_index = interpolation_search(best_arr, size, best_target);

        if (best_index != -1)
            printf("Element found at index %d\n", best_index);
        else
            printf("Element not found\n");

        printf("Time elapsed: %lld nanoseconds\n", best_elapsed_time);
        printMemoryUsage(best_pmc);

        free(best_arr);

        printf("Best Case Ended\n\n");

        // Average Case: Randomized Array
        printf("Average Case Started\n");

        int* average_arr = (int*)malloc(size * sizeof(int));
        if (average_arr == NULL) {
            printf("Memory allocation failed. Exiting...\n");
            return 1;
        }

        for (int j = 0; j < size; ++j) {
            average_arr[j] = rand() % 1001; // Generate random numbers between 0 and 1000
        }

        int average_target = rand() % 1001; // Random target element

        long long average_elapsed_time = measureElapsedTime(average_arr, size, average_target);
        PROCESS_MEMORY_COUNTERS_EX average_pmc;
        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&average_pmc, sizeof(average_pmc));

        insertion_sort(average_arr, size); // Sort the array

        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&average_pmc, sizeof(average_pmc));
        SIZE_T average_memory_usage = average_pmc.PrivateUsage;

        int average_index = interpolation_search(average_arr, size, average_target);

        if (average_index != -1)
            printf("Element found at index %d\n", average_index);
        else
            printf("Element not found\n");

        printf("Time elapsed: %lld nanoseconds\n", average_elapsed_time);
        printMemoryUsage(average_pmc);

        free(average_arr);

        printf("Average Case Ended\n\n");

        // Worst Case: Descending Order Array
        printf("Worst Case Started\n");

        int* worst_arr = (int*)malloc(size * sizeof(int));
        if (worst_arr == NULL) {
            printf("Memory allocation failed. Exiting...\n");
            return 1;
        }

        for (int j = 0; j < size; ++j) {
            worst_arr[j] = size - j; // Assigning numbers in descending order
        }

        int worst_target = size + 1; // Target element that is not present in the array

        long long worst_elapsed_time = measureElapsedTime(worst_arr, size, worst_target);
        PROCESS_MEMORY_COUNTERS_EX worst_pmc;
        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&worst_pmc, sizeof(worst_pmc));

        insertion_sort(worst_arr, size); // Sort the array

        GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&worst_pmc, sizeof(worst_pmc));
        SIZE_T worst_memory_usage = worst_pmc.PrivateUsage;

        int worst_index = interpolation_search(worst_arr, size, worst_target);

        if (worst_index != -1)
            printf("Element found at index %d\n", worst_index);
        else
            printf("Element not found\n");

        printf("Time elapsed: %lld nanoseconds\n", worst_elapsed_time);
        printMemoryUsage(worst_pmc);

        free(worst_arr);

        printf("Worst Case Ended\n\n");

        printf("// Case %d Completed\n\n", i + 1);
    }

    return 0;
}