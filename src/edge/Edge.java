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

    public String toString(){
        return label;
    }

    public ActivePoint insert(ActivePoint ap, char c){
        //El sufijo ya esta en el arbol. Solo modifico active point
        if(label.charAt(ap.active_length) == c){
            ap.active_length++;
            ap.checkEdge();
            ap.remainder++;
            ap.run = false;
            return ap;
        }
        //Hay que dividir
        else{
            //Separacion de etiqueta
            String new_label = label.substring(0, ap.active_length);
            String remainder = label.substring(ap.active_length);
            //Nuevo nodo
            Node new_leaf1 = new Node();
            Edge new_link1 = this.node.link(new_leaf1, remainder);
            this.setLabel(new_label);

            Node new_leaf2 = new Node();
            Edge new_link2 = this.node.link(new_leaf2, String.valueOf(c));

            //Modificar los links que llevan a hojas
            ap.leafEdges.add(new_link1);
            ap.leafEdges.add(new_link2);
            ap.leafEdges.remove(this);

            //Rule 2: Si habia un lastSplitted, creamos suffix link
            if(ap.lastSplited != null){
                ap.lastSplited.setSuffixLink(this.node);
            }
            //Seteamos el nodo dividido como lastSplitted
            ap.lastSplited = this.node;

            //Sacamos lo que insertamos
            ap.toInsert = ap.toInsert.substring(1);
            ap.remainder--;

            //Rule1
            if(ap.isRoot){
                if(ap.toInsert.length()>0){
                    ap.active_edge = ap.active_node.getEdge(ap.getFirstChar());
                    ap.active_length--;
                }
                else{
                    ap.active_edge = null;
                    ap.active_length = 0;
                    ap.run = false;
                }
            }

            //Rule 3
            //TODO: Para que esto funcione, hay que hacer que mas de una insercion ocurra en un paso.
            else{
                if(ap.active_node.getSuffixLink() != null){
                    ap.active_node = ap.active_node.getSuffixLink();
                }
                else{
                    ap.active_node = ap.root;
                    ap.isRoot = true;
                }
                ap.active_edge = ap.active_node.getEdge(ap.active_edge.getLabel().charAt(0));
            }
            ap.checkEdge();
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

    public int getLabelLength(){ return label.length(); }

    public void setLabel(String s){
        this.label = s;
    }

    public void setNode(Node node){
        this.node = node;
    }
    /*----------------------------------*/
}