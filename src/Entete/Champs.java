package Entete;

public class Champs {

    public static String getChamps(String s, int debut, int longueur) {
        StringBuilder sb = new StringBuilder();
        String[] octets = s.split("\\s+");
        for (int i = debut; i<debut+longueur; i++) {
            sb.append(octets[i] + " ");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

    public static int nbrOctets (String s) {
        String[] octets = s.split("\\s+");
        return octets.length;
    }
}
