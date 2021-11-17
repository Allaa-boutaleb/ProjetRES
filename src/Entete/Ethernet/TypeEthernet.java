package Entete.Ethernet;

public class TypeEthernet {

    private final String type;

    public TypeEthernet(String type) {
        this.type = type.replaceAll(" ","");;
    }

    public String getType () {
        return ("Type : " + this.typeFromValue(type) + " (0x"+type+")\n\n");
    }

    public String typeFromValue(String type) {
        if (type.equals("0800")) return "IPv4";
        if (type.equals("0806")) return "ARP";
        return "Type not recognized";
    }

    public String getTypeValue() {
        return type;
    }

    public static void main(String[] args) {
        TypeEthernet type = new TypeEthernet("08 00");
        System.out.println(type.getType());
    }

    @Override
    public String toString() {
        return getType();
    }
}
