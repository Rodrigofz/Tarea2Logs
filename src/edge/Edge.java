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

    @Override
    public String toString(){
        return label;
    }

    public ActivePoint insert(ActivePoint ap, char c, boolean round){
        //El sufijo ya esta en el arbol. Solo modifico active point
        if(label.charAt(ap.active_length) == c){
            if(round){
                System.out.println("Wanted to insert " + c + "... not dividing");
            }
            ap.active_length++;
            if(ap.lastSplited != null){
                ap.lastSplited.setSuffixLink(ap.active_node);
                ap.lastSplited = ap.active_node;
            }
            ap.toInsert = ap.toInsert + c;
            ap.remainder ++;
            ap.checkEdge();
            ap.run = false;
            return ap;
        }
        //Hay que dividir
        else{
            if(round){
                System.out.println("Dividing to insert " + c);
            }
            //Separacion de etiqueta
            String new_label = label.substring(0, ap.active_length);
            String remainder = label.substring(ap.active_length);
            //Nuevo nodo
            Node new_node = new Node(ap.getCounter());
            Edge new_link1 = new_node.link(this.getNode(), remainder);
            this.setLabel(new_label);
            this.setNode(new_node);

            Node new_leaf = new Node(ap.getCounter());
            Edge new_link2 = new_node.link(new_leaf, String.valueOf(c));

            //Modificar, si hay que hacerlo, los links que llevan a hojas
            if(new_link1.getNode().getEdges_count() == 0){
                ap.leafEdges.add(new_link1);
            }
            if(new_link2.getNode().getEdges_count() == 0){
                ap.leafEdges.add(new_link2);
            }
            ap.leafEdges.remove(this);

            //Rule 2: Si habia un lastSplitted, creamos suffix link
            if(ap.lastSplited != null && ap.lastSplited.label != 0){
                ap.lastSplited.setSuffixLink(this.node);
            }
            //Seteamos el nodo dividido como lastSplitted
            ap.lastSplited = this.node;


            //Sacamos lo que insertamos
            if(!round){
                ap.toInsert = ap.toInsert.substring(1);
                ap.remainder = (ap.remainder==0)? 0 : ap.remainder-1;
                System.out.println("Reducing remainder to: " + ap.remainder);
            }

            //Rule1
            if(ap.isRoot){
                if(ap.toInsert.length()>0){
                    ap.run = true;
                    ap.active_length--;
                    ap.active_edge = (ap.active_length == 0)? null : ap.active_edge;
                }
                /*
                if(ap.active_length>0){
                    ap.active_edge = ap.active_node.getEdge(ap.getFirstChar());
                    ap.checkEdge();
                    //ap.active_length--;
                }
                if(ap.active_length == 0){
                    ap.active_edge = null;
                    ap.run = ap.toInsert.length() != 0;
                }
                */
            }

            //Rule 3
            else{
                if(ap.active_node.getSuffixLink() != null){
                    ap.active_node = ap.active_node.getSuffixLink();
                }
                else{
                    ap.active_node = ap.root;
                    ap.partial_string = "";
                    ap.isRoot = true;
                }
                ap.active_edge = null;
                //ap.active_edge = ap.active_node.getEdge(ap.active_edge.getLabel().charAt(0));
            }
            if(ap.active_edge != null){
                ap.checkEdge();
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