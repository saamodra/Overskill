/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import overskill.DBConnect;
import overskill.OSLib;

/**
 *
 * @author samod
 */
public class LaporanPendaftaran extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    
    /**
     * Creates new form User
     */
    public LaporanPendaftaran() {
        initComponents();
        OSLib.setDefaultDateFilter(tglAkhir, tglAwal, 2020);
    }
    
    
    private void formLoad() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("TglAwal", tglAwal.getDate());
        map.put("TglAkhir", tglAkhir.getDate());
        Locale locale = new Locale("id", "ID");
        map.put( JRParameter.REPORT_LOCALE, locale );
        try {
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporanPendaftaran.jasper"), map, connection.conn);
            JRViewer jr = new JRViewer(jp);
            
            pnlMaster.setLayout(new BorderLayout());
            pnlMaster.repaint();
            pnlMaster.add(jr);
            pnlMaster.revalidate();
            
        } catch(JRException e) {
            System.out.println(e.toString());
        }
    }

    public JPanel getPanel() {
        return Panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnShow = new components.MaterialButton();
        tglAwal = new com.toedter.calendar.JDateChooser();
        tglAkhir = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        pnlMaster = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel.setBackground(new java.awt.Color(250, 250, 250));
        Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                PanelformComponentShown(evt);
            }
        });
        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 75));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(954, 75));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Laporan Pendaftaran");
        jPanel2.add(jLabel1);

        Panel.add(jPanel2);

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(954, 50));

        btnShow.setBackground(new java.awt.Color(255, 193, 7));
        btnShow.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnShow.setText("Tampilkan");
        btnShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowMouseClicked(evt);
            }
        });
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        tglAwal.setPreferredSize(new java.awt.Dimension(91, 30));

        tglAkhir.setPreferredSize(new java.awt.Dimension(91, 30));

        jLabel2.setText("s.d");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(518, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel.add(jPanel3);

        pnlMaster.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        pnlMaster.setOpaque(false);
        pnlMaster.setLayout(new javax.swing.BoxLayout(pnlMaster, javax.swing.BoxLayout.LINE_AXIS));
        Panel.add(pnlMaster);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PanelformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelformComponentShown
//        formLoad();
    }//GEN-LAST:event_PanelformComponentShown

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        pnlMaster.removeAll();
        formLoad();
        
    }//GEN-LAST:event_btnShowActionPerformed

    private void btnShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LaporanPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanPendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanPendaftaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private components.MaterialButton btnShow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel pnlMaster;
    private com.toedter.calendar.JDateChooser tglAkhir;
    private com.toedter.calendar.JDateChooser tglAwal;
    // End of variables declaration//GEN-END:variables
}
