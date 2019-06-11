package edge;

import active_point.ActivePoint;
import node.Node;

public class Edge{
    private Node node;
    private String label;
    
    public Edge(Node node, String s){
        this.node = node;
        this.label = s;
    }

    public ActivePoint insert(ActivePoint ap, char c){
        if(label.charAt(ap.active_length) == c){
            ap.active_length++;
            return ap;
        }
        else{
            String new_label = label.substring(0, ap.active_length);
            String remainder = label.substring(ap.active_length);
            Node new_node = new Node();
            Edge new_link1 = new_node.link(this.node, remainder);
            this.setLabel(new_label);
            this.setNode(new_node);

            Node new_leaf = new Node();
            Edge new_link2 = new_node.link(new_leaf, String.valueOf(c));
            //TODO: implementar reglas para cuando el nodo se divide
            ap.leafEdges.add(new_link1);
            ap.leafEdges.add(new_link2);
            return ap;
        }
    }

    public void expand_label(char c){
        this.setLabel(this.getLabel() + c);
    }

    /* Getters and setters ------------*/
    public Node getNode(){
        return node;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String s){
        this.label = s;
    }

    public void setNode(Node node){
        this.node = node;
    }
    /*----------------------------------*/
}