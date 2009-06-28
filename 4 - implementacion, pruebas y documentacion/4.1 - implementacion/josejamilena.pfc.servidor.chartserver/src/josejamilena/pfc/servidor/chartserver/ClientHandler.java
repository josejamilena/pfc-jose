package josejamilena.pfc.servidor.chartserver;

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
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Hilo de ejecución del servidor.
 * @author Jose Antonio Jamilena Daza
 */
class ClientHandler extends Thread {

    /** Socket. */
    private Socket miSocketServidor;
    /** Nombre de fichero index para este hilo. */
    private String nombreFichero;
    /** Tamaño del buffer de lectura. */
    private final int bufferSize = 4096;
    /** Número de grupos de estadisticas. */
    private final int numGrupos = 3;
    /** Longitud del nombre de fichero. */
    private final int longitudNombre = 8;

    /**
     * Hilo de ejecución del navegado cliente. Genera un página web para el
     * explorador cliente según los datos disponibles.
     * @param s socket
     */
    public ClientHandler(final Socket s) {
        try {
            nombreFichero = RandomStringUtils.randomAlphabetic(longitudNombre);
            nombreFichero = nombreFichero + ".html";
            miSocketServidor = s;
            PrintWriter pw;
            Grafico g = null;
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(
                    "jdbc:sqlite:.\\estadisticas.db3");
            List < GrupoConsulta > lgc = SQLUtils.listaGruposConsultas(conn);
            List < String > googleCharts = new LinkedList < String > ();
            //System.err.println(lgc.size());
            if (lgc.size() == numGrupos) {
                GrupoConsulta gcHostSgbd = lgc.get(0);
                GrupoConsulta gcHostClientes = lgc.get(1);
                GrupoConsulta gcHostTipo = lgc.get(2);
                String plantilla1 = "";
                for (String tmp : gcHostSgbd.getLista()) {
                    g = SQLUtils.consultaSQL2Grafico(conn, tmp);
                    String textoAlternativo1 = "dibujo";
                    String datos1 = "";
                    for (String i : g.getLista()) {
                        datos1 = datos1 + "," + i;
                    }
                    String media1 = "";
                    int cantidad = g.getLista().size();
                    double mediad = 0.0;
                    for (String i : g.getLista()) {
                        mediad = mediad + Double.parseDouble(i);
                    }
                    mediad = mediad / cantidad;
                    for (String i : g.getLista()) {
                        media1 = media1 + "," + Math.round(mediad);
                    }
                    String leyendaDatos1 = tmp.substring(tmp.indexOf("'") + 1,
                            tmp.lastIndexOf("'"));
                    String tituloGrafico1 = "Servidor de bases de datos "
                            + leyendaDatos1;
                    String leyendaMedia1 = "media";
                    String graficaActual = "<img src=\"http://chart.apis."
                            + "google.com/chart?chs=600x400&chd=t:" + datos1
                            + "|" + media1 + "&cht=lc&chtt=" + tituloGrafico1
                            + "&chts=FF0000,20&chdl=" + leyendaDatos1 + "|"
                            + leyendaMedia1 + "&chco=ff0000,0000ff&chxt=y"
                            + "&chxl=1:|0|10000&chds=10,30000\"" + "  alt=\""
                            + textoAlternativo1 + "\">";
                    graficaActual = graficaActual.replace("chd=t:,", "chd=t:");
                    graficaActual = graficaActual.replace("|,", "|");
                    plantilla1 = plantilla1 + "<p>" + graficaActual;
                }
                googleCharts.add(plantilla1);
                String plantilla2 = "";
                for (String tmp : gcHostClientes.getLista()) {
                    g = SQLUtils.consultaSQL2Grafico(conn, tmp);
                    // System.err.println(tmp);
                    String textoAlternativo2 = "dibujo";
                    String datos2 = "";
                    for (String i : g.getLista()) {
                        datos2 = datos2 + "," + i;
                    }
                    String media2 = "";
                    int cantidad = g.getLista().size();
                    double mediad = 0.0;
                    for (String i : g.getLista()) {
                        mediad = mediad + Double.parseDouble(i);
                    }
                    mediad = mediad / cantidad;
                    for (String i : g.getLista()) {
                        media2 = media2 + "," + Math.round(mediad);
                    }
                    String leyendaDatos2 = tmp.substring(tmp.indexOf("'") + 1,
                            tmp.lastIndexOf("'"));
                    String tituloGrafico2 = "Cliente de bases de datos "
                            + leyendaDatos2;
                    String leyendaMedia2 = "media";
                    String graficaActual = "<img src=\"http://chart.apis."
                            + "google.com/chart?chs=600x400&chd=t:" + datos2
                            + "|" + media2 + "&cht=lc&chtt=" + tituloGrafico2
                            + "&chts=FF0000,20&chdl=" + leyendaDatos2 + "|"
                            + leyendaMedia2 + "&chco=00FF00,0000ff&chxt=y"
                            + "&chxl=1:|0|10000&chds=10,30000\"" + "  alt=\""
                            + textoAlternativo2 + "\">";
                    graficaActual = graficaActual.replace("chd=t:,", "chd=t:");
                    graficaActual = graficaActual.replace("|,", "|");
                    plantilla2 = plantilla2 + "<p>" + graficaActual;
                }
                googleCharts.add(plantilla2);
                String plantilla3 = "";
                for (String tmp : gcHostTipo.getLista()) {
                    g = SQLUtils.consultaSQL2Grafico(conn, tmp);
                    // System.err.println(tmp);
                    String textoAlternativo3 = "dibujo";
                    String datos3 = "";
                    for (String i : g.getLista()) {
                        datos3 = datos3 + "," + i;
                    }
                    String media3 = "";
                    int cantidad = g.getLista().size();
                    double mediad = 0.0;
                    for (String i : g.getLista()) {
                        mediad = mediad + Double.parseDouble(i);
                    }
                    mediad = mediad / cantidad;
                    for (String i : g.getLista()) {
                        media3 = media3 + "," + Math.round(mediad);
                    }
                    String leyendaDatos3 = tmp.substring(tmp.indexOf("'") + 1,
                            tmp.lastIndexOf("'"));
                    String tituloGrafico3 = "Script " + leyendaDatos3;
                    String leyendaMedia3 = "media";
                    String graficaActual = "<img src=\"http://chart.apis."
                            + "google.com/chart?chs=600x400&chd=t:" + datos3
                            + "|" + media3 + "&cht=lc&chtt=" + tituloGrafico3
                            + "&chts=FF0000,20&chdl=" + leyendaDatos3 + "|"
                            + leyendaMedia3 + "&chco=000000,0000ff&chxt=y&"
                            + "chxl=1:|0|10000&chds=10,30000\"" + "  alt=\""
                            + textoAlternativo3 + "\">";
                    graficaActual = graficaActual.replace("chd=t:,", "chd=t:");
                    graficaActual = graficaActual.replace("|,", "|");
                    plantilla3 = plantilla3 + "<p>" + graficaActual;
                }
                googleCharts.add(plantilla3);
            }
            /* g = SQLUtils.consultaSQL2Grafico(conn,
            "select tiempo, fecha from estadisticas where tipo='ejemplo.sql'");
            */
            pw = new PrintWriter(nombreFichero);
            String titulo = "Estadisticas online";
            String script = "<script type=\"text/JavaScript\">"
                    + "<!-- "
                    + "function timedRefresh(timeoutPeriod) {"
                    + "setTimeout(\"location.reload(true);\",timeoutPeriod);"
                    + "}"
                    + "//   -->"
                    + " </script> ";
            String encabezado = "<html><head><meta content=\"text/html; "
                    + "charset=ISO-8859-1\" http-equiv=\"content-type\"><title>"
                    + titulo + "</title>" + script
                    + "</head> <body onload=\"JavaScript:timedRefresh(5000);\">"
                    + " <h1>" + titulo
                    + "</h1><br><br><br> ";
            String graficas = "";
            for (String i : googleCharts) {
                graficas = graficas + "<br><br><br>" + i;
            }
            String pie = "<br><br><br>  Powered by "
                    + "<A HREF=\"http://www.google.com/\">"
                    + "<IMG SRC=\"http://www.google.com/logos/Logo_40wht.gif\" "
                    + "border=\"0\" ALT=\"Google\" align=\"absmiddle\">"
                    + "</A></body> </html>";
            pw.println(encabezado + graficas + pie);
            pw.flush();
            pw.close();
            start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método que ejecuta el Thread.
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    miSocketServidor.getInputStream()));
            PrintStream out = new PrintStream(new BufferedOutputStream(
                    miSocketServidor.getOutputStream()));

