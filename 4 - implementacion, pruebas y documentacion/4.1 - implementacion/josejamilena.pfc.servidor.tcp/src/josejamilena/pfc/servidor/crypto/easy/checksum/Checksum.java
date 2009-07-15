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