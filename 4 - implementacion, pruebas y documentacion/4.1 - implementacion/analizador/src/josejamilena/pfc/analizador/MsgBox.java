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
