package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }
        

    	// WRITE YOUR CODE HERE
        String inputfilename = args[0];
        String outputfilename = args[1];
        StdIn.setFile(inputfilename);

        int n = StdIn.readInt();
        double[] func = new double[n];
        int [][] generators = new int[n][n];
        for(int i = 0; i<n && StdIn.hasNextLine(); i++){
            int index = StdIn.readInt();
            func[index] = StdIn.readDouble();
        }
        for(int r = 0; r < n && StdIn.hasNextLine(); r++){
            for(int c = 0; c < n && StdIn.hasNextLine(); c++){
                generators[r][c] = StdIn.readInt();
                if(generators[r][c] != 0){
                    
                    generators[r][c] =  (int)(generators[r][c]/(func[r]*func[c]));
                }
                //System.out.print(generators[r][c]+" ");
            }
            //System.out.println("");
        }

        //Dijkstra’s Algorithm
        int[] minCost = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i<n; i++){
            if(i != 0){
                minCost[i] = Integer.MAX_VALUE;
            }
        }

        //iterate n-1 times
        for(int i = 1; i<n; i++){
            int currentSource = getMinCost(minCost,visited);
            visited[currentSource] = true;
            for(int j = 0; j < n; j++){
                if(generators[currentSource][j] != 0){
                    if(visited[j]==false && minCost[currentSource] != Integer.MAX_VALUE && minCost[currentSource] + generators[currentSource][j] < minCost[j]){
                        minCost[j] = minCost[currentSource] + generators[currentSource][j];
                    }
                }
            }
        }

        //TEST print out minCost
        // for(int i = 0; i<n; i++){
        //     System.out.print(minCost[i]+" ");
        //     System.out.print(visited[i]+" ");
        // }

        //System.out.println(minCost[n-1]);
        StdOut.setFile(outputfilename);
        StdOut.print(minCost[n-1]);

    }
    
    private static int getMinCost(int[] a, boolean[] b){
        
        int minValue = Integer.MAX_VALUE; 
        int minIndex = 0;
        for(int i=1;i<a.length;i++){ 
            if(a[i] < minValue && b[i] == false){ 
                minValue = a[i]; 
                minIndex = i;
            }
        } 
        return minIndex; 
        
    }
}
