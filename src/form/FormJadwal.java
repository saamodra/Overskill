/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

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
            PreparedStatement p = connection.conn.prepareStatement(query);
            p.setString(1, id_jadwal);
            p.setString(2, id_kelas.get(i));
            p.setString(3, txtDeskripsi.getText());
            p.setDate(4, tglLahir);
            p.setString(5, sdf.format(timeAwal));
            p.setString(6, sdf.format(timeAkhir));
            
            p.executeUpdate();
            p.close();

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
            PreparedStatement p = connection.conn.prepareStatement(query);
            p.setString(1, id_kelas.get(i));
            p.setString(2, txtDeskripsi.getText());
            p.setDate(3, tglLahir);
            p.setString(4, sdf.format(timeAwal));
            p.setString(5, sdf.format(timeAkhir));
            p.setString(6, lblID.getText());
            
            p.executeUpdate();
            p.close();

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
            if(formtype == "Tambah") {
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
        Tanggal = new javax.swing.JLabel();
        txtDeskripsi = new components.UITextField();
        jLabel5 = new javax.swing.JLabel();
        btnSimpan = new components.MaterialButton();
        jLabel8 = new javax.swing.JLabel();
        cmbKelas = new javax.swing.JComboBox<>();
        tanggal = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        spinAwal = new javax.swing.JSpinner();
        spinAkhir = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        thisPanel.setLayout(new javax.swing.BoxLayout(thisPanel, javax.swing.BoxLayout.Y_AXIS));

        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 30));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 50));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Tambah Jadwal");
        jPanel1.add(lblTitle);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3);

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblID.setText("ID");
        jPanel1.add(lblID);

        Panel.add(jPanel1);

        thisPanel.add(Panel);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Deskripsi Jadwal");
        jLabel2.setToolTipText("");

        Tanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Tanggal.setText("Tanggal");
        Tanggal.setToolTipText("");

        txtDeskripsi.setAe_Placeholder("Deskripsi Jadwal");
        txtDeskripsi.setPreferredSize(new java.awt.Dimension(230, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Jam Awal");
        jLabel5.setToolTipText("");

        btnSimpan.setBackground(new java.awt.Color(255, 51, 51));
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Kelas");
        jLabel8.setToolTipText("");

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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Jam Akhir");
        jLabel6.setToolTipText("");

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
                            .addComponent(jLabel2)
                            .addComponent(Tanggal)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDeskripsi, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(cmbKelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(spinAwal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                .addComponent(spinAkhir, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tanggal)
                    .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(spinAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(spinAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
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
    private components.MaterialButton btnSimpan;
    private javax.swing.JComboBox<String> cmbKelas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSpinner spinAkhir;
    private javax.swing.JSpinner spinAwal;
    private com.toedter.calendar.JDateChooser tanggal;
    private javax.swing.JPanel thisPanel;
    private components.UITextField txtDeskripsi;
    // End of variables declaration//GEN-END:variables
}

