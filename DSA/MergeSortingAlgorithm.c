#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

// Function to measure time using QueryPerformanceCounter
long long measureTime(void (*func)(int[], int), int arr[], int size) {
    LARGE_INTEGER frequency, start, end;
    long long elapsed_time;

    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

    func(arr, size);

    QueryPerformanceCounter(&end);
    elapsed_time = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;

    return elapsed_time;
}

// Function to measure memory using GetProcessMemoryInfo
void measureMemory(SIZE_T* peak_memory) {
    PROCESS_MEMORY_COUNTERS_EX pmc;
    ZeroMemory(&pmc, sizeof(pmc));
    pmc.cb = sizeof(pmc);

    GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
    *peak_memory = pmc.PeakWorkingSetSize;
}

void printMemoryUsage(SIZE_T peak_memory) {
    SIZE_T virtualMemoryUsed = peak_memory;
    printf("Memory Usage: %llu bytes\n", (unsigned long long)virtualMemoryUsed);
    printf("Peak Working Set Size: %llu bytes\n", (unsigned long long)peak_memory);
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
    int sizes[] = {5000, 10000, 15000};
     int num_sizes = sizeof(sizes) / sizeof(sizes[0]);

    LARGE_INTEGER frequency;
    QueryPerformanceFrequency(&frequency);

    for (int i = 0; i < num_sizes; i++) {
        int size = sizes[i];
        printf("Case %d (Size: %d)\n", i + 1, size);

        int arr[size];
        srand(time(0));
        for (int j = 0; j < size; j++) {
            arr[j] = rand() % 100;
        }

        // Reset variables for the next case
        LARGE_INTEGER start, end;
        long long executionTime = 0;
        SIZE_T peak_memory = 0;

        // Best Case
        printf("Best Case Started\n");
        start.QuadPart = 0;
        end.QuadPart = 0;
        executionTime = 0;
        peak_memory = 0;

        measureMemory(&peak_memory);
        QueryPerformanceCounter(&start);
        merge_sort(arr, size);
        QueryPerformanceCounter(&end);
        executionTime = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;
        printMemoryUsage(peak_memory);
        printf("Time elapsed: %lld nanoseconds\n", executionTime);

        // Average Case
        printf("Average Case Started\n");
        start.QuadPart = 0;
        end.QuadPart = 0;
        executionTime = 0;
        peak_memory = 0;

        measureMemory(&peak_memory);
        QueryPerformanceCounter(&start);
        merge_sort(arr, size);
        QueryPerformanceCounter(&end);
        executionTime = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;
        printMemoryUsage(peak_memory);
        printf("Time elapsed: %lld nanoseconds\n", executionTime);

        // Worst Case
        printf("Worst Case Started\n");
        start.QuadPart = 0;
        end.QuadPart = 0;
        executionTime = 0;
        peak_memory = 0;

        measureMemory(&peak_memory);
        QueryPerformanceCounter(&start);
        merge_sort(arr, size);
        QueryPerformanceCounter(&end);
        executionTime = (end.QuadPart - start.QuadPart) * 1000000000LL / frequency.QuadPart;
        printMemoryUsage(peak_memory);
        printf("Time elapsed: %lld nanoseconds\n", executionTime);

        printf("Case %d Completed\n\n", i + 1);
    }

    return 0;
}
