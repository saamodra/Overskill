/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.form;

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
        hideLabel();
        passwordForm();
        loadStatusSiswa();
    }
    
    public FormSiswa(JDialog d, String... data) 
    {
        initComponents();
        this.d = d;
        btnJenkel.add(rdLaki);
        btnJenkel.add(rdPerempuan);
        loadStatusSiswa();
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
            Logger.getLogger(FormSiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(data[3].equals("L")) 
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
        txtNoTelpOrtu.setText(data[7]);
        cmbStatusSiswa.setSelectedItem(data[8]);
        txtUsername.setText(data[9]);
        hideLabel();
        txtPassword.putClientProperty("JTextField.placeholderText", "Password Lama");
        txtPassword1.putClientProperty("JTextField.placeholderText", "Password Baru");
    }
    
    private void passwordForm() {
        if(formtype.equals("Tambah")) {
            lblPassBaru.setVisible(false);
            txtPassword1.setVisible(false);
            lblPassLama.setText("Password");
            txtPassword.putClientProperty("JTextField.placeholderText", "Password");
        }
    }
    
    private void loadStatusSiswa() {
        cmbStatusSiswa.addItem("Pilih Kategori Siswa ..");
        cmbStatusSiswa.addItem("SMP");
        cmbStatusSiswa.addItem("SMA");
        cmbStatusSiswa.addItem("UMUM");
    }
    
    private void hideLabel() {
        lblNama.setVisible(false);
        lblJenkel.setVisible(false);
        lblTgl.setVisible(false);
        lblAlamat.setVisible(false);
        lblNoTelp.setVisible(false);
        lblEmail.setVisible(false);
        lblNoTelpOrtu.setVisible(false);
        lblUsername.setVisible(false);
        lblPasswordBaru.setVisible(false);
        lblPasswordLama.setVisible(false);
        lblStatusSiswa.setVisible(false);
    }
    
    public JPanel getPanel() {
        return thisPanel;
    }
    
    private void saveData() {
        try {
            String id_siswa = OSLib.AutoNumber("Siswa", "ID_Siswa", "SW");
            String query = "INSERT INTO Siswa (ID_Siswa, Nama_Siswa, Tanggal_lahir, Jenis_Kelamin, Alamat, No_telp, Email,"
                    + "Notelp_ortu, Status_peserta, Username, Password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            String jenkel = "L";
            if(rdPerempuan.isSelected()) {
                jenkel = "P";
            }
            
            java.sql.Date tglLahir = new java.sql.Date(TglLahir.getDate().getTime());
            
            try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                p.setString(1, id_siswa);
                p.setString(2, txtNama.getText());
                p.setDate(3, tglLahir);
                p.setString(4, jenkel);
                p.setString(5, txtAlamat.getText());
                p.setString(6, txtNoTelp.getText());
                p.setString(7, txtEmail.getText());
                p.setString(8, txtNoTelpOrtu.getText());
                p.setString(9, cmbStatusSiswa.getSelectedItem().toString());
                p.setString(10, txtUsername.getText());
                p.setString(11, txtPassword.getText());
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Data Siswa berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah siswa : " + e);
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
            try {
                String query;
                String jenkel = "L";
                boolean updatePass = false;
                
                if(rdPerempuan.isSelected()) {
                    jenkel = "P";
                }
                
                if(txtPassword.getText().equals("")) {
                    query = "UPDATE Siswa SET Nama_Siswa=?, Tanggal_lahir=?, Jenis_Kelamin=?, Alamat=?, No_telp=?, Email=?, "
                        + "Notelp_ortu=?, Status_peserta=?, Username=? "
                        + "WHERE ID_Siswa = ?";
                } else {
                    query = "UPDATE Siswa SET Nama_Siswa=?, Tanggal_lahir=?, Jenis_Kelamin=?, Alamat=?, No_telp=?, Email=?, "
                        + "Notelp_ortu=?, Status_peserta=?, Username=?, Password=? "
                        + "WHERE ID_Siswa = ?";
                    updatePass = true;
                }
                
                java.sql.Date tglLahir = new java.sql.Date(TglLahir.getDate().getTime());
                try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                    p.setString(1, txtNama.getText());
                    p.setDate(2, tglLahir);
                    p.setString(3, jenkel );
                    p.setString(4, txtAlamat.getText());
                    p.setString(5, txtNoTelp.getText());
                    p.setString(6, txtEmail.getText());
                    p.setString(7, txtNoTelpOrtu.getText());
                    p.setString(8, cmbStatusSiswa.getSelectedItem().toString());
                    p.setString(9, txtUsername.getText());
                    
                    if(updatePass) {
                        p.setString(10, txtPassword1.getText());
                        p.setString(11, lblID.getText());
                    } else {
                        p.setString(10, lblID.getText());
                    }
                    
                    p.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Data Siswa berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
                d.dispose();
            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat ubah siswa : " + e);
            }
    }
    
    private boolean validateAll() {
        boolean nama = OSLib.fieldRequired(txtNama.getText(), lblNama);
        boolean tglLahir = OSLib.dateRequired(TglLahir.getDate(), lblTgl);
        boolean jenkel = OSLib.toggleRequired(lblJenkel, rdLaki.isSelected(), rdPerempuan.isSelected());
        boolean alamat = OSLib.fieldRequired(txtAlamat.getText(), lblAlamat);
        boolean notelp = OSLib.fieldRequired(txtNoTelp.getText(), lblNoTelp);
        boolean email = OSLib.emailRequired(txtEmail.getText(), lblEmail);
        boolean noTelpOrtu = OSLib.fieldRequired(txtNoTelpOrtu.getText(), lblNoTelpOrtu);
        boolean statusSiswa = OSLib.comboRequired(cmbStatusSiswa.getSelectedItem().toString(), "Pilih Kategori Siswa ..", lblStatusSiswa);
        boolean username = OSLib.fieldRequired(txtUsername.getText(), lblUsername);
        
        if(formtype.equals("Tambah")){
            boolean passlama = OSLib.fieldRequired(txtPassword.getText(), lblPasswordLama);
            
            return nama && tglLahir && jenkel && alamat && notelp && email && noTelpOrtu && statusSiswa && username && passlama;
        } else {
            
            if(!txtPassword.getText().equals("")) {
                boolean passlama = OSLib.fieldRequired(txtPassword.getText(), lblPasswordLama);
                boolean passbaru = OSLib.fieldRequired(txtPassword1.getText(), lblPasswordBaru);
                
                return nama && tglLahir && jenkel && alamat && notelp && email && noTelpOrtu && statusSiswa && username && passlama && passbaru;
            } else {
                return nama && tglLahir && jenkel && alamat && notelp && email && noTelpOrtu && statusSiswa && username;
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
        rdPerempuan = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblPassLama = new javax.swing.JLabel();
        lblPassBaru = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TglLahir = new com.toedter.calendar.JDateChooser();
        lblPasswordBaru = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        lblNoTelpOrtu = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPasswordLama = new javax.swing.JLabel();
        lblJenkel = new javax.swing.JLabel();
        lblAlamat = new javax.swing.JLabel();
        lblNoTelp = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtNama = new org.jdesktop.swingx.JXTextField();
        txtAlamat = new org.jdesktop.swingx.JXTextField();
        txtNoTelp = new org.jdesktop.swingx.JXTextField();
        txtEmail = new org.jdesktop.swingx.JXTextField();
        txtUsername = new org.jdesktop.swingx.JXTextField();
        btnSimpan = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        txtPassword1 = new javax.swing.JPasswordField();
        txtNoTelpOrtu = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        lblStatusSiswa = new javax.swing.JLabel();
        cmbStatusSiswa = new org.jdesktop.swingx.JXComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setBackground(new java.awt.Color(250, 250, 250));
        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setBackground(new java.awt.Color(250, 250, 250));
        Panel.setOpaque(false);
        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah Siswa");
        jPanel1.add(lblTitle);

        jPanel3.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 656, Short.MAX_VALUE)
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

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setOpaque(false);

        jPanel4.setOpaque(false);
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

        rdLaki.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdLaki.setText("Laki-Laki");
        rdLaki.setOpaque(false);
        jPanel4.add(rdLaki, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        rdPerempuan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdPerempuan.setText("Perempuan");
        rdPerempuan.setOpaque(false);
        jPanel4.add(rdPerempuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("No. Telepon");
        jLabel5.setToolTipText("");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 245, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Email");
        jLabel6.setToolTipText("");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Username");
        jLabel7.setToolTipText("");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, -1, -1));

        lblPassLama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassLama.setText("Password Lama");
        lblPassLama.setToolTipText("");
        jPanel4.add(lblPassLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, -1, -1));

        lblPassBaru.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassBaru.setText("Password Baru");
        lblPassBaru.setToolTipText("");
        jPanel4.add(lblPassBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tanggal Lahir");
        jLabel8.setToolTipText("");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 96, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("No. Telepon Ortu");
        jLabel9.setToolTipText("");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 45, -1, -1));

        TglLahir.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        TglLahir.setPreferredSize(new java.awt.Dimension(91, 25));
        jPanel4.add(TglLahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 95, 241, -1));

        lblPasswordBaru.setForeground(new java.awt.Color(255, 51, 51));
        lblPasswordBaru.setText("Wajib diisi.");
        jPanel4.add(lblPasswordBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 300, -1, -1));

        lblNama.setForeground(new java.awt.Color(255, 51, 51));
        lblNama.setText("Wajib diisi.");
        jPanel4.add(lblNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 75, -1, -1));

        lblTgl.setForeground(new java.awt.Color(255, 51, 51));
        lblTgl.setText("Wajib diisi.");
        jPanel4.add(lblTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, -1, -1));

        lblNoTelpOrtu.setForeground(new java.awt.Color(255, 51, 51));
        lblNoTelpOrtu.setText("Wajib diisi.");
        jPanel4.add(lblNoTelpOrtu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 75, -1, -1));

        lblUsername.setForeground(new java.awt.Color(255, 51, 51));
        lblUsername.setText("Wajib diisi.");
        jPanel4.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, -1, -1));

        lblPasswordLama.setForeground(new java.awt.Color(255, 51, 51));
        lblPasswordLama.setText("Wajib diisi.");
        jPanel4.add(lblPasswordLama, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, -1, -1));

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

        txtNama.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNama.setPrompt("Nama");
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaKeyTyped(evt);
            }
        });
        jPanel4.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 240, 30));

        txtAlamat.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtAlamat.setPrompt("Alamat");
        jPanel4.add(txtAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 185, 240, 30));

        txtNoTelp.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNoTelp.setPrompt("No. Telepon");
        txtNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTelpKeyTyped(evt);
            }
        });
        jPanel4.add(txtNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 240, 30));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtEmail.setPrompt("Email");
        jPanel4.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 295, 240, 30));

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtUsername.setPrompt("Username");
        jPanel4.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 155, 240, 30));

        btnSimpan.setBackground(new java.awt.Color(245, 121, 0));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 340, 90, 30));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel4.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 240, 30));

        txtPassword1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel4.add(txtPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 260, 240, 30));

        txtNoTelpOrtu.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNoTelpOrtu.setPrompt("No. Telepon orang tua");
        txtNoTelpOrtu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTelpOrtuKeyTyped(evt);
            }
        });
        jPanel4.add(txtNoTelpOrtu, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 240, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Kategori Siswa");
        jLabel10.setToolTipText("");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, -1, -1));

        lblStatusSiswa.setForeground(new java.awt.Color(255, 51, 51));
        lblStatusSiswa.setText("Wajib diisi.");
        jPanel4.add(lblStatusSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, -1, -1));

        cmbStatusSiswa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel4.add(cmbStatusSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 95, 240, 30));

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
        if(validateAll()) {
            if(formtype.equals("Tambah")) {
                saveData();
            } else {
                if(!txtPassword.getText().equals("")) {
                    if(checkPassword(lblID.getText(), txtPassword.getText())) {
                        updateData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Password lama tidak sesuai.", "Gagal",  JOptionPane.ERROR_MESSAGE);
                    }
                } 
                else {
                    updateData();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Isi data dengan lengkap.", "Gagal",  JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtNamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyTyped
        OSLib.alphabetTextField(evt);
    }//GEN-LAST:event_txtNamaKeyTyped

    private void txtNoTelpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTelpKeyTyped
        OSLib.numericMaxTextField(evt, 13);
    }//GEN-LAST:event_txtNoTelpKeyTyped

    private void txtNoTelpOrtuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTelpOrtuKeyTyped
        OSLib.numericMaxTextField(evt, 13);
    }//GEN-LAST:event_txtNoTelpOrtuKeyTyped

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
    private javax.swing.JButton btnSimpan;
    private org.jdesktop.swingx.JXComboBox cmbStatusSiswa;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblJenkel;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNoTelp;
    private javax.swing.JLabel lblNoTelpOrtu;
    private javax.swing.JLabel lblPassBaru;
    private javax.swing.JLabel lblPassLama;
    private javax.swing.JLabel lblPasswordBaru;
    private javax.swing.JLabel lblPasswordLama;
    private javax.swing.JLabel lblStatusSiswa;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JRadioButton rdLaki;
    private javax.swing.JRadioButton rdPerempuan;
    private javax.swing.JPanel thisPanel;
    private org.jdesktop.swingx.JXTextField txtAlamat;
    private org.jdesktop.swingx.JXTextField txtEmail;
    private org.jdesktop.swingx.JXTextField txtNama;
    private org.jdesktop.swingx.JXTextField txtNoTelp;
    private org.jdesktop.swingx.JXTextField txtNoTelpOrtu;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword1;
    private org.jdesktop.swingx.JXTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
