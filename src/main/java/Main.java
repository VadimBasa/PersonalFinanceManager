import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public final static int PORT = 8989;
    static List<Marcet> basket = new ArrayList<Marcet>();
    private String textClient;
    static Main server = new Main();

    public String getTextClient() {
        return textClient;
    }

    public void setTextClient(String textClient) {
        this.textClient = textClient;
    }

    public static void main(String[] args) { //throws IOException

        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            System.out.println("Server started!");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("New connection accepted");
                    server.setTextClient(in.readLine());
                    //System.out.println(server.getTextClient());
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Marcet marcet = gson.fromJson(Main.server.getTextClient(), Marcet.class);
                    server.basket.add(marcet);// добавляем в массив корзины сообщения с клиента
                    //System.out.println("Заголовок: " + marcet.title + "\nДата: " + marcet.date + "\nСумма: " + marcet.sum);
                    System.out.println("{\"maxCategory\": " + CalculationMax.tsvr2(new File("categories.tsv"))+ " }");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

