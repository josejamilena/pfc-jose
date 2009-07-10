package josejamilena.pfc.analizador;

import java.sql.SQLException;
import java.util.List;
import javax.swing.WindowConstants;
import org.jdesktop.application.Action;
import org.jfree.ui.RefineryUtilities;

public class SeleccionarSGBD extends javax.swing.JDialog {

    private List<String> lista = null;

    public SeleccionarSGBD(java.awt.Frame parent, List<String> lst) {
        super(parent);
        lista = lst;
        initComponents();
        getRootPane().setDefaultButton(closeButton);
    }

    @Action
    public void closeSelecionarScriptBox() {
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        listaScript = new javax.swing.JComboBox();
        cargarGrafica = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(josejamilena.pfc.analizador.App.class).getContext().getActionMap(SeleccionarSGBD.class, this);
        closeButton.setAction(actionMap.get("closeSelecionarScriptBox")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(josejamilena.pfc.analizador.App.class).getContext().getResourceMap(SeleccionarSGBD.class);
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        listaScript.setName("listaScript"); // NOI18N
        for (String i : lista) {
            this.listaScript.addItem(i);
        }

        cargarGrafica.setAction(actionMap.get("cargarGrafica")); // NOI18N
        cargarGrafica.setText(resourceMap.getString("cargarGrafica.text")); // NOI18N
        cargarGrafica.setName("cargarGrafica"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(listaScript, 0, 123, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cargarGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                        .addComponent(closeButton)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(184, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cargarGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listaScript))
                .addGap(105, 105, 105))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void cargarGrafica() {
        try {
            GraficoPorSGBD demo = new GraficoPorSGBD((String) listaScript.getSelectedItem());
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
            demo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cargarGrafica;
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox listaScript;
    // End of variables declaration//GEN-END:variables
}
