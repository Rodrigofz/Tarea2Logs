import active_point.ActivePoint;
import edge.*;
import node.*;

import java.io.*;


import java.util.ArrayList;
import java.util.Random;
import java.nio.file.*;
import java.lang.Math.*;
import java.lang.String.*;
import java.text.NumberFormat;
import java.util.stream.*;

import java.lang.instrument.Instrumentation;


public class Main {

    public static void experiment(String T, int i, String type){
        //Generamos tamaño para particionar texto
        int n = (int) Math.pow(2,i);

        //Genera texto del largo especificado (n=2^i)
        String sub_text = T.substring(0, n);

        //Inserta texto en el suffix tree; Se mide el tiempo
        final long startBuild = System.nanoTime();
        Node root = quadratic_build(sub_text);
        final long buildTime = System.nanoTime() - startBuild;


        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("out/buildTime_exp.csv", true));
            String line = Integer.toString(i) + "," + Integer.toString(n) + "," + 
                Long.toString(buildTime) + "," + type + "\n";
            writer.append(line);
            writer.close(); 
        }
        catch (IOException e){
            e.printStackTrace(); 
        } 

        // Si es english los patrones P seran 
        // n/10 palabras del texto T seleccionadas de forma aleatoria.
        Random rand = new Random();
        if(type.equals("english")){
            int lines_count = 0;
            try{
                BufferedReader reader = new BufferedReader(new FileReader("../datasets/english_words"));
                while (reader.readLine() != null) lines_count++;
                reader.close();
            }catch (IOException e){e.printStackTrace();} 
            
            long countTimeAcc = 0;
            long locateTimeAcc = 0;
            for(int j=0; j<n/10; j++){
                try (Stream<String> lines = Files.lines(Paths.get("../datasets/english_words"))) {
                    int r = rand.nextInt(lines_count-1);
                    String p = lines.skip(r).findFirst().get();
                    
                    long startCount = System.nanoTime();
                    root.count(p);
                    long countTime = System.nanoTime() - startCount;
                    countTimeAcc+=countTime;

                    long startLocate = System.nanoTime();
                    root.locate(p);
                    long locateTime = System.nanoTime() - startLocate;
                    locateTimeAcc+=locateTime;

                }catch (IOException e){e.printStackTrace();}
            }
            long countTimeProm = countTimeAcc/(n/10);
            long locateTimeProm = locateTimeAcc/(n/10);

            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("out/english_count_locate_exp.csv", true));
                String line = Integer.toString(i) + "," + Integer.toString(n) + "," + 
                    Long.toString(countTimeProm) + "," + Long.toString(locateTimeProm) +
                    "," + "english\n";
                    writer.append(line);
                    writer.close();    
                }catch(IOException e){e.printStackTrace();} 
        }else{
            // Si es ADN los patrones P serán n/10 substrings del texto de tamanos 
            // m = {8, 16, 32, 64} escogidos aleatoriamente
            if(type.equals("dna")){
                int m[] = {8,16,32,64};
                for(int k=0; k<4; k++){
                    long countTimeAcc = 0;
                    long locateTimeAcc = 0;
                    for(int j=0;j<n/10;j++){
                        int r = rand.nextInt(sub_text.length()-64);
                        String p = sub_text.substring(r, r+m[k]);

                        long startCount = System.nanoTime();
                        root.count(p);
                        long countTime = System.nanoTime() - startCount;
                        countTimeAcc+=countTime;

                        long startLocate = System.nanoTime();
                        root.locate(p);
                        long locateTime = System.nanoTime() - startLocate;
                        locateTimeAcc+=locateTime;
                    }   
                    long countTimeProm = countTimeAcc/(n/10);
                    long locateTimeProm = locateTimeAcc/(n/10);
                    try{
                        BufferedWriter writer = new BufferedWriter(new FileWriter("out/dna_count_locate_exp.csv", true));
                        String line = Integer.toString(i) + "," + Integer.toString(n) + "," + 
                            Long.toString(countTimeProm) + "," + Long.toString(locateTimeProm) +
                            "," + Integer.toString(m[k]) + "," + "dna\n";
                        writer.append(line);
                        writer.close();    
                    }
                    catch (IOException e){
                        e.printStackTrace(); 
                    } 
                    

                }   
                /*
                int k_arr[] = {3,5,10};
                int q_arr[] = {4,8,16,32};
                for (int k: k_arr){
                    for(int q: q_arr){
                        long startTopkq = System.nanoTime();
                        root.top_k_q(k,q);
                        long topkqTime = System.nanoTime() - startTopkq;
                        try{
                            BufferedWriter writer = new BufferedWriter(new FileWriter("out/dna_topkq_exp.csv", true));
                            String line = Integer.toString(n) + "," +
                                Integer.toString(k) + "," + Integer.toString(q) + "," +
                                Long.toString(topkqTime) + ", dna\n;
                            writer.append(line);
                            writer.close();    
                        }
                        catch (IOException e){
                            e.printStackTrace(); 
                        } 
                    }
                }
                
                */  
                
                
            }  
        }
    }

    public static void main(String Args[]){
        /*
        String dna_file = "../datasets/clean_datasets/dna_clean.50MB";
        String english_file = "../datasets/clean_datasets/english_clean.50MB";
        
        try{
            BufferedReader dna_dataset = new BufferedReader(new FileReader(dna_file)); 
            BufferedReader english_dataset = new BufferedReader(new FileReader(english_file)); 
            
            String dna_text = dna_dataset.readLine();
            String english_text = english_dataset.readLine();

            BufferedWriter w0 = new BufferedWriter(new FileWriter("out/buildTime_exp.csv"));
            w0.write("");
            BufferedWriter w1 = new BufferedWriter(new FileWriter("out/dna_count_locate_exp.csv"));
            w1.write("");
            BufferedWriter w2 = new BufferedWriter(new FileWriter("out/dna_topkq_exp.csv"));
            w2.write("");
            BufferedWriter w3 = new BufferedWriter(new FileWriter("out/english_count_locate_exp.csv"));
            w3.write("");
            BufferedWriter w4 = new BufferedWriter(new FileWriter("out/english_topkq_exp.csv"));
            w4.write("");
                        

            //n = 2^i simbolos, con i {10, 11, . . . , 23}

            //Calentamiento:
            Node root = quadratic_build(dna_text.substring(0, 2048));
            root.count("AC");
            root.locate("AC");
            int lines_count = 0;
            try{
                BufferedReader reader = new BufferedReader(new FileReader("../datasets/english_words"));
                while (reader.readLine() != null) lines_count++;
                reader.close();
            }catch (IOException e){e.printStackTrace();} 

            
            for(int i=10;i<=15;i++){
                System.out.println(i);
                experiment(dna_text, i, "dna");
                experiment(english_text, i, "english");
            }
            
            dna_dataset.close();
            english_dataset.close();

        }
        catch (IOException e){
            e.printStackTrace(); 
        }

        */

        /*
        Node root = quadratic_build("GATCAATGAGGTGGACACCAGAGGCGGGGACTTGTAAATAACACTGGGCTGTAGGAGTGATGGGGTTCACCTCTAATTCT" +
                "AAGATGGCTAGATAATGCATCTTTCAGGGTTGTGCTTCTATCTAGAAGGTAGAGCTGTGGTCGTTCAATAAAAGTCCTCA" +
                "AGAGGTTGGTTAATACGCATGTTTAATAGTACAGTATGGTGACTATAGTCAACAATAATTTATTGTACATTTTTAAATAG" +
                "CTAGAAGAAAAGCATTGGGAAGTTTCCAACATGAAGAAAAGATAAATGGTCAAGGGAATGGATATCCTAATTACCCTGAT" +
                "TTGATCATTATGCATTATATACATGAATCAAAATATCACACATACCTTCAAACTATGTACAAATATTATATACCAATAAA" +
                "AAATCATCATCATCATCTCCATCATCACCACCCTCCTCCTCATCACCACCAGCATCACCACCATCATCACCACCACCATC" +
                "ATCACCACCACCACTGCCATCATCATCACCACCACTGTGCCATCATCATCACCACCACTGTCATTATCACCACCACCATC" +
                "ATCACCAACACCACTGCCATCGTCATCACCACCACTGTCATTATCACCACCACCATCACCAACATCACCACCACCATTAT" +
                "CACCACCATCAACACCACCACCCCCATCATCATCATCACTACTACCATCATTACCAGCACCACCACCACTATCACCACCA" +
                "CCACCACAATCACCATCACCACTATCATCAACATCATCACTACCACCATCACCAACACCACCATCATTATCACCACCACC" +
                "ACCATCACCAACATCACCACCATCATCATCACCACCATCACCAAGACCATCATCATCACCATCACCACCAACATCACCAC" +
                "CATCACCAACACCACCATCACCACCACCACCACCATCATCACCACCACCACCATCATCATCACCACCACCGCCATCATCA" +
                "TCGCCACCACCATGACCACCACCATCACAACCATCACCACCATCACAACCACCATCATCACTATCGCTATCACCACCATC" +
                "ACCATTACCACCACCATTACTACAACCATGACCATCACCACCATCACCACCACCATCACAACGATCACCATCACAGCCAC" +
                "CATCATCACCACCACCACCACCACCATCACCATCAAACCATCGGCATTATTATTTTTTTAGAATTTTGTTGGGATTCAGT" +
                "ATCTGCCAAGATACCCATTCTTAAAACATGAAAAAGCAGCTGACCCTCCTGTGGCCCCCTTTTTGGGCAGTCATTGCAGG" +
                "ACCTCATCCCCAAGCAGCAGCTCTGGTGGCATACAGGCAACCCACCACCAAGGTAGAGGGTAATTGAGCAGAAAAGCCAC" +
                "TTCCTCCAGCAGTTCCCTGTCTGAGCTGCTGTCCTTGGACTTGAAGAAGCTTCTGGAACATGCTGGGGAGGAAGGAAGAC" +
                "ATTTCACTTATTGAGTGGCCTGATGCAGAACAGAGACCCAGCTGGTTCACTCTAGTTCGGACTAAAACTCACCCCTGTCT" +
                "ATAAGCATCAGCCTCGGCAGGATGCATTTCACATTTGTGATCTCATTTAACCTCCACAAAGACCCAGAAGGGTTGGTAAC" +
                "ATTATCATACCTAGGCCTACTATTTTAAAAATCTAACACCCATGCAGCCCGGGCACTGAAGTGGAGGCTGGCCACGGAGA");
        root.topkq(1, 2);
        */

        //Node root = buildTree("cdddcdcd");
        //Node root = quadratic_build("abcabxabcd");
        //root.printTree(0);
        //Node root = buildTree("elaelapacacapela");
        //Node root = buildTree("GATCAATGAGGTGGA");
        //Node root = buildTree("BANANA");
        //Node root = buildTree("hopolapacopomopoepestapamapas");
        //Node root = buildTree("abcdabe");
        Node root = quadratic_build("BANANA");
        root.topkq(2,3 );
        //Node root = quadratic_build("GATCAATGAGGTGGA");
        //root.printTree(0);
        //System.out.println(root.locate("N"));
    }

    public static Node quadratic_build(String word){
        word = word + "$";
        Node root = new Node(0);
        Node new_leaf = new Node(0);
        root.link(new_leaf, word);

        for(int i=1; i<word.length(); i++){
            String suffix = word.substring(i);
            root.quadratic_insert(suffix, i);
        }

        root.setUpLengths(0);
        return root;
    }

    public static Node buildTree(String word){
        word = word + "$";
        Node root = new Node(0);
        ActivePoint ap = new ActivePoint(root);

        for(int i=0; i<word.length(); i++) {
            char c = word.charAt(i);
            //System.out.println("\t\tInserting: " + c);
            //Inserto a todos los edges que llevan a hojas
            for(Edge e : ap.leafEdges){
                e.setLabel(e.getLabel() + c);
            }

            ap.toInsert = ap.toInsert + c;
            ap.remainder++;
            ap.run = true;

            while(ap.run){
                ap.insert(ap.toInsert.charAt(ap.toInsert.length()-1));
                //System.out.println("END OF ROUND");
                //root.printTree(0);
                //System.out.println("Active node: " + ap.active_node);
                //System.out.println("Is root: " + ap.isRoot);
                /*if(ap.active_edge != null){
                    System.out.println("Active edge: " + ap.active_edge);
                }
                else{
                    System.out.println("Active edge: null");
                }*/
                //System.out.println("Active length: " + ap.active_length);
                //System.out.println("Remainder: " + ap.remainder);
                //System.out.println("To insert: " + ap.toInsert);
                //System.out.println("------------------------------------");
            }

            ap.lastSplited = null;
        }
        return root;
    }
}
