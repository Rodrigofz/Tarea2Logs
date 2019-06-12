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
    public int counter;

    public Node active_node;
    public Edge active_edge;
    public int active_length;

    public ActivePoint(Node n){
        isRoot = true;
        root = n;
        leafEdges = new ArrayList<Edge>();
        toInsert = "";
        remainder = 0;
        counter = 0;

        active_node = n;
        active_edge = null;
        active_length = 0;
    }

    public ActivePoint insert(char c, boolean round){
        return active_node.insert(this, c, round);
    }

    public char getFirstChar(){
        return toInsert.charAt(0);
    }

    public int getCounter() {counter++; return counter;}

    //TODO: Revisar si esto es un while.
    public void checkEdge(){
        //System.out.println("Checking ae " + active_edge + " and al " + active_length);
        if(active_edge.getLabelLength() <= active_length){
            System.out.println("Changing active point...");
            active_node = active_edge.getNode();
            active_edge = null;
            active_length = 0;
            isRoot = false;
        }
    }
}
