package active_point;

import edge.Edge;
import node.Node;

import java.util.ArrayList;

public class ActivePoint {
    public boolean root;
    public ArrayList<Edge> leafEdges;

    public Node active_node;
    public Edge active_edge;
    public int active_length;

    public ActivePoint(Node n){
        root = true;
        leafEdges = new ArrayList<Edge>();

        active_node = n;
        active_edge = null;
        active_length = 0;
    }

    public ActivePoint insert(char c){
        return active_node.insert(this, c);
    }
}
