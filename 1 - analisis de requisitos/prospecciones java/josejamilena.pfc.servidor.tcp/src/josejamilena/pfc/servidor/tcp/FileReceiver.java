package josejamilena.pfc.servidor.tcp;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import josejamilena.pfc.servidor.util.ChannelTools;
import josejamilena.pfc.servidor.crypto.easy.checksum.Checksum;

public class FileReceiver {

    public static void main(String[] argv) {
        FileReceiver c = new FileReceiver();
        c.converse("localhost", 3185);
    }

    protected void converse(String hostname, int port) {
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
                 Channels.newChannel(new FileOutputStream(filename));
            ChannelTools.fastChannelCopy(inputChannel, outputChannel);
            outputChannel.close();
            inputChannel.close();
            is.close();
            os.close();
            if (Checksum.Checksum(filename) != Long.parseLong(checksum)) {
                throw new RuntimeException("Error en Checksum");
            }
        } catch (IOException e) {	// handles all input/output errors
            System.err.println(e);
        } finally {					// cleanup
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException ignoreMe) {
                // nothing
            }
        }
    }
}

