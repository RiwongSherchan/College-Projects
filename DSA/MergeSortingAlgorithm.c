#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

// Function to measure time using QueryPerformanceCounter
double measureTime(void (*func)(int[], int), int arr[], int size) {
    LARGE_INTEGER frequency, start, end;
    double elapsed_time;

    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

    func(arr, size);

    QueryPerformanceCounter(&end);
    elapsed_time = (double)(end.QuadPart - start.QuadPart) / frequency.QuadPart;

    return elapsed_time;
}

// Function to measure memory using GetProcessMemoryInfo
void measureMemory(SIZE_T* peak_memory) {
    HANDLE process = GetCurrentProcess();
    PROCESS_MEMORY_COUNTERS_EX pmc;

    if (GetProcessMemoryInfo(process, (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc))) {
        *peak_memory = pmc.PeakWorkingSetSize;
    }
}

void merge(int arr[], int left[], int left_size, int right[], int right_size) {
    int i = 0, j = 0, k = 0;

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

    while (i < left_size) {
        arr[k] = left[i];
        i++;
        k++;
    }

    while (j < right_size) {
        arr[k] = right[j];
        j++;
        k++;
    }
}

void merge_sort(int arr[], int size) {
    if (size <= 1)
        return;

    int mid = size / 2;
    int left[mid];
    int right[size - mid];

    for (int i = 0; i < mid; i++)
        left[i] = arr[i];

    for (int i = mid; i < size; i++)
        right[i - mid] = arr[i];

    merge_sort(left, mid);
    merge_sort(right, size - mid);

    merge(arr, left, mid, right, size - mid);
}

int main() {
    int size;
    printf("Enter the size of the array: ");
    scanf("%d", &size);

    int arr[size];

    // Generate random array elements
    srand(time(0));
    for (int i = 0; i < size; i++) {
        arr[i] = rand() % 100; // Random elements from 0 to 99
    }

    printf("Array before sorting: ");
    for (int i = 0; i < size; i++)
        printf("%d ", arr[i]);

    double elapsed_time = measureTime(merge_sort, arr, size);

    printf("\nArray after sorting: ");
    for (int i = 0; i < size; i++)
        printf("%d ", arr[i]);

    printf("\n\nSorting Time: %f seconds\n", elapsed_time);

    SIZE_T peak_memory;
    measureMemory(&peak_memory);

    printf("Peak Memory Usage: %llu bytes\n", (unsigned long long)peak_memory);

    return 0;
}
