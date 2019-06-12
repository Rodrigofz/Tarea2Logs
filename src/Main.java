import active_point.ActivePoint;
import edge.*;
import node.*;

import java.util.ArrayList;

public class Main {
    public static void main(String Args[]){
        Node root = buildTree("elaelapacacapela");
        //Node root = buildTree("abcabxabcd");
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
            ap.run = true;
            ap.remainder++;
            System.out.println("Inserting " + c + "...");
            ap.toInsert = ap.toInsert + c;
            ap = ap.insert(c);
            while(ap.run){
                System.out.println("Inserting " + ap.toInsert + " from previous rounds");
                ap.insert(ap.toInsert.charAt(ap.toInsert.length()-1));
                root.printTree(0);
                System.out.println("");
            }
            root.printTree(0);
            System.out.println("Remainder to insert: " + ap.toInsert);
            System.out.println("");
            ap.lastSplited = null;

        }
        return root;
    }
}
