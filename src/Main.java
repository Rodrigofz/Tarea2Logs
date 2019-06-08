import edge.*;
import node.*;

import java.util.ArrayList;

public class Main {
    public static void main(String Args[]){
        Node tree = buildTree("banana");
        tree.printTree(0);
    }

    public static Node buildTree(String word){
        word = word + "$";
        Node root = new Node();
        Node active_node = root;
        String active_edge = "";
        int active_length = 0;
        int remainder = 0;

        ArrayList<Edge> leafEdges = new ArrayList<Edge>();

        for(int i=0; i<word.length(); i++) {
            //Obtener caracteres
            char c = word.charAt(i);
            String c_str = "" + c;
            //Annadir caracter a cada link que lleva a hoja
            for (Edge e : leafEdges) {
                e.setLabel(e.getLabel() + c);
            }

            //Verificar si el sufijo esta incluido en el arbol
            boolean in_tree = false;
            for (Edge e : active_node.getEdges()) {
                if (e.getLabel().startsWith(c_str)) {
                    in_tree = true;
                }
            }
            if (!in_tree) {
                Node new_node = new Node();
                Edge new_edge = root.link(new_node, c_str);
                leafEdges.add(new_edge);
            } else {

            }
        }
        return root;
    }
}
