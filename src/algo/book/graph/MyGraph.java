package algo.book.graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 20:11 2019/10/24
 */

public class MyGraph implements Graph {
    private static final int UNVISITED = 0;
    private static final int VISITED = 1;
    private static final int DEFAULT_LENGTH = 10;
    private int numVertex;                  // we can't delete vertex currently
    private int numEdge;
    private ArrayList<Edge> edges;          // no use currently
    private int[] mark;                 // mark is used to traversals
    private int[][] matrix;             // save the weight of each edege, return 0 if non-exist

    public MyGraph() {
        this(DEFAULT_LENGTH);
    }

    public MyGraph(int numVert) {
        Init(numVert);
    }

    private void Init(int n) {
        numVertex = n;
        numEdge = 0;
        edges = new ArrayList<>();
        mark = new int[n];
        for (int i = 0; i < numVertex; i++)
            mark[i] = UNVISITED;
        matrix = new int[n][n];
    }

    @Override
    public int[] getMarks() {
        return mark;
    }

    @Override
    public int[][] getMatrixs() {
        return matrix;
    }

    @Override
    public int getWeight(int v1, int v2) {
        return matrix[v1][v2];
    }

    @Override
    public int first(int v) {
        for (int i = 0; i < numVertex; i++)
            if (matrix[v][i] != 0)
                return i;
        return numVertex;       // Return n if none
    }

    @Override
    public int next(int v, int w) {
        for (int i = w + 1; i < numVertex; i++)
            if (matrix[v][i] != 0)
                return i;
        return numVertex;       // Return n if none
    }

    @Override
    public void setEdge(int v1, int v2, int wt) {
        if (matrix[v1][v2] == 0)
            numEdge++;
        matrix[v1][v2] = wt;
    }

    @Override
    public void setEdge(int v1, int v2) {
        setEdge(v1, v2, 1);     // default weight set it 1
        setEdge(v2, v1, 1);
    }

    @Override
    public void delEdge(int v1, int v2) {
        if (matrix[v1][v2] != 0)
            numEdge--;
        matrix[v1][v2] = 0;
    }

    @Override
    public boolean isEdge(int v1, int v2) {
        return matrix[v1][v2] != 0;
    }

    @Override
    public int weight(int v1, int v2) {
        return matrix[v1][v2];
    }

    @Override
    public int getMark(int v) {
        return mark[v];
    }

    @Override
    public void setMark(int v, int val) {
        mark[v] = val;
    }

    @Override
    public String toString() {
        String vertices1 = "The number of the vertices in this graph is: " + numVertex;
        String edges1 = "The number of the edges in this graph is: " + numEdge;
        String edges2 = "They are : ";
        String edgess = "";
        String total = vertices1 + "\n" + edges1 + "\n" + edges2 + "\n";
        int temp = 1;
        for (int i = 0; i < matrix.length; i++)            // actually it's numVertex
            for (int j = 0; j < matrix[0].length; j++)
                if (matrix[i][j] != 0) {
                    edgess += "[" + i + "-> " + j + "] , weight: " + matrix[i][j];
                    if (temp++ % 5 == 0)
                        edgess += "\n";
                    else
                        edgess += "      ";
                }
        total += edgess;
        return total;
    }

    @Override
    public int getNumVertex() {
        return numVertex;
    }

    @Override
    public void setNumVertex(int numVertex) {
        this.numVertex = numVertex;
    }

    @Override
    public int getNumEdge() {
        return numEdge;
    }

    @Override
    public void setNumEdge(int numEdge) {
        this.numEdge = numEdge;
    }

    public static void main(String[] args) {
        Graph graph = new MyGraph(5);
        graph.setEdge(0, 1, 10);
        graph.setEdge(0, 2, 3);
        graph.setEdge(0, 3, 20);
        graph.setEdge(1, 3, 5);
        graph.setEdge(2, 1, 2);
        graph.setEdge(2, 4, 15);
        graph.setEdge(3, 4, 11);
        //System.out.println(graph);
        GraphTool tool = new GraphTool();
       // tool.DFSgraphTraverse(graph);
        System.out.println("---");
        tool.dijkstra(graph, 2);
    }
}
