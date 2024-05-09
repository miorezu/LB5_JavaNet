import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    private final String server;
    private final int port;

    public Client(String server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(server, port);
            ObjectOutputStream objOutStr = new ObjectOutputStream(socket.getOutputStream());
            String classFile = "out/production/LB5_Client/Factorial.class";

            FileInputStream fis = new FileInputStream(classFile);
            objOutStr.writeObject(classFile);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            objOutStr.writeObject(bytes);

            System.out.println("Enter the number");
            Scanner inputScanner = new Scanner(System.in);
            int number = inputScanner.nextInt();
            Factorial factorial = new Factorial(number);

            objOutStr.writeObject(factorial);
            objOutStr.flush();

            ObjectInputStream objInStr = new ObjectInputStream(socket.getInputStream());

            classFile = (String) objInStr.readObject();
            classFile = classFile.replaceFirst("LB5_Server", "LB5_Client");
            FileOutputStream fos = new FileOutputStream(classFile);
            bytes = (byte[]) objInStr.readObject();

            fos.write(bytes);
            fos.close();
            Result result = (Result) objInStr.readObject();
            System.out.println("result = " + result.output() + ", time taken = " + result.scoreTime() + "ns");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 7891);
        client.start();
    }
}