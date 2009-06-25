package josejamilena.pfc.servidor.tcp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import josejamilena.pfc.servidor.crypto.easy.checksum.Checksum;
import josejamilena.pfc.servidor.util.ChannelTools;

/**
 *
 * @author Jose Antonio Jamilena Daza.
 */
public class FileSender {

    public static int port;
    private static String file = "";

    public FileSender(String fileToSend, int tcpPort) {
        this.file = fileToSend;
        this.port = tcpPort;
    }

    public static void main(String[] av) {
        new FileSender("build.xml", 3185).runServer();
    }

    public void runServer() {
        ServerSocket sock;
        Socket clientSocket;
        try {
            sock = new ServerSocket(port);
            while (true) {
                clientSocket = sock.accept();
                new Handler(clientSocket, file).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Handler extends Thread {

        Socket sock;
        String file;

        Handler(Socket s, String fileToSend) {
            sock = s;
            file = fileToSend;
        }

        public void run() {
            try {
                BufferedReader is =
                        new BufferedReader(
                        new InputStreamReader(sock.getInputStream()));
                PrintStream os = new PrintStream(sock.getOutputStream(), true);
                String line;
                line = is.readLine();
                System.err.println(line);
                if (line.equalsIgnoreCase("HELLO")) {
                    os.println(file); // respondo el nombre del archivo
                    os.flush();
                    System.err.println(file);
                    line = is.readLine();
                    System.err.println(line);
                    if (line.equalsIgnoreCase("NEXT")) {
                        // checksum del fichero
                        os.println(Checksum.Checksum(file));
                        os.flush();
                        System.err.println(Checksum.Checksum(file));
                        line = is.readLine();
                        System.err.println(line);
                        if (line.equalsIgnoreCase("NEXT")) {
                            final ReadableByteChannel inputChannel =
                                Channels.newChannel(new FileInputStream(file));
                            final WritableByteChannel outputChannel =
                                Channels.newChannel(os);
                            ChannelTools.fastChannelCopy(inputChannel,
                                    outputChannel);
                        } else {}
                    } else {}
                    os.flush();
                    os.close();
                    is.close();
                }
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
