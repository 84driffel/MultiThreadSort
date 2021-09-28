import java.io.*;
import java.util.ArrayList;


class MergeSort {
    private static int threads = 4;

    private static class SortingThreads extends Thread {
        SortingThreads(int[] arr, int l, int r) {
            super(() -> {
                MergeSort.sort(arr, l, r);
            });
            this.start();
        }
    }

    public static void threadsSorting(int[] array) {
        long time = System.currentTimeMillis();
        final int length = array.length;
        boolean exact = length % threads == 0;
        int maxlim;
        if(exact){
            maxlim = length/threads;
        }else {
            maxlim = length/(threads-1);
        }
        maxlim = Math.max(maxlim, threads);
        final ArrayList<SortingThreads> threads = new ArrayList<>();
        for (int i = 0; i < length; i += maxlim) {
            int l = i;
            int remain = (length) - i;
            int r;
	    if(remain < maxlim){
		    r = i + (remain - 1);   
	    }
	    else{
		    r =  i + (maxlim - 1);
	    }
            final SortingThreads t = new SortingThreads(array, l, r);
            threads.add(t);
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        }
        for (int i = 0; i < length; i += maxlim) {
            int mid;
	    if(i == 0){ mid = 0;}
	    else{ mid = i-1;}
            int remain = (length) - i;
            int end;
	    if (remain < maxlim){ end = i + (remain - 1);}
	    else{                 end = i + (maxlim - 1);}
            merge(array, 0, mid, end);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Time spent for custom multi-threaded recursive merge_sort(): "+ time+ "ms");
    }

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    public static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    public static void sort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver code
    public static void main(String args[]) throws IOException {
	threads = Integer.parseInt(args[0]);
        FileInputStream fis = new FileInputStream("array.bin");
        DataInputStream dis = new DataInputStream(fis);
        StringBuilder str = new StringBuilder();
        try {
            long index = 0;
            while (true) {
                long l = dis.readLong();
                //System.out.println(index + " " + l);
                str.append(l + " ");
                index += 1;
            }
        } catch (EOFException e) {
            fis.close();
        }

        String[] splitted = str.toString().split("\\s+");
        int arr[] = new int[splitted.length];
        for (int i = 0; i < splitted.length; i++) {
            arr[i] = Integer.parseInt(splitted[i]);

        }


        MergeSort ob = new MergeSort();

	long time = System.currentTimeMillis();
        ob.sort(arr, 0, arr.length - 1);
	time = System.currentTimeMillis() - time;

	System.out.println("Time spent for standard merge sort: " + time + "ms");


        MergeSort.threadsSorting(arr);

    }
}
