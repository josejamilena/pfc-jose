package josejamilena.pfc.cliente.estadisticas;

import josejamilena.pfc.servidor.tcp.FileReceiver;

/**
 * Lanzador.
 * @author Jose Antonio Jamilena Daza
 */
public final class Main {

    /** */
    private Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        try {
            FileReceiver c = new FileReceiver();
            c.receive(args[0], Integer.parseInt(args[1]));
        } catch (Exception ex) {
            System.out.println("USO:");
            System.out.println("java -jar josejamilena.pfc.cliente.estadisticas"
                    + " <host> <puerto>");
            System.out.println("Siendo:");
            System.out.println("\thost   -> proveedor de BD de estadisticas.");
            System.out.println("\tpuerto -> puerto TCP por el que accede.");
        }
    }

}
