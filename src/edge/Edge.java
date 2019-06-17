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

    public ActivePoint insert(ActivePoint ap, char c){
        //Veo si el caracter esta en el active point
        //Si esta, muevo el active point, no sigo insertando
        if(this.getLabel().charAt(ap.active_length) == c){
            System.out.println("In label, advancing ap...");
            ap.active_length++;
            if(ap.lastSplited != null){
                ap.lastSplited.setSuffixLink(ap.active_node);
            }
            ap.checkEdge();
            ap.run = false;
            return ap;
        }
        //De lo contrario, divido
        else{
            System.out.println("Dividing...");
            //Nuevas labels
            String half1 = this.getLabel().substring(0, ap.active_length);
            String half2 = this.getLabel().substring(ap.active_length);

            //Nuevos nodos
            Node new_node = new Node(ap.getCounter());
            Node new_leaf = new Node(ap.getCounter());

            //Modificar links correspondientes
            Edge new_link1 = new_node.link(this.node, half2);
            this.setNode(new_node);
            this.setLabel(half1);
            Edge new_link2 = new_node.link(new_leaf, ""+c);

            //Modificar leafEdges
            ap.leafEdges.remove(this);
            if(new_link1.getNode().getEdges_count() == 0) {
                ap.leafEdges.add(new_link1);
            }
            if(new_link2.getNode().getEdges_count() == 0){
                ap.leafEdges.add(new_link2);
            }

            //Modificar el toInsert
            ap.toInsert = (ap.toInsert.length()==1)?"":ap.toInsert.substring(1);

            //Modificar remainder
            ap.remainder--;

            //Rules
            //Rule 2
            if(ap.lastSplited!=null){
                System.out.println("Creating suffix link from " + ap.lastSplited + " to " + new_node);
                ap.lastSplited.setSuffixLink(new_node);
            }
            System.out.println("Setting last splitted as " + new_node);
            ap.lastSplited = new_node;


            //Rule 1
            if(ap.isRoot && ap.active_length>0){
                ap.active_length--;
                ap.active_edge = (ap.active_length==0)? null : ap.active_node.getEdge(ap.toInsert.charAt(0));
            }

            //Rule 3
            if(!ap.isRoot){
                Node slink = ap.active_node.getSuffixLink();
                if(slink != null){
                    System.out.println("Following suffix link to " + slink);
                    ap.active_node = slink;
                    ap.isRoot = ap.active_node.label == 0;
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