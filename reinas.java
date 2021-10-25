/*
Implementacion solucion al problema de las n-reinas
Alejandro Vejar Henriquez
Kevin Carrasco Zenteno
*/

import java.security.Policy;
import java.util.*;
import java.util.Random;
public class reinas{
    public static void main(String[] args) {

        /*----------------------------------------------
        Validacion de los argumentos de entrada
        ----------------------------------------------*/

        if (args.length != 6) {
			System.out.println("Ingresar 6 argumentos, el tamano del tablero,el tamano de la poblacion, la semilla, la prob de cruza,la prob de mutacion y la cantidad de iteraciones respectivamente");
			System.exit(-1);
		}
        if(args[0].equals("0") || args[0].equals("1")|| args[0].equals("2") ){
            System.out.println("El tamano del tablero debe ser mayor a 2");
            System.exit(-1);
        }
        if(args[1].equals("0") || args[1].equals("1") ){
            System.out.println("El tamano de la poblacion debe ser mayor a 1");
            System.exit(-1);
        }


        /*----------------------------------------------
        Asignacion de los argumentos a las variables y declaracion de variables
        ----------------------------------------------*/

        int tamanoTablero = Integer.parseInt(args[0]);
        int poblacion = Integer.parseInt(args[1]);
        int[][] tableros = new int[poblacion][tamanoTablero];
        int[] arrayfitness = new int[poblacion];
        int[] arrayfitnessInv= new int[poblacion];
        Random aleatorio = new Random();
        int semilla = Integer.parseInt(args[2]);
        double probabilidadCruza = Double.parseDouble(args[3]);
        double probabilidadMutacion = Double.parseDouble(args[4]);
        int iteraciones = Integer.parseInt(args[5]);
        boolean tableroIdeal = false;
        int maxActual=0;
        int posMaxActual=0;
        int iteracionActual=0;
        int iteracion = 0;
        int fitnessmax=0;
        aleatorio.setSeed(semilla);
        
        
        /*----------------------------------------------
        Calculo del valor maximo de fitness dependiendo del tamano del tablero
        ----------------------------------------------*/
        for(int j=(tamanoTablero-1); j>0; j--){
            fitnessmax=fitnessmax+j;
        }
        fitnessmax=fitnessmax+1;
        //System.out.println("el fitness max es: "+fitnessmax);


        /*----------------------------------------------
        Inicializacion de los tableros
        ----------------------------------------------*/
        for(int i=0; i<poblacion; i++){
            for(int j=0; j<tamanoTablero; j++){
                tableros[i][j]=j+1;
            }
        }
        //imprimirArreglo(tableros,"Tableros iniciales");


        /*----------------------------------------------
        Desordena los tableros inicialez
        ----------------------------------------------*/
        for(int i=0; i<poblacion; i++){
            for(int j=0; j<tamanoTablero; j++){
                int posAleatoria = aleatorio.nextInt(tamanoTablero);
                int temp = tableros[i][j];
                tableros[i][j] = tableros[i][posAleatoria];
                tableros[i][posAleatoria] = temp;
            }
        }

        do{
            //imprimirArreglo(tableros, "Tableros");
            
            
            /*----------------------------------------------
            Calculo del fitness para cada tablero
            ----------------------------------------------*/ 
            for(int i=0; i<poblacion; i++){
                int fitness = 0;
                int[] auxdiagonal = new int[tamanoTablero];
                int[] auxinversa = new int[tamanoTablero];
            
                for(int j=0; j<tamanoTablero; j++){
                auxdiagonal[j] = ((j+1) - tableros[i][j]);
                auxinversa[j] = Math.abs((j+1) +tableros[i][j]);  
                }
                

                for(int z=0; z<tamanoTablero; z++){
                    for(int x=0; x<tamanoTablero; x++){
                        if(z!=x){
                            if(auxdiagonal[z]==auxdiagonal[x]){
                                fitness=fitness+1;
                            }    
                            if(auxinversa[z]==auxinversa[x]){
                                fitness=fitness+1;
                            } 
                        }
                    }
                    
                }
                arrayfitness[i]=fitness/2;
            }

            


            /*----------------------------------------------
            Inversion del valor de fitness
            ----------------------------------------------*/
            for(int i=0; i<arrayfitnessInv.length; i++){
                arrayfitnessInv[i]=fitnessmax-arrayfitness[i];
                
            }
            

            /*----------------------------------------------
            Comprobacion si se encontro el tablero sin colisiones, en caso contrario guarda el mejor tablero encontrado
            ----------------------------------------------*/
            for(int i = 0; i<arrayfitness.length; i++){
                if(arrayfitnessInv[i]==fitnessmax){
                    System.out.println("El tablero sin colisiones fue encontrado en la iteracion: "+iteracion+", el tablero es: ");
                    tableroIdeal = true;
                    for(int j=0; j<tamanoTablero;j++){
                        System.out.print(tableros[i][j]+"\t");
                    }
                    System.out.println("");
                    System.out.println("El fitness del tablero es: "+arrayfitnessInv[i]);
                    break;
                }
            }
            if(tableroIdeal==false){
                for (int i = 0; i < arrayfitnessInv.length; i++) {
              
                    if(arrayfitnessInv[i]>maxActual){
                    
                        maxActual=arrayfitnessInv[i];
                        posMaxActual=i;
                        iteracionActual=iteracion;
                    }
                }
            }


            /*----------------------------------------------
            Suma total de los fitness
            ----------------------------------------------*/
            int sumaProporcion=0;
            for(int i=0; i<arrayfitnessInv.length; i++){
                sumaProporcion=sumaProporcion+arrayfitnessInv[i];
            }
            
            
            /*----------------------------------------------
            Calculo de las proporciones para cada tablero
            ----------------------------------------------*/
            double[] arrayProporciones = new double[poblacion];
            for(int i=0; i<arrayfitnessInv.length; i++){
                arrayProporciones[i]=Double.valueOf(arrayfitnessInv[i])/Double.valueOf(sumaProporcion);
            }
            

            /*----------------------------------------------
            Asignacion de los valores para la ruleta
            ----------------------------------------------*/
            double[] valorRuleta = new double[poblacion];
            for(int i=0; i<arrayfitnessInv.length; i++){
                if(i==0){
                    valorRuleta[i]=arrayProporciones[i];    
                }else{
                    valorRuleta[i]=valorRuleta[i-1]+arrayProporciones[i];
                }
            }
            //imprimirArreglo(valorRuleta, "ruleta");


            int tableroHijos[][] = new int[poblacion][tamanoTablero];
            int auxiliarPosTableroHijos = 0;
            do{
                /*----------------------------------------------
                Seleccion de 2 tableros a traves de la ruleta
                ----------------------------------------------*/
                int seleccion1 = 0;
                int seleccion2 = 0;
                double numeroEntre0y1a = aleatorio.nextDouble();
                //System.out.println("Numero entre 0 y 1: "+numeroEntre0y1a);

                for(int i = 0; i<valorRuleta.length; i++){
                    if(numeroEntre0y1a<=valorRuleta[i]){
                        seleccion1=i;
                        break;
                    }
                }

                
                do{
                    double numeroEntre0y1b = aleatorio.nextDouble();
                    //System.out.println("Numero entre 0 y 1: "+numeroEntre0y1b);
                    for(int i = 0; i<valorRuleta.length; i++){
                        if(numeroEntre0y1b<=valorRuleta[i]){
                            seleccion2=i;
                            break;
                        }
                    }
                }while(seleccion1==seleccion2);
                //System.out.println("Seleccion 1: "+seleccion1+"\nSeleccion 2: "+seleccion2);

                /*----------------------------------------------
                Se realiza la cruza en caso que el valor aleatorio este dentro del rango ingresado por parametro
                ----------------------------------------------*/
                
                
                int auxHijo1[] = new int[tamanoTablero];
                int auxHijo2[] = new int[tamanoTablero];
                double aleatorioCruza = aleatorio.nextDouble();
                if(aleatorioCruza<=probabilidadCruza){
                    int randomNumber = aleatorio.nextInt(tamanoTablero+1);
                    
                        for(int j=0; j<randomNumber; j++){
                            auxHijo1[j]=tableros[seleccion1][j];
                        }
                        for(int k=randomNumber; k<tamanoTablero; k++){
                            auxHijo1[k]=tableros[seleccion2][k];
                        }

                        /*----------------------------------------------
                        Funcion de correccion para el hijo numero 1 para evitar valores repetidos
                        ----------------------------------------------*/
                        auxHijo1=arregloArray2(auxHijo1);
                        /*
                        Asignacion del array auxiliar al tablero de hijos
                        */
                        for(int i =0; i<tamanoTablero; i++){
                            tableroHijos[auxiliarPosTableroHijos][i]=auxHijo1[i];
                        }
                        

                        /*----------------------------------------------
                        Se realiza la mutacion en caso que el valor aleatorio este dentro del rango ingresado por parametro
                        ----------------------------------------------*/
                        double aletorioMutacion = aleatorio.nextDouble();
                        int posicionAletoria2;
                        int posicionAletoria1;
                        int auxPos1;
                        int auxPos2;
                        if(aletorioMutacion<=probabilidadMutacion){
                            posicionAletoria1 = aleatorio.nextInt(tamanoTablero);    
                            do{
                                posicionAletoria2=aleatorio.nextInt(tamanoTablero);  
                            }while(posicionAletoria1==posicionAletoria2);
                            auxPos1=tableroHijos[auxiliarPosTableroHijos][posicionAletoria1];
                            auxPos2=tableroHijos[auxiliarPosTableroHijos][posicionAletoria2];
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria1]=auxPos2;
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria2]=auxPos1;
                        }
                         
                        auxiliarPosTableroHijos=auxiliarPosTableroHijos+1;
                        
                        if(auxiliarPosTableroHijos<poblacion){
                            for(int j=randomNumber; j<tamanoTablero; j++){
                                auxHijo2[j]=tableros[seleccion1][j];
                            }
                            for(int k=0; k<randomNumber; k++){
                                auxHijo2[k]=tableros[seleccion2][k];
                             }

                            /*----------------------------------------------
                            Funcion de correccion para el hijo numero 2 para evitar valores repetidos
                            ----------------------------------------------*/
                            auxHijo2=arregloArray2(auxHijo2);
                            for(int i =0; i<tamanoTablero; i++){
                                tableroHijos[auxiliarPosTableroHijos][i]=auxHijo2[i];
                            }
                            

                            /*----------------------------------------------
                            Se realiza la mutacion en caso que el valor aleatorio este dentro del rango ingresado por parametro
                            ----------------------------------------------*/
                            aletorioMutacion = aleatorio.nextDouble();
                            if(aletorioMutacion<=probabilidadMutacion){
                            posicionAletoria1 = aleatorio.nextInt(tamanoTablero);    
                            do{
                                posicionAletoria2=aleatorio.nextInt(tamanoTablero);  
                            }while(posicionAletoria1==posicionAletoria2);
                            auxPos1=tableroHijos[auxiliarPosTableroHijos][posicionAletoria1];
                            auxPos2=tableroHijos[auxiliarPosTableroHijos][posicionAletoria2];
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria1]=auxPos2;
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria2]=auxPos1;
                        }


                            auxiliarPosTableroHijos=auxiliarPosTableroHijos+1;
                        }

                        
                        
                }
            }while(auxiliarPosTableroHijos<poblacion);   
            
            /*----------------------------------------------
            Se hace el reemplazo de la generacion anterior por la nueva generacion
            ----------------------------------------------*/
            tableros=tableroHijos;
            iteracion=iteracion+1;
        }while((iteracion<iteraciones) && (tableroIdeal==false));

        if(tableroIdeal==false){
            System.out.println("No se ha encontrado un tablero en el que no existan colisiones de reinas");
            System.out.println("El mejor tablero encontrado se encontro en la iteracion: "+iteracionActual+" ,el tablero es:");
            for(int j=0; j<tamanoTablero;j++){
                System.out.print(tableros[posMaxActual][j]+"\t");
            }
            System.out.println("");
            System.out.println("El fitness del tablero es: "+arrayfitnessInv[posMaxActual]);
        }

         
        
    }















    /* 
    Funcion de que entrega los numeros faltantes del hijo
    */

    public static int[] arregloArray2(int[] array){
        int[] numeroFaltante = new int[array.length];
        int aux=0;
        for(int k=0; k<array.length;k++){
             numeroFaltante[k]=0;
         }
        for(int i=1; i<=array.length; i++){
            int count = 0;
            for(int j=0; j<array.length; j++){
                if(array[j]==i){
                    count++;
                }
            }
            if(count==0){
                numeroFaltante[aux]=i;
                aux++;
            }
        }
       
        int nFaltante[] = new int[aux];
        for(int i=0; i<nFaltante.length;i++){
            nFaltante[i]=numeroFaltante[i];
        }
         int count2=0;
         for(int i=0; i<nFaltante.length;i++){
            if(nFaltante[i]!=0){
                count2++;
            }
        }
        if(count2!=0){
            //imprimirArreglo(nFaltante, "numeros faltantes");
            return arregloArray(array, nFaltante, aux);
        }else{
            return array;
        }
        
         

    }




    
   /* 
    Funcion que entrega las posiciones a cambiar
    */
    public static int[] arregloArray(int[] array, int [] nFaltante, int tamano){
         int[] aux = new int[tamano];
         for(int k=0; k<aux.length;k++){
             aux[k]=0;
         }
         int auxNum=0;
        for(int i=0; i<array.length; i++){
            for(int j= i+1; j<array.length; j++){
                if(array[i]==array[j]){
                        int count=0;
                        for(int k=0; k<aux.length; k++){
                            if(j==aux[k]){
                                count++;
                            }
                        }
                        if(count==0){
                        aux[auxNum]=j;
                        auxNum++;
                        }
                        
                }
            }
        }
        //imprimirArreglo(aux, "posiciones a cambiar");
        return funcionCorreccion(array, nFaltante, aux);
    }






    /*
    Funcion que realiza la correccion de un hijo
    */
    public static int[] funcionCorreccion(int[] array, int [] nFaltante, int[] posicionesCambiar){
        int numAuxiliar=0;
        do{
            for(int i=0; i<array.length;i++){
                if(numAuxiliar<nFaltante.length){
                        if(i==posicionesCambiar[numAuxiliar]){
                        //System.out.println("entro al if con num: "+numAuxiliar);
                        array[i]=nFaltante[numAuxiliar];
                        //imprimirArreglo(array, "cambiando");
                        if(numAuxiliar<nFaltante.length){
                            numAuxiliar++;
                            
                        }
                        //System.out.println("salio del if con num"+numAuxiliar);
                        
                    }
                }
            }
        }while(numAuxiliar!=nFaltante.length);
        //imprimirArreglo(array, "arreglo correjido");
        return array;
    }

  


    /* 
    Funcion para imprimir matriz por pantalla
    */
    public static void imprimirArreglo( int[][] matriz,String nombre){
        System.out.println(nombre);
        for (int x=0; x < matriz.length; x++) {
            for (int y=0; y < matriz[x].length; y++) {
              System.out.print (matriz[x][y]);
              if (y!=matriz[x].length-1) System.out.print("\t");
            }
            System.out.println("\n");
          }    
    }

    /* 
    Funcion para imprimir array por pantalla
    */
    public static void imprimirArreglo( int[] matriz,String nombre){
        System.out.println(nombre);
        for (int x=0; x < matriz.length; x++) {
            System.out.print(matriz[x] + "\t");
        }
        System.out.println("");    
    }   

    /* 
    Funcion para imprimir array por pantalla
    */
    public static void imprimirArreglo( double[] matriz,String nombre){
        System.out.println(nombre);
        for (int x=0; x < matriz.length; x++) {
            System.out.print(matriz[x] + "\t");
        }
        System.out.println("");    
    }   

}


