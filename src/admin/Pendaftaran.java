/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import admin.form.FormInstruktur;
import admin.form.FormUser;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import overskill.DBConnect;
import overskill.OSLib;
import overskill.OSSession;

/**
 *
 * @author samod
 */
public class Pendaftaran extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<String> id_kelas = new ArrayList<>();
    ArrayList<String> id_siswa = new ArrayList<>();
    ArrayList<Double> harga = new ArrayList<>();
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    
    
    /**
     * Creates new form User
     */
    public Pendaftaran() {
        initComponents();
        formLoad();
    }
    
    
    private void formLoad() {
        loadKelas();
        loadSiswa();
        cmbKelas.addItemListener(new ItemChangeListener());
        hideLabel();
    }

    private void loadKelas() {
        try 
        {
            cmbKelas.removeAllItems();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Kelas WHERE Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            
            cmbKelas.addItem("Pilih Kelas..");
            id_kelas.add("Pilih Kelas..");
            harga.add(0.0);
            while(c.result.next()) {
                ResultSet r = c.result;
                cmbKelas.addItem(r.getString("Nama_Kelas"));
                id_kelas.add(r.getString("ID_Kelas"));
                harga.add(r.getDouble("Harga"));
            }
            
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data kelas "  + e);
        }
    }
    
    private void loadSiswa() {
        try 
        {
            cmbSiswa.removeAllItems();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Siswa WHERE Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            
            cmbSiswa.addItem("Pilih Siswa..");
            id_siswa.add("Pilih Siswa..");
            while(c.result.next()) {
                ResultSet r = c.result;
                cmbSiswa.addItem(r.getString("Nama_Siswa"));
                id_siswa.add(r.getString("ID_Siswa"));
            }
            
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data siswa "  + e);
        }
    }
    
    public JPanel getPanel() {
        return Panel;
    }
    
    private void SaveData() {
        int i = cmbKelas.getSelectedIndex();
        int j = cmbSiswa.getSelectedIndex();
        try {
            String id_pendaftaran = OSLib.AutoNumber("Pendaftaran", "ID_Pendaftaran", "PD");
            String id_feedback = OSLib.AutoNumber("Feedback", "ID_Feedback", "FB");
            
            String queryPendaftaran = "INSERT INTO Pendaftaran (ID_Pendaftaran, ID_Kelas, ID_Siswa, ID_User, Pembayaran) VALUES (?,?,?,?,?)";
            String queryFeedback = "INSERT INTO Feedback (ID_Feedback, ID_Kelas, ID_Siswa) VALUES (?,?,?)";
         
            
            try (PreparedStatement p = connection.conn.prepareStatement(queryPendaftaran)) {
                p.setString(1, id_pendaftaran);
                p.setString(2, id_kelas.get(i));
                p.setString(3, id_siswa.get(j));
                p.setString(4, OSSession.getId());
                p.setDouble(5, harga.get(i));
                
                p.executeUpdate();
            }
            
            try (PreparedStatement p = connection.conn.prepareStatement(queryFeedback)) {
                p.setString(1, id_feedback);
                p.setString(2, id_kelas.get(i));
                p.setString(3, id_siswa.get(j));
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Pendaftaran berhasil.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat pendaftaran: " + e);
        }
    
    }
    
    private void clearForm() {
        cmbKelas.setSelectedIndex(0);
        cmbSiswa.setSelectedIndex(0);
        txtBayar.setText(String.valueOf(0));
    }
    
    private void hideLabel() {
        lblKelas.setVisible(false);
        lblSiswa.setVisible(false);
    }
    
    private boolean validateAll() {
        boolean kelas = OSLib.comboRequired(cmbKelas.getSelectedItem().toString(), "Pilih Kelas..", lblKelas);
        boolean siswa = OSLib.comboRequired(cmbSiswa.getSelectedItem().toString(), "Pilih Siswa..", lblSiswa);
        
        return kelas && siswa;
    }
    
    class ItemChangeListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
           if (event.getStateChange() == ItemEvent.SELECTED) {
                int i = cmbKelas.getSelectedIndex();
                txtBayar.setText(formatter.format(harga.get(i)));
           }
        }

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
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbKelas = new javax.swing.JComboBox<>();
        cmbSiswa = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtBayar = new org.jdesktop.swingx.JXTextField();
        btnBayar = new components.MaterialButton();
        lblKelas = new javax.swing.JLabel();
        lblSiswa = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

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
        jLabel1.setText("Pendaftaran Siswa");
        jPanel2.add(jLabel1);

        Panel.add(jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(400, 32767));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 499));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Kelas");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 36, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Siswa");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        cmbKelas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cmbKelas.setPreferredSize(new java.awt.Dimension(56, 30));
        cmbKelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbKelasItemStateChanged(evt);
            }
        });
        cmbKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKelasActionPerformed(evt);
            }
        });
        jPanel1.add(cmbKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 31, 244, -1));

        cmbSiswa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cmbSiswa.setPreferredSize(new java.awt.Dimension(56, 30));
        jPanel1.add(cmbSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 85, 244, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Pembayaran");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 145, -1, -1));

        txtBayar.setEnabled(false);
        txtBayar.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtBayar.setPreferredSize(new java.awt.Dimension(65, 30));
        txtBayar.setPrompt("Bayar");
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBayarKeyTyped(evt);
            }
        });
        jPanel1.add(txtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 244, -1));

        btnBayar.setBackground(new java.awt.Color(40, 167, 69));
        btnBayar.setText("Daftar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 122, 34));

        lblKelas.setForeground(new java.awt.Color(255, 51, 51));
        lblKelas.setText("Wajib diisi.");
        jPanel1.add(lblKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 65, -1, -1));

        lblSiswa.setForeground(new java.awt.Color(255, 51, 51));
        lblSiswa.setText("Wajib diisi.");
        jPanel1.add(lblSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        jPanel4.add(jPanel1);

        jPanel3.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel3);

        Panel.add(jPanel4);

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
        formLoad();
    }//GEN-LAST:event_PanelformComponentShown

    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        OSLib.textFieldCurrencyFormat(txtBayar);
    }//GEN-LAST:event_txtBayarKeyReleased

    private void txtBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtBayarKeyTyped

    private void cmbKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKelasActionPerformed

    private void cmbKelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbKelasItemStateChanged
        
    }//GEN-LAST:event_cmbKelasItemStateChanged

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        if(validateAll()) {
            SaveData();
        } else {
            JOptionPane.showMessageDialog(this, "Isi data dengan lengkap.", "Gagal",  JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnBayarActionPerformed

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
            java.util.logging.Logger.getLogger(Pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Pendaftaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private components.MaterialButton btnBayar;
    private javax.swing.JComboBox<String> cmbKelas;
    private javax.swing.JComboBox<String> cmbSiswa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblKelas;
    private javax.swing.JLabel lblSiswa;
    private org.jdesktop.swingx.JXTextField txtBayar;
    // End of variables declaration//GEN-END:variables
}
