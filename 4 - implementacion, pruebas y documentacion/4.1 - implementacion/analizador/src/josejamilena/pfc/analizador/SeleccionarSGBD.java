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

import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import josejamilena.pfc.analizador.sql.SQLUtils;
import org.jdesktop.application.Action;
import org.jfree.ui.RefineryUtilities;

public class SeleccionarSGBD extends javax.swing.JDialog {

    private List<String> lista = null;

    public SeleccionarSGBD(java.awt.Frame parent, List<String> lst) {
        super(parent);
        this.setTitle("Seleccione...");
        lista = lst;
        initComponents();
        getRootPane().setDefaultButton(siguienteButton);
    }

    @Action
    public void siguienteSelecionarScriptBox() {
        try {
            JFrame mainFrame = App.getApplication().getMainFrame();
            SeleccionarSGBDScript scs = new SeleccionarSGBDScript(mainFrame, SQLUtils.listaScript(App.conn), (String) listaScript.getSelectedItem());
            scs.setLocationRelativeTo(mainFrame);
            scs.setSize(300, 600);
            scs.pack();
            scs.setResizable(false);
            App.getApplication().show(scs);
        } catch (SQLException ex) {
            new MsgBox("Mensaje de Error", ex.getSQLState(), "ERROR");
        } finally {
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        siguienteButton = new javax.swing.JButton();
        listaScript = new javax.swing.JComboBox();
        icono = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(josejamilena.pfc.analizador.App.class).getContext().getActionMap(SeleccionarSGBD.class, this);
        siguienteButton.setAction(actionMap.get("siguienteSelecionarScriptBox")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(josejamilena.pfc.analizador.App.class).getContext().getResourceMap(SeleccionarSGBD.class);
        siguienteButton.setText(resourceMap.getString("siguienteButton.text")); // NOI18N
        siguienteButton.setName("siguienteButton"); // NOI18N

        listaScript.setName("listaScript"); // NOI18N
        for (String i : lista) {
            this.listaScript.addItem(i);
        }

        icono.setIcon(resourceMap.getIcon("icono.icon")); // NOI18N
        icono.setText(resourceMap.getString("icono.text")); // NOI18N
        icono.setName("icono"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(icono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listaScript, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(siguienteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(icono)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(listaScript, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(siguienteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icono;
    private javax.swing.JComboBox listaScript;
    private javax.swing.JButton siguienteButton;
    // End of variables declaration//GEN-END:variables
}
