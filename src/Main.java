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
        //Node root = buildTree("abcdabe");
        //Node root = buildTree("BANANA");
    }

    public static Node buildTree(String word){
        word = word + "$";
        Node root = new Node(0);
        ActivePoint ap = new ActivePoint(root);
        int remainder = 0;

        for(int i=0; i<word.length(); i++) {
            char c = word.charAt(i);
            System.out.println("\t\tInserting: " + c);
            //Inserto a todos los edges que llevan a hojas
            for(Edge e : ap.leafEdges){
                e.setLabel(e.getLabel() + c);
            }

            ap.toInsert = ap.toInsert + c;
            ap.remainder++;
            ap.run = true;

            while(ap.run){
                ap.insert(ap.toInsert.charAt(ap.toInsert.length()-1));
                System.out.println("END OF ROUND");
                root.printTree(0);
                System.out.println("Active node: " + ap.active_node);
                if(ap.active_edge != null){
                    System.out.println("Active edge: " + ap.active_edge);
                }
                else{
                    System.out.println("Active edge: null");
                }
                System.out.println("Active length: " + ap.active_length);
                System.out.println("Remainder: " + ap.remainder);
                System.out.println("To insert: " + ap.toInsert);
                System.out.println("------------------------------------");
            }

            ap.lastSplited = null;
        }
        return root;
    }
}
