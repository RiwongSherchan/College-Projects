#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

int linearSearch(int arr[], int n, int target) {
    for (int i = 0; i < n; i++) {
        if (arr[i] == target) {
            return i;  // Return the index where the target element is found
        }
    }
    return -1;  // Return -1 if the target element is not found
}

double getCurrentTime() {
    LARGE_INTEGER frequency;
    QueryPerformanceFrequency(&frequency);

    LARGE_INTEGER start;
    QueryPerformanceCounter(&start);

    return (double)start.QuadPart / (double)frequency.QuadPart;
}

int main() {
    int n;
    printf("Enter the size of the array: ");
    scanf("%d", &n);

    // Dynamically allocate memory for the array
    int* arr = (int*)malloc(n * sizeof(int));

    // Generate random array elements
    srand(time(NULL));
    printf("Generated array elements:\n");
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 10000001;  // Generating random numbers between 0 and 10,000,000
        printf("%d ", arr[i]);
    }
    printf("\n");

    int target;
    printf("Enter the element to search: ");
    scanf("%d", &target);

    double start = getCurrentTime();  // Start the clock

    int result = linearSearch(arr, n, target);

    double end = getCurrentTime();  // Stop the clock

    if (result == -1) {
        printf("Element not found.\n");
    } else {
        printf("Element found at index %d.\n", result);
    }

    // Calculate and print the execution time
    double execution_time = end - start;
    printf("Execution time: %lf seconds.\n", execution_time);

    // Free dynamically allocated memory
    free(arr);

    return 0;
}
