package josejamilena.pfc.servidor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FileCopy {

    /** Logger. */
    private static Logger logger = Logger.getLogger(FileCopy.class);

    public static void main(String[] args) {
        try {
           PropertyConfigurator.configure("log4j.properties");
            copy(args[0], args[1]);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static void copy(String fromFileName, String toFileName)
            throws IOException {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        if (!fromFile.exists()) {
            logger.warn("El fichero " + fromFileName + " no existe.");
        }
        if (!fromFile.isFile()) {
            logger.warn("No es un fichero.");
        }
        if (!fromFile.canRead()) {
            throw new IOException("No se ha podido acceder al fichero origen "
                    + fromFileName);
        }
        if (toFile.isDirectory()) {
            toFile = new File(toFile, fromFile.getName());
        }
        if (toFile.exists()) {
            if (!toFile.canWrite()) {
                logger.warn("El fichero destino no se puede escribir.");
            }
//            System.out.print("Overwrite existing file " + toFile.getName()
//                       + "? (Y/N): ");
//            System.out.flush();
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    System.in));
//            String response = in.readLine();
//            if (!response.equals("Y") && !response.equals("y")) {
//                throw new IOException("FileCopy: " + "existing file was
            //              not overwritten.");
//            }
        } else {
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists()) {
                logger.warn("El directorio destino no existe.");
            }
            if (dir.isFile()) {
                logger.warn("El directorio es un fichero.");
            }
            if (!dir.canWrite()) {
                logger.warn("El directorio no es accesible.");
            }
        }
        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write
            }
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                    ;
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
    }
}
