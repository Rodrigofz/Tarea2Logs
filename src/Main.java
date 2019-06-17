import active_point.ActivePoint;
import edge.*;
import node.*;

import java.util.ArrayList;

public class Main {
    public static void main(String Args[]){
        //Node root = buildTree("cdddcdcd");
        //Node root = buildTree("abcabxabcd");
        //Node root = buildTree("elaelapacacapela");
        Node root = buildTree("GATCAATGAGGTGGA");
        root.printTree(0);
    }

    public static Node buildTree(String word){
        word = word + "$";
        Node root = new Node(0);
        ActivePoint ap = new ActivePoint(root);
        int remainder = 0;

        for(int i=0; i<word.length(); i++) {
            System.out.println("\t\tRound " + (i+1));
            //Obtener caracteres
            char c = word.charAt(i);
            String c_str = "" + c;


            //Annadir caracter a cada link que lleva a hoja
            for (Edge e : ap.leafEdges) {
                e.setLabel(e.getLabel() + c);
            }


            //Intentar insertar en el active point
            ap.run = true;
            System.out.println("Inserting " + c + "...");
            ap = ap.insert(c, true);


            while(ap.run){
                System.out.println("Inserting " + ap.toInsert.charAt(ap.toInsert.length()-1) + " from previous rounds into");
                System.out.println("\tRemainder to insert: " + ap.toInsert);
                System.out.println("\tActive node: " + ap.active_node.label);
                if(ap.active_edge != null){
                    System.out.println("\tActive edge: " + ap.active_edge.getLabel());
                }
                else{
                    System.out.println("\tThere's no active edge :c");
                }
                System.out.println("\tActive length : " + ap.active_length);
                System.out.println("\tRemainder: " + ap.remainder);
                System.out.println("");
                ap.insert(ap.toInsert.charAt(ap.toInsert.length()-1), false);
                root.printTree(0);
                System.out.println("");


            }

            root.printTree(0);
            System.out.println("\t\tEND OF ROUND");
            System.out.println("Remainder to insert: " + ap.toInsert);
            System.out.println("Active node: " + ap.active_node.label);
            if(ap.active_edge != null){
                System.out.println("Active edge: " + ap.active_edge.getLabel());
            }
            else{
                System.out.println("There's no active edge :c");
            }
            System.out.println("Active length : " + ap.active_length);
            System.out.println("Remainder: " + ap.remainder);
            System.out.println("Partial string: " + ap.partial_string);
            System.out.println("");
            System.out.println("/-------------------------------------------------/");
            ap.lastSplited = null;

        }
        return root;
    }
}
