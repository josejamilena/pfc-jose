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
                 Channels.newChannel(new FileOutputStream(hostname + "."
                 + filename));
            ChannelTools.fastChannelCopy(inputChannel, outputChannel);
            outputChannel.close();
            inputChannel.close();
            is.close();
            os.close();
            if (Checksum.checksum(hostname + "." + filename)
                    != Long.parseLong(checksum)) {
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
}

