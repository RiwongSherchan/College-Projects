#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include <psapi.h>

void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
  
    for (int j = low; j <= high - 1; j++) {
        if (arr[j] <= pivot) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

void quick_sort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quick_sort(arr, low, pi - 1);
        quick_sort(arr, pi + 1, high);
    }
}

void measureTime(int arr[], int size) {
    LARGE_INTEGER frequency, start, end;
    double elapsedSeconds;

    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

    quick_sort(arr, 0, size - 1);

    QueryPerformanceCounter(&end);
    elapsedSeconds = (end.QuadPart - start.QuadPart) / (double)frequency.QuadPart;

    printf("Sorting time: %.6f seconds\n", elapsedSeconds);
}

void measureMemory() {
    PROCESS_MEMORY_COUNTERS_EX pmc;
    GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));

    SIZE_T virtualMemUsedByMe = pmc.PrivateUsage;

    printf("Memory used: %lu bytes\n", virtualMemUsedByMe);
}

int main() {
    int size;
    printf("Enter the size of the array: ");
    scanf("%d", &size);

    int numbers[size];
    srand(time(NULL));
    printf("Generated array elements: ");
    for (int i = 0; i < size; i++) {
        numbers[i] = rand() % 100;  // Generates random numbers between 0 and 99
        printf("%d ", numbers[i]);
    }
    printf("\n");

    measureTime(numbers, size);

    printf("Sorted array elements: ");
    for (int i = 0; i < size; i++) {
        printf("%d ", numbers[i]);
    }
    printf("\n");

    measureMemory();

    return 0;
}
