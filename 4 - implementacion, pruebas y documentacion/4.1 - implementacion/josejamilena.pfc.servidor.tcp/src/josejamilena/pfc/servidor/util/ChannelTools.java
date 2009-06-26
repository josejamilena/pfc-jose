package josejamilena.pfc.servidor.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public final class ChannelTools {

    public static void fastChannelCopy(final ReadableByteChannel src,
            final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            // prepare the buffer to be drained
            buffer.flip();
            // write to the channel, may block
            dest.write(buffer);
            // If partial transfer, shift remainder down
            // If buffer is empty, same as doing clear()
            buffer.compact();
        }
        // EOF will leave buffer in fill state
        buffer.flip();
        // make sure the buffer is fully drained.
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String inputFile = args[0];
        String outputFile = args[1];
        // allocate the stream ... only for example
        final InputStream input = new FileInputStream(inputFile);
        final OutputStream output = new FileOutputStream(outputFile);
        // get an channel from the stream
        final ReadableByteChannel inputChannel = Channels.newChannel(input);
        final WritableByteChannel outputChannel = Channels.newChannel(output);
        // copy the channels
        ChannelTools.fastChannelCopy(inputChannel, outputChannel);
        // closing the channels
        inputChannel.close();
        outputChannel.close();
    }
}
