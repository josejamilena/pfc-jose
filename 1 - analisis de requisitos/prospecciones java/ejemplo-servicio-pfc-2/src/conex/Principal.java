package conex;

/**
 * Clase principal.
 * @author Jose Antonio Jamilena Daza, Miguel Angel Moreno Leiva, Pablo Landin.
 */
public class Principal {

    /**
     * main.
     * @param args argumentos.
     */
    public static void main(String[] args) {
        // mostrarAyuda();
        // try {
        //            String nombreFichero;
        //            Comun comun = new Comun();
        //            Conexion conexion = new Conexion();
        //            Estadisticas estadisticas = new Estadisticas();
        //            if (args.length == 0) {
        //                nombreFichero = "ejemplo.sql";
        //            } else {
        //                nombreFichero = args[0];
        //            }
        //            LanzarConsultasDeFichero lcf = new LanzarConsultasDeFichero(conexion, estadisticas, nombreFichero);
        //            System.out.println("Media de tiempos del fichero:");
        //            System.out.println(lcf.mediaDeTiempos());
        //            System.out.println("Media de tiempos en manipulacion de datos (INSERT / UPDATE /DELETE):");
        //            System.out.println(estadisticas.mostrarMediaManipulacionDatos());
        //            System.out.println("Media de tiempos en manipulacion de tablas (DROP / CREATE):");
        //            System.out.println(estadisticas.mostrarMediaManipulacionTablas());
        //            System.out.println("Media de tiempos en consultas de selecion (SELECT):");
        //            System.out.println(estadisticas.mostrarMediaSeleccion());
        //            System.out.println("Media de tiempos de otras operaciones (?):");
        //            System.out.println(estadisticas.mostrarMediaOtrasOperaciones());
        //            System.out.println("Media de tiempos total:");
        //            System.out.println(estadisticas.mostrarMedia());

        //        } catch (java.sql.SQLException ex) {
        //            System.err.println("FALLO EN CONEXION CON LA BASE DE DATOS");
        //        } catch (FileNotFoundException ex) {
        //            ex.printStackTrace();
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //        }
        GUI.GestorVentanas gv = new GUI.GestorVentanas();
        gv.crearVentanaDatosConex();
    }
    //    /**
    //     * muestra mensaje de ayuda.
    //     */
    //    private static void mostrarAyuda() {
    //        System.out.println();
    //        System.out.println("=================");
    //        System.out.println("Benchmark Ligeros");
    //        System.out.println("=================");
    //        System.out.println();
    //        System.out.println("Modo de empleo:");
    //        System.out.println("java -jar practica.jar ");
    //        System.out.println("\tAsi, carga el script SQL del fichero ejemplo.sql");
    //        System.out.println("java -jar practica.jar <ficheroSQL> ");
    //        System.out.println("\tAsi, carga el script SQL del fichero indicado");
    //        System.out.println("\tCada linea del fichero ha de ser una sentencia SQL, sino no va");
    //        System.out.println();
    //        System.out.println("Las conexiones y usuarios se modifican en el fichero config.properties");
    //        System.out.println();
    //    }
}
