package Entete.Ethernet;

import Conversion.*;

public class Mac {

    private final String macSrc;
    private final String macDest;

    public Mac(String macSrc, String macDest) {
        this.macSrc = macSrc;
        this.macDest = macDest;
    }

    public String getMacSrc() {
        StringBuilder sb = new StringBuilder();
        String macsource = this.macSrc.replaceAll(" ",":");
        sb.append("Source: "+ macsource +"\n");
        sb.append(getMacDetails(macsource));
        return sb.toString();
    }

    public static String getMacDetails(String mac) {
        StringBuilder sb = new StringBuilder();
        sb.append("Address" + "(" + mac + ")\n");
        String macBinaire = Conversion.hexToBinary(mac.replaceAll(":",""));
        char LGbit = macBinaire.charAt(6);
        char IGbit = macBinaire.charAt(7);
        sb.append("LGBit : " + LGbit + "\n");
        sb.append("IGBit : " + IGbit + "\n\n");
        return sb.toString();
    }

    public String getMacDest() {
        StringBuilder sb = new StringBuilder();
        String macdestination = this.macDest.replaceAll(" ",":");
        sb.append("Destination: "+ macdestination +"\n");
        sb.append(getMacDetails(macdestination));
        return sb.toString();
    }


    public static void main(String[] args) {
        Mac mac = new Mac ("08 00 20 0a 70 66","08 00 20 0a ac 96");
        System.out.println(mac.getMacSrc());
        System.out.println(mac.getMacDest());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMacDest()+getMacSrc());
        return sb.toString();
    }
}
