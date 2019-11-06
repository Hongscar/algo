package algo.book.graph;


import java.util.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 10:30 2019/11/5
 */

public class GraphTool {
    public static final int UNVISITED = 0;
    public static final int VISITED = 1;
    public static final int INFINITE = Integer.MAX_VALUE;

    public void setUnvisited(Graph g) {
        int[] mark = g.getMarks();
        int numVertexs = g.getNumVertex();
        for (int i = 0; i < numVertexs; i++)
            mark[i] = UNVISITED;
    }

    public void DFSgraphTraverse(Graph g) {
        int[] mark = g.getMarks();
        setUnvisited(g);
        int numVertexs = g.getNumVertex();
        for (int v = 0; v < numVertexs; v++)
            if (mark[v] == UNVISITED)
                DFS(g, v);
    }

    public void BFSgraphTraverse(Graph g) {
        LinkedList<Integer> queue = new LinkedList<>();
        int[] mark = g.getMarks();
        setUnvisited(g);
        BFS(g, 0, queue);
    }

    public void BFS(Graph g, int start, LinkedList<Integer> queue) {
        queue.addFirst(start);
        int[] mark = g.getMarks();
        int numVertexs = g.getNumVertex();
        mark[start] = VISITED;
        while (queue.size() != 0) {
            int v = queue.removeLast();
            preVisit(g, v);
            for (int w = g.first(v); w < numVertexs; w = g.next(v, w))
                if (mark[w] == UNVISITED) {
                    mark[w] = VISITED;
                    queue.addFirst(w);
                }
        }
    }

    public void DFS(Graph g, int v) {
        preVisit(g, v);
        int[] mark = g.getMarks();
        int numVertexs = g.getNumVertex();
        mark[v] = VISITED;
        for (int w = g.first(v); w < numVertexs; w = g.next(v, w))
            if (mark[w] == UNVISITED)
                DFS(g, w);
        // postVisit(g, v);
    }

    public void preVisit(Graph g, int v) {
        System.out.print(v + "---");
    }

    public void postVisit(Graph g, int v) {
        System.out.print(v + "---");        // the reverse order of DFS/BFS it means.
    }

    // use DFS to accomplish topological sort
    // it can't detect whether the graph is acyclic,so it's not good.
    public void topSort(Graph g) {
        LinkedList<Integer> res = new LinkedList<>();
        setUnvisited(g);
        int[] mark = g.getMarks();
        int numVertexs = g.getNumVertex();
        for (int i = 0; i < numVertexs; i++)
            if (mark[i] == UNVISITED)
                topHelper(g, i, res);
        System.out.println(res);
    }

    // 利用DFS实现的Topological,只需要反序输出即可(并不是简单的对DFS的结果反序,而是每一次递归都反序)
    public void topHelper(Graph g, int v, LinkedList<Integer> res) {
        int[] mark = g.getMarks();
        mark[v] = VISITED;
        int numVertexs = g.getNumVertex();
        for (int w = g.first(v); w < numVertexs; w = g.next(v, w))
            if (mark[w] == UNVISITED)
                topHelper(g, w, res);
        res.addFirst(v);
    }

    // true for acyclic, false for cyclic
    public boolean isAcyclic(Graph g) {
        if (!g.getIsDirected())
            return false;           // 目前暂时把非有向图默认为无环,后续再修改 TODO
                                    // 因为我这里仅仅使用拓扑去判断是否有环,而无向图是不存在拓扑这种东西的
        LinkedList<Integer> queue = new LinkedList<>();
        return topSortHelper(g, queue, true);
    }

    // use queue rather than recursion
    public boolean topSortWithQueue(Graph g) {
        LinkedList<Integer> queue = new LinkedList<>();
        return topSortHelper(g, queue, false);     // false for the cyclic graph, true for the DAG
    }

    // if check is true, means it only aim to check whether it's acyclic graph, no need to print
    public boolean topSortHelper(Graph g, LinkedList<Integer> queue, boolean check) {
        int numVertexs = g.getNumVertex();
        int[] indegree = new int[numVertexs];
        int current = 0;                    // to detect whether the graph is acyclic
        String str = "";
        for (int v = 0; v < numVertexs; v++)
            for (int w = g.first(v); w < numVertexs; w = g.next(v, w))
                indegree[w]++;      // process each edge and count the indegree

        for (int v = 0; v < numVertexs; v++)
            if (indegree[v] == 0) {
                queue.addLast(v);
                current++;
            }

        while (queue.size() != 0) {
            int v = queue.removeFirst();
            str += v + "---";        // process the vertices
            for (int w = g.first(v); w < numVertexs; w = g.next(v, w)) {
                indegree[w]--;              // one less prerequisite
                if (indegree[w] == 0) {
                    queue.addLast(w);
                    current++;
                }
            }
        }
        if (current != numVertexs) {
            if (!check)
                System.out.println("The graph is cyclic, it can not be topological sort");
            return false;
        }
        if (!check)
            System.out.println(str);
        return true;
    }

    public void dijkstra(Graph g, int start) {
        int[] mark = g.getMarks();
        int numVertexs = g.getNumVertex();
        int[] distance = initDijkstra(numVertexs, start);
        for (int i = 0; i < numVertexs; i++) {
            int v = minVertex(g, distance);
            if (v == -1 || distance[v] == INFINITE)     // seems v == -1 is no use
                break;
            mark[v] = VISITED;
            for (int w = g.first(v); w < numVertexs; w = g.next(v, w))
                distance[w] = distance[w] > (distance[v] + g.getWeight(v, w)) ?
                        distance[v] + g.getWeight(v, w) : distance[w];
            // v is the closest vertex between the start point (get it in the checking edges)
            // then check the edge among with the vertex v, for example, w.
            // try to update distance[w], consist that distance[v] is the shortest path from start to v
            // then distance[w] is Math.min (distance[w], distance[v] + g.getWeight(v, w))
        }
        Integer[] dis = Arrays.stream(distance).boxed().toArray(Integer[]::new);
        ArrayList<Integer> res = new ArrayList<>(Arrays.asList(dis));
        System.out.println(res);
    }

