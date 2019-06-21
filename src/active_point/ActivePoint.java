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
    public String partial_string;

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
        partial_string = "";

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

    public int getCounter() {counter++; return counter;}

    public void checkEdge(){
        if(active_edge.getLabelLength() <= active_length){
            active_node = active_edge.getNode();
            int ex_label_l = active_edge.getLabelLength();
            active_length -= active_edge.getLabelLength();
            active_edge = null;
            isRoot = false;

            if(active_length > 0){
                for(Edge e : active_node.getEdges()){
                    if(e.getLabel().charAt(0) == toInsert.charAt(ex_label_l)){
                        active_edge = e;
                    }
                }
                if(active_edge!=null){
                    this.checkEdge();
                }
            }
        }


    }
}
