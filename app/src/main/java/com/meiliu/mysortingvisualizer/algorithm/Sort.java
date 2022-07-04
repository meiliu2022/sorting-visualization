package com.meiliu.mysortingvisualizer.algorithm;

import java.util.Arrays;


public class Sort {
    static int[] a8 = {40, 60, 30, 80, 50, 20, 90, 20, 10, 70};
    static int[] a7 = {3, 2, 1};
    static int[] a5 = {22, 3, 2, 1, 0, 5, 4};
    static int[] a4 = {22, 3, 2, 1, 0, 5, 4};
    static int[] a3 = {22, 3, 2, 1, 0, 5, 4};
    static int[] a = {2, 3, 6, 11};
    static int[] b = {1, 4, 8, 9};
    static int[] c = new int[a.length + b.length];
    static int[] a1 = {2, 3, 6, 11, 2, 3, 4};
    static int[] a6 = {5, 4, 3, 1, 0, 2, 6};


    public static void main(String[] args) {

//        quickSort(a6, 0, a6.length - 1);
//		memeryArray(a, a.length, b, b.length, c);
//		sort(a1, 0, a1.length-1);
//		selectSort(a3);
//		bubbleSort(a4);
//		InsertSort(a5);
        HillSort(a7);
//        shellSort(a7);
//        sort(a7);
//		HeapSort.sortHeap();
//		binarySearch(a8);
        System.out.println("Finished……");
    }


    private static void binarySearch(int[] a) {

        System.out.println("Binary search");
        int target = 80;
        Arrays.sort(a);
        boolean result = false;
        int min = 0;
        int b = 0;
        int max = a.length - 1;
        while (min <= max) {
            b = (min + max) / 2;
            if (target > a[b]) {
                min = b + 1;
            }
            if (target < a[b]) {
                max = b - 1;
            }
            if (target == a[b]) {
                min++;
                result = true;

            }

        }
        System.out.println("result:" + result + ",b:" + b);
    }


    public static void shellSort(int[] a) {
        System.out.println("Hill");
        int n = a.length;
        int d = n / 2;
        while (d > 0) {
            for (int i = d; i < n; i++) {
                int j = i - d;
                while (j >= 0 && a[j] > a[j + d]) {
                    int tmp = a[j];
                    a[j] = a[j + d];
                    a[j + d] = tmp;
                    j = j - d;
                }
            }
            d = d / 2;
        }
        for (int o : a) {
            System.out.print(o + ",");
        }
    }

    @Deprecated
    private static void HillSort(int[] a) {
        System.out.println("Hill");
        int d = a.length / 2;
        //判断是否为基数 a%2！=0
        if ((a.length & 1) == 1) {
            d++;
        }
        while (true) {
            for (int i = 0; i < d; i++) {
                for (int j = i; j + d < a.length; j += d) {
                    int temp;
                    if (a[j] > a[j + d]) {
                        temp = a[j];
                        a[j] = a[j + d];
                        a[j + d] = temp;
                    }
                }
            }

            if (d == 1) {
                break;
            }
            d--;
        }
        for (int o : a) {
            System.out.print(o + ",");
        }

    }


    public static void sort(int[] arr) {
        System.out.println("Hill new");
        int i, j, r, tmp;
        for (r = arr.length / 2; r >= 1; r = r / 2) {
            for (i = r; i < arr.length; i++) {
                tmp = arr[i];
                j = i - r;
                while (j >= 0 && tmp < arr[j]) {
                    arr[j + r] = arr[j];
                    j -= r;
                }
                arr[j + r] = tmp;
            }
            System.out.println(i + ":" + Arrays.toString(arr));
        }
    }

