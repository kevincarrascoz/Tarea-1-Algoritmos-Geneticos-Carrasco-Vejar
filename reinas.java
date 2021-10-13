import java.util.*;
import java.util.Random;
public class reinas{
    public static void main(String[] args) {

        if (args.length != 2) {
			System.out.println("Ingresar 2 argumentos, el tamano del tablero y el tamano de la poblacion.");
			System.exit(-1);
		}


        int tamanoTablero = Integer.parseInt(args[0]);
        int poblacion = Integer.parseInt(args[1]);
        int[][] tableros = new int[poblacion][tamanoTablero];
        int[] arrayfitness = new int[poblacion];
        Random r = new Random();

        //inicializacion tableros
        for(int i=0; i<poblacion; i++){
            for(int j=0; j<tamanoTablero; j++){
                tableros[i][j]=j+1;
            }
        }

        //muestra tableros inicializados
        imprimirArreglo(tableros,"Tableros iniciales");


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
        imprimirArreglo(tableros, "Tableros desordenados");
           
        
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
        imprimirArreglo(arrayfitness, "fitness");
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
            
    }   

}


