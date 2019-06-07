import edge.*;
import node.*;

public class Main {
    public static void main(String Args[]){
        Node root = new Node();
        Node node1 = new Node();
        root.link(node1, "jaja");
        Node node2 = new Node();
        root.link(node2, "jojo");
        Node node3 = new Node();
        node1.link(node3, "jiji");
        root.printTree(0);
        String s = "gola";
    }

    public static Node buildTree(String word){
        Node root = new Node();
        Node active_node = root;
        String active_edge = "";
        int active_length = 0;
        int remainder = 0;

        Node node = new Node();
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

        }
        return root;
    }
}
