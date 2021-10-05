import java.util.*;
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
}