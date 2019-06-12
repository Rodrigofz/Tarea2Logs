package active_point;

import edge.Edge;
import node.Node;

import java.util.ArrayList;

public class ActivePoint {
    public boolean run;
    public boolean isRoot;
    public Node root;
    public ArrayList<Edge> leafEdges;
    public Node lastSplited;
    public String toInsert;
    public int remainder;

    public Node active_node;
    public Edge active_edge;
    public int active_length;

    public ActivePoint(Node n){
        isRoot = true;
        root = n;
        leafEdges = new ArrayList<Edge>();
        toInsert = "";
        remainder = 0;

        active_node = n;
        active_edge = null;
        active_length = 0;
    }

    public ActivePoint insert(char c){
        return active_node.insert(this, c);
    }

    public char getFirstChar(){
        return toInsert.charAt(0);
    }

    public void checkEdge(){
        System.out.println("Checking ae " + active_edge + " and al" + active_length);
        if(active_edge.getLabelLength() <= active_length){
            active_node = active_edge.getNode();
            active_edge = null;
            active_length = 0;
            isRoot = false;
        }
    }
}
