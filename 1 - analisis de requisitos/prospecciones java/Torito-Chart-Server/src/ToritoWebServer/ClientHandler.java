package ToritoWebServer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.StringTokenizer;

class ClientHandler extends Thread {

    private Socket miSocketServidor;
    private String nombreFichero;

    public ClientHandler(Socket s) {
        try {
            nombreFichero = org.apache.commons.lang.RandomStringUtils.randomAlphabetic(8);
            nombreFichero = nombreFichero + ".html";
            miSocketServidor = s;
            PrintWriter pw;
            Grafico g = null;
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\estadisticas.db3");
            g = SQLtoGrafico.consultaSQL(conn, "select tiempo, fecha from estadisticas where tipo='ejemplo.sql'");
            pw = new PrintWriter(nombreFichero);
            String titulo = "Titulo";
            String tituloGrafico = "Titulo Grafico";
            String textoAlternativo = "dibujo";
            String datos = "";
            for (String tmp : g.getLista()) {
                datos = datos + "," + tmp;
            }
            String media = "";
            int cantidad = g.getLista().size();
            double mediad = 0.0;
            for (String tmp : g.getLista()) {
                mediad = mediad + Double.parseDouble(tmp);
            }
            mediad = mediad / cantidad;
            for (String tmp : g.getLista()) {
                media = media + "," + Math.round(mediad);
            }
            String leyendaDatos = g.getTituloEjeX();
            String leyendaMedia = "media";
            String plantilla = "<html><head><meta content=\"text/html; "
                    + "charset=ISO-8859-1\" http-equiv=\"content-type\"><title>"
                    + titulo + "</title> </head> <body> <h1>" + titulo + "</h1>"
                    + "<img src=\"http://chart.apis.google.com/chart?chs=600x400&chd=t:"
                    + datos + "|" + media + "&cht=lc&chtt=" + tituloGrafico
                    + "&chts=FF0000,20&chdl=" + leyendaDatos  + "|" + leyendaMedia
                    + "&chco=ff0000,0000ff&chxt=y&chxl=1:|0|10000&chds=10,30000\""
                    + "  alt=\"" + textoAlternativo + "\"> </body> </html>";
            plantilla = plantilla.replace("chd=t:,", "chd=t:");
            plantilla = plantilla.replace("|,", "|");
            pw.println(plantilla);
            pw.flush();
            pw.close();
            start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(miSocketServidor.getInputStream()));
            PrintStream out = new PrintStream(new BufferedOutputStream(miSocketServidor.getOutputStream()));

            String s = in.readLine();
            System.out.println(s);  // Salida para el Log

            // Atiende a servir archivos.
            String filename = "";
            StringTokenizer st = new StringTokenizer(s);
            try {

                // Parsea la petición desde el comando GET
                if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements()) {
                    filename = st.nextToken();
                } else {
                    throw new FileNotFoundException();
                }
                // Añade "/" with "index.html"
                if (filename.endsWith("/")) {
                    filename += nombreFichero;
                }
                // Quita / del nombre del archivo
                while (filename.indexOf("/") == 0) {
                    filename = filename.substring(1);
                }
                // Reemplaza "/" por "\" en el path para servidores en Win32
                filename = filename.replace('/', File.separator.charAt(0));

                // Comprueba caracteres no permitidos, para impedir acceder a directorios superiores
                if ((filename.indexOf("..") >= 0) || (filename.indexOf(':') >= 0) || (filename.indexOf('|') >= 0)) {
                    throw new FileNotFoundException();
                }
                // Si la petición de acceso a un directorio no tiene "/",
                // envia al cliente un respuesta HTTP
                if (new File(filename).isDirectory()) {
                    filename = filename.replace('\\', '/');
                    out.print("HTTP/1.0 301 Movido permantemente \r\n" +
                            "Albergado en: /" + filename + "/\r\n\r\n");
                    out.close();
                    return;
                }
                // Abriendo el archivo (puede lanzar FileNotFoundException)
                InputStream f = new FileInputStream(filename);
                // Determina el tipe MIME e imprime la cabecera HTTP
                String mimeType = "text/plain";
                if (filename.endsWith(".html") || filename.endsWith(".htm")) {
                    mimeType = "text/html";
                } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                    mimeType = "image/jpeg";
                } else if (filename.endsWith(".gif")) {
                    mimeType = "image/gif";
                } else if (filename.endsWith(".class")) {
                    mimeType = "application/octet-stream";
                } else if (filename.endsWith(".jnlp")) {
                    mimeType = "application/x-java-jnlp-file";
                }
                out.print("HTTP/1.0 200 OK\r\n" +
                        "Content-type: " + mimeType + "\r\n\r\n");
                // Envia el fichero ala cliente, y cierra la conexión
                byte[] a = new byte[4096];
                int n;
                while ((n = f.read(a)) > 0) {
                    out.write(a, 0, n);
                }
                f.close();
                out.close();
                File aborrar = new File(nombreFichero);
                boolean success = aborrar.delete();
                if (!success)
                    throw new IllegalArgumentException("Delete: deletion failed");
            } catch (FileNotFoundException x) {
                out.println("HTTP/1.0 404 No encontrado.\r\n" +
                        "Content-type: text/html\r\n\r\n" +
                        "<html><head></head><body>" + "El fichero " +
                        filename + " no ha sido encontrado.</body></html>\n");
                out.close();
            }
        } catch (IOException x) {
            System.out.println(x);
        }
    }
}


