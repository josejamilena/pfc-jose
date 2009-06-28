package josejamilena.pfc.servidor.crypto.easy.checksum;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CheckedInputStream;
import java.util.zip.CRC32;

/**
 * Comprobador de suma.
 * @author Jose Antonio Jamilena Daza
 */
public final class Checksum {

    /** no se podrá instanciar. */
    private Checksum() {
    }

    /**
     * Obtiene la suma de comprobación del fichero dado.
     * @param fichero fichero
     * @return suma
     * @throws java.io.IOException error
     */
    public static long checksum(final String fichero)
            throws IOException {
        FileInputStream file = new FileInputStream(fichero);
        CheckedInputStream check =
                new CheckedInputStream(file, new CRC32());
        BufferedInputStream in =
                new BufferedInputStream(check);
        while (in.read() != -1) {
        }
        return check.getChecksum().getValue();
    }
}