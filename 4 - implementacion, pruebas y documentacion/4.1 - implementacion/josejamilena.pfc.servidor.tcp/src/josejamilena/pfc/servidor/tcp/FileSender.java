//   Copyright 2009 Jose Antonio Jamilena Daza
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

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
 * Servicio que envia los ficheros de BBDD de estadisticas por TCP.
 * @author Jose Antonio Jamilena Daza.
 */
public class FileSender {

    /** Puerto TCP. */
    private static int port;

    /** Nombre del fichero. */
    private static String file = "";

    /**
     * @return the port
     */
    public static int getPort() {
        return port;
    }

    /**
     * @param aPort the port to set
     */
    public static void setPort(final int aPort) {
        port = aPort;
    }

    /**
     * @return the file
     */
    public static String getFile() {
        return file;
    }

    /**
     * @param aFile the file to set
     */
    public static void setFile(final String aFile) {
        file = aFile;
    }

    /**
     * Constructor.
     * @param fileToSend fichero
     * @param tcpPort puerto
     */
    public FileSender(final String fileToSend, final int tcpPort) {
        this.file = fileToSend;
        this.port = tcpPort;
    }

//    public static void main(String[] av) {
//        new FileSender("build.xml", 3185).runServer();
//    }

    /**
     * Ejecutor.
     */
    public final void runServer() {
        ServerSocket sock;
        Socket clientSocket;
        try {
            sock = new ServerSocket(getPort());
            while (true) {
                clientSocket = sock.accept();
                new Handler(clientSocket, getFile()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hilo de ejecución.
     */
    class Handler extends Thread {

        /** socket. */
        private Socket sock;

        /** fichero.*/
        private String file;

        /**
         * Constructor.
         * @param s socket
         * @param fileToSend fichero
         */
        Handler(final Socket s, final String fileToSend) {
            sock = s;
            file = fileToSend;
        }

        /**
         * metodo que ejecuta.
         */
        @Override
        public void run() {
            try {
                BufferedReader is =
                        new BufferedReader(
                        new InputStreamReader(getSock().getInputStream()));
                PrintStream os = new PrintStream(getSock().getOutputStream(),
                        true);
                String line;
                line = is.readLine();
                // System.err.println(line);
                if (line.equalsIgnoreCase("HELLO")) {
                    os.println(getFile()); // respondo el nombre del archivo
                    os.flush();
                    // System.err.println(file);
                    line = is.readLine();
                    // System.err.println(line);
                    if (line.equalsIgnoreCase("NEXT")) {
                        // checksum del fichero
                        os.println(Checksum.checksum(getFile()));
                        os.flush();
                        // System.err.println(Checksum.Checksum(file));
                        line = is.readLine();
                        // System.err.println(line);
                        if (line.equalsIgnoreCase("NEXT")) {
                            final ReadableByteChannel inputChannel =
                                Channels.newChannel(
                                new FileInputStream(getFile()));
                            final WritableByteChannel outputChannel =
                                Channels.newChannel(os);
                            ChannelTools.fastChannelCopy(inputChannel,
                                    outputChannel);
                        } else {
                        }
                    } else {
                    }
                    os.flush();
                    os.close();
                    is.close();
                }
                getSock().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * @return the sock
         */
        public Socket getSock() {
            return sock;
        }

        /**
         * @param aSock the sock to set
         */
        public void setSock(final Socket aSock) {
            this.sock = aSock;
        }

        /**
         * @return the file
         */
        public String getFile() {
            return file;
        }

        /**
         * @param aFile the file to set
         */
        public void setFile(final String aFile) {
            this.file = aFile;
        }
    }
}
