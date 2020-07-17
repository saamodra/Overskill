/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instruktur;

import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
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
public class Absensi extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel modelAbsensi;
    DefaultTableModel modelJadwal;
    ArrayList<String> id_kelas = new ArrayList<>();
    String id_absensi = "";
    String id_jadwal = "";
    
    /**
     * Creates new form User
     */
    public Absensi() {
        initComponents();
        formLoad();
        
    }
    
    private void formLoad() {
        addColumnAbsensi();
        addColumnJadwal();
        loadJadwal("");
        loadKelas();
        loadKomputer();
    }

    
    private void addColumnAbsensi() {
        String colTitles[] = {"No.", "ID Absensi", "Nama Siswa", "Jenis Kelamin", "Alamat", "No. Telp", "Email", "Keterangan"};
        boolean[] isEditable = {false,false,false,false,false,false,false,false};
        modelAbsensi = new DefaultTableModel(colTitles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make read only fields except column 0,13,14
                return isEditable[column];
            }
        };
        // The 0 argument is number rows. 
        tblAbsensi.setModel(modelAbsensi);
        tblAbsensi.removeColumn(tblAbsensi.getColumnModel().getColumn(1));
        tblAbsensi.packAll();
    }
    
    private void addColumnJadwal() {
        String colTitles[] = {"ID Jadwal", "Kelas", "Tgl. Jadwal"};
        boolean[] isEditable = {false,false,false};
        modelJadwal = new DefaultTableModel(colTitles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make read only fields except column 0,13,14
                return isEditable[column];
            }
        };
        // The 0 argument is number rows. 
        tblJadwal.setModel(modelJadwal);
        tblJadwal.removeColumn(tblJadwal.getColumnModel().getColumn(0));
        tblJadwal.packAll();
    }
    
    private void loadJadwal(String id_kelas) {
        try 
        {
            SimpleDateFormat sdfw = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
            
            modelJadwal.getDataVector().removeAllElements();
            modelJadwal.fireTableDataChanged();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Jadwal j JOIN Kelas k ON j.ID_Kelas = k.ID_Kelas "
                    + "WHERE k.ID_Instruktur = '" + OSSession.getId() + "' AND j.ID_Kelas LIKE '%" + id_kelas + "%'";
            
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[3];
                obj[0] = r.getString("ID_Jadwal");
                obj[1] = r.getString("Nama_Kelas");
                obj[2] = formatDate.format(r.getDate("Tanggal")) + " (" + sdfw.format(r.getTime("Jam_Awal")) + " - "+ sdfw.format(r.getTime("Jam_Akhir")) + ")";
                
                modelJadwal.addRow(obj);
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data instruktur "  + e);
        }
    }
    
    private void loadData(String id_jadwal) {
        try 
        {
            modelAbsensi.getDataVector().removeAllElements();
            modelAbsensi.fireTableDataChanged();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Absensi a JOIN Siswa s ON s.ID_Siswa = a.ID_Siswa "
                    + "WHERE a.ID_Jadwal='" + id_jadwal + "'";
            
            c.result = c.stat.executeQuery(sql);
            int no = 1;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[8];
                obj[0] = no++;
                obj[1] = r.getString("ID_Absensi");
                obj[2] = r.getString("Nama_Siswa");
                obj[3] = (r.getString("Jenis_Kelamin").equals("L") ? "Laki-Laki" : "Perempuan");
                obj[4] = r.getString("Alamat");
                obj[5] = r.getString("No_telp");
                obj[6] = r.getString("Email");
                obj[7] = (r.getString("Keterangan_Absen").equals("0") ? "-" : 
                        r.getString("Keterangan_Absen").equals("1") ? "Hadir" : "Tidak Hadir");
                
                modelAbsensi.addRow(obj);
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data instruktur "  + e);
        }
    }    
    
    private void addSiswa(String id_jadwal, String id_siswa) {
        try {
            
            DBConnect c = new DBConnect();
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Absensi "
                    + "WHERE ID_Jadwal='" + id_jadwal + "' AND id_siswa='" + id_siswa + "'";
            System.out.println(sql);
            c.result = c.stat.executeQuery(sql);
            int count = 0;
            
            while(c.result.next()) {
                count++;
            }
            c.stat.close();
            c.result.close();
            
            if(count == 0) {
                String query = "INSERT INTO Absensi (ID_Jadwal, ID_Siswa, ID_Komputer, Keterangan_Absen) "
                        + "VALUES (?,?,?,?)";

                try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                    p.setString(1, id_jadwal);
                    p.setString(2, id_siswa);
                    p.setNull(3, Types.INTEGER);
                    p.setString(4, "-");
                    
                    p.executeUpdate();
                }
            }
            
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah instruktur : " + e);
        }  
    }
    
    private void addAbsensi(String id_jadwal) {
        try 
        {   
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Pendaftaran p WHERE ID_Kelas = (SELECT ID_Kelas FROM Jadwal WHERE ID_Jadwal = '" + id_jadwal +"')";
            System.out.println(sql);
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                ResultSet r = c.result;
                System.out.println("Perulangan");
                addSiswa(id_jadwal, r.getString("ID_Siswa"));
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data kelas "  + e);
        }
    }
    
    private void loadKelas() {
        try 
        {
            cmbKelas.removeAllItems();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Kelas WHERE ID_Instruktur='" + OSSession.getId() + "'"
                    + " AND Status='1' AND Status_Kelas='1'";
            
            c.result = c.stat.executeQuery(sql);
            cmbKelas.addItem("Semua");
            id_kelas.add("");
            while(c.result.next()) {
                ResultSet r = c.result;
                id_kelas.add(r.getString("ID_Kelas"));
                cmbKelas.addItem(r.getString("Nama_Kelas"));
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data kelas "  + e);
        }
    }
    
    private void loadKomputer() {
        try 
        {
            cmbKomputer.removeAllItems();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Komputer WHERE Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            cmbKomputer.addItem("Pilih Komputer..");
            while(c.result.next()) {
                ResultSet r = c.result;
                cmbKomputer.addItem(r.getString("ID_Komputer"));
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data kelas "  + e);
        }
    }
    
    public JPanel getPanel() {
        return Panel;
    }
    
    private void absenSiswa(String status) {
        try {
            String query = "UPDATE Absensi SET ID_Komputer = ?, Keterangan_Absen = '" + status + "' WHERE ID_Absensi = ?";

            PreparedStatement p = connection.conn.prepareStatement(query);
            p.setString(1, (cmbKomputer.getSelectedIndex() == 0) ? "-" : cmbKomputer.getSelectedItem().toString());
            p.setString(2, id_absensi);

            p.executeUpdate();
            p.close();
            
            JOptionPane.showMessageDialog(this, "Absensi berhasil dilakukan", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            
            loadData(id_jadwal);
            clearForm();
        } catch(SQLException e) {
            System.out.println("Terjadi kesalahan saat absensi : " + e);
        }
        
    }
    
    private void clearForm() {
        id_absensi = "";
        txtNama.setText("");
        txtJenkel.setText("");
        txtAlamat.setText("");
        txtNoTelp.setText("");
        txtEmail.setText("");
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
        jPanel1 = new javax.swing.JPanel();
        cmbKelas = new javax.swing.JComboBox<>();
        btnShow = new components.MaterialButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblJadwal = new org.jdesktop.swingx.JXTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAbsensi = new org.jdesktop.swingx.JXTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbKomputer = new javax.swing.JComboBox<>();
        lblID = new javax.swing.JLabel();
        btnShow1 = new components.MaterialButton();
        btnShow2 = new components.MaterialButton();
        txtNama = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtJenkel = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAlamat = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNoTelp = new org.jdesktop.swingx.JXTextField();
        txtEmail = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();

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
        jLabel1.setText("Absensi");
        jPanel2.add(jLabel1);

        Panel.add(jPanel2);

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(928, 40));

        btnShow.setBackground(new java.awt.Color(255, 193, 7));
        btnShow.setText("Tampilkan");
        btnShow.setMaximumSize(new java.awt.Dimension(85, 25));
        btnShow.setMinimumSize(new java.awt.Dimension(85, 25));
        btnShow.setPreferredSize(new java.awt.Dimension(85, 25));
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(cmbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(616, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel.add(jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 459));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Jadwal");
        jLabel9.setToolTipText("");

        tblJadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblJadwal.setHorizontalScrollEnabled(true);
        tblJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblJadwalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblJadwalMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tblJadwal);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(196, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 11, 11, 11));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 45));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(280, 45));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Daftar Siswa");
        jLabel10.setToolTipText("");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel7);

        tblAbsensi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAbsensi.setHorizontalScrollEnabled(true);
        tblAbsensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAbsensiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAbsensi);

        jPanel6.add(jScrollPane2);

        jPanel4.add(jPanel6);

        jPanel3.setMaximumSize(new java.awt.Dimension(350, 32767));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 459));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Detail Siswa");
        jLabel2.setToolTipText("");

        jLabel3.setText("Nama Siswa");

        jLabel8.setText("Komputer");

        cmbKomputer.setPreferredSize(new java.awt.Dimension(56, 30));

        lblID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblID.setText("ID");
        lblID.setToolTipText("");

        btnShow1.setBackground(new java.awt.Color(40, 167, 69));
        btnShow1.setText("Hadir");
        btnShow1.setMaximumSize(new java.awt.Dimension(85, 25));
        btnShow1.setMinimumSize(new java.awt.Dimension(85, 25));
        btnShow1.setPreferredSize(new java.awt.Dimension(85, 25));
        btnShow1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShow1ActionPerformed(evt);
            }
        });

        btnShow2.setBackground(new java.awt.Color(255, 51, 51));
        btnShow2.setText("Tidak Hadir");
        btnShow2.setMaximumSize(new java.awt.Dimension(85, 25));
        btnShow2.setMinimumSize(new java.awt.Dimension(85, 25));
        btnShow2.setPreferredSize(new java.awt.Dimension(85, 25));
        btnShow2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShow2ActionPerformed(evt);
            }
        });

        txtNama.setEnabled(false);
        txtNama.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNama.setPrompt("Nama Siswa");

        jLabel11.setText("Jenis Kelamin");

        txtJenkel.setEnabled(false);
        txtJenkel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtJenkel.setPrompt("Jenis Kelamin");

        jLabel12.setText("Alamat");

        txtAlamat.setEnabled(false);
        txtAlamat.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtAlamat.setPrompt("Alamat");

        jLabel13.setText("No. Telepon");

        txtNoTelp.setEnabled(false);
        txtNoTelp.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNoTelp.setPrompt("No. Telepon");

        txtEmail.setEnabled(false);
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtEmail.setPrompt("Email");

        jLabel14.setText("Email");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(41, 41, 41)
                        .addComponent(cmbKomputer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblID))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtJenkel, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                    .addComponent(txtAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                    .addComponent(txtNoTelp, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnShow2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnShow1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblID))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtJenkel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKomputer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShow2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShow1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
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

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        loadJadwal(id_kelas.get(cmbKelas.getSelectedIndex()));
    }//GEN-LAST:event_btnShowActionPerformed

    private void tblJadwalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJadwalMouseEntered
        
    }//GEN-LAST:event_tblJadwalMouseEntered

    private void tblJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJadwalMouseClicked
        int i = tblJadwal.getSelectedRow();
        
        if(i == -1) {
            return;
        }
        
        id_jadwal = (String) modelJadwal.getValueAt(i, 0);
        System.out.println(id_jadwal);
        addAbsensi(id_jadwal);
        loadData(id_jadwal);
    }//GEN-LAST:event_tblJadwalMouseClicked

    private void tblAbsensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAbsensiMouseClicked
        int i = tblAbsensi.getSelectedRow();
        
        if(i == -1) {
            return;
        }
        
        id_absensi = (String) modelAbsensi.getValueAt(i, 1);
        txtNama.setText((String) modelAbsensi.getValueAt(i, 2));
        txtJenkel.setText((String) modelAbsensi.getValueAt(i, 3));
        txtAlamat.setText((String) modelAbsensi.getValueAt(i, 4));
        txtNoTelp.setText((String) modelAbsensi.getValueAt(i, 5));
        txtEmail.setText((String) modelAbsensi.getValueAt(i, 6));
    }//GEN-LAST:event_tblAbsensiMouseClicked

    private void btnShow1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShow1ActionPerformed
        if(cmbKomputer.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Silahkan pilih komputer yang digunakan!", "Gagal", JOptionPane.ERROR_MESSAGE);
        } else {
            absenSiswa("1");
        }
    }//GEN-LAST:event_btnShow1ActionPerformed

    private void btnShow2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShow2ActionPerformed

        absenSiswa("2");
    }//GEN-LAST:event_btnShow2ActionPerformed

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
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Absensi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Absensi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private components.MaterialButton btnShow;
    private components.MaterialButton btnShow1;
    private components.MaterialButton btnShow2;
    private javax.swing.JComboBox<String> cmbKelas;
    private javax.swing.JComboBox<String> cmbKomputer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblID;
    private org.jdesktop.swingx.JXTable tblAbsensi;
    private org.jdesktop.swingx.JXTable tblJadwal;
    private org.jdesktop.swingx.JXTextField txtAlamat;
    private org.jdesktop.swingx.JXTextField txtEmail;
    private org.jdesktop.swingx.JXTextField txtJenkel;
    private org.jdesktop.swingx.JXTextField txtNama;
    private org.jdesktop.swingx.JXTextField txtNoTelp;
    // End of variables declaration//GEN-END:variables
}
