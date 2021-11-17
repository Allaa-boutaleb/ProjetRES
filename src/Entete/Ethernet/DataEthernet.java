package Entete.Ethernet;

public class DataEthernet {

    private final String data;

    public DataEthernet(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ("Data : " + data + "\n\n");
    }

}
