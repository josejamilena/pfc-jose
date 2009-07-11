/*
 * View.java
 */
package josejamilena.pfc.analizador;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import josejamilena.pfc.analizador.sql.SQLUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The application's main frame.
 */
public class View extends FrameView {

    public View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void openFile() {
        if (openBox == null) {
            JFrame mainFrame = App.getApplication().getMainFrame();
            openBox = new FileChooser(mainFrame, true);
            openBox.setLocationRelativeTo(mainFrame);
        }
        App.getApplication().show(openBox);
        // reinicia las cajas
        this.clienteBox = null;
        this.sgbdBox = null;
        this.selectBox = null;
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = App.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        App.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        generarInforme = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        graficasMenu = new javax.swing.JMenu();
        graficoPorSGBD = new javax.swing.JMenuItem();
        graficoPorScript = new javax.swing.JMenuItem();
        graficoPorCliente = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(josejamilena.pfc.analizador.App.class).getContext().getActionMap(View.class, this);
        generarInforme.setAction(actionMap.get("generarInforme")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(josejamilena.pfc.analizador.App.class).getContext().getResourceMap(View.class);
        generarInforme.setIcon(resourceMap.getIcon("Pets.icon[0]")); // NOI18N
        generarInforme.setAutoscrolls(true);
        generarInforme.setBorder(null);
        generarInforme.setDisabledIcon(resourceMap.getIcon("petButton.disabledIcon")); // NOI18N
        generarInforme.setDisabledSelectedIcon(resourceMap.getIcon("Pets.icon[0]")); // NOI18N
        generarInforme.setName("petButton"); // NOI18N
        generarInforme.setPressedIcon(resourceMap.getIcon("Pets.pressedIcon")); // NOI18N
        generarInforme.setRequestFocusEnabled(false);
        generarInforme.setRolloverIcon(resourceMap.getIcon("Pets.icon[0]")); // NOI18N
        generarInforme.setRolloverSelectedIcon(resourceMap.getIcon("Pets.icon[0]")); // NOI18N
        generarInforme.setSelectedIcon(resourceMap.getIcon("Pets.icon[0]")); // NOI18N
        generarInforme.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(462, Short.MAX_VALUE)
                .addComponent(generarInforme)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(207, Short.MAX_VALUE)
                .addComponent(generarInforme)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        openMenuItem.setAction(actionMap.get("openFile")); // NOI18N
        openMenuItem.setIcon(resourceMap.getIcon("StatusBar.icons[0]")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("StatusBar.icons[1]")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        graficasMenu.setText(resourceMap.getString("graficasMenu.text")); // NOI18N
        graficasMenu.setName("graficasMenu"); // NOI18N

        graficoPorSGBD.setAction(actionMap.get("graficosPorSGBD")); // NOI18N
        graficoPorSGBD.setIcon(resourceMap.getIcon("StatusBar.icons[3]")); // NOI18N
        graficoPorSGBD.setText(resourceMap.getString("graficoPorSGBD.text")); // NOI18N
        graficoPorSGBD.setName("graficoPorSGBD"); // NOI18N
        graficasMenu.add(graficoPorSGBD);

        graficoPorScript.setAction(actionMap.get("graficosPorScript")); // NOI18N
        graficoPorScript.setIcon(resourceMap.getIcon("StatusBar.icons[2]")); // NOI18N
        graficoPorScript.setText(resourceMap.getString("graficoPorScript.text")); // NOI18N
        graficoPorScript.setName("graficoPorScript"); // NOI18N
        graficasMenu.add(graficoPorScript);

        graficoPorCliente.setAction(actionMap.get("graficosPorCliente")); // NOI18N
        graficoPorCliente.setIcon(resourceMap.getIcon("StatusBar.icons[4]")); // NOI18N
        graficoPorCliente.setText(resourceMap.getString("graficoPorCliente.text")); // NOI18N
        graficoPorCliente.setName("graficoPorCliente"); // NOI18N
        graficasMenu.add(graficoPorCliente);

        menuBar.add(graficasMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setIcon(resourceMap.getIcon("StatusBar.icons[5]")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 431, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void graficosPorScript() {
        if (App.iniciarConexion()) {
            if (selectBox == null) {
                try {
                    JFrame mainFrame = App.getApplication().getMainFrame();
                    selectBox = new SeleccionarScript(mainFrame, SQLUtils.listaScript(App.conn));
                    selectBox.setLocationRelativeTo(mainFrame);
                    App.getApplication().show(selectBox);
                } catch (SQLException ex) {
                    new MsgBox("Mensaje de Error",
                            "Formato de fichero erroneo", "ERROR");
                }
            }
        } else {
            new MsgBox("Mensaje de Error",
                    "Fichero de estadisticas no seleccionado", "ERROR");
        }
    }

    @Action
    public void graficosPorSGBD() {
        if (App.iniciarConexion()) {
            if (sgbdBox == null) {
                try {
                    JFrame mainFrame = App.getApplication().getMainFrame();
                    sgbdBox = new SeleccionarSGBD(mainFrame, SQLUtils.listaHostSgbd(App.conn));
                    sgbdBox.setLocationRelativeTo(mainFrame);
                    App.getApplication().show(sgbdBox);
                } catch (SQLException ex) {
                    new MsgBox("Mensaje de Error",
                            "Formato de fichero erroneo", "ERROR");
                }
            }
        } else {
            new MsgBox("Mensaje de Error",
                    "Fichero de estadisticas no seleccionado", "ERROR");
        }
    }

    @Action
    public void graficosPorCliente() {
        if (App.iniciarConexion()) {
            if (clienteBox == null) {
                try {
                    JFrame mainFrame = App.getApplication().getMainFrame();
                    clienteBox = new SeleccionarCliente(mainFrame, SQLUtils.listaHostCliente(App.conn));
                    App.getApplication().show(clienteBox);
                } catch (SQLException ex) {
                    new MsgBox("Mensaje de Error",
                            "Formato de fichero erroneo", "ERROR");
                }
            }
        } else {
            new MsgBox("Mensaje de Error",
                    "Fichero de estadisticas no seleccionado", "ERROR");
        }
    }

    @Action
    public void generarInforme() {
        this.progressBar.setMaximum(0);
        this.progressBar.setMaximum(100);
        this.progressBar.setVisible(true);

        this.progressBar.setValue(50);

        this.progressBar.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton generarInforme;
    private javax.swing.JMenu graficasMenu;
    private javax.swing.JMenuItem graficoPorCliente;
    private javax.swing.JMenuItem graficoPorSGBD;
    private javax.swing.JMenuItem graficoPorScript;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[6];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JDialog openBox;
    private JDialog selectBox;
    private JDialog sgbdBox;
    private JDialog clienteBox;
}
