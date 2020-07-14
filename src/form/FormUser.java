/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import overskill.DBConnect;
import overskill.OSLib;

/**
 *
 * @author samod
 */
public class FormUser extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    private JDialog d;
    private String formtype = "Tambah";
    
    /**
     * Creates new form TambahUser
     */
    public FormUser() {
        initComponents();
    }
    
    public FormUser(JDialog d) {
        initComponents();
        this.d = d;
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        passwordForm();
        hideLabel();
    }
    
    public FormUser(JDialog d, String... data) {
        initComponents();
        this.d = d;
        
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        
        formtype = "Ubah";
        lblTitle.setText("Ubah User");
        lblID.setText(data[0]);
        
        //Form
        txtNama.setText(data[1]);
        if(data[2].equals("Laki-Laki")) 
        {
            rdLaki.setSelected(true);
        } 
        else 
        {
            rdPerempuan.setSelected(true);
        }
        
        txtAlamat.setText(data[3]);
        txtNoTelp.setText(data[4]);
        txtEmail.setText(data[5]);
        txtUsername.setText(data[6]);
        hideLabel();
    }
    
    private void passwordForm() {
        if(formtype.equals("Tambah")) {
            lblPassBaru.setVisible(false);
            txtPassword1.setVisible(false);
            lblPassLama.setText("Password");
            txtPassword.setAe_Placeholder("Password");
        }
    }
    
    
    public JPanel getPanel() {
        return thisPanel;
    }
    
    private void saveData() {
        try {
            String id_user = OSLib.AutoNumber("TabelUser", "ID_User", "US");
            String query = "INSERT INTO TabelUser (ID_User, Nama_User, Jenis_Kelamin, Alamat, No_telp, Email, Username, Password) VALUES (?,?,?,?,?,?,?,?)";
            String jenkel = "L";
            if(rdPerempuan.isSelected()) {
                jenkel = "P";
            }
            PreparedStatement p = connection.pstat;
            p = connection.conn.prepareStatement(query);
            p.setString(1, id_user);
            p.setString(2, txtNama.getText());
            p.setString(3, jenkel);
            p.setString(4, txtAlamat.getText());
            p.setString(5, txtNoTelp.getText());
            p.setString(6, txtEmail.getText());
            p.setString(7, txtUsername.getText());
            p.setString(8, txtPassword.getText());
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data User berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah user : " + e);
        }
    }
    
    private boolean checkPassword(String id_user, String password) {
        boolean returnVar = false;
        
        try {
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM TabelUser WHERE ID_User='" + id_user + "' AND Password='" + password + "'";

            c.result = c.stat.executeQuery(sql);

            while(c.result.next()) {
                returnVar = true;
            }

            c.stat.close();
            c.result.close();
            
        } catch(SQLException e) {
            System.out.println("Gagal check password : " + e);
        }
        
        return returnVar;
    }
    
    private void updateData() {
        
        if(checkPassword(lblID.getText(), txtPassword.getText())) {
            try {
                String query = "UPDATE TabelUser SET Nama_User=?, Jenis_Kelamin=?, Alamat=?, No_telp=?, Email=?, Username=?, Password=? "
                        + "WHERE ID_User = ?";
                String jenkel = "L";
                if(rdPerempuan.isSelected()) {
                    jenkel = "P";
                }
                PreparedStatement p = connection.pstat;
                p = connection.conn.prepareStatement(query);
                p.setString(1, txtNama.getText());
                p.setString(2, jenkel);
                p.setString(3, txtAlamat.getText());
                p.setString(4, txtNoTelp.getText());
                p.setString(5, txtEmail.getText());
                p.setString(6, txtUsername.getText());
                p.setString(7, txtPassword1.getText());
                p.setString(8, lblID.getText());

                p.executeUpdate();
                p.close();

                JOptionPane.showMessageDialog(this, "Data User berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
                d.dispose();
            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat ubah user : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Password lama tidak sesuai.", "Gagal",  JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hideLabel() {
        lblNama.setVisible(false);
        lblJenkel.setVisible(false);
        lblAlamat.setVisible(false);
        lblNoTelp.setVisible(false);
        lblEmail.setVisible(false);
        lblUsername.setVisible(false);
        lblPassbaru.setVisible(false);
        lblPasslama.setVisible(false);
    }
    
    private boolean validateAll() {
        boolean nama = OSLib.fieldRequired(txtNama.getText(), lblNama);
        boolean jenkel = OSLib.toggleRequired(lblJenkel, rdLaki.isSelected(), rdPerempuan.isSelected());
        boolean alamat = OSLib.fieldRequired(txtAlamat.getText(), lblAlamat);
        boolean notelp = OSLib.fieldRequired(txtNoTelp.getText(), lblNoTelp);
        boolean email = OSLib.fieldRequired(txtEmail.getText(), lblEmail);
        boolean username = OSLib.fieldRequired(txtUsername.getText(), lblUsername);
//        boolean passlama = OSLib.fieldRequired(txtPassword.getText(), lblUsername);
        
        if(nama && jenkel && alamat && notelp && email && username) {
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rdLaki = new javax.swing.JRadioButton();
        txtNama = new components.UITextField();
        rdPerempuan = new javax.swing.JRadioButton();
        txtAlamat = new components.UITextField();
        jLabel5 = new javax.swing.JLabel();
        txtNoTelp = new components.UITextField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new components.UITextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsername = new components.UITextField();
        lblPassLama = new javax.swing.JLabel();
        txtPassword = new components.UIPassField();
        btnSimpan = new components.MaterialButton();
        lblPassBaru = new javax.swing.JLabel();
        txtPassword1 = new components.UIPassField();
        lblPassbaru = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblJenkel = new javax.swing.JLabel();
        lblAlamat = new javax.swing.JLabel();
        lblNoTelp = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPasslama = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setMinimumSize(new java.awt.Dimension(191, 50));
        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah User");

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblID.setText("ID");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
                .addComponent(lblID)
                .addGap(42, 42, 42))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblID))
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        Panel.add(jPanel1);

        thisPanel.add(Panel);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nama");
        jLabel2.setToolTipText("");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 35, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Jenis Kelamin");
        jLabel3.setToolTipText("");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 85, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Alamat");
        jLabel4.setToolTipText("");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        rdLaki.setText("Laki-Laki");
        jPanel4.add(rdLaki, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 85, -1, -1));

        txtNama.setAe_Placeholder("Nama User");
        txtNama.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 241, -1));

        rdPerempuan.setText("Perempuan");
        jPanel4.add(rdPerempuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 85, -1, -1));

        txtAlamat.setAe_Placeholder("Alamat");
        txtAlamat.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 135, 241, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("No. Telepon");
        jLabel5.setToolTipText("");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 195, -1, -1));

        txtNoTelp.setAe_Placeholder("No. Telepon");
        txtNoTelp.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 241, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Email");
        jLabel6.setToolTipText("");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, -1, -1));

        txtEmail.setAe_Placeholder("Email");
        txtEmail.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 245, 241, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Username");
        jLabel7.setToolTipText("");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 305, -1, -1));

        txtUsername.setAe_Placeholder("Username");
        txtUsername.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 241, -1));

        lblPassLama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassLama.setText("Password Lama");
        lblPassLama.setToolTipText("");
        jPanel4.add(lblPassLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        txtPassword.setAe_Placeholder("Password Lama");
        jPanel4.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 355, 241, -1));

        btnSimpan.setBackground(new java.awt.Color(255, 51, 51));
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 470, -1, -1));

        lblPassBaru.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassBaru.setText("Password Baru");
        lblPassBaru.setToolTipText("");
        jPanel4.add(lblPassBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 415, -1, -1));

        txtPassword1.setAe_Placeholder("Password Baru");
        jPanel4.add(txtPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 241, -1));

        lblPassbaru.setForeground(new java.awt.Color(255, 51, 51));
        lblPassbaru.setText("lblNama");
        lblPassbaru.setToolTipText("");
        jPanel4.add(lblPassbaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 445, -1, -1));

        lblNama.setForeground(new java.awt.Color(255, 51, 51));
        lblNama.setText("lblNama");
        lblNama.setToolTipText("");
        jPanel4.add(lblNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 65, -1, -1));

        lblJenkel.setForeground(new java.awt.Color(255, 51, 51));
        lblJenkel.setText("lblNama");
        lblJenkel.setToolTipText("");
        jPanel4.add(lblJenkel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, -1, -1));

        lblAlamat.setForeground(new java.awt.Color(255, 51, 51));
        lblAlamat.setText("lblNama");
        lblAlamat.setToolTipText("");
        jPanel4.add(lblAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, -1, -1));

        lblNoTelp.setForeground(new java.awt.Color(255, 51, 51));
        lblNoTelp.setText("lblNama");
        lblNoTelp.setToolTipText("");
        jPanel4.add(lblNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 225, -1, -1));

        lblEmail.setForeground(new java.awt.Color(255, 51, 51));
        lblEmail.setText("lblNama");
        lblEmail.setToolTipText("");
        jPanel4.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, -1, -1));

        lblUsername.setForeground(new java.awt.Color(255, 51, 51));
        lblUsername.setText("lblNama");
        lblUsername.setToolTipText("");
        jPanel4.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 335, -1, -1));

        lblPasslama.setForeground(new java.awt.Color(255, 51, 51));
        lblPasslama.setText("lblNama");
        lblPasslama.setToolTipText("");
        jPanel4.add(lblPasslama, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FormUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private javax.swing.ButtonGroup btnJenkel;
    private components.MaterialButton btnSimpan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblJenkel;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNoTelp;
    private javax.swing.JLabel lblPassBaru;
    private javax.swing.JLabel lblPassLama;
    private javax.swing.JLabel lblPassbaru;
    private javax.swing.JLabel lblPasslama;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JRadioButton rdLaki;
    private javax.swing.JRadioButton rdPerempuan;
    private javax.swing.JPanel thisPanel;
    private components.UITextField txtAlamat;
    private components.UITextField txtEmail;
    private components.UITextField txtNama;
    private components.UITextField txtNoTelp;
    private components.UIPassField txtPassword;
    private components.UIPassField txtPassword1;
    private components.UITextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
