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

        if(String.valueOf(c).equals(ap.partial_string)){
            if (round) {
                System.out.println(c + " already on partial string, not inserting");
            }
            ap.remainder++;
            ap.partial_string = "";
            ap.run = false;
            return ap;
        }

        if(ap.active_length == 0){
            System.out.println("");
            for(Edge e : edges){
                if(e.getLabel().charAt(0) == c){
                    if(round){
                        System.out.println(c + " already on label, changing active point");
                    }
                    ap.toInsert = ap.toInsert + c;
                    ap.remainder++;
                    ap.active_length ++;
                    ap.active_edge = e;
                    ap.run = false;
                    ap.partial_string = "";
                    if(!round){
                        ap.remainder++;
                    }
                    ap.checkEdge();
                    return ap;
                }
            }
            System.out.println("Inserting " + c + " directly into node");
            Node new_node = new Node(ap.getCounter());
            Edge new_edge = this.link(new_node, "" + c);

            if(!round){
                if(ap.toInsert.length() == 1){
                    ap.toInsert = "";
                    ap.run = false;
                }
                else{
                    ap.toInsert = ap.toInsert.substring(1);
                    ap.run = true;
                }
                ap.remainder = (ap.remainder==0)? 0 : ap.remainder-1;
            }
            else{
                ap.run = false;
            }
            ap.leafEdges.add(new_edge);

            //Rule 2
            if(!ap.isRoot && ap.lastSplited != null && ap.lastSplited.label != 0){
                ap.lastSplited.setSuffixLink(this);
            }

            //Rule 3
            if(!ap.isRoot){
                if(this.getSuffixLink()!=null){
                    ap.active_node = this.getSuffixLink();
                }
                else{
                    ap.active_node = ap.root;
                    ap.partial_string = "";
                    ap.isRoot = true;
                }
            }


            if(ap.toInsert.length()>0){
                if(ap.active_node.getEdge(ap.toInsert.charAt(0)) != null){
                    ap.active_edge = ap.active_node.getEdge(ap.toInsert.charAt(0));
                    ap.active_length++;
                    ap.checkEdge();
                }
            }

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

    public int getEdges_count(){
        return edges_count;
    }

}