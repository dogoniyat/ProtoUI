package protoui;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DialogAbout.java
 * Displays the About modal dialog 
 * Generated with NetBeans 8.0.2
 * @author Régis M. LeClerc
 */
public class DialogAbout extends javax.swing.JDialog {

    /**
     * Creates new form About
     */
    public DialogAbout(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LabelTitle = new javax.swing.JLabel();
        LabelAuthor = new javax.swing.JLabel();
        ButtonAckLicense = new javax.swing.JButton();
        LabelImage = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAbout = new javax.swing.JTextArea();

        setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFocusCycleRoot(false);
        setFocusableWindowState(false);
        setModal(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(500, 223));
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        LabelTitle.setFont(LabelTitle.getFont().deriveFont(LabelTitle.getFont().getStyle() | java.awt.Font.BOLD, 24));
        LabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTitle.setText("Antidote JSON Viewer");

        LabelAuthor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelAuthor.setText("(c) 2015 by Régis M. LeClerc <marabiloso@marabiloso.org>");

        ButtonAckLicense.setIcon(new javax.swing.ImageIcon(getClass().getResource("/protoui/Images/icon_true.png"))); // NOI18N
        ButtonAckLicense.setText("Got it!");
        ButtonAckLicense.setActionCommand("");
        ButtonAckLicense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAckLicenseActionPerformed(evt);
            }
        });

        LabelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/protoui/Images/danger.png"))); // NOI18N

        TextAbout.setEditable(false);
        TextAbout.setColumns(20);
        TextAbout.setLineWrap(true);
        TextAbout.setRows(5);
        TextAbout.setWrapStyleWord(true);
        TextAbout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setViewportView(TextAbout);
        try {
            InputStream is = getClass().getResourceAsStream("About.txt");
            char[] AboutChar = new char[is.available()];
            int index = 0;
            while(is.available() > 0)
            AboutChar[index++] = (char) is.read();
            is.close();
            TextAbout.setText(String.valueOf(AboutChar));
            TextAbout.setCaretPosition(0);
        } catch (IOException ex) {
            Logger.getLogger(DialogAbout.class.getName()).log(Level.SEVERE, null, ex);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonAckLicense, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelAuthor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LabelImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(LabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelAuthor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelImage)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonAckLicense)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonAckLicenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAckLicenseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_ButtonAckLicenseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonAckLicense;
    private javax.swing.JLabel LabelAuthor;
    private javax.swing.JLabel LabelImage;
    private javax.swing.JLabel LabelTitle;
    protected javax.swing.JTextArea TextAbout;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
