/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instruktur;

import siswa.*;
import instruktur.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import overskill.DBConnect;
import overskill.OSLib;
import overskill.OSSession;
import overskill.SiswaPage;

/**
 *
 * @author samod
 */
public class Submission_Siswa extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<String> id_kelas = new ArrayList<>();
    String id_submission;
    SimpleDateFormat datetimef = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SiswaPage sp;
    
    /**
     * Creates new form User
     * @param sp
     */
    public Submission_Siswa() {
        initComponents();
        formLoad();
    }
    
    public Submission_Siswa(SiswaPage sp) {
        initComponents();
        this.sp = sp;
        formLoad();
    }
    
    
    private void formLoad() {
        loadData("");
        addColumn();
    }

    
    private void addColumn() {
        
        String colTitles[] = {"No. ", "ID SubmissionSiswa", "Nama Kelas", "Judul", "Nama_Siswa", "Duedate", "Terakhir_dimodifikasi"};
        boolean[] isEditable = {false,false,false,false,false,false,false,false};
        model = new DefaultTableModel(colTitles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make read only fields except column 0,13,14
                return isEditable[column];
            }
        };
        // The 0 argument is number rows. 
        tblMaster.setModel(model);
        loadData("");
    }
    
    private boolean checkSubmission(String id_submission) {
        boolean result = false;
        try 
        {
            DBConnect c = new DBConnect();
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Jawaban j JOIN Soal s ON j.ID_Soal = s.ID_Soal WHERE s.ID_Submission = '" + id_submission + "'";
            
            c.result = c.stat.executeQuery(sql);
            int count = 0;
            
            while(c.result.next()) {
                count++;
                
            }
            
            if(count > 0) {
                result = true;
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data jawaban "  + e);
        }
        
        return result;
    }
    
    private void loadData(String cari) {
        try 
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM viewSubmissionSiswa";
            
            c.result = c.stat.executeQuery(sql);
            int no = 1;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[7];
                obj[0] = no;
                obj[1] = r.getString("ID_SubmissionSiswa");
                obj[2] = r.getString("Nama_Kelas");
                obj[3] = r.getString("Judul");
                obj[4] = r.getString("Nama_Siswa");
                obj[5] = r.getString("Duedate");
                obj[6] = sdf.format(r.getTimestamp("Terakhir_dimodifikasi"));
                        
                
                no++;
                model.addRow(obj);
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data instruktur "  + e);
        }
    }
    
    public JPanel getPanel() {
        return PanelContent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnTambah = new components.MaterialButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMaster = new org.jdesktop.swingx.JXTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        PanelContent.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                PanelContentformComponentShown(evt);
            }
        });
        PanelContent.setLayout(new javax.swing.BoxLayout(PanelContent, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 75));
        jPanel2.setPreferredSize(new java.awt.Dimension(954, 75));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Submission Siswa");
        jPanel2.add(jLabel1);

        PanelContent.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(954, 50));

        btnTambah.setBackground(new java.awt.Color(40, 167, 69));
        btnTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTambah.setText("Nilai");
        btnTambah.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(835, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PanelContent.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        tblMaster.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblMaster);

        jPanel4.add(jScrollPane2);

        PanelContent.add(jPanel4);

        getContentPane().add(PanelContent);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        int i = tblMaster.getSelectedRow();
        if(((String)model.getValueAt(i, 7)).equals("Menunggu koreksi")) {
            JOptionPane.showMessageDialog(this, "Submission ini sudah dikerjakan.", "Gagal", JOptionPane.INFORMATION_MESSAGE);
        } else {
            sp.newJawaban(sp, id_submission);
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void PanelContentformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelContentformComponentShown
        formLoad();
    }//GEN-LAST:event_PanelContentformComponentShown

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
            java.util.logging.Logger.getLogger(Submission_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Submission_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Submission_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Submission_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Submission_Siswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    private components.MaterialButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTable tblMaster;
    // End of variables declaration//GEN-END:variables
}