            String s = in.readLine();
            // System.out.println(s);  // Salida para el Log

            // Atiende a servir archivos.
            String filename = "";
            StringTokenizer st = new StringTokenizer(s);
            try {

                // Parsea la petición desde el comando GET
                if (st.hasMoreElements()
                        && st.nextToken().equalsIgnoreCase("GET")
                        && st.hasMoreElements()) {
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

                /* Comprueba caracteres no permitidos,
                 para impedir acceder a directorios superiores */
                if ((filename.indexOf("..") >= 0)
                        || (filename.indexOf(':') >= 0)
                        || (filename.indexOf('|') >= 0)) {
                    throw new FileNotFoundException();
                }
                // Si la petición de acceso a un directorio no tiene "/",
                // envia al cliente un respuesta HTTP
                if (new File(filename).isDirectory()) {
                    filename = filename.replace('\\', '/');
                    out.print("HTTP/1.0 301 Movido permantemente \r\n"
                            + "Albergado en: /" + filename + "/\r\n\r\n");
                    out.close();
                    return;
                }
                // Abriendo el archivo (puede lanzar FileNotFoundException)
                InputStream f = new FileInputStream(filename);
                // Determina el tipe MIME e imprime la cabecera HTTP
                String mimeType = "text/plain";
                if (filename.endsWith(".html")
                        || filename.endsWith(".htm")) {
                    mimeType = "text/html";
                } else if (filename.endsWith(".jpg")
                        || filename.endsWith(".jpeg")) {
                    mimeType = "image/jpeg";
                } else if (filename.endsWith(".gif")) {
                    mimeType = "image/gif";
                } else if (filename.endsWith(".class")) {
                    mimeType = "application/octet-stream";
                } else if (filename.endsWith(".jnlp")) {
                    mimeType = "application/x-java-jnlp-file";
                }
                out.print("HTTP/1.0 200 OK\r\n"
                        + "Server: josejamilena_Torito-Chart-Server\r\n"
                        + "Content-type: " + mimeType + "\r\n\r\n");
                // Envia el fichero ala cliente, y cierra la conexión
                byte[] a = new byte[bufferSize];
                int n;
                while ((n = f.read(a)) > 0) {
                    out.write(a, 0, n);
                }
                f.close();
                out.close();
            } catch (FileNotFoundException x) {
                out.println("HTTP/1.0 404 No encontrado.\r\n"
                        + "Server: josejamilena_Torito-Chart-Server\r\n"
                        + "Content-type: text/html\r\n\r\n"
                        + "<html><head></head><body>" + "El fichero "
                        + filename + " no ha sido encontrado.</body></html>\n");
                out.close();
            }
        } catch (IOException x) {
            System.out.println(x);
        } finally {
            File aborrar = new File(nombreFichero);
            org.apache.commons.io.FileUtils.deleteQuietly(aborrar);
        }
    }
}


