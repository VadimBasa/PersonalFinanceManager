import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public final static int PORT = 8989;
    static List<Marcet> basket = new ArrayList<>();

    public static void main(String[] args) {
        CalculationMax calculationMax = new CalculationMax();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("New connection accepted");
                    calculationMax.addMarcet(in);
                    //System.out.println("Заголовок: " + marcet.title + "\nДата: " + marcet.date + "\nСумма: " + marcet.sum);
                    calculationMax.loadFromTSV(new File("categories.tsv"));
                    System.out.println("{\"maxCategory\": " + calculationMax.loadFromTSV(new File("categories.tsv")) + " }");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

