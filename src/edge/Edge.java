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
        //El sufijo ya esta en el arbol. Solo modifico active point
        if(label.charAt(ap.active_length) == c){
            ap.active_length++;
            for(int i=0; i<ap.toInsert.size(); i++){
                ap.toInsert.set(i, ap.toInsert.get(i) + c);
            }
            ap.toInsert.add(""+c);
            if(ap.active_length == ap.active_edge.getLabelLength()){
                ap.active_node = this.node;
                ap.isRoot = false;
                ap.active_length = 0;
                ap.active_edge = null;
            }
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

            //Si habia un lastSplitted, creamos suffix link
            if(ap.lastSplited != null){
                ap.lastSplited.setSuffixLink(this.node);
            }
            //Seteamos el nodo dividido como lastSplitted
            ap.lastSplited = this.node;

            //Sacamos lo que insertamos
            ap.toInsert.remove(0);

            //Rule1
            if(ap.isRoot){
                if(ap.toInsert.size()>0){
                    ap.active_edge = ap.active_node.getEdge(ap.getFirstChar());
                    ap.active_length--;
                    System.out.println(ap.active_edge);
                }
                else{
                    ap.active_edge = null;
                    ap.active_length = 0;
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
                }
                ap.active_edge = ap.active_node.getEdge(ap.active_edge.label.charAt(0));
            }

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