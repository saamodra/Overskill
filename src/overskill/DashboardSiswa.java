/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DashboardSiswa extends javax.swing.JFrame {
    // Deklarasi connection
    DBConnect c = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    
    
    public DashboardSiswa() {
        initComponents();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Set model tblDaftar
        tblDaftar.setModel(model);
        // Menambahkan kolom pada model
        addColumn();
        
        
        FormLoad();
    }
    
    private void FormLoad() {
        // Memanggil fungsi getAllData()
        getAllData();
        
        // Memanggil load data
        loadData("");
        tblDaftar.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblDaftar.getColumnModel().getColumn(0).setMaxWidth(50);
        // Menampilkan kolom minimal selebar data di cell (tidak menjadi ..)
        tblDaftar.packAll();
    }
    
    // Menampilkan data pada ringkasan yang ada di page dashboard
    private void getAllData() {
        try {
            // Membuat statement. Fungsinya agar bisa executeQuery
            c.stat = c.conn.createStatement();
            // sql query
            String sql = "SELECT COUNT(*) as jumlah FROM Pendaftaran WHERE ID_Siswa = '" + OSSession.getId() + "'";
            
            // Mendapatkan result dari query yg dijalankan
            c.result = c.stat.executeQuery(sql);
            
            // melakukan perulangan dari result
            while(c.result.next()) {
                ResultSet r = c.result;
                lblPermintaanPickup.setText(r.getString("jumlah"));
            }
            
            sql = "SELECT COUNT(*) as jumlah FROM Jadwal j JOIN Kelas k ON j.ID_Kelas = k.ID_Kelas "
                    + "JOIN Pendaftaran p ON p.ID_Kelas = k.ID_Kelas "
                    + "WHERE p.ID_Siswa= '" + OSSession.getId() + "' AND j.Status='1'";
            c.result = c.stat.executeQuery(sql);
            while(c.result.next()) {
                ResultSet r = c.result;
                lblChecking.setText(r.getString("jumlah"));
            }
            
            sql = "SELECT COUNT(*) as jumlah FROM Quiz j JOIN Kelas k ON j.ID_Kelas = k.ID_Kelas "
                    + "JOIN Pendaftaran p ON p.ID_Kelas = k.ID_Kelas "
                    + "WHERE p.ID_Siswa= '" + OSSession.getId() + "' AND j.Status='1'";
            c.result = c.stat.executeQuery(sql);
            while(c.result.next()) {
                ResultSet r = c.result;
                lblSending.setText(r.getString("jumlah"));
            }
            
            sql = "SELECT COUNT(*) as jumlah FROM Feedback WHERE ID_Siswa = '" + OSSession.getId() + "' AND Status='1'";
            c.result = c.stat.executeQuery(sql);
            while(c.result.next()) {
                ResultSet r = c.result;
                lblAll.setText(r.getString("jumlah"));
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data summary "  + e);
        }
    }
    
    // Return panel utama untuk dipanggil di menu utama (Template.java)
    public JPanel getPanel() {
        return Dashboard;
    }
    
    private void addColumn() {
        // Menambahkan column yang akan ditampilkan di table
        model.addColumn("No.");
        model.addColumn("Nama Kelas");
        model.addColumn("Nama Siswa");
        model.addColumn("Tgl. Daftar");
        model.addColumn("Pembayaran");
        model.addColumn("Nama Kasir");
    }
    
    
    // Fungsi untuk load data "Pemesanan terakhir"
    private void loadData(String cari) {
        
        // Mengatur formatter tanggal menjadi dd-MM-yyyy
        SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        
        
        try {
            // Meremove semua data pada table.
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged(); 
            
            // Membuat statement baru
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM viewSubmissionSiswa WHERE ID_Instruktur = '" + OSSession.getId() + "' "
                    + "AND Nilai IS NULL ORDER BY Terakhir_dimodifikasi DESC";
            
            // Menampung result ke c.result
            c.result = c.stat.executeQuery(sql);
            int no = 1;
            // Melakukan looping pada result dari query
            while(c.result.next()) {
                ResultSet r = c.result;
                
                // Membuat array dari object untuk ditambahkan ke model
                Object obj[] = new Object[7];
                obj[0] = no++;
                obj[1] = r.getString("Nama_Kelas");
                obj[2] = r.getString("Nama_Siswa");
                obj[3] = f.format(r.getDate("Tgl_Pendaftaran"));
                obj[4] = formatter.format(r.getDouble("Pembayaran"));
                obj[5] = r.getString("Nama_Pegawai");
                
                // Menambahkan row baru ke model
                model.addRow(obj);
            }
            
            c.stat.close();
            c.result.close();
        } catch(SQLException e) {
            System.out.println("Terjadi error saat load data pendaftaran "  + e);
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

        Dashboard = new javax.swing.JPanel();
        DashboardTitle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ReportSummary = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblPermintaanPickup = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        lblChecking = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblSending = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblAll = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        DContent = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDaftar = new org.jdesktop.swingx.JXTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Dashboard.setBackground(new java.awt.Color(255, 255, 255));
        Dashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        Dashboard.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                DashboardHierarchyChanged(evt);
            }
        });
        Dashboard.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                DashboardComponentShown(evt);
            }
        });
        Dashboard.setLayout(new javax.swing.BoxLayout(Dashboard, javax.swing.BoxLayout.Y_AXIS));

        DashboardTitle.setBackground(new java.awt.Color(255, 255, 255));
        DashboardTitle.setMaximumSize(new java.awt.Dimension(32767, 70));
        DashboardTitle.setPreferredSize(new java.awt.Dimension(1136, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Dashboard");

        javax.swing.GroupLayout DashboardTitleLayout = new javax.swing.GroupLayout(DashboardTitle);
        DashboardTitle.setLayout(DashboardTitleLayout);
        DashboardTitleLayout.setHorizontalGroup(
            DashboardTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(1051, Short.MAX_VALUE))
        );
        DashboardTitleLayout.setVerticalGroup(
            DashboardTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardTitleLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        Dashboard.add(DashboardTitle);

        ReportSummary.setBackground(new java.awt.Color(255, 255, 255));
        ReportSummary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ReportSummary.setMaximumSize(new java.awt.Dimension(32767, 120));
        ReportSummary.setPreferredSize(new java.awt.Dimension(1194, 120));
        ReportSummary.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBackground(new java.awt.Color(225, 228, 230));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBackground(new java.awt.Color(23, 162, 184));

        lblPermintaanPickup.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblPermintaanPickup.setForeground(new java.awt.Color(255, 255, 255));
        lblPermintaanPickup.setText("100");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kelas");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblPermintaanPickup))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblPermintaanPickup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(20, 20, 20))
        );

        jPanel3.add(jPanel4);

        ReportSummary.add(jPanel3);

        jPanel5.setBackground(new java.awt.Color(225, 228, 230));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jPanel10.setBackground(new java.awt.Color(40, 167, 69));

        lblChecking.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblChecking.setForeground(new java.awt.Color(255, 255, 255));
        lblChecking.setText("100");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jadwal");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblChecking))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblChecking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(20, 20, 20))
        );

        jPanel5.add(jPanel10);

        ReportSummary.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(225, 228, 230));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 193, 7));

        lblSending.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSending.setForeground(new java.awt.Color(255, 255, 255));
        lblSending.setText("100");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Quiz");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(lblSending))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblSending)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(20, 20, 20))
        );

        jPanel6.add(jPanel2);

        ReportSummary.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(225, 228, 230));
        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel9.setBackground(new java.awt.Color(220, 53, 69));

        lblAll.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAll.setForeground(new java.awt.Color(255, 255, 255));
        lblAll.setText("100");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Feedback");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lblAll))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(20, 20, 20))
        );

        jPanel7.add(jPanel9);

        ReportSummary.add(jPanel7);

        Dashboard.add(ReportSummary);

        DContent.setBackground(new java.awt.Color(255, 255, 255));
        DContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        DContent.setPreferredSize(new java.awt.Dimension(1136, 400));
        DContent.setLayout(new javax.swing.BoxLayout(DContent, javax.swing.BoxLayout.LINE_AXIS));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.Y_AXIS));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel12.setPreferredSize(new java.awt.Dimension(686, 60));
        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel8.setText("Quiz yang perlu dikerjakan");
        jPanel12.add(jLabel8);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 838, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        jPanel12.add(jPanel13);

        jPanel8.add(jPanel12);

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.Y_AXIS));
        jPanel8.add(jPanel14);

        tblDaftar.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(tblDaftar);

        jPanel8.add(jScrollPane2);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );

        DContent.add(jPanel11);

        Dashboard.add(DContent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1236, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 1236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 703, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DashboardHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_DashboardHierarchyChanged
        
    }//GEN-LAST:event_DashboardHierarchyChanged

    // Memanggil fungsi formload untuk refresh data ketika page dashboard di load
    private void DashboardComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_DashboardComponentShown
        FormLoad(); 
    }//GEN-LAST:event_DashboardComponentShown

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
            java.util.logging.Logger.getLogger(DashboardSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new DashboardSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DContent;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel DashboardTitle;
    private javax.swing.JPanel ReportSummary;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAll;
    private javax.swing.JLabel lblChecking;
    private javax.swing.JLabel lblPermintaanPickup;
    private javax.swing.JLabel lblSending;
    private org.jdesktop.swingx.JXTable tblDaftar;
    // End of variables declaration//GEN-END:variables
}
