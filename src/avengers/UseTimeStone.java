package avengers;
/**
 * Given a starting event and an Adjacency Matrix representing a graph of all
 * possible
 * events once Thanos arrives on Titan, determine the total possible number of
 * timelines
 * that could occur AND the number of timelines with a total Expected Utility
 * (EU) at
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 * 1. t (int): expected utility (EU) threshold
 * 2. v (int): number of events (vertices in the graph)
 * 3. v lines, each with 2 values: (int) event number and (int) EU value
 * 4. v lines, each with v (int) edges: 1 means there is a direct edge between
 * two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix
 * for a directed
 * graph.
 * The rows represent the "from" vertex and the columns represent the "to"
 * vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2)
 * from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU
 * threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 * To read from a file use StdIn:
 * StdIn.setFile(inputfilename);
 * StdIn.readInt();
 * StdIn.readDouble();
 * 
 * To write to a file use StdOut:
 * StdOut.setFile(outputfilename);
 * //Call StdOut.print() for total number of timelines
 * //Call StdOut.print() for number of timelines with EU >= threshold EU
 * 
 * Compiling and executing:
 * 1. Make sure you are in the ../InfinityWar directory
 * 2. javac -d bin src/avengers/*.java
 * 3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    int t;
    int v;
    int[] eu;
    int[][] matrix;
    boolean[] marked;
    int totalNum;
    int utilTimeLines;
    

    public UseTimeStone() {
        t = StdIn.readInt();
        v = StdIn.readInt();
        eu = new int[v];
        matrix = new int[v][v];
        marked = new boolean[v];
        totalNum = 1; 

        //read in eu for each vertex
        for (int i = 0; i < v; i++) {
            int index = StdIn.readInt();
            eu[index] = StdIn.readInt();
        }

        //read in the adjacency matrix
        for (int r = 0; r < v; r++) {
            for (int c = 0; c < v; c++) {
                matrix[r][c] = StdIn.readInt();
            }
        }
        
        countTimeLines(0);
        utilTimeLines = countTh(matrix,eu,0,0,0,t);
    }

    //count total possible time lines
    private void countTimeLines(int source) {
        
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[source][i] == 1) {
                totalNum += 1;
                countTimeLines(i);
            }
        }
        
    }
    
    //count time lines that is greater than or equal to threshold
    private static int countTh(int[][] graph, int[] e, int s, int sum, int counter, int threshold){
        sum += e[s];
        for(int i = 0; i < graph.length; i++){
            if(graph[s][i]==1){
                counter = countTh(graph,e,i,sum,counter,threshold);
            }
        }
        if(sum >= threshold){
            counter++;
        }
        return counter;
    }
    

    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

        // WRITE YOUR CODE HERE
        String input = args[0];
        String output = args[1];
        StdIn.setFile(input);
        StdOut.setFile(output);

        UseTimeStone timeStone = new UseTimeStone();
        StdOut.println(timeStone.totalNum);
        StdOut.println(timeStone.utilTimeLines);
        
    }

    
}
