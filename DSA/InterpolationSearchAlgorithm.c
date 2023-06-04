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

double measureElapsedTime(int arr[], int size, int target) {
    LARGE_INTEGER frequency, start, end;
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

    insertion_sort(arr, size); // Sort the array

    interpolation_search(arr, size, target);

    QueryPerformanceCounter(&end);
    double elapsed_time = (double)(end.QuadPart - start.QuadPart) / frequency.QuadPart;

    return elapsed_time;
}

SIZE_T measureMemoryUsage(int arr[], int size, int target) {
    HANDLE process = GetCurrentProcess();
    PROCESS_MEMORY_COUNTERS_EX mem_info;
    GetProcessMemoryInfo(process, (PROCESS_MEMORY_COUNTERS*)&mem_info, sizeof(mem_info));

    interpolation_search(arr, size, target);

    return mem_info.PeakWorkingSetSize;
}

int main() {
    int size;
    printf("Enter the size of the array: ");
    scanf("%d", &size);

    int* arr = (int*)malloc(size * sizeof(int));
    if (arr == NULL) {
        printf("Memory allocation failed. Exiting...\n");
        return 1;
    }

    srand(time(NULL));  // Seed the random number generator

    printf("Generated array: ");
    for (int i = 0; i < size; ++i) {
        arr[i] = rand() % 100;  // Generate random numbers between 0 and 99
        printf("%d ", arr[i]);
    }
    printf("\n");

    int target;
    printf("Enter the target element: ");
    scanf("%d", &target);

    double elapsed_time = measureElapsedTime(arr, size, target);
    SIZE_T peak_working_set_size = measureMemoryUsage(arr, size, target);

    insertion_sort(arr, size); // Sort the array

    printf("Sorted array: ");
    for (int i = 0; i < size; ++i) {
        printf("%d ", arr[i]);
    }
    printf("\n");

    int index = interpolation_search(arr, size, target);

    if (index != -1)
        printf("Element found at index %d\n", index);
    else
        printf("Element not found\n");

    printf("Time elapsed: %.6f seconds\n", elapsed_time);
    printf("Peak working set size: %lu bytes\n", peak_working_set_size);

    free(arr); // Free the dynamically allocated memory

    return 0;
}
