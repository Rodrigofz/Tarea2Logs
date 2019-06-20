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
        //System.out.println("Linking: " + this.label + " with " + son.label);
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

    public static String greatestCommonPrefix(String a, String b) {
        int minLength = Math.min(a.length(), b.length());
        for (int i = 0; i < minLength; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.substring(0, i);
            }
        }
        return a.substring(0, minLength);
    }

    public void quadratic_insert(String s, int label){
        for(Edge e : edges){
            String common_prefix = greatestCommonPrefix(e.getLabel(), s);
            if(common_prefix.equals("")){
                continue;
            }
            else if(common_prefix.equals(e.getLabel())){
                s = s.substring(e.getLabelLength());
                e.getNode().quadratic_insert(s, label);
                return;
            }
            else{
                String rest1 = e.getLabel().substring(common_prefix.length());
                String rest2 = s.substring(common_prefix.length());
                Node new_node = new Node(-1);
                Node new_leaf = new Node(label);

                Node new_leaf2 = e.getNode();
                e.setNode(new_node);
                e.setLabel(common_prefix);

                new_node.link(new_leaf2, rest1);
                new_node.link(new_leaf, rest2);
                return;
            }
        }
        //Inserto directo, termino el match
        Node new_node = new Node(label);
        this.link(new_node, s);
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
                //System.out.println("Creating suffix link from " + ap.lastSplited + " to " + this);
                ap.lastSplited.setSuffixLink(this);
                ap.lastSplited = this;
            }

            //Rule 1
            if(ap.isRoot){
                ap.active_length = (ap.active_length==0)?0:ap.active_length-1;
                ap.active_edge = (ap.active_length==0)?null:ap.active_node.getEdge(ap.toInsert.charAt(0));
                if(ap.active_edge == null && ap.toInsert != ""){
                    //System.out.println("No encontre ningun edge con " + ap.toInsert.charAt(0) + ":c");
                }
            }

            //Rule 3
            if(!ap.isRoot){
                Node slink = ap.active_node.getSuffixLink();
                if(slink != null){
                    //System.out.println("Following suffix link to " + slink);
                    ap.active_node = slink;
                    ap.isRoot = ap.active_node.label == 0;
                }
                else{
                    ap.active_node = ap.root;
                    ap.isRoot = true;
                }
                ap.active_edge = (ap.active_length==0 || ap.active_edge==null)? null : ap.active_node.getEdge(ap.active_edge.getLabel().charAt(0));
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

    public Node find_node(String P){
        Edge edge = this.getEdge(P.charAt(0));
        if (edge == null){return null;}
        Node new_root = edge.getNode();
        String label_edge = edge.toString();

        if (!(P.startsWith(label_edge)) && !label_edge.startsWith(P)){return null;}

        while(!label_edge.equals(P) && !label_edge.startsWith(P)){

            int position = label_edge.length();

            char wanted_char = P.charAt(position);

            edge = new_root.getEdge(wanted_char);

            if (edge == null){return null;}
            label_edge = label_edge + edge.toString();

            new_root = edge.getNode();
            if (!(P.startsWith(label_edge)) && !label_edge.startsWith(P)){return null;}
        }

        return new_root;

    }

    public int getEdges_count(){
        return edges_count;
    }

    public ArrayList<Integer> locate(String P){
        ArrayList<Integer> locations = new ArrayList<Integer>();
        Node new_root = this.find_node(P);
        if(new_root==null){return locations;}
        if(new_root.edges_count==0){
            locations.add(new_root.label);
        }
        else{
            for (Edge e : new_root.getEdges()){
                locations.addAll(aux_loc(e));
            }
        }

        return locations;
    }


    public static ArrayList<Integer> aux_loc (Edge e){
        ArrayList<Integer> locations = new ArrayList<Integer>();
        Node node = e.getNode();
        int count_edges = node.edges_count;
        if (count_edges == 0){
            locations.add(node.label);
        }
        else{
            for (Edge ed : node.getEdges()){
                locations.addAll(aux_loc(ed));
            }
        }

        return locations;
    }


    public int count(String P){
        int n=0;
        Node new_root = this.find_node(P);

        if (new_root==null){return n;}

        if(new_root.edges_count==0){
            n++;
        }

        for (Edge e : new_root.getEdges()) {
            n += aux_count(e);
        }


        return n;
    }

    public static int aux_count (Edge e){
        Node node = e.getNode();
        int n=0;
        int count_edges = node.edges_count;
        if (count_edges == 0){
            n++;
        }
        else{
            for (Edge ed : node.getEdges()){
                n+=aux_count(ed);
            }
        }

        return n;
    }

}