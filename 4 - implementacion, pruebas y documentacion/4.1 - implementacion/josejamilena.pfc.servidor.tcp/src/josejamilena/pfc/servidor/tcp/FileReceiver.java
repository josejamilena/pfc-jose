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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import josejamilena.pfc.servidor.util.ChannelTools;
import josejamilena.pfc.servidor.crypto.easy.checksum.Checksum;

/**
 * Receptor de ficheros.
 * @author Jose Antonio Jamilena Daza
 */
public class FileReceiver {

    /**
     * recibir.
     * @param hostname host
     * @param port puerto
     */
    public final void receive(final String hostname, final int port) {
        Socket sock = null;
        try {
            sock = new Socket(hostname, port);
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            PrintStream os = new PrintStream(
                    sock.getOutputStream(), true);
            String filename, checksum;
            os.println("HELLO");
            os.flush();
            filename = is.readLine();
            System.err.println(filename);
            os.println("NEXT");
            os.flush();
            checksum = is.readLine();
            System.err.println(checksum);
            os.println("NEXT");
            os.flush();
            final ReadableByteChannel inputChannel =
                    Channels.newChannel(sock.getInputStream());
            final WritableByteChannel outputChannel =
                    Channels.newChannel(new FileOutputStream(hostname + "." + filename));
            ChannelTools.fastChannelCopy(inputChannel, outputChannel);
            outputChannel.close();
            inputChannel.close();
            is.close();
            os.close();
            if (Checksum.checksum(hostname + "." + filename) != Long.parseLong(checksum)) {
                throw new RuntimeException("Error en Checksum");
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    /**
     * recibir.
     * @param hostname host
     * @param port puerto
     * @return nombre fichero
     * @throws TCPException error
     */
    public final String receive2(final String hostname, final int port)
            throws TCPException {
        Socket sock = null;
        String res = "";
        try {
            sock = new Socket(hostname, port);
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            PrintStream os = new PrintStream(
                    sock.getOutputStream(), true);
            String filename, checksum;
            os.println("HELLO");
            os.flush();
            filename = is.readLine();
            String tmpPath = System.getProperty("java.io.tmpdir");
            if (!tmpPath.endsWith(System.getProperty("file.separator"))) {
                tmpPath = tmpPath + System.getProperty("file.separator");
            }
            res = tmpPath + hostname + "." + filename;
            // System.err.println(res);
            os.println("NEXT");
            os.flush();
            checksum = is.readLine();
            // System.err.println(checksum);
            os.println("NEXT");
            os.flush();
            final ReadableByteChannel inputChannel =
                    Channels.newChannel(sock.getInputStream());
            final WritableByteChannel outputChannel =
                    Channels.newChannel(new FileOutputStream(res));
            ChannelTools.fastChannelCopy(inputChannel, outputChannel);
            outputChannel.close();
            inputChannel.close();
            is.close();
            os.close();
            if (Checksum.checksum(res) != Long.parseLong(checksum)) {
                // System.err.println("Error en Checksum");
                throw new TCPException("Error en Checksum");
            }
        } catch (IOException e) {
            // e.printStackTrace();
            throw new TCPException("Error en comunicación.");
        } finally {
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException ex) {
            }
            // System.err.println(res);
            return res;
        }
    }
}
