import java.util.Random;
import java.util.Arrays;
public class reinas
{
   
    static void rand( int array[], int a)
    {
        // Creating object for Random class
        Random rd = new Random();
         
        // Starting from the last element and swapping one by one.
        for (int i = a-1; i > 0; i--) {
             
            // Pick a random index from 0 to i
            int j = rd.nextInt(i+1);
             
            // Swap array[i] with the element at random index
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        // Printing the random generated array
        System.out.println(Arrays.toString(array));
    }
     
    public static void main(String[] args)
    {
         int tamanoTablero = 20;
         int poblacion = 3;
         int[] ar = new int[tamanoTablero];
         int b = ar.length;
         for(int i=0; i<tamanoTablero; i++){
            
                ar[i]=i+1;
            }
          for(int j=0; j<tamanoTablero; j++){
            
                 rand (ar, b);
            }
    }
}




/**import java.util.*;
public class reinas{
    public static void main(String[] args) {
        int tamanoTablero = 4;
        int poblacion = 3;

        int[][] tableros = new int[poblacion][tamanoTablero];

        for(int i=0; i<poblacion; i++){
        
            for(int j=0; j<tamanoTablero; j++){
            
                tableros[i][j]=j+1;
            }
  
        }


        
        
       
        
        
        for (int x=0; x < tableros.length; x++) {
            for (int y=0; y < tableros[x].length; y++) {
              System.out.print (tableros[x][y]);
              if (y!=tableros[x].length-1) System.out.print("\t");
            }
            System.out.println("\n");
          }

    }
}**/