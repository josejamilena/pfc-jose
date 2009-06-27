package ToritoWebServer;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Webserver {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            // Por defecto, inicia en el puerto 80
            serverSocket = new ServerSocket(80);
        } else {
            // Se inicia en en puerto pasado por argumento
            try {
                serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                Socket s = serverSocket.accept();
                new ClientHandler(s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

