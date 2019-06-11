package active_point;

import edge.Edge;
import node.Node;

import java.util.ArrayList;

public class ActivePoint {
    public boolean isRoot;
    public Node root;
    public ArrayList<Edge> leafEdges;
    public Node lastSplited;
    public ArrayList<String> toInsert;

    public Node active_node;
    public Edge active_edge;
    public int active_length;

    public ActivePoint(Node n){
        isRoot = true;
        root = n;
        leafEdges = new ArrayList<Edge>();
        toInsert = new ArrayList<String>();

        active_node = n;
        active_edge = null;
        active_length = 0;
    }

    public ActivePoint insert(char c){
        return active_node.insert(this, c);
    }

    public char getFirstChar(){
        return toInsert.get(0).charAt(0);
    }
}
