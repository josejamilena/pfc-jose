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

package josejamilena.pfc.servidor.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Utilidades para trabajar con Channels.
 * @see java.nio.channels.Channels
 * @author Jose Antonio Jamilena Daza
 */
public final class ChannelTools {

    /** No instanciable. */
    private ChannelTools() {
    }


    /**
     * Copia rápida por Channels.
     * @param src oringen
     * @param dest destino
     * @throws java.io.IOException error
     */
    public static void fastChannelCopy(final ReadableByteChannel src,
            final WritableByteChannel dest) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

//    public static void main(String[] args) throws FileNotFoundException,
//        IOException {
//        String inputFile = args[0];
//        String outputFile = args[1];
//        final InputStream input = new FileInputStream(inputFile);
//        final OutputStream output = new FileOutputStream(outputFile);
//        final ReadableByteChannel inputChannel = Channels.newChannel(input);
//        final WritableByteChannel outputChannel = Channels.newChannel(output);
//        ChannelTools.fastChannelCopy(inputChannel, outputChannel);
//        inputChannel.close();
//        outputChannel.close();
//    }
}
