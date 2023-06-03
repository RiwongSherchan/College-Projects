#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int linearSearch(int arr[], int n, int target) {
    for (int i = 0; i < n; i++) {
        if (arr[i] == target) {
            return i;  // Return the index where the target element is found
        }
    }
    return -1;  // Return -1 if the target element is not found
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
        arr[i] = rand() % 100;  // Generating random numbers between 0 and 99
        printf("%d ", arr[i]);
    }
    printf("\n");

    int target;
    printf("Enter the element to search: ");
    scanf("%d", &target);

    clock_t start = clock();  // Start the clock

    int result = linearSearch(arr, n, target);

    clock_t end = clock();  // Stop the clock

    if (result == -1) {
        printf("Element not found.\n");
    } else {
        printf("Element found at index %d.\n", result);
    }

    // Calculate and print the execution time
    double execution_time = (double)(end - start) / CLOCKS_PER_SEC;
    printf("Execution time: %lf seconds.\n", execution_time);

    // Free dynamically allocated memory
    free(arr);

    return 0;
}
