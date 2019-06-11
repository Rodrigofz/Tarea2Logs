import active_point.ActivePoint;
import edge.*;
import node.*;

import java.util.ArrayList;

public class Main {
    public static void main(String Args[]){
        Node root = buildTree("banana");
        root.printTree(0);
    }

    public static Node buildTree(String word){
        word = word + "$";
        Node root = new Node();
        ActivePoint ap = new ActivePoint(root);
        int remainder = 0;

        for(int i=0; i<word.length(); i++) {
            //Obtener caracteres
            char c = word.charAt(i);
            String c_str = "" + c;
            //Annadir caracter a cada link que lleva a hoja
            for (Edge e : ap.leafEdges) {
                e.setLabel(e.getLabel() + c);
            }

            //Intentar insertar en el active point
            ap = ap.insert(c);


        }
        return root;
    }
}
