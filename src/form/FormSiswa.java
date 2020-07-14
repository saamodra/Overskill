/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

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
public class FormSiswa extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    private JDialog d;
    private String formtype = "Tambah";
    
    /**
     * Creates new form TambahUser
     */
    public FormSiswa() {
        initComponents();
    }
    
    public FormSiswa(JDialog d) {
        initComponents();
        this.d = d;
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        passwordForm();
    }
    
    public FormSiswa(JDialog d, String... data) {
        initComponents();
        this.d = d;
        
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        
        formtype = "Ubah";
        lblTitle.setText("Ubah Siswa");
        lblID.setText(data[0]);
        
        //Form
        txtNama.setText(data[1]);
        String date = (data[2]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try 
        {
            TglLahir.setDate(sdf.parse(date));
        } 
        catch (ParseException ex) 
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
        txtNotelp_ortu.setText(data[7]);
        txtStatusSiwa.setText(data[8]);
        txtUsername.setText(data[9]);
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
            String id_siswa = OSLib.AutoNumber("Siswa", "ID_Siswa", "SW");
            String query = "INSERT INTO Siswa (ID_Siswa, Nama_Siswa, Tanggal_lahir, Jenis_Kelamin, Alamat, No_telp, Email,"
                    + "Notelp_ortu, Status_Siswa, Username, Password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            String jenkel = "L";
            if(rdPerempuan.isSelected()) {
                jenkel = "P";
            }
            
            java.sql.Date tglLahir = new java.sql.Date(TglLahir.getDate().getTime());
            
            PreparedStatement p = connection.pstat;
            p = connection.conn.prepareStatement(query);
            p.setString(1, id_siswa);
            p.setString(2, txtNama.getText());
            p.setDate(3, tglLahir);
            p.setString(4, jenkel);
            p.setString(5, txtAlamat.getText());
            p.setString(6, txtNoTelp.getText());
            p.setString(7, txtEmail.getText());
            p.setString(8, txtNotelp_ortu.getText());
            p.setString(9, txtStatusSiwa.getText());
            p.setString(10, txtUsername.getText());
            p.setString(11, txtPassword.getText());
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data Siswa berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah siswa : " + e);
        }
    }
    
    private boolean checkPassword(String id_siswa, String password) {
        boolean returnVar = false;
        
        try {
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Siswa WHERE ID_Siswa='" + id_siswa + "' AND Password='" + password + "'";

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
                String query = "UPDATE Siswa SET Nama_Siswa=?, Tanggal_lahir=?, Jenis_Kelamin=?, Alamat=?, No_telp=?, Email=?, "
                        + "Notelp_ortu=?, Status_Siswa=?, Username=?, Password=? "
                        + "WHERE ID_Siswa = ?";
                String jenkel = "L";
                if(rdPerempuan.isSelected()) {
                    jenkel = "P";
                }
                java.sql.Date tglLahir = new java.sql.Date(TglLahir.getDate().getTime());
                PreparedStatement p = connection.pstat;
                p = connection.conn.prepareStatement(query);
                p.setString(1, txtNama.getText());
                p.setDate(2, tglLahir);
                p.setString(3, jenkel);
                p.setString(4, txtAlamat.getText());
                p.setString(5, txtNoTelp.getText());
                p.setString(6, txtEmail.getText());
                p.setString(7, txtNoTelp.getText());
                p.setString(8, txtNotelp_ortu.getText());
                p.setString(9, txtStatusSiwa.getText());
                p.setString(10, txtPassword1.getText());
                p.setString(11, lblID.getText());

                p.executeUpdate();
                p.close();

                JOptionPane.showMessageDialog(this, "Data Siswa berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
                d.dispose();
            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat ubah siswa : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Password lama tidak sesuai.", "Gagal",  JOptionPane.ERROR_MESSAGE);
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

        btnJenkel = new javax.swing.ButtonGroup();
        thisPanel = new javax.swing.JPanel();
        Panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
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
        txtNotelp_ortu = new components.UITextField();
        txtStatusSiwa = new components.UITextField();
        jLabel10 = new javax.swing.JLabel();
        TglLahir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah Siswa");
        jPanel1.add(lblTitle);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nama");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Jenis Kelamin");
        jLabel3.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Alamat");
        jLabel4.setToolTipText("");

        rdLaki.setText("Laki-Laki");

        txtNama.setAe_Placeholder("Nama User");
        txtNama.setPreferredSize(new java.awt.Dimension(230, 30));

        rdPerempuan.setText("Perempuan");

        txtAlamat.setAe_Placeholder("Alamat");
        txtAlamat.setPreferredSize(new java.awt.Dimension(230, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("No. Telepon");
        jLabel5.setToolTipText("");

        txtNoTelp.setAe_Placeholder("No. Telepon");
        txtNoTelp.setPreferredSize(new java.awt.Dimension(230, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Email");
        jLabel6.setToolTipText("");

        txtEmail.setAe_Placeholder("Email");
        txtEmail.setPreferredSize(new java.awt.Dimension(230, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Username");
        jLabel7.setToolTipText("");

        txtUsername.setAe_Placeholder("Username");
        txtUsername.setPreferredSize(new java.awt.Dimension(230, 30));

        lblPassLama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassLama.setText("Password Lama");
        lblPassLama.setToolTipText("");

        txtPassword.setAe_Placeholder("Password Lama");

        btnSimpan.setBackground(new java.awt.Color(255, 51, 51));
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        lblPassBaru.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassBaru.setText("Password Baru");
        lblPassBaru.setToolTipText("");

        txtPassword1.setAe_Placeholder("Password Baru");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("No. Telepon Orang Tua");
        jLabel8.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Status Siswa");
        jLabel9.setToolTipText("");

        txtNotelp_ortu.setAe_Placeholder("No. Telepon Orang Tua");

        txtStatusSiwa.setAe_Placeholder("Status Siswa");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Tanggal Lahir");
        jLabel10.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(lblPassBaru)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(lblPassLama)
                            .addComponent(jLabel10))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(rdLaki)
                                .addGap(18, 18, 18)
                                .addComponent(rdPerempuan))
                            .addComponent(txtNoTelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassword1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNotelp_ortu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtStatusSiwa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(48, 48, 48))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAlamat, txtEmail, txtNama, txtNoTelp, txtPassword, txtUsername});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(TglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rdLaki)
                    .addComponent(rdPerempuan))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNotelp_ortu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtStatusSiwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassLama))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassBaru))
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        thisPanel.add(jPanel4);

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
            java.util.logging.Logger.getLogger(FormSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FormSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private com.toedter.calendar.JDateChooser TglLahir;
    private javax.swing.ButtonGroup btnJenkel;
    private components.MaterialButton btnSimpan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblPassBaru;
    private javax.swing.JLabel lblPassLama;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JRadioButton rdLaki;
    private javax.swing.JRadioButton rdPerempuan;
    private javax.swing.JPanel thisPanel;
    private components.UITextField txtAlamat;
    private components.UITextField txtEmail;
    private components.UITextField txtNama;
    private components.UITextField txtNoTelp;
    private components.UITextField txtNotelp_ortu;
    private components.UIPassField txtPassword;
    private components.UIPassField txtPassword1;
    private components.UITextField txtStatusSiwa;
    private components.UITextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
