package josejamilena.pfc.servidor.tcp;

//import java.net.*;
//import java.io.*;

/**
 * @deprecated
 * @author Jose Antonio Jamilena Daza
 */
public class ServidorTCP {

//    public static final int ECHOPORT = 3581;
//    public static ServerSocket miSocketServidor;
//
//    public static void main(String[] av) {
//        new ServidorTCP(ECHOPORT);
//    }
//
//    public ServidorTCP(int port) {
//        ServerSocket servSock;
//
//        try {
//            servSock = new ServerSocket(port);
//
//        } catch (IOException e) {
//            /* Crash the server if IO fails. Something bad has happened */
//            throw new RuntimeException("Could not create ServerSocket " + e);
//        }
//
//        while (true) {
//            try {
//                Socket s = miSocketServidor.accept();
//                new Handler(s);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    class Handler extends Thread {
//        private String lineaDeEntrada,  comando;
//        private BufferedReader is = null;
//        private PrintStream os = null;
//        Socket servSock;
//
//        Handler(Socket s) {
//            super();
//            servSock = s;
//        }
//
//        public void run() {
//            try {
//                is = new BufferedReader(new InputStreamReader(
//                      servSock.getInputStream()));
//                os = new PrintStream(servSock.getOutputStream(), true);
//                lineaDeEntrada = is.readLine();
//
//                os.println("+OK");
//                os.flush();
//
//                os.close();
//                is.close();
//                servSock.close();
//            } catch (UnsupportedEncodingException ex) {
//                ex.printStackTrace();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
}