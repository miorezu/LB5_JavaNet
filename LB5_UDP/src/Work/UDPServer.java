package Work;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPServer {

    private ActiveUsers userList;
    private DatagramSocket socket = null; // датаграмний сокет для взаємодії комп’ютерів по мережі
    private DatagramPacket packet = null; // датаграмний пакет для отримання і відправки інформації
    private InetAddress address = null; // клас, який представляє мережеву адресу комп’ютера
    private int port = -1;

    public UDPServer(int serverPort) {
        userList = new ActiveUsers();

        try {
            socket = new DatagramSocket(serverPort);

        } catch (SocketException e) {
            System.out.println("Error: " + e);
        }
    }

    public void work(int bufferSize) {
        try {
            System.out.println("Server start...");
            while (true) { // безкінечний цикл роботи з клієнтами
                getUserData(bufferSize); // отримання запиту клієнта
                log(address, port); // вивід інформації про клієнта на екран
                sendUserData(); // формування та відправка відповіді клієнту
                System.out.println("Type 0 to end server\n" +
                        "or something else to continue working: ");
                Scanner inScanner = new Scanner(System.in);
                if (inScanner.nextInt() == 0) {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            System.out.println("Server end...");
            socket.close();
        }
    }

    private void log(InetAddress address, int port) {
        System.out.println("Request from: " + address.getHostAddress() +
                " port: " + port);
    }

    private void clear(byte[] array) {
        Arrays.fill(array, (byte) 0);
    }

    private void getUserData(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();
        User usr = new User(address, port);
        if (userList.isEmpty()) {
            userList.add(usr);
        } else if (!userList.contains(usr)) {
            userList.add(usr);
        }
        clear(buffer);
    }

    private void sendUserData() throws IOException {
        byte[] buffer;
        for (int i = 0; i < userList.size(); i++) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(userList.get(i));
            buffer = bout.toByteArray();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
        buffer = "end".getBytes();
        packet = new DatagramPacket(buffer, 0, address, port);
        socket.send(packet);
    }

    public static void main(String[] args) {
        (new UDPServer(1501)).work(256);
    }
}