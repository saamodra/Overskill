/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siswa;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Quiz_Siswa extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<String> id_kelas = new ArrayList<>();
    String id_submission = "";
    SimpleDateFormat datetimef = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    SiswaPage sp;
    
    /**
     * Creates new form User
     */
    public Quiz_Siswa() {
        initComponents();
        formLoad();
    }
    
    public Quiz_Siswa(SiswaPage sp) {
        initComponents();
        this.sp = sp;
        formLoad();
    }
    
    
    private void formLoad() {
        loadData("");
        addColumn();
    }

    
    private void addColumn() {
        
        String colTitles[] = {"No. ", "ID Quiz", "ID Kelas", "Nama Kelas", "Judul", "Duedate", "Deskripsi", "Status Submission"};
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
        OSLib.tableSettings(tblMaster);
        tblMaster.removeColumn(tblMaster.getColumnModel().getColumn(1));
        tblMaster.removeColumn(tblMaster.getColumnModel().getColumn(2));
        loadData("");
    }
    
    private boolean checkQuiz(String id_quiz) {
        boolean result = false;
        try 
        {
            DBConnect c = new DBConnect();
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT s.*, ss.ID_Siswa FROM Jawaban j JOIN QuizSiswa ss ON "
                    + "ss.ID_QuizSiswa = j.ID_QuizSiswa JOIN Soal s ON j.ID_Soal = s.ID_Soal "
                    + "WHERE s.ID_Quiz = '" + id_quiz + "' AND ss.ID_Siswa = '" + OSSession.getId() + "'";
            
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
    
    private boolean showQuiz(String id_quiz) {
        boolean result = false;
        try 
        {
            DBConnect c = new DBConnect();
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT COUNT(*) as jumlah FROM Soal s WHERE s.ID_Quiz = '" + id_quiz + "'";
            
            c.result = c.stat.executeQuery(sql);
            int count = 0;
            
            while(c.result.next()) {
                count = c.result.getInt("jumlah");
            }
            
            if(count > 4) {
                result = true;
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data jumlah soal untuk id quiz " + id_quiz + " : "  + e);
        }
        
        return result;
    }
    
    private void loadData(String cari) {
        try 
        {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
//            String sql = "SELECT * FROM viewSubmissionSiswa WHERE ID_Siswa = '" + OSSession.getId() + "' "
//                    + "AND Status='1'";
            
            String sql = "SELECT * FROM Quiz s JOIN Kelas k ON k.ID_Kelas = s.ID_Kelas WHERE "
                    + "s.ID_Kelas IN (SELECT ID_Kelas FROM Pendaftaran WHERE ID_Siswa = '" + OSSession.getId() + "') "
                    + "AND s.Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            int no = 1;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                if(showQuiz(r.getString("ID_Quiz"))) {
                    Object obj[] = new Object[8];
                    obj[0] = no;
                    obj[1] = r.getString("ID_Quiz");
                    obj[2] = r.getString("ID_Kelas");
                    obj[3] = r.getString("Nama_Kelas");
                    obj[4] = r.getString("Judul");
                    obj[5] = datetimef.format(r.getTimestamp("Duedate"));
                    obj[6] = r.getString("Deskripsi");
                    obj[7] = (checkQuiz(r.getString("ID_Quiz")) ? "Telah dikerjakan" : "Belum dikerjakan");


                    no++;
                    model.addRow(obj);
                } 
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data siswa "  + e);
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

        PanelContent.setBackground(new java.awt.Color(255, 255, 255));
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
        jLabel1.setText("Quiz");
        jPanel2.add(jLabel1);

        PanelContent.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(954, 50));

        btnTambah.setBackground(new java.awt.Color(40, 167, 69));
        btnTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTambah.setText("Kerjakan");
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
                .addContainerGap(872, Short.MAX_VALUE))
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

        tblMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMasterMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMaster);

        jPanel4.add(jScrollPane2);

        PanelContent.add(jPanel4);

        getContentPane().add(PanelContent);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        int i = tblMaster.getSelectedRow();
        
        if(i == -1 || id_submission.equals("")) {
            JOptionPane.showMessageDialog(this, "Silahkan pilih quiz untuk dikerjakan.", "Gagal", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if(((String)model.getValueAt(i, 7)).equals("Telah dikerjakan")) {
                JOptionPane.showMessageDialog(this, "Quiz ini sudah dikerjakan.", "Gagal", JOptionPane.INFORMATION_MESSAGE);
            } else {
                sp.newJawaban(sp, id_submission);
            }
            
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void PanelContentformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelContentformComponentShown
        formLoad();
    }//GEN-LAST:event_PanelContentformComponentShown

    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMasterMouseClicked
        int i = tblMaster.getSelectedRow();
        if(i == -1) 
        {
            return;
        }
        
        id_submission = (String)model.getValueAt(i, 1);
    }//GEN-LAST:event_tblMasterMouseClicked

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
            java.util.logging.Logger.getLogger(Quiz_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Quiz_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Quiz_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Quiz_Siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Quiz_Siswa().setVisible(true);
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
