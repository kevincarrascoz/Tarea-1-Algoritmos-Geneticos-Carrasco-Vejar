import java.util.*;
import java.util.Random;
public class reinas{
    public static void main(String[] args) {
        int tamanoTablero = 4;
        int poblacion = 4;

        Random r = new Random();

        int[][] tableros = new int[poblacion][tamanoTablero];

        //inicializacion tableros
        for(int i=0; i<poblacion; i++){
        
            for(int j=0; j<tamanoTablero; j++){
            
                tableros[i][j]=j+1;
            }
  
        }

        //muestra tableros inicializados
        for (int x=0; x < tableros.length; x++) {
            for (int y=0; y < tableros[x].length; y++) {
              System.out.print (tableros[x][y]);
              if (y!=tableros[x].length-1) System.out.print("\t");
            }
            System.out.println("\n");
          }
          System.out.println("\n\n\n");


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
        for (int x=0; x < tableros.length; x++) {
            for (int y=0; y < tableros[x].length; y++) {
              System.out.print (tableros[x][y]);
              if (y!=tableros[x].length-1) System.out.print("\t");
            }
            System.out.println("\n");
          }
           
        


        //fitness  
          for(int i=0; i<poblacion; i++){
            int fitness = 0;
            int[] auxdiagonal = new int[tamanoTablero];
            int[] auxinversa = new int[tamanoTablero];
        
            for(int j=0; j<tamanoTablero; j++){
              auxdiagonal[j] = ((j+1) - tableros[i][j]);
              auxinversa[j] = Math.abs((j+1) +tableros[i][j]);  
              //System.out.print(auxdiagonal[j]+"\t");
              //System.out.println(auxinversa[j]);

            }
            

            for(int z=0; z<tamanoTablero; z++){
                for(int x=0; x<tamanoTablero; x++){
                    
                    if(z!=x){
                        if(auxdiagonal[z]==auxdiagonal[x]){

                            fitness=fitness+1;
                            //System.out.println("aca sume 1 en diagonal:"+auxdiagonal[z]+" "+auxdiagonal[x]);
                        }    
                        if(auxinversa[z]==auxinversa[x]){

                            fitness=fitness+1;
                            //System.out.println("aca sume 1 en inversa:"+auxdiagonal[z]+" "+auxdiagonal[x]);
                        } 
                    }

                      
                }
                
            }
            System.out.println("fitness tablero " +(i+1)+": "+fitness/2 +"\n");
  
        }
       
        
        
        

    }
}