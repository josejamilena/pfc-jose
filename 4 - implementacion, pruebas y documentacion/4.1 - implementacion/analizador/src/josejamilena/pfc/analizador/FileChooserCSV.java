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

import java.io.IOException;
import java.sql.SQLException;
import josejamilena.pfc.analizador.sql.SQLite2CSV;

/**
 *
 * @author Jose Antonio Jamilena Daza
 */
public class FileChooserCSV extends javax.swing.JDialog {

    /** Creates new form FileChooserCSV */
    public FileChooserCSV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Guardar como fichero .CSV...");
        this.setResizable(false);
        initComponents();
        this.pack();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        fileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileChooser.setName("fileChooser"); // NOI18N
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")
                && fileChooser.getSelectedFile()!=null) {
            try {
                String fichero = fileChooser.getSelectedFile().getAbsolutePath();
                SQLite2CSV s2c = new SQLite2CSV();
                if (!fichero.toUpperCase().endsWith(".csv".toUpperCase())) {
                    fichero = fichero.concat(".csv");
                }
                s2c.csv(fichero, App.getApplication().conn, "SELECT * FROM ESTADISTICAS");
            } catch (java.lang.NullPointerException ex) {
                new MsgBox("Error de Base de Datos",
                        ex.getMessage(),
                        "ERROR");
            } catch (SQLException ex) {
                new MsgBox("Error de Base de Datos",
                        ex.getSQLState(),
                        "ERROR");
            } catch (IOException ex) {
                new MsgBox("Error de Escritura",
                        ex.getMessage(),
                        "ERROR");
            }
        }
        this.dispose();
}//GEN-LAST:event_fileChooserActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FileChooserCSV dialog = new FileChooserCSV(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    // End of variables declaration//GEN-END:variables

}
