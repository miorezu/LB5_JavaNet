package Work;

import java.io.Serial;
import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {

    @Serial
    private final static long serialVersionUID = -1L;

    private InetAddress address;
    private int port;

    public User(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public User() {
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "User{" +
                "address=" + address +
                ", port=" + port +
                '}';
    }
}