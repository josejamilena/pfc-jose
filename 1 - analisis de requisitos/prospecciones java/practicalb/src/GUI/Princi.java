/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Princi {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        GestorVentanas gestor = new GestorVentanas();
        //gestor.crearVentanaPrincipal();
        //gestor.crearVentanaMuestra();
        //gestor.crearVentanaSeleccion();
        gestor.crearVentanaDatosConex();
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BarraProgreso.createAndShowGUI();
            }
        });*/
    }
}
