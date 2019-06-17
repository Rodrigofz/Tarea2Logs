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

    public ActivePoint insert(ActivePoint ap, char c){
        //Tengo active edge, es responsabilidad de este hacer la insercion.
        if(ap.active_edge != null){
            return ap.active_edge.insert(ap, c);
        }
        //No tengo active edge.
        //Veo si el caracter esta en alguno de los edges.
        else{
            //Busco si el caracter esta en algun edge
            for(Edge e : ap.active_node.getEdges()){
                if(e.getLabel().charAt(0) == c){
                    //El caracter esta en este edge
                    //Se mueve el active point, no se sigue insertando
                    ap.active_edge = e;
                    ap.active_length++;
                    ap.run = false;
                    if(ap.lastSplited != null){
                        ap.lastSplited.setSuffixLink(ap.active_node);
                    }
                    //Checkear edge
                    if(ap.active_edge != null){
                        ap.checkEdge();
                    }
                    return ap;
                }

            }
            //El caracter no esta en ningun edge
            //Inserto directamente al nodo
            Node new_node = new Node(ap.getCounter());
            Edge new_link = ap.active_node.link(new_node, "" + c);
            ap.leafEdges.add(new_link);

            //Modificar el toInsert
            ap.toInsert = (ap.toInsert.length()==1)?"":ap.toInsert.substring(1);

            //Modificar remainder
            ap.remainder--;

            //Rule 2
            if(ap.lastSplited!=null){
                System.out.println("Creating suffix link from " + ap.lastSplited + " to " + this);
                ap.lastSplited.setSuffixLink(this);
                ap.lastSplited = this;
            }

            //Rule 1
            if(ap.isRoot && ap.active_length>0){
                ap.active_length--;
                ap.active_edge = ap.active_node.getEdge(ap.toInsert.charAt(0));
            }

            //Rule 3
            if(!ap.isRoot){
                Node slink = ap.active_node.getSuffixLink();
                if(slink != null){
                    System.out.println("Following suffix link to " + slink);
                    ap.active_node = slink;
                }
                else{
                    ap.active_node = ap.root;
                    ap.isRoot = true;
                }
                ap.active_edge = (ap.active_length==0)? null : ap.active_node.getEdge(ap.active_edge.getLabel().charAt(0));
            }

            if(ap.active_edge != null){
                ap.checkEdge();
            }

            ap.run = ap.remainder != 0;
            return ap;

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