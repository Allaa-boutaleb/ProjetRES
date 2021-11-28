package Entete.UDP;

import Entete.Champs;

public class UDPHeader {

    private final String header;
    private final int nbrOctets;

    public UDPHeader(String header) {
        this.header = header;
        String[] octets = header.split("\\s+");
        nbrOctets = octets.length;

    }

    public String getPortSrc() {
        return ("Source Port: " + Integer.parseInt(Champs.getChamps(header,0,2).replaceAll(" ", ""),16)+"\n");
    }

    public String getPortDest() {
        return ("Destination Port: " + Integer.parseInt(Champs.getChamps(header,2,2).replaceAll(" ", ""),16)+"\n");
    }

    public String getLength() {
        return ("Length: " + Integer.parseInt(Champs.getChamps(header,4,2).replaceAll(" ", ""),16)+"\n");

    }

    public String getChecksum() {
        String checksum = Champs.getChamps(header,6,2).replaceAll(" ", "");
        if (checksum.equals("0000")) return ("[Checksum: [missing]]\n");
        else return ("[Checksum: [0x" + Champs.getChamps(header,6,2).replaceAll(" ", "")+"]]\n");

    }

    public String getPayload() {
        StringBuilder sb = new StringBuilder();
        int payloadLength = nbrOctets-8;
        sb.append("UDP payload ");
        sb.append("("+payloadLength+" bytes)\n");
        return sb.toString();
    }

    public String getData() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data ("+(nbrOctets-8)+" bytes)\n");
        sb.append("\tData: " + Champs.getChamps(header,8,nbrOctets-8).replaceAll(" ", "")+"\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPortSrc());
        sb.append(getPortDest());
        sb.append(getLength());
        sb.append(getChecksum());
        sb.append(getPayload());
        sb.append(getData());

        return sb.toString();
    }

    public static void main(String[] args) {
        UDPHeader udp = new UDPHeader("08 00 a2 56 2f 00 00 00 29 36 8c 41 00 03 86 2b 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f 20 21 22 23 24 25 26 27 28 29 2a 2b 2c 2d 2e 2f 30 31 32 33 34 35 36 37");
        System.out.println(udp.toString());
    }

}
