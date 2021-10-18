import java.security.Policy;
import java.util.*;
import java.util.Random;
public class reinas{
    public static void main(String[] args) {

        if (args.length != 3) {
			System.out.println("Ingresar 3 argumentos, el tamano del tablero,el tamano de la poblacion y la semilla respectivamente.");
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
        
        //numero aleatorio entre 0 y n
        aleatorio.setSeed(semilla);
        for(int i=0; i<10; i++){
            int randomNumber = aleatorio.nextInt(tamanoTablero+1);
            System.out.println("Random "+(i+1)+": "+randomNumber);
        }
        

        

        //calculo del fitness maximo (cantidad maxima de colisiones)
        int fitnessmax=0;
        for(int j=(tamanoTablero-1); j>0; j--){
            fitnessmax=fitnessmax+j;
        }
        System.out.println("el fitness max es: "+fitnessmax);

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
        //imprimirArreglo(arrayfitness, "fitness");


        //inversion del fitness para poder obtener la proporcion de cada tablero
        for(int i=0; i<arrayfitnessInv.length; i++){
            arrayfitnessInv[i]=fitnessmax-arrayfitness[i];
        }
        //calculo de la suma de los fitness
        int sumaProporcion=0;
        for(int i=0; i<arrayfitnessInv.length; i++){
            sumaProporcion=sumaProporcion+arrayfitnessInv[i];
        }
        System.out.println("Suma: "+sumaProporcion);
        
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

        //asignar la ruleta a un resultado
        int seleccion1 = 0;
        int seleccion2 = 0;
        //numero aleatorio entre 0 y 1
        double numeroEntre0y1a = aleatorio.nextDouble();
        System.out.println("Numero entre 0 y 1: "+numeroEntre0y1a);

        for(int i = 0; i<valorRuleta.length; i++){
            if(numeroEntre0y1a<=valorRuleta[i]){
                seleccion1=i;
                break;
            }
        }

        //numero aleatorio entre 0 y 1
        do{
            double numeroEntre0y1b = aleatorio.nextDouble();
            System.out.println("Numero entre 0 y 1: "+numeroEntre0y1b);
            for(int i = 0; i<valorRuleta.length; i++){
                if(numeroEntre0y1b<=valorRuleta[i]){
                    seleccion2=i;
                    break;
                }
            }
        }while(seleccion1==seleccion2);
        System.out.println("Seleccion 1: "+seleccion1+" Seleccion 2: "+seleccion2);


        
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


