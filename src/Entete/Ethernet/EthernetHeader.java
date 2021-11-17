package Entete.Ethernet;

import Entete.Champs;

public class EthernetHeader {

    private final Mac mac;
    private final TypeEthernet type;
    private final DataEthernet data;

    public EthernetHeader (Mac mac, TypeEthernet type, DataEthernet data) {
        this.mac = mac;
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mac.toString()+type.toString()+data.toString());
        return sb.toString();

    }

    public static void main(String[] args) {
        String chaine = "08 00 20 0a ac 96 08 00 20 0a 70 66 08 00 4f 00 00 7c cb c9 00 00 ff 01 b9 7f 84 e3 3d 05 c0 21 9f 06 07 27 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 08 00 a2 56 2f 00 00 00 29 36 8c 41 00 03 86 2b 08 09 0a 0b 0c 0d 0e 0f 10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f 20 21 22 23 24 25 26 27 28 29 2a 2b 2c 2d 2e 2f 30 31 32 33 34 35 36 37";

        Mac mac = new Mac(Champs.getChamps(chaine,0,6), Champs.getChamps(chaine,6,6));
        TypeEthernet typeEthernet = new TypeEthernet(Champs.getChamps(chaine,12,2));
        DataEthernet dataEthernet = new DataEthernet(Champs.getChamps(chaine,14,Champs.nbrOctets(chaine) - 14 ));

        EthernetHeader ethernet = new EthernetHeader(mac,typeEthernet,dataEthernet);
        System.out.println(ethernet.toString());
    }

    public TypeEthernet getType() {
        return type;
    }

    public DataEthernet getData() {
        return data;
    }

    public Mac getMac() {
        return mac;
    }
}
