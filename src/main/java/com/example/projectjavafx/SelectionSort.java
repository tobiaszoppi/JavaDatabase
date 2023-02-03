package com.example.projectjavafx;

public class SelectionSort {
        /* podria ser static, la diferencia en la invocacion
         * es que cuando es un metodo de clase o instancia estatico no lo ves
         */
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
        /*  El pasaje de parametros en java es por valor, recibimos la direccion del arreglo,
         * si bien la direccion de comienzo del arreglo no se puede cambiar,
         * el acceso a las componentes se puede cambiar por que esta hecho por referencia
         */
    }
