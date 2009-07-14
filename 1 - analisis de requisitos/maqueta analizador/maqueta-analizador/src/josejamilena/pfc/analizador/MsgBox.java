package josejamilena.pfc.analizador;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Mensajes emergentes.
 * @author Jose Antonio Jamilena Daza
 */
public class MsgBox {

    /** no se permite contructor sin atributos. */
    private MsgBox() {
    }

    /**
     * constructor.
     * @param titulo titulo de la ventana
     * @param mensaje mensaje a mostrar
     * @param tipo tipo de ventana [ERROR | WARNING | INFO]
     */
    public MsgBox(String titulo, String mensaje, String tipo) {
        int optionType = JOptionPane.INFORMATION_MESSAGE;
        if (tipo.equalsIgnoreCase("ERROR")) {
            optionType = JOptionPane.ERROR_MESSAGE;
        } else if (tipo.equalsIgnoreCase("INFO")) {
            optionType = JOptionPane.INFORMATION_MESSAGE;
        } else if (tipo.equalsIgnoreCase("WARNING")) {
            optionType = JOptionPane.WARNING_MESSAGE;
        }
        JOptionPane.showMessageDialog(new JFrame(),
                mensaje,
                titulo, optionType);
    }
}
