# Problema de las N reinas con algoritmos genéticos
Alejandro Vejar Henriquez
Kevin Carrasco Zenteno

Aca se presenta la implementación en Java de la solución para el problema de las N reinas mediante la implementación del algoritmo genético.
El problema consisten en poner n-reinas en un tablero tradicional de ajedrez de forma tal que ninguna reína amenace a cualquier otra reína.

Parametros utilizados por el programa.

Tamaño del tablero: Este parametro representa el tamaño que tendra el tablero de reinas, se debe ingresar un numero entero mayor a 2 y con este se generara un tablero de n x n, donde n es el tamaño del tablero. Ejemplo: 4

Tamaño de la población: Este parametro representa la cantidad de tableros que se tendran como poblacion, se debe ingresar un numero entero mayor a 1 y con este se generaran m tableros de tamaño n donde n es el tamaño del tablero y m es la cantidad de tableros de la población. Ejemplo: 10

Semilla: Este parametro representa el valor de semilla del programa, a partir de este numero se generan los valores aleatorios, se debe ingresar un numero entero. Ejemplo: 3

Probabilidad de cruza: Este parametro representa la probabilidad de cruza entre los cromosomas, se debe ingresar un valor double entre 0 y 1 usando "." como punto flotante que representara el porcentaje de probabilidad de cruza. Ejemplo: 0.9

Probabilidad de mutación: Este parametro representa la probabilidad de mutación de un cromosoma, se debe ingresar un valor double entre 0 y 1 usando "." como punto flotante que representara el porcentaje de probabilidad de mutación. Ejemplo: 0.02

Cantidad de iteraciones: Este parametro representa la cantidad de iteraciones (generaciones) que tendra el sistema, se debe ingresar un valor entero mayor o igual 1.

Instrucciones para correr el programa en Linux.

> $ git clone https://github.com/kevincarrascoz/Tarea-1-Algoritmos-Geneticos-Carrasco-Vejar.git

> $ cd Tarea-1-Algoritmos-Geneticos-Carrasco-Vejar

> $ javac reinas.java

> $ java reinas 'tamaño del tablero' 'tamaño de la población' 'semilla' 'probabilidad de cruza' 'probabilidad de mutacion' 'cantidad de iteraciones'

