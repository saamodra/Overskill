/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import overskill.DBConnect;
import overskill.OSLib;

/**
 *
 * @author samod
 */
public class FormInstruktur extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    private JDialog d;
    private String formtype = "Tambah";
    
    /**
     * Creates new form TambahUser
     */
    public FormInstruktur() {
        initComponents();
    }
    
    public FormInstruktur(JDialog d) {
        initComponents();
        this.d = d;
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        hideLabel();
        passwordForm();
    }
    
    public FormInstruktur(JDialog d, String... data) 
    {
        initComponents();
        this.d = d;
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        formtype = "Ubah";
        lblTitle.setText("Ubah Instruktur");
        lblID.setText(data[0]);
        //Form
        txtNama.setText(data[1]);
        String date = (data[2]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            TglLahir.setDate(sdf.parse(date));
        } catch (ParseException ex) 
        {
            Logger.getLogger(FormInstruktur.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(data[3].equals("Laki-Laki")) 
        {
            rdLaki.setSelected(true);
        } 
        else 
        {
            rdPerempuan.setSelected(true);
        }
        txtAlamat.setText(data[4]);
        txtNoTelp.setText(data[5]);
        txtEmail.setText(data[6]);
        txtPengalaman.setText(data[7]);
        txtUsername.setText(data[8]);
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
    
    private void hideLabel() {
        lblNama.setVisible(false);
        lblJenkel.setVisible(false);
        lblTgl.setVisible(false);
        lblAlamat.setVisible(false);
        lblNoTelp.setVisible(false);
        lblEmail.setVisible(false);
        lblPengalamanKerja.setVisible(false);
        lblUsername.setVisible(false);
        lblPasswordBaru.setVisible(false);
        lblPasswordLama.setVisible(false);
    }
    
    public JPanel getPanel() {
        return thisPanel;
    }
    
    private void saveData() {
        try {
            String id_user = OSLib.AutoNumber("Instruktur", "ID_Instruktur", "IN");
            String query = "INSERT INTO Instruktur (ID_Instruktur, Nama_Instruktur, Tanggal_lahir, Jenis_Kelamin, Alamat, No_telp, Email, "
                    + "Pengalaman_kerja, Username, Password) VALUES (?,?,?,?,?,?,?,?,?,?)";
            String jenkel = "L";
            if(rdPerempuan.isSelected()) {
                jenkel = "P";
            }
            java.sql.Date tglLahir = new java.sql.Date(TglLahir.getDate().getTime());
            PreparedStatement p = connection.pstat;
            p = connection.conn.prepareStatement(query);
            p.setString(1, id_user);
            p.setString(2, txtNama.getText());
            p.setDate(3, tglLahir);
            p.setString(4, jenkel);
            p.setString(5, txtAlamat.getText());
            p.setString(6, txtNoTelp.getText());
            p.setString(7, txtEmail.getText());
            p.setString(8, txtPengalaman.getText());
            p.setString(9, txtUsername.getText());
            p.setString(10, txtPassword.getText());
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data Instruktur berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah instruktur : " + e);
        }
    }
    
    private boolean checkPassword(String id_instruktur, String password) {
        boolean returnVar = false;
        
        try {
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Instruktur WHERE ID_Instruktur='" + id_instruktur + "' AND Password='" + password + "'";

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
                String query = "UPDATE Instruktur SET Nama_Instruktur=?,Tanggal_lahir=?, Jenis_Kelamin=?, Alamat=?, No_telp=?, Email=?, Pengalaman_kerja=?, Username=?, Password=? "
                        + "WHERE ID_Instruktur = ?";
                String jenkel = "L";
                if(rdPerempuan.isSelected()) {
                    jenkel = "P";
                }
                java.sql.Date tglLahir = new java.sql.Date(TglLahir.getDate().getTime());
                PreparedStatement p = connection.pstat;
                p = connection.conn.prepareStatement(query);
                p.setString(1, txtNama.getText());
                p.setDate(2, tglLahir);
                p.setString(3, jenkel );
                p.setString(4, txtAlamat.getText());
                p.setString(5, txtNoTelp.getText());
                p.setString(6, txtEmail.getText());
                p.setString(7, txtPengalaman.getText());
                p.setString(8, txtUsername.getText());
                p.setString(9, txtPassword1.getText());
                p.setString(10, lblID.getText());

                p.executeUpdate();
                p.close();

                JOptionPane.showMessageDialog(this, "Data Instruktur berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
                d.dispose();
            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat ubah instruktur : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Password lama tidak sesuai.", "Gagal",  JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateAll() {
        boolean nama = OSLib.fieldRequired(txtNama.getText(), lblNama);
        boolean tglLahir = OSLib.fieldRequired(TglLahir.getDate(), lblTgl);
        boolean jenkel = OSLib.toggleRequired(lblJenkel, rdLaki.isSelected(), rdPerempuan.isSelected());
        boolean alamat = OSLib.fieldRequired(txtAlamat.getText(), lblAlamat);
        boolean notelp = OSLib.fieldRequired(txtNoTelp, lblNoTelp);
        boolean email = OSLib.emailRequired(txtEmail.getText(), lblEmail);
        boolean pk = OSLib.fieldRequired(txtPengalaman.getText(), lblPengalamanKerja);
        boolean username = OSLib.fieldRequired(txtUsername.getText(), lblUsername);
        boolean passlama = OSLib.fieldRequired(txtPassword.getText(), lblPasswordLama);
        if(formtype.equals("Tambah")){
            
        } else {
            boolean passbaru = OSLib.fieldRequired(txtPassword1.getText(), lblPasswordBaru);
        }
        
        return false;
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
        lblTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TglLahir = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPengalaman = new javax.swing.JTextArea();
        lblPasswordBaru = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        lblPengalamanKerja = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPasswordLama = new javax.swing.JLabel();
        lblJenkel = new javax.swing.JLabel();
        lblAlamat = new javax.swing.JLabel();
        lblNoTelp = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah Instruktur");
        jPanel1.add(lblTitle);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 618, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3);

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblID.setText("ID");
        jPanel1.add(lblID);

        Panel.add(jPanel1);

        thisPanel.add(Panel);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nama");
        jLabel2.setToolTipText("");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 45, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Jenis Kelamin");
        jLabel3.setToolTipText("");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Alamat");
        jLabel4.setToolTipText("");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        rdLaki.setText("Laki-Laki");
        jPanel4.add(rdLaki, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        txtNama.setAe_Placeholder("Nama Instruktur");
        txtNama.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 241, -1));

        rdPerempuan.setText("Perempuan");
        jPanel4.add(rdPerempuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, -1));

        txtAlamat.setAe_Placeholder("Alamat");
        txtAlamat.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 185, 241, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("No. Telepon");
        jLabel5.setToolTipText("");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 245, -1, -1));

        txtNoTelp.setAe_Placeholder("No. Telepon");
        txtNoTelp.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 241, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Email");
        jLabel6.setToolTipText("");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        txtEmail.setAe_Placeholder("Email");
        txtEmail.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 295, 241, 31));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Username");
        jLabel7.setToolTipText("");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, -1, -1));

        txtUsername.setAe_Placeholder("Username");
        txtUsername.setPreferredSize(new java.awt.Dimension(230, 30));
        jPanel4.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 185, 241, -1));

        lblPassLama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassLama.setText("Password Lama");
        lblPassLama.setToolTipText("");
        jPanel4.add(lblPassLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 245, -1, -1));

        txtPassword.setAe_Placeholder("Password Lama");
        jPanel4.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 240, 241, -1));

        btnSimpan.setBackground(new java.awt.Color(255, 51, 51));
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 360, -1, -1));

        lblPassBaru.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassBaru.setText("Password Baru");
        lblPassBaru.setToolTipText("");
        jPanel4.add(lblPassBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, -1, -1));

        txtPassword1.setAe_Placeholder("Password Baru");
        txtPassword1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassword1ActionPerformed(evt);
            }
        });
        jPanel4.add(txtPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 295, 241, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tanggal Lahir");
        jLabel8.setToolTipText("");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 96, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Pengalaman Kerja");
        jLabel9.setToolTipText("");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 45, -1, -1));
        jPanel4.add(TglLahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 95, 241, -1));

        txtPengalaman.setColumns(20);
        txtPengalaman.setRows(5);
        jScrollPane1.setViewportView(txtPengalaman);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 241, 120));

        lblPasswordBaru.setForeground(new java.awt.Color(255, 51, 51));
        lblPasswordBaru.setText("Wajib diisi.");
        jPanel4.add(lblPasswordBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 330, -1, -1));

        lblNama.setForeground(new java.awt.Color(255, 51, 51));
        lblNama.setText("Wajib diisi.");
        jPanel4.add(lblNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 75, -1, -1));

        lblTgl.setForeground(new java.awt.Color(255, 51, 51));
        lblTgl.setText("Wajib diisi.");
        jPanel4.add(lblTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, -1, -1));

        lblPengalamanKerja.setForeground(new java.awt.Color(255, 51, 51));
        lblPengalamanKerja.setText("Wajib diisi.");
        jPanel4.add(lblPengalamanKerja, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 165, -1, -1));

        lblUsername.setForeground(new java.awt.Color(255, 51, 51));
        lblUsername.setText("Wajib diisi.");
        jPanel4.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, -1, -1));

        lblPasswordLama.setForeground(new java.awt.Color(255, 51, 51));
        lblPasswordLama.setText("Wajib diisi.");
        jPanel4.add(lblPasswordLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 275, -1, -1));

        lblJenkel.setForeground(new java.awt.Color(255, 51, 51));
        lblJenkel.setText("Wajib diisi.");
        jPanel4.add(lblJenkel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 165, -1, -1));

        lblAlamat.setForeground(new java.awt.Color(255, 51, 51));
        lblAlamat.setText("Wajib diisi.");
        jPanel4.add(lblAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, -1, -1));

        lblNoTelp.setForeground(new java.awt.Color(255, 51, 51));
        lblNoTelp.setText("Wajib diisi.");
        jPanel4.add(lblNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 275, -1, -1));

        lblEmail.setForeground(new java.awt.Color(255, 51, 51));
        lblEmail.setText("Wajib diisi.");
        jPanel4.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
        );

        thisPanel.add(jPanel2);

        getContentPane().add(thisPanel);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(formtype.equals("Tambah")) {
            saveData();
        } else {
            updateData();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtPassword1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassword1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassword1ActionPerformed

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
            java.util.logging.Logger.getLogger(FormInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FormInstruktur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private com.toedter.calendar.JDateChooser TglLahir;
    private javax.swing.ButtonGroup btnJenkel;
    private components.MaterialButton btnSimpan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblJenkel;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNoTelp;
    private javax.swing.JLabel lblPassBaru;
    private javax.swing.JLabel lblPassLama;
    private javax.swing.JLabel lblPasswordBaru;
    private javax.swing.JLabel lblPasswordLama;
    private javax.swing.JLabel lblPengalamanKerja;
    private javax.swing.JLabel lblTgl;
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
    private javax.swing.JTextArea txtPengalaman;
    private components.UITextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
