package algo.book.graph;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 20:09 2019/10/24
 */
public interface Graph {

    public int getWeight(int v1, int v2);

    public int first(int v);

    public int next(int v, int w);

    public void setEdge(int v1, int v2, int wt);

    //no weight graph
    public void setEdge(int v1, int v2);

    public void delEdge(int v1, int v2);

    public int weight(int v1, int v2);

    public int[] getMarks();

    public int[][] getMatrixs();

    public boolean isEdge(int v1, int v2);

    public int getMark(int k);

    public void setMark(int k, int val);

    public int getNumVertex();

    public int getNumEdge();

    public void setNumVertex(int vertex);

    public void setNumEdge(int edge);

}
