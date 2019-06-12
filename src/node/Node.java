package node;

import active_point.ActivePoint;
import edge.Edge;
import java.util.ArrayList;

public class Node {
    private ArrayList<Edge> edges;
    private int edges_count;
    public int label;
    private Node suffixLink;

    public Node(int n){
        edges = new ArrayList<Edge>();
        edges_count = 0;
        label = n;
    }

    public Node(ArrayList<Edge> edges){
        this.edges = edges;
        this.edges_count = edges.size();
    }

    public Edge link(Node son, String label) {
        Edge link = new Edge(son, label);
        this.edges.add(link);
        this.edges_count++;
        System.out.println("Linking: " + this.label + " with " + son.label);
        return link;
    }

    public Edge getEdge(char c){
        for(Edge ed : edges){
            if(ed.getLabel().charAt(0) == c){
                return ed;
            }
        }
        return null;
    }

    public ActivePoint insert(ActivePoint ap, char c, boolean round){
        if(ap.active_length == 0){
            for(Edge e : edges){
                if(e.getLabel().charAt(0) == c){
                    ap.active_length ++;
                    ap.active_edge = e;
                    ap.run = false;
                    ap.checkEdge();
                    return ap;
                }
            }
            Node new_node = new Node(ap.getCounter());
            Edge new_edge = this.link(new_node, "" + c);
            ap.toInsert = "";
            ap.leafEdges.add(new_edge);
            ap.run = false;
            ap.remainder--;
            return ap;
        }
        else{
            return ap.active_edge.insert(ap, c, round);
        }
    }

    public void setSuffixLink(Node node){
        suffixLink = node;
    }

    public Node getSuffixLink(){
        return suffixLink;
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    @Override
    public String toString(){
        return "Node " + label;
    }

    public void printTree(int level){
        if(edges_count == 0){
            return;
        }
        else{
            for(int i=0; i<edges_count; i++){
                StringBuilder b = new StringBuilder();
                for(int j=0; j<level; j++){
                    b.append("\t");
                }
                System.out.println(b.toString() + this.label + "----" + edges.get(i).getLabel() + "---->" + edges.get(i).getNode().label);
                edges.get(i).getNode().printTree(level+1);
            }
        }
    }

}