package Entete.IP;

import Entete.Champs;
import Conversion.*;

public class IPHeader {

    private final String header;

    public IPHeader (String header) {
        this.header = header;
    }

    public String getType() {
        String s = Champs.getChamps(header,0,1);
        s = Conversion.hexToBinary(s);
        return (s.substring(0,4) + " ...." + " = " + "Version " + Integer.toString(Integer.parseInt(s.substring(0,4),2))+"\n\n");
    }

    public String getHeaderLength() {
        String s = Champs.getChamps(header,0,1);
        s = Conversion.hexToBinary(s);
        return (".... " + s.substring(4,8) + " = Header Length: " +  + Integer.parseInt(s.substring(4,8),2) * 4 + " bytes" + " (" + Integer.toString(Integer.parseInt(s.substring(4,8),2)) + ")\n\n");
    }

    public String getToS() {
        StringBuilder sb = new StringBuilder();
        sb.append("Differentiated Services Field: ");
        sb.append("0x" + Champs.getChamps(header,1,1) + "\n");
        sb.append("\t"+Conversion.hexToBinary(Champs.getChamps(header,1,1)).substring(0,6) +".. = DSCP\n");
        sb.append("\t......" + Conversion.hexToBinary(Champs.getChamps(header,1,1)).substring(6,8) +" = ECN\n\n");

        return sb.toString();
    }

    public String getTotalLength() {
        return ("Total Length : " + Integer.parseInt(Champs.getChamps(header,2,2).replaceAll(" ", ""),16)+"\n\n");
    }

    public String getIdentification() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identification: 0x" + Champs.getChamps(header, 4,2).replaceAll(" ", ""));
        sb.append(" (" + Integer.parseInt(Champs.getChamps(header,4,2).replaceAll(" ", ""),16) + ")\n\n");
        return sb.toString();
    }

    public String getFlags() {
        StringBuilder sb = new StringBuilder();
        String flagsBin = Conversion.hexToBinary(Champs.getChamps(header,6,1));
        String RB = flagsBin.substring(0,1);
        String DF = flagsBin.substring(1,2);
        String MF = flagsBin.substring(2,3);


        sb.append("Flags: 0x" + Champs.getChamps(header,6,1) + "\n");

        sb.append("\t" + RB + "... .... = Reserved bit: ");
            if (RB == "0") sb.append("Not set");
            else sb.append("Set");
        sb.append("\n\t." + DF + ".. .... = Don't fragment: ");
            if (DF == "0") sb.append("Not set");
            else sb.append("Set");
        sb.append("\n\t.." + MF + ". .... = More fragments: ");
            if (MF == "0") sb.append("Not set");
            else sb.append("Set");
        sb.append("\n\n");
        return sb.toString();
    }

    public String getFragmentOffset() {
        return ("Fragment Offset: " + Integer.parseInt(Champs.getChamps(header,6,2).replaceAll(" ", ""), 16)+"\n\n");
    }

    public String getTTL() {
        return ("Time to Live: " + Integer.parseInt(Champs.getChamps(header,8,1).replaceAll(" ", ""), 16)+"\n\n");
    }

    public String getProtocol() {
        String s = Champs.getChamps(header,9,1);
        System.out.println(s);
        System.out.println(Integer.parseInt(s,16));
        if (Integer.parseInt(s,16) != 17) return ("Protocol non traite");
        else return ("Protocol: UDP (" + Integer.parseInt(s,16) +")\n\n");
    }

    public String getChecksum() {
        return ("Header Checksum: 0x"+ Champs.getChamps(header,10,2).replaceAll(" ", "") + "\n\n");
    }

    public String getIpSrc() {
        StringBuilder sb = new StringBuilder("Source Address : ");
        String s = Champs.getChamps(header,12,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o,16) + ".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getIpDest() {
        StringBuilder sb = new StringBuilder("Destination Address: ");
        String s = Champs.getChamps(header,16,4);
        String[] octets = s.split("\\s+");
        for (String o : octets) {
            sb.append(Integer.parseInt(o,16) + ".");
        }
        sb.setLength(sb.length()-1);
        sb.append("\n");
        return sb.toString();
    }

    public String getOptions() {
        int headerLength = Integer.parseInt(Conversion.hexToBinary(Champs.getChamps(header, 0, 1)).substring(4, 8), 2);
        if (headerLength == 5) {
            return "";
        } else {
            String optionsField = Champs.getChamps(header, 20, (headerLength - 5) * 4);
            StringBuilder sb = new StringBuilder();

            //Option Record Route
            if (Champs.getChamps(optionsField, 0, 1).equals("07")) {
                sb.append("\tIP Option - Record Route (39 bytes)\n");
                sb.append("\t\tType: " + Integer.parseInt(Champs.getChamps(optionsField, 0, 1), 16) + "\n");
                sb.append("\t\tLength: " + Integer.parseInt(Champs.getChamps(optionsField, 1, 1), 16) + "\n");
                sb.append("\t\tPointer: " + Integer.parseInt(Champs.getChamps(optionsField, 2, 1), 16) + "\n");
                String route; String routeState; int indRoute;
                for (indRoute = 3; indRoute < 39; indRoute += 4) {
                    route = this.IPHextoDec(Champs.getChamps(optionsField, 3, 4));
                    if (route.equals("0.0.0.0")) routeState = "Empty Route";
                    else routeState = "Route";
                    sb.append("\t\t" + routeState + ": " + route);
                    if (indRoute == Integer.parseInt(Champs.getChamps(optionsField, 2, 1), 16)-1)
                        sb.append(" <--- (next)\n");
                    else sb.append("\n");
                }
            }

            return sb.toString();
        }
    }

    public String getData() {
        int headerLength = Integer.parseInt(Conversion.hexToBinary(Champs.getChamps(header, 0, 1)).substring(4, 8), 2);
        int packetLength = Integer.parseInt(Champs.getChamps(header,2,2).replaceAll(" ", ""),16);
        return ("Data: " + Champs.getChamps(header,20+(headerLength - 5) * 4,packetLength -(20+ (headerLength - 5) * 4)).replaceAll(" ", "") + "\n");
    }


    private String IPHextoDec (String IPHex){
            StringBuilder sb = new StringBuilder();
            String[] octets = IPHex.split("\\s+");
            for (String o : octets) {
                sb.append(Integer.parseInt(o, 16) + ".");
            }
            sb.setLength(sb.length() - 1);
            return sb.toString();

        }

        public String getHeader() {
            return header;
        }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getType());
        sb.append(getHeaderLength());
        sb.append(getToS());
        sb.append(getTotalLength());
        sb.append(getIdentification());
        sb.append(getFlags());
        sb.append(getFragmentOffset());
        sb.append(getTTL());
        sb.append(getProtocol());
        sb.append(getChecksum());
        sb.append(getIpSrc());
        sb.append(getIpDest());
        sb.append(getOptions());
        sb.append(getData());
        return sb.toString();
    }






    public static void main(String[] args) {
        String s = "4f 00 00 7c cb c9 00 00 ff 11 b9 7f 84 e3 3d 05 c0 21 9f 06 07 27 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 08 00 a2 56 2f 00 00 00 29 36 8c 41 00 03 86 2b 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f 20 21 22 23 24 25 26 27 28 29 2a 2b 2c 2d 2e 2f 30 31 32 33 34 35 36 37";
        IPHeader ip = new IPHeader(s);
        System.out.println(ip.toString());







    }



}
