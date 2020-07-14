/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;
import overskill.DBConnect;
import overskill.OSLib;

/**
 *
 * @author KEL15
 */
public class FormKelas extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    private JDialog d;
    private String formtype = "Tambah";
    private ArrayList<String> id_ins = new ArrayList<>();
    private int instrukturPlaceholder = 0;
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    /**
     * Creates new form TambahUser
     */
    public FormKelas() {
        initComponents();
        hideLabel();
    }
    
    public FormKelas(JDialog d) {
        initComponents();
        this.d = d;
        loadInstruktur();
        lblID.setVisible(false);
        hideLabel();
    }
    
    public FormKelas(JDialog d, String... data) {
        initComponents();
        this.d = d;
        formtype = "Ubah";
        loadInstruktur();
        
        lblTitle.setText("Ubah Kelas");
        lblID.setText(data[0]);
        
        //Form
        int i = id_ins.indexOf(data[2]);
        
        cmbIns.setSelectedIndex(i);
        txtNama.setText(data[1]);
        txtPertemuan.setText(data[4]);
        String harga = OSLib.getNumberCurrencyComma(data[5]);
        
        txtHarga.setText(formatter.format(Double.parseDouble(harga)).replace(",", "."));
        hideLabel();
    }
    
    private void hideLabel() {
        lblNama.setVisible(false);
        lblHarga.setVisible(false);
        lblPertemuan.setVisible(false);
//        lblInstruktur.setVisible(false);
    }
    
    public JPanel getPanel() {
        return thisPanel;
    }
    
    private void saveData() {
        int i = cmbIns.getSelectedIndex();
        try {
            String id_kelas = OSLib.AutoNumber("Kelas", "ID_Kelas", "KL");
            String query = "INSERT INTO Kelas (ID_Kelas, ID_Instruktur, Nama_Kelas, Pertemuan, Harga) VALUES (?,?,?,?,?)";
         
            PreparedStatement p = connection.pstat;
            p = connection.conn.prepareStatement(query);
            p.setString(1, id_kelas);
            p.setString(2, id_ins.get(i));
            p.setString(3, txtNama.getText());
            p.setString(4, txtPertemuan.getText());
            p.setString(5, OSLib.getNumberCurrency(txtHarga.getText()));
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data Kelas berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah kelas : " + e);
        }
    }
    
    private void updateData() 
    {
        int i = cmbIns.getSelectedIndex();
        try 
        {
            String query = "UPDATE Kelas SET ID_Instruktur=?, Nama_Kelas=?, Pertemuan=?, Harga=?"
                + " WHERE ID_Kelas = ?";
            PreparedStatement p = connection.pstat;
            p = connection.conn.prepareStatement(query);
            
            p.setString(1, id_ins.get(i));
            p.setString(2, txtNama.getText());
            p.setString(3, txtPertemuan.getText());
            p.setString(4, OSLib.getNumberCurrency(txtHarga.getText()));
            p.setString(5, lblID.getText());

            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data Kelas berhasil diubah.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error pada saat ubah kelas : " + e);
        }
    }
    
    private void loadInstruktur() {
        try {
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Instruktur WHERE Status = 1";
            c.result = c.stat.executeQuery(sql);
            if(formtype == "Tambah") {
                cmbIns.addItem("Pilih Instruktur ..");
            }
            
            while(c.result.next()) {
                id_ins.add(c.result.getString("ID_Instruktur"));
                cmbIns.addItem(c.result.getString("Nama_Instruktur"));
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data kelas "  + e);
        }
    }
    
    private boolean validateAll() {
        boolean nama = OSLib.fieldRequired(txtNama.getText(), lblNama);
        boolean pertemuan = OSLib.fieldRequired(txtPertemuan.getText(), lblPertemuan);
        boolean harga = OSLib.fieldRequired(txtHarga.getText(), lblHarga);
        
        if(nama && pertemuan && harga) {
            return true;
        } else {
            return false;
        }
//        boolean nama = OSLib.fieldRequired(txtNama.getText(), lblNama);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnJenkel = new javax.swing.ButtonGroup();
        thisPanel = new javax.swing.JPanel();
        Panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new components.UITextField();
        txtHarga = new components.UITextField();
        jLabel5 = new javax.swing.JLabel();
        txtPertemuan = new components.UITextField();
        btnSimpan = new components.MaterialButton();
        jLabel8 = new javax.swing.JLabel();
        cmbIns = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblPertemuan = new javax.swing.JLabel();
        lblHarga = new javax.swing.JLabel();
        lblInstruktur = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah Kelas");

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblID.setText("ID");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                .addComponent(lblID)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblID))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        Panel.add(jPanel1);

        thisPanel.add(Panel);

        jPanel4.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nama");
        jLabel2.setToolTipText("");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 41, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Pertemuan");
        jLabel4.setToolTipText("");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 97, -1, -1));

        txtNama.setAe_Placeholder("Overskill");
        txtNama.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 37, 241, -1));

        txtHarga.setAe_Placeholder("150.000");
        txtHarga.setPreferredSize(new java.awt.Dimension(230, 30));
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });
        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaKeyTyped(evt);
            }
        });
        jPanel4.add(txtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 154, 207, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Harga");
        jLabel5.setToolTipText("");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 158, -1, -1));

        txtPertemuan.setAe_Placeholder("16");
        txtPertemuan.setPreferredSize(new java.awt.Dimension(230, 30));
        txtPertemuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPertemuanKeyTyped(evt);
            }
        });
        jPanel4.add(txtPertemuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 93, 103, -1));

        btnSimpan.setBackground(new java.awt.Color(255, 51, 51));
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 285, -1, 35));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Instruktur");
        jLabel8.setToolTipText("");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 215, -1, -1));

        cmbIns.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cmbIns.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbInsFocusGained(evt);
            }
        });
        cmbIns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbInsMouseClicked(evt);
            }
        });
        jPanel4.add(cmbIns, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 215, 246, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Rp.");
        jLabel6.setToolTipText("");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 158, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Pertemuan");
        jLabel7.setToolTipText("");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 97, -1, -1));

        lblNama.setForeground(new java.awt.Color(255, 51, 51));
        lblNama.setText("lblNama");
        lblNama.setToolTipText("");
        jPanel4.add(lblNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 73, -1, -1));

        lblPertemuan.setForeground(new java.awt.Color(255, 51, 51));
        lblPertemuan.setText("lblPertemuan");
        lblPertemuan.setToolTipText("");
        jPanel4.add(lblPertemuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 129, -1, -1));

        lblHarga.setForeground(new java.awt.Color(255, 51, 51));
        lblHarga.setText("lblHarga");
        lblHarga.setToolTipText("");
        jPanel4.add(lblHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 190, -1, -1));

        lblInstruktur.setForeground(new java.awt.Color(255, 51, 51));
        lblInstruktur.setText("lblInstruktur");
        lblInstruktur.setToolTipText("");
        jPanel4.add(lblInstruktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 251, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        thisPanel.add(jPanel2);

        getContentPane().add(thisPanel);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(validateAll()) {
            if(formtype.equals("Tambah")) {
                saveData();
            } else {
                updateData();
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void cmbInsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbInsFocusGained
        if(formtype.equals("Tambah")) {
            if(instrukturPlaceholder == 0) {
                cmbIns.removeItemAt(0);
                instrukturPlaceholder++;
            }
        }
    }//GEN-LAST:event_cmbInsFocusGained

    private void cmbInsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbInsMouseClicked
        
    }//GEN-LAST:event_cmbInsMouseClicked

    private void txtHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaKeyTyped

    private void txtHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyReleased
        OSLib.textFieldCurrencyFormat(txtHarga);
    }//GEN-LAST:event_txtHargaKeyReleased

    private void txtPertemuanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPertemuanKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtPertemuanKeyTyped

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
            java.util.logging.Logger.getLogger(FormKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FormKelas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private javax.swing.ButtonGroup btnJenkel;
    private components.MaterialButton btnSimpan;
    private javax.swing.JComboBox<String> cmbIns;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblInstruktur;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPertemuan;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel thisPanel;
    private components.UITextField txtHarga;
    private components.UITextField txtNama;
    private components.UITextField txtPertemuan;
    // End of variables declaration//GEN-END:variables
}