    private static void InsertSort(int[] a) {
        System.out.println("Insert");
        long t1 = System.nanoTime();
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (a[j] > temp) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = temp;
        }
        for (int q = 0; q < a.length; q++) {
            System.out.print(a[q] + ",");
        }
    }

    private static void bubbleSort(int[] numbers) {
        System.out.println("Bubble");
        int temp;
        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (numbers[i] < numbers[j]) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        for (int q = 0; q < numbers.length; q++) {
            System.out.print(numbers[q] + ",");
        }
    }

    public static void selectSort(int[] array) {
        System.out.println("Select");
        int min;
        int tmp = 0;
        for (int i = 0; i < array.length; i++) {
            min = array[i];
            for (int j = i; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    tmp = array[i];
                    array[i] = min;
                    array[j] = tmp;
                }
            }
        }
        for (int q = 0; q < array.length; q++) {
            System.out.print(array[q] + ",");
        }

    }


    public static void memeryArray(int a[], int n, int b[], int m, int c[]) {
        int i, j, k;

        i = j = k = 0;
        while (i < n && j < m) {
            if (a[i] < b[j])
                c[k++] = a[i++];
            else
                c[k++] = b[j++];
        }
        for (int q = 0; q < c.length; q++) {
            System.out.print(c[q] + ",");
        }
        System.out.println();

        while (i < n)
            c[k++] = a[i++];

        for (int q = 0; q < c.length; q++) {
            System.out.print(c[q] + ",");
        }
        System.out.println();

        while (j < m)
            c[k++] = b[j++];

        for (int q = 0; q < c.length; q++) {
            System.out.print(c[q] + ",");
        }
    }


    public static int[] sort(int[] a, int low, int high) {
        System.out.println("Merge");
        int mid = (low + high) / 2;
        if (low < high) {
            sort(a, low, mid);
            sort(a, mid + 1, high);
            merge(a, low, mid, high);
        }
        return a;
    }

    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = a[i++];
        }

        while (j <= high) {
            temp[k++] = a[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            a[x + low] = temp[x];
        }
        for (int q = 0; q < a.length; q++) {
            System.out.print(a[q] + ",");
        }
        System.out.println();
    }


    public static void quickSort(int[] a, int low, int high) {
        int start = low;
        int end = high;
        int key = a[low];

        while (end > start) {


            while (end > start && a[end] >= key)
                end--;
            if (a[end] <= key) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
                System.out.println("key:" + key + ",从后往前比较 end:" + end + ",a[end]:" + a[end] + ",start:" + start + ",a[start]:" + a[start]);
                System.out.println("---start:" + start);
            }

            while (end > start && a[start] <= key)
                start++;
            if (a[start] >= key) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
                System.out.println("key:" + key + ",从前往后比较 end:" + end + ",a[end]:" + a[end] + ",start:" + start + ",a[start]:" + a[start]);
                System.out.println("---end:" + end);
            }

        }
        StringBuilder sb = new StringBuilder("a6 =");
        for (int i = 0; i < a6.length; i++) {
            sb.append(a6[i] + ",");

        }
        System.out.println("sb:" + sb.toString());

        if (start > low) {
            System.out.println("**************************************************");
            System.out.println("recursion******start > low!" + ",start:" + start + ",low:" + low);
            quickSort(a, low, start - 1);
        }
        if (end < high) {
            System.out.println("**************************************************");
            System.out.println("recursion*******end < high!" + ",end:" + end + ",high:" + high);
            quickSort(a, end + 1, high);
        }
    }


    public static class HeapSort {

        public static void maxHeapDown(int[] a, int start, int end) {
            int c = start;
            int l = 2 * c + 1;
            int tmp = a[c];

            for (; l <= end; c = l, l = 2 * l + 1) {
                // "l": left child; "l+1": right child
                if (l < end && a[l] < a[l + 1])
                    l++;
                if (tmp >= a[l])
                    break;
                else {
                    a[c] = a[l];
                    a[l] = tmp;
                }
            }

            System.out.printf("(max)binary heap\n");
            for (int i = 0; i < a.length; i++)
                System.out.printf("%d ", a[i]);
            System.out.printf("\n");
        }

        public static void heapSortAsc(int[] a, int n) {
            int i, tmp;

            for (i = n / 2 - 1; i >= 0; i--)
                maxHeapDown(a, i, n - 1);


            for (i = n - 1; i > 0; i--) {
                tmp = a[0];
                a[0] = a[i];
                a[i] = tmp;
                maxHeapDown(a, 0, i - 1);
            }
        }


        public static void minHeapDown(int[] a, int start, int end) {
            int c = start;
            int l = 2 * c + 1;
            int tmp = a[c];

            for (; l <= end; c = l, l = 2 * l + 1) {
                if (l < end && a[l] > a[l + 1])
                    l++;
                if (tmp <= a[l])
                    break;
                else {
                    a[c] = a[l];
                    a[l] = tmp;
                }
            }
        }

        public static void heapSortDesc(int[] a, int n) {
            int i, tmp;

            for (i = n / 2 - 1; i >= 0; i--)
                minHeapDown(a, i, n - 1);

            for (i = n - 1; i > 0; i--) {
                tmp = a[0];
                a[0] = a[i];
                a[i] = tmp;
                minHeapDown(a, 0, i - 1);
            }
        }

        public static void sortHeap() {
            System.out.println("Heap");
            int i;
            int a[] = {1, 2, 4, 3, 0};

            System.out.printf("before sort:");
            for (i = 0; i < a.length; i++)
                System.out.printf("%d ", a[i]);
            System.out.printf("\n");

            heapSortAsc(a, a.length);

            System.out.printf("after  sort:");
            for (i = 0; i < a.length; i++)
                System.out.printf("%d ", a[i]);
            System.out.printf("\n");
        }
    }

}
