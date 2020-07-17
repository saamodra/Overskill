/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.form;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.NumberFormatter;
import overskill.DBConnect;
import overskill.OSLib;

/**
 *
 * @author KEL15
 */
public class FormJadwal extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    private JDialog d;
    private String formtype = "Tambah";
    private ArrayList<String> id_kelas = new ArrayList<>();
    private int kelasPlaceholder = 0;
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    /**
     * Creates new form TambahJadwal
     */
    public FormJadwal() {
        initComponents();
    }
    
    public FormJadwal(JDialog d) {
        initComponents();
        this.d = d;
        loadKelas();
        lblID.setVisible(false);
        
        Date date = new Date();
        SpinnerDateModel sdm = new SpinnerDateModel(date, null,null, Calendar.HOUR_OF_DAY);
        SpinnerDateModel sdd = new SpinnerDateModel(date, null,null, Calendar.HOUR_OF_DAY);

        spinAwal.setModel(sdm);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinAwal, "HH:mm");
        spinAwal.setEditor(dateEditor);

        spinAkhir.setModel(sdd);
        JSpinner.DateEditor dateeditor = new JSpinner.DateEditor(spinAkhir, "HH:mm");
        spinAkhir.setEditor(dateeditor);
        
        hideLabel();
    }
    
    public FormJadwal(JDialog d, String... data) {
        initComponents();
        this.d = d;
        formtype = "Ubah";
        loadKelas();
        
        Date date = new Date();
        SpinnerDateModel sdm = new SpinnerDateModel(date, null,null, Calendar.HOUR_OF_DAY);
        SpinnerDateModel sda = new SpinnerDateModel(date, null,null, Calendar.HOUR_OF_DAY);
         
        spinAwal.setModel(sdm);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinAwal, "HH:mm");
        spinAwal.setEditor(dateEditor);
         
        spinAkhir.setModel(sda);
        JSpinner.DateEditor dateeditor = new JSpinner.DateEditor(spinAkhir, "HH:mm");
        spinAkhir.setEditor(dateeditor);
        
        lblTitle.setText("Ubah Jadwal");
        lblID.setText(data[0]);
        
        //Form
        int i = id_kelas.indexOf(data[2]);
        
        cmbKelas.setSelectedIndex(i);
        txtDeskripsi.setText(data[1]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdd = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfjam = new SimpleDateFormat("HH:mm");
        
        try {
            tanggal.setDate(sdd.parse(data[4]));
            
            spinAwal.setValue(sdfjam.parse(data[5]));
            spinAkhir.setValue(sdfjam.parse(data[6]));
        } catch (ParseException ex) 
        {
            Logger.getLogger(FormInstruktur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hideLabel();
    }
    
    public JPanel getPanel() {
        return thisPanel;
    }
    
    private void saveData() {
        int i = cmbKelas.getSelectedIndex();
        try {
            String id_jadwal = OSLib.AutoNumber("Jadwal", "ID_Jadwal", "JD");
            String query = "INSERT INTO Jadwal (ID_Jadwal, ID_Kelas, Deskripsi_Jadwal, Tanggal, Jam_Awal, Jam_Akhir) VALUES (?,?,?,?,?,?)";
         
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date timeAwal = (Date)spinAwal.getValue();
            Date timeAkhir = (Date)spinAkhir.getValue();
            
            java.sql.Date tglLahir = new java.sql.Date(tanggal.getDate().getTime());
            try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                p.setString(1, id_jadwal);
                p.setString(2, id_kelas.get(i));
                p.setString(3, txtDeskripsi.getText());
                p.setDate(4, tglLahir);
                p.setString(5, sdf.format(timeAwal));
                p.setString(6, sdf.format(timeAkhir));
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Data Jadwal berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah jadwal : " + e);
        }
    }
    
    private void updateData() 
    {
        int i = cmbKelas.getSelectedIndex();
        try 
        {
            String query = "UPDATE Jadwal SET ID_Kelas=?, Deskripsi_Jadwal=?, Tanggal=?, Jam_Awal=?, Jam_Akhir=? " 
                    + " WHERE ID_Jadwal = ?";
            
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date timeAwal = (Date)spinAwal.getValue();
            Date timeAkhir = (Date)spinAkhir.getValue();
            
            java.sql.Date tglLahir = new java.sql.Date(tanggal.getDate().getTime());
            try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                p.setString(1, id_kelas.get(i));
                p.setString(2, txtDeskripsi.getText());
                p.setDate(3, tglLahir);
                p.setString(4, sdf.format(timeAwal));
                p.setString(5, sdf.format(timeAkhir));
                p.setString(6, lblID.getText());
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Data Jadwal berhasil diubah.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            d.dispose();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error pada saat ubah jadwal : " + e);
        }
    }
    
    private void loadKelas() {
        try {
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Kelas WHERE Status = 1";
            c.result = c.stat.executeQuery(sql);
            if(formtype.equals("Tambah")) {
                cmbKelas.addItem("Pilih Kelas ..");
            }
            
            while(c.result.next()) {
                id_kelas.add(c.result.getString("ID_Kelas"));
                cmbKelas.addItem(c.result.getString("Nama_Kelas"));
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data jadwal "  + e);
        }
    }
    
    private void hideLabel() {
        lblDeskripsi.setVisible(false);
        lblNamaKelas.setVisible(false);
        lblJamAkhir.setVisible(false);
        lblJamAwal.setVisible(false);
        lblTgl.setVisible(false);
    }
    
    private boolean validateAll() {
        boolean deskripsi = OSLib.fieldRequired(txtDeskripsi.getText(), lblDeskripsi);
        boolean tglJadwal = OSLib.dateRequired(tanggal.getDate(), lblTgl);
        boolean namaIns = OSLib.comboRequired(cmbKelas.getSelectedItem().toString(), "Pilih Kelas ..", lblNamaKelas);
        
        return deskripsi && tglJadwal && namaIns;
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
        Tanggal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbKelas = new javax.swing.JComboBox<>();
        tanggal = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        spinAwal = new javax.swing.JSpinner();
        spinAkhir = new javax.swing.JSpinner();
        btnSimpan = new javax.swing.JButton();
        txtDeskripsi = new org.jdesktop.swingx.JXTextField();
        lblDeskripsi = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        lblJamAwal = new javax.swing.JLabel();
        lblJamAkhir = new javax.swing.JLabel();
        lblNamaKelas = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setBackground(new java.awt.Color(250, 250, 250));
        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setOpaque(false);
        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah Jadwal");

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblID.setText("ID");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                .addComponent(lblID)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblID))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        Panel.add(jPanel1);

        thisPanel.add(Panel);

        jPanel2.setOpaque(false);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Deskripsi Jadwal");
        jLabel2.setToolTipText("");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        Tanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Tanggal.setText("Tanggal");
        Tanggal.setToolTipText("");
        jPanel4.add(Tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Jam Awal");
        jLabel5.setToolTipText("");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Kelas");
        jLabel8.setToolTipText("");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, -1));

        cmbKelas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cmbKelas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbKelasFocusGained(evt);
            }
        });
        cmbKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbKelasMouseClicked(evt);
            }
        });
        jPanel4.add(cmbKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 241, 30));

        tanggal.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel4.add(tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 241, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Jam Akhir");
        jLabel6.setToolTipText("");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        spinAwal.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel4.add(spinAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 241, 30));

        spinAkhir.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel4.add(spinAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 241, 30));

        btnSimpan.setBackground(new java.awt.Color(245, 121, 0));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 83, 34));

        txtDeskripsi.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtDeskripsi.setPrompt("Deskripsi Jadwal");
        jPanel4.add(txtDeskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 241, 28));

        lblDeskripsi.setForeground(new java.awt.Color(255, 51, 51));
        lblDeskripsi.setText("Wajib diisi.");
        jPanel4.add(lblDeskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, -1, -1));

        lblTgl.setForeground(new java.awt.Color(255, 51, 51));
        lblTgl.setText("Wajib diisi.");
        jPanel4.add(lblTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 115, -1, -1));

        lblJamAwal.setForeground(new java.awt.Color(255, 51, 51));
        lblJamAwal.setText("Wajib diisi.");
        jPanel4.add(lblJamAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, -1));

        lblJamAkhir.setForeground(new java.awt.Color(255, 51, 51));
        lblJamAkhir.setText("Wajib diisi.");
        jPanel4.add(lblJamAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        lblNamaKelas.setForeground(new java.awt.Color(255, 51, 51));
        lblNamaKelas.setText("Wajib diisi.");
        jPanel4.add(lblNamaKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 547, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );

        thisPanel.add(jPanel2);

        getContentPane().add(thisPanel);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKelasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbKelasFocusGained
        if(formtype.equals("Tambah")) {
            if(kelasPlaceholder == 0) {
                cmbKelas.removeItemAt(0);
                kelasPlaceholder++;
            }
        }
    }//GEN-LAST:event_cmbKelasFocusGained

    private void cmbKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbKelasMouseClicked
        
    }//GEN-LAST:event_cmbKelasMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(validateAll()) {
            if(formtype.equals("Tambah")) {
                saveData();
            } else {
                updateData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Isi data dengan lengkap.", "Gagal",  JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(FormJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormJadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormJadwal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private javax.swing.JLabel Tanggal;
    private javax.swing.ButtonGroup btnJenkel;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbKelas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblJamAkhir;
    private javax.swing.JLabel lblJamAwal;
    private javax.swing.JLabel lblNamaKelas;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSpinner spinAkhir;
    private javax.swing.JSpinner spinAwal;
    private com.toedter.calendar.JDateChooser tanggal;
    private javax.swing.JPanel thisPanel;
    private org.jdesktop.swingx.JXTextField txtDeskripsi;
    // End of variables declaration//GEN-END:variables
}

