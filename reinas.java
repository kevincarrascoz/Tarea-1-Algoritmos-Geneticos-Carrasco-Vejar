import java.security.Policy;
import java.util.*;
import java.util.Random;
public class reinas{
    public static void main(String[] args) {

        if (args.length != 6) {
			System.out.println("Ingresar 6 argumentos, el tamano del tablero,el tamano de la poblacion, la semilla, la prob de cruza,la prob de mutacion y la cantidad de iteraciones respectivamente");
			System.exit(-1);
		}


        int tamanoTablero = Integer.parseInt(args[0]);
        int poblacion = Integer.parseInt(args[1]);
        int[][] tableros = new int[poblacion][tamanoTablero];
        int[] arrayfitness = new int[poblacion];
        int[] arrayfitnessInv= new int[poblacion];
        Random r = new Random();
        Random aleatorio = new Random();
        Integer semilla = Integer.parseInt(args[2]);
        double probabilidadCruza = Double.parseDouble(args[3]);
        double probabilidadMutacion = Double.parseDouble(args[4]);
        Integer iteraciones = Integer.parseInt(args[5]);
        boolean tableroIdeal = false;
        
        //set semilla
        aleatorio.setSeed(semilla);
        /*for(int i=0; i<tamanoTablero; i++){
            int randomNumber = aleatorio.nextInt(tamanoTablero+1);
            //System.out.println("Random "+(i+1)+": "+randomNumber);
        }*/
        
        

        int iteracion = 0;

        //calculo del fitness maximo (cantidad maxima de colisiones)
        int fitnessmax=0;
        for(int j=(tamanoTablero-1); j>0; j--){
            fitnessmax=fitnessmax+j;
        }
        //System.out.println("el fitness max es: "+fitnessmax);

        //inicializacion tableros
        for(int i=0; i<poblacion; i++){
            for(int j=0; j<tamanoTablero; j++){
                tableros[i][j]=j+1;
            }
        }

        //muestra tableros inicializados
        //imprimirArreglo(tableros,"Tableros iniciales");


        //desordena los tableros
        for(int i=0; i<poblacion; i++){
            for(int j=0; j<tamanoTablero; j++){
                int posAleatoria = r.nextInt(tamanoTablero);
                int temp = tableros[i][j];
                tableros[i][j] = tableros[i][posAleatoria];
                tableros[i][posAleatoria] = temp;
            }
        }

        do{
            //muestra tableros desordenados
            //imprimirArreglo(tableros, "Tableros desordenados");
            
            
            //calculo del fitness por tablero  
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
                //guardar el fitness de cada tablero en un arreglo
                arrayfitness[i]=fitness/2;
                //System.out.println("fitness tablero " +(i+1)+": "+fitness/2 +"\n");
            }

            
            //imprimirArreglo(arrayfitness, "fitness normal");


            //inversion del fitness para poder obtener la proporcion de cada tablero
            for(int i=0; i<arrayfitnessInv.length; i++){
                arrayfitnessInv[i]=fitnessmax-arrayfitness[i];
                if(arrayfitnessInv[i]==0){
                    arrayfitnessInv[i]=1;
                }
            }
            //imprimirArreglo(arrayfitnessInv, "fitness invertido");

            for(int i = 0; i<arrayfitness.length; i++){
                if(arrayfitnessInv[i]==fitnessmax){
                    System.out.println("El tablero sin colisiones fue encontrado en la iteracion: "+iteracion+", el tablero es: ");
                    tableroIdeal = true;
                    for(int j=0; j<tamanoTablero;j++){
                        System.out.print(tableros[i][j]+"\t");
                    }
                    System.out.println("");
                    break;
                }
            }

            //calculo de la suma de los fitness
            int sumaProporcion=0;
            for(int i=0; i<arrayfitnessInv.length; i++){
                sumaProporcion=sumaProporcion+arrayfitnessInv[i];
            }
            //System.out.println("Suma: "+sumaProporcion);
            
            //calculo proporcion de cada fitness
            double[] arrayProporciones = new double[poblacion];
            for(int i=0; i<arrayfitnessInv.length; i++){
                arrayProporciones[i]=Double.valueOf(arrayfitnessInv[i])/Double.valueOf(sumaProporcion);
            }
            //imprimirArreglo(arrayProporciones, "proporciones");


            //calculo valor para ruleta
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
                //asignar la ruleta a un resultado
                int seleccion1 = 0;
                int seleccion2 = 0;
                //numero aleatorio entre 0 y 1
                double numeroEntre0y1a = aleatorio.nextDouble();
                //System.out.println("Numero entre 0 y 1: "+numeroEntre0y1a);

                for(int i = 0; i<valorRuleta.length; i++){
                    if(numeroEntre0y1a<=valorRuleta[i]){
                        seleccion1=i;
                        break;
                    }
                }

                

                //numero aleatorio entre 0 y 1
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

                //cruza
                //numero aleatorio entre 0 y n
                
                int auxHijo1[] = new int[tamanoTablero];
                int auxHijo2[] = new int[tamanoTablero];
                //System.out.println("posicion: "+auxiliarPosTableroHijos);
                double aleatorioCruza = aleatorio.nextDouble();
                //System.out.println("aleatorio cruza: "+aleatorioCruza+"prob cruza: "+probabilidadCruza);
                if(aleatorioCruza<=probabilidadCruza){
                    int randomNumber = aleatorio.nextInt(tamanoTablero+1);
                    //System.out.println("numero aleatorio: "+randomNumber);

                    
                        for(int j=0; j<randomNumber; j++){
                            auxHijo1[j]=tableros[seleccion1][j];
                            //tableroHijos[auxiliarPosTableroHijos][j]=tableros[seleccion1][j];
                        }
                        for(int k=randomNumber; k<tamanoTablero; k++){
                            auxHijo1[k]=tableros[seleccion2][k];
                            //tableroHijos[auxiliarPosTableroHijos][k]=tableros[seleccion2][k];
                        }

                        
                        //imprimirArreglo(auxHijo1, "hijo sin arreglo");
                        auxHijo1=arregloArray2(auxHijo1);
                        for(int i =0; i<tamanoTablero; i++){
                            tableroHijos[auxiliarPosTableroHijos][i]=auxHijo1[i];
                        }
                        //imprimirArreglo(tableroHijos, "tablero hijo arreglado");

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
                            //System.out.println("Hubo una mutacion entre la posicion: "+posicionAletoria1+" y: "+posicionAletoria2);
                            auxPos1=tableroHijos[auxiliarPosTableroHijos][posicionAletoria1];
                            auxPos2=tableroHijos[auxiliarPosTableroHijos][posicionAletoria2];
                            //imprimirArreglo(tableroHijos,"estaba asi");
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria1]=auxPos2;
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria2]=auxPos1;
                            //imprimirArreglo(tableroHijos,"quedo asa");
                        }
                         
                        
                        auxiliarPosTableroHijos=auxiliarPosTableroHijos+1;
                        //imprimirArreglo(auxiliar, "hijo 1");

                        if(auxiliarPosTableroHijos<poblacion){
                            //imprimirArreglo(tableroHijos, "tablero de hijos");
                            for(int j=randomNumber; j<tamanoTablero; j++){
                                auxHijo2[j]=tableros[seleccion1][j];
                                //tableroHijos[auxiliarPosTableroHijos][j]=tableros[seleccion1][j];
                            }
                            for(int k=0; k<randomNumber; k++){
                                auxHijo2[k]=tableros[seleccion2][k];
                                //tableroHijos[auxiliarPosTableroHijos][k]=tableros[seleccion2][k];
                            }

                            //imprimirArreglo(auxHijo2, "hijo sin arreglo");
                            auxHijo2=arregloArray2(auxHijo2);
                            for(int i =0; i<tamanoTablero; i++){
                                tableroHijos[auxiliarPosTableroHijos][i]=auxHijo2[i];
                            }
                            //imprimirArreglo(tableroHijos, "tablero hijo arreglado");

                            aletorioMutacion = aleatorio.nextDouble();
                            if(aletorioMutacion<=probabilidadMutacion){
                            posicionAletoria1 = aleatorio.nextInt(tamanoTablero);    
                            do{
                                posicionAletoria2=aleatorio.nextInt(tamanoTablero);  
                            }while(posicionAletoria1==posicionAletoria2);
                            //System.out.println("Hubo una mutacion entre la posicion: "+posicionAletoria1+" y: "+posicionAletoria2);
                            auxPos1=tableroHijos[auxiliarPosTableroHijos][posicionAletoria1];
                            auxPos2=tableroHijos[auxiliarPosTableroHijos][posicionAletoria2];
                            //imprimirArreglo(tableroHijos,"estaba asi");
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria1]=auxPos2;
                            tableroHijos[auxiliarPosTableroHijos][posicionAletoria2]=auxPos1;
                            //imprimirArreglo(tableroHijos,"quedo asa");
                        }


                            auxiliarPosTableroHijos=auxiliarPosTableroHijos+1;
                            //imprimirArreglo(auxiliar2, "hijo 2");
                        }

                        
                        
                }
            }while(auxiliarPosTableroHijos<poblacion);   
            //imprimirArreglo(tableroHijos, "tablero de hijos");
            tableros=tableroHijos;
            iteracion=iteracion+1;
        }while((iteracion<iteraciones) && (tableroIdeal==false));

        if(tableroIdeal==false){
            System.out.println("No se ha encontrado un tablero en el que no existan colisiones de reinas");
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


