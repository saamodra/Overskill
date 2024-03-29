/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author samod
 */
public class Login extends javax.swing.JFrame {
    DBConnect c = new DBConnect("Overskill");
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        
        btnRole.add(rdSiswa);
        btnRole.add(rdInstruktur);
        btnRole.add(rdPegawai);
        this.setResizable(false);
        txtPassword.putClientProperty("JTextField.placeholderText", "Password");
    }
    
    private void LoginUser() {
        try {
            c.stat = c.conn.createStatement();
            String nama, id, role, sql;
            
            if(rdPegawai.isSelected()) {
                sql = "SELECT * FROM Pegawai WHERE Username='" + txtUsername.getText() + "' AND Password='" + txtPassword.getText() + "' AND Status='1'";
                nama = "Nama_Pegawai";
                id = "ID_Pegawai";
                role = "";
            } else if(rdInstruktur.isSelected()) {
                sql = "SELECT * FROM Instruktur WHERE Username='" + txtUsername.getText() + "' AND Password='" + txtPassword.getText() + "' AND Status='1'";
                nama = "Nama_Instruktur";
                id = "ID_Instruktur";
                role = "Instruktur";
            } else {
                sql = "SELECT * FROM Siswa WHERE Username='" + txtUsername.getText() + "' AND Password='" + txtPassword.getText() + "' AND Status='1'";
                nama = "Nama_Siswa";
                id = "ID_Siswa";
                role = "Siswa";
            }
            
            c.result = c.stat.executeQuery(sql);
            int count = 0;
            
            while(c.result.next()) {
                count++;
                if(nama.equals("Nama_Pegawai")) {
                    role = c.result.getString("Role");
                }
                OSSession.setId(c.result.getString(id));
                OSSession.setNama(c.result.getString(nama));
                OSSession.setRole(role);
                OSSession.setUsername(c.result.getString("Username"));
            }
            
            
            if(count > 0) {
                JOptionPane.showMessageDialog(this, "Login berhasil", "Login berhasil", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                if(role.equals("Instruktur")) {
                    InstrukturPage app = new InstrukturPage();
                    app.setVisible(true);
                } else if(role.equals("Siswa")) {
                    SiswaPage app = new SiswaPage();
                    app.setVisible(true);
                } else {
                    if(role.equals("1")) {
                        AdminPage app = new AdminPage();
                        app.setVisible(true);
                    } else {
                        KasirPage app = new KasirPage();
                        app.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login gagal", JOptionPane.ERROR_MESSAGE);
            }
            
            c.stat.close();
            c.result.close();
        } catch(Exception e) {
            System.out.println("Terjadi error saat load data user "  + e);
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

        btnRole = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new org.jdesktop.swingx.JXTextField();
        rdSiswa = new javax.swing.JRadioButton();
        rdInstruktur = new javax.swing.JRadioButton();
        rdPegawai = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(993, 591));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(980, 563));
        jPanel1.setPreferredSize(new java.awt.Dimension(980, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogin.setBackground(new java.awt.Color(255, 129, 5));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 90, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setText("Password");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 270, -1, -1));

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 320, 30));

        txtUsername.setPrompt("Username");
        jPanel1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 320, 30));

        rdSiswa.setText("Siswa");
        rdSiswa.setOpaque(false);
        jPanel1.add(rdSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 350, -1, -1));

        rdInstruktur.setText("Instruktur");
        rdInstruktur.setOpaque(false);
        jPanel1.add(rdInstruktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 350, -1, -1));

        rdPegawai.setSelected(true);
        rdPegawai.setText("Pegawai");
        rdPegawai.setOpaque(false);
        jPanel1.add(rdPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel1.setText("Username");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LoginScreen2.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginUser();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        LoginUser();
    }//GEN-LAST:event_btnLoginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.ButtonGroup btnRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton rdInstruktur;
    private javax.swing.JRadioButton rdPegawai;
    private javax.swing.JRadioButton rdSiswa;
    private javax.swing.JPasswordField txtPassword;
    private org.jdesktop.swingx.JXTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
