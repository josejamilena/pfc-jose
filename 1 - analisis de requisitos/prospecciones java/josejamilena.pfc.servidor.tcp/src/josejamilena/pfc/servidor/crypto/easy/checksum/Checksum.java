package josejamilena.pfc.servidor.crypto.easy.checksum;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CheckedInputStream;
import java.util.zip.CRC32;

public class Checksum {

    public static long Checksum(String fichero)
            throws IOException {
        FileInputStream file = new FileInputStream(fichero);
        CheckedInputStream check =
                new CheckedInputStream(file, new CRC32());
        BufferedInputStream in =
                new BufferedInputStream(check);
        while (in.read() != -1) {
            // Read file in completely
        }
        return check.getChecksum().getValue();
    }
}