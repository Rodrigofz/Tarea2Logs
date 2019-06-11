package node;

import active_point.ActivePoint;
import edge.Edge;
import java.util.ArrayList;

public class Node {
    private ArrayList<Edge> edges;
    private int edges_count;
    private Node suffixLink;

    public Node(){
        edges = new ArrayList<Edge>();
        edges_count = 0;
    }

    public Node(ArrayList<Edge> edges){
        this.edges = edges;
        this.edges_count = edges.size();
    }

    public Edge link(Node son, String label) {
        Edge link = new Edge(son, label);
        this.edges.add(link);
        this.edges_count++;
        System.out.println("Joined link: "+label);
        return link;
    }

    public Edge getEdge(Edge e){
        for(Edge ed : edges){
            if(e.getLabel() == ed.getLabel()){
                return ed;
            }
        }
        return null;
    }

    public ActivePoint insert(ActivePoint ap, char c){
        if(ap.active_length == 0){
            for(Edge e : edges){
                if(e.getLabel().charAt(0) == c){
                    ap.active_length ++;
                    ap.active_edge = e;
                    return ap;
                }
            }
            Node new_node = new Node();
            Edge new_edge = this.link(new_node, "" + c);
            ap.leafEdges.add(new_edge);
            return ap;
        }
        else{
            return ap.active_edge.insert(ap, c);
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
                System.out.println(b.toString() + "----" + edges.get(i).getLabel() + "---->");
                edges.get(i).getNode().printTree(level+1);
            }
        }
    }

}