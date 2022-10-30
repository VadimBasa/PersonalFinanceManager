import java.io.*;
import java.net.Socket;

public class Client {
    public final static int SERVICE_PORT = 8989;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(HOST, SERVICE_PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            //далее клиент отправляет на сервер {"title": "булка", "date": "2022.02.08", "sum": 200}
            String jsonText = "{\"title\": \"булка\", \"date\": \"2022.02.08\", \"sum\": 500}";
            out.println(jsonText);
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}