    public int[] initDijkstra(int length, int start) {
        int[] distance = new int[length];
//        int numVertexs = g.getNumVertex();
        for (int i = 0; i < length; i++)
            distance[i] = i == start ? 0 : INFINITE;
//        for (int v = g.first(start); v < numVertexs; v = g.next(start, v))
//            distance[v] = g.getWeight(start, v);
        return distance;
    }

    public int minVertex(Graph g, int[] distance) {
        int i, v = -1;
        int numVertexs = g.getNumVertex();
        int[] mark = g.getMarks();
        for (i = 0; i < numVertexs; i++)
            if (mark[i] == UNVISITED) {     // find an unvisited vertex firstly, then set it to v
                v = i;                      // Initialize v to some unvisited vertex.
                break;
            }
        for (i++; i < numVertexs; i++)
            if (mark[i] == UNVISITED && distance[i] < distance[v])
                v = i;                      // Now find smallest vertex value that's unvisited
        return v;
    }

    // TODO
    public void prim(Graph g, int start) {
        int numVertexs = g.getNumVertex();
        setUnvisited(g);
        int[] distance = new int[numVertexs];
        distance = initDijkstra(numVertexs, start);
        List<Integer> vertexs = new ArrayList<>();          // Store closest vertex
        for (int i = 0; i < numVertexs; i++)
            vertexs.add(0);
        int[] mark = g.getMarks();
        for (int i = 0; i < numVertexs; i++) {              // Process the vertices
            int v = minVertex(g, distance);
            mark[v] = VISITED;
            if (v != start)
                addEdgeToMST(vertexs, v);                   // Add edge to MST
            if (distance[v] == INFINITE)
                return;                                     // Unreachable vertices
            for (int w = g.first(v); w < numVertexs; w = g.next(v, w))
                if (distance[w] > g.getWeight(v, w)) {
                    distance[w] = g.getWeight(v, w);        // Update distance
                    vertexs.set(w, v);                         // Where it came from
                }
        }
        System.out.println(vertexs);
        for (int dis: distance)
            System.out.print(dis + ", ");
        System.out.println();
        int re = 0;
        for (int ver: vertexs)
            re += ver;
        System.out.println(re);
    }

    public void addEdgeToMST(List<Integer> vertexs, int v) {
        // form a minimum-cost spanning tree
    }

    public static void main(String[] args) {
        Graph g = new MyGraph(6);
        g.setEdge(0, 2);
        g.setEdge(0, 4);
        g.setEdge(1, 2);
        g.setEdge(1, 5);
        g.setEdge(2, 3);
        g.setEdge(2, 5);
        g.setEdge(3, 5);
        g.setEdge(4, 5);
        GraphTool tool = new GraphTool();

        //tool.BFSgraphTraverse(g);
        Graph g2 = new MyGraph(8);
        g2.setEdge(0, 1, 11);
        g2.setEdge(1, 3, 11);
        g2.setEdge(3, 4, 11);
        g2.setEdge(0, 5, 11);
        g2.setEdge(5, 6, 11);
        g2.setEdge(1, 7, 11);
        g2.setEdge(7, 2, 11);
        g2.setEdge(6, 2, 11);
        g2.setEdge(2, 4, 11);
        //tool.DFSgraphTraverse(g2);
//        System.out.println();
        //tool.BFSgraphTraverse(g2);
//        System.out.println();
       // tool.topSort(g2);
       // System.out.println();
       // tool.topSortWithQueue(g2);
        Graph g3 = new MyGraph(5);
        g3.setEdge(0, 1, 12);
        g3.setEdge(1, 2, 12);
        // g3.setEdge(2, 3, 122);
        g3.setEdge(3, 4, 12);
        g3.setEdge(4, 1, 12);
        System.out.println("---=-=---");
       // tool.topSortWithQueue(g3);
        //System.out.println(tool.isAcyclic(g3));

        Graph g4 = new MyGraph(6, false);
        g4.setEdge(0, 2, 7);
        g4.setEdge(0, 4, 9);
        g4.setEdge(1, 2, 5);
        g4.setEdge(1, 5, 6);
        g4.setEdge(2, 3, 1);
        g4.setEdge(2, 5, 2);
        g4.setEdge(3, 5, 2);
        g4.setEdge(4, 5, 1);
        //tool.DFSgraphTraverse(g4);
        System.out.println();
        //tool.BFSgraphTraverse(g4);
        System.out.println();
        //System.out.println(tool.isAcyclic(g4));
        //tool.topSort(g4);
        System.out.println();
        tool.prim(g4, 0);

        Graph g5 = new MyGraph(6, false);
        g5.setEdge(0, 1, 10);
        g5.setEdge(0, 3, 20);
        g5.setEdge(0, 2, 5);
        g5.setEdge(1, 2, 3);
        g5.setEdge(1, 3, 5);
        g5.setEdge(2, 4, 15);
        g5.setEdge(3,4, 11);
        g5.setEdge(4, 5, 3);
        g5.setEdge(3, 5, 10);
        tool.prim(g5, 0);
    }
}
