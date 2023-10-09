package avengers;
/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
    
    int p;
    int [][] matrix;
    boolean [] removed;
    boolean [] marked;

    public PredictThanosSnap(){
        long seed = StdIn.readLong();
        p = StdIn.readInt();
        matrix = new int[p][p];
        removed = new boolean[p];
        marked = new boolean [p];

        //read in adjacency matrix
        for(int r = 0; r < p && StdIn.hasNextChar(); r++){
            for(int c = 0; c < p && StdIn.hasNextChar(); c++){
                matrix[r][c] = StdIn.readInt();
            }
        }

        //remove vertices randomly
        StdRandom.setSeed(seed);
        for(int i = 0; i<p; i++){
            if(StdRandom.uniform()<=0.5){
                removed[i] = true;
                for(int j = 0; j<p; j++){
                    matrix[i][j] = 0;
                    matrix[j][i]= 0;
                }
            }
        }
    }

    private static int findSource(boolean[] r){
        for(int i = 0; i<r.length; i++){
            if(r[i]==false){
                return i;
            }
        }
        return -1;
    }

    private static void dfs(int[][] g, int s, boolean[] m)
    {
        m[s] = true;
        for (int w = 0; w < g.length; w++){
            if (m[w]==false && g[s][w]!=0){
                dfs(g, w, m);
            }
        }  
    } 

    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
    	// WRITE YOUR CODE HERE
        String input = args[0];
        String output = args[1];
        StdIn.setFile(input);
        StdOut.setFile(output);

        PredictThanosSnap thanos = new PredictThanosSnap();

        //TEST print removed
        // for (boolean tf: thanos.removed){
        //     System.out.print(tf+" ");
        // }
        // System.out.println("");
        
        //dfs
        int source = findSource(thanos.removed);
        dfs(thanos.matrix, source, thanos.marked);
        boolean connected = true; 

        //TEST print marked
        // for (boolean tf: thanos.marked){
        //     System.out.print(tf+" ");
        // }

        for(int i = 0; i<thanos.p; i++){
            if(thanos.removed[i] == false && thanos.marked[i] == false){
                connected = false;
            }
        }
        StdOut.print(connected);
        

    }
    
    
}
