package algo.book.graph;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 20:12 2019/10/24
 */

public class Edge {
    public int vert, wt;

    public Edge() {
        vert = -1;
        wt = -1;
    }

    public Edge(int v, int w) {
        vert = v;
        wt = w;
    }

    public int vertex() {
        return vert;
    }

    public int weight() {
        return wt;
    }
}
