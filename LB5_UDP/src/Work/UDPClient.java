package Work;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class UDPClient {
    private ActiveUsers userList;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private int serverPort;
    private InetAddress serverAddress = null;

    public UDPClient(String address, int port) {
        userList = new ActiveUsers();
        serverPort = port;
        try {
            serverAddress = InetAddress.getByName(address);
            socket = new DatagramSocket();
            //socket.setSoTimeout(1000);
        } catch (UnknownHostException | SocketException e) {
            System.out.println("Error: " + e);
        }
    }

    public void work(int bufferSize) throws ClassNotFoundException {
        byte[] buffer = new byte[bufferSize];
        try {
            packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Sending request");
            while (true) {
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                if (packet.getLength() == 0) break;
                ObjectInputStream in = new ObjectInputStream(
                        new ByteArrayInputStream(
                                packet.getData(), 0, packet.getLength()));
                User usr = (User) in.readObject();
                userList.add(usr);
                clear(buffer);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Server is unreachable: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            socket.close();
        }
        System.out.println("Registered users: " + userList.size());
        System.out.println(userList);
    }

    private void clear(byte[] array) {
        Arrays.fill(array, (byte) 0);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        (new UDPClient("127.0.0.1", 1501)).work(256);
    }
}