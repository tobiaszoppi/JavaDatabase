package com.example.projectjavafx;

public class SelectionSort {
    public void selectionSort(Usuario[] a, int n) {
        for (int i = 0; i < n - 1; i++) {
            int p = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j].compareTo(a[p]) < 0) {
                    p = j;
                }
            }
            swap(a, p, i);
        }
    }
    public static void swap(Usuario []a, int i, int j) {
        Usuario temp = a[i]; // copia de a[i] en temp
        a[i] = a[j]; // guardo a[j] en a[i]
        a[j] = temp; // guardo la copia de a[i] de temp en a[j]
    }
}
