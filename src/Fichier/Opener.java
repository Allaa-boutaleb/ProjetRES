package Fichier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Opener {

    public static ArrayList<String>[] openTrames(String fileName) throws IOException {
        List<String> trames = recupTrames(FileToString(fileName));
        ArrayList<String>[] results = new ArrayList[trames.size()];
        for (int i = 0; i<trames.size(); i++) {
            results[i] = recupeOctets(trames.get(i));
        }
        return results;
    }



    public static String FileToString(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }

    public static ArrayList<String> recupTrames(String fileContent) {
        fileContent.trim();
        String[] trames = fileContent.split("0000");
        ArrayList <String> results = new ArrayList<>();
        for (int i =1; i<trames.length; i++) {
            results.add(filtreTrame(trames[i]));
        }
        return results;
    }

    public static String filtreTrame(String s) {

        s = s.replaceAll("((\n)|\\b\\w{3,}\\b\\s?)|([r-zR-Z])|(\\b\\w{1}\\b\\s?)|([-+.^:,?!/><'*%;`~$#&_=])", " ");
        s = s.replaceAll(" +", " ");
        return s;
    }



    public static ArrayList<String> recupeOctets(String trame) {
        trame.trim();
        String trameContent = trame.replaceAll(" +"," ");
        String[] bytes = trameContent.split(" ");

        ArrayList <String> results = new ArrayList<>();
        for (int i =1; i<bytes.length; i++) {
            results.add(bytes[i]);

        }
        return results;
    }


    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\Studies\\Desktop\\trames.txt";
        ArrayList<String>[] results = openTrames(fileName);
        System.out.println(results[0]);
        System.out.println(results[1]);


    }


}
