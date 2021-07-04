/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siswa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import overskill.DBConnect;
import overskill.OSLib;
import overskill.OSSession;
import overskill.SiswaPage;

/**
 *
 * @author samod
 */
public class Jawaban extends javax.swing.JFrame {
    DBConnect c = new DBConnect();
    String id_quiz = "";
    ArrayList<String> id_soal;
    ArrayList<String> jawaban;
    ArrayList<String> soal;
    SiswaPage ss;
    /**
     * Creates new form Jawaban
     */
    public Jawaban() {
        initComponents();
        id_soal = new ArrayList<>();
        soal = new ArrayList<>();
    }
    
    public Jawaban(SiswaPage ss, String id_quiz) {
        initComponents();
        this.ss = ss;
        this.id_quiz = id_quiz;
        id_soal = new ArrayList<>();
        soal = new ArrayList<>();
        formLoad(id_quiz);
    }
    
    private void formLoad(String id_quiz) {
        try 
        {
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Soal s JOIN Quiz sm ON sm.ID_Quiz=s.ID_Quiz "
                    + "JOIN Kelas k ON k.ID_Kelas = sm.ID_Kelas WHERE s.ID_Quiz='" + id_quiz + "' AND s.Status='1'";
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                ResultSet r = c.result;
                id_soal.add(r.getString("ID_Soal"));
                soal.add(r.getString("Soal"));
                lblJudul.setText(r.getString("Nama_Kelas") + " - " + r.getString("Judul"));
            }
            
            c.stat.close();
            c.result.close();
            
            lblSoal1.setText("1. " + soal.get(0));
            lblSoal2.setText("2. " + soal.get(1));
            lblSoal3.setText("3. " + soal.get(2));
            lblSoal4.setText("4. " + soal.get(3));
            lblSoal5.setText("5. " + soal.get(4));
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data instruktur "  + e);
        }
    }
    
    private boolean validate(JTextArea text) {
        return !text.getText().equals("");
    }
    
    private void saveData() {
        jawaban = new ArrayList<>();
        jawaban.add(txtArea1.getText());
        jawaban.add(txtArea2.getText());
        jawaban.add(txtArea3.getText());
        jawaban.add(txtArea4.getText());
        jawaban.add(txtArea5.getText());
        
        if(validate(txtArea1) && validate(txtArea2) && validate(txtArea3) && validate(txtArea4) && validate(txtArea5)) {
            String id_ss = OSLib.AutoNumber("QuizSiswa", "ID_QuizSiswa", "SS");
            try {
                String query = "INSERT INTO QuizSiswa (ID_QuizSiswa, ID_Siswa) VALUES (?,?)";

                PreparedStatement p = c.conn.prepareStatement(query);
                p.setString(1, id_ss);
                p.setString(2, OSSession.getId());

                p.executeUpdate();
                p.close();

            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat tambah quiz siswa : " + e);
            }
            
            for(int i = 0; i < 5; i++) {
                try {
                    String query = "INSERT INTO Jawaban (ID_Soal, ID_QuizSiswa, Jawaban) VALUES (?,?,?)";

                    PreparedStatement p = c.conn.prepareStatement(query);
                    p.setString(1, id_soal.get(i));
                    p.setString(2, id_ss);
                    p.setString(3, jawaban.get(i));

                    p.executeUpdate();
                    p.close();
       
                } catch(SQLException e) {
                    System.out.println("Terjadi error pada saat tambah jadwal : " + e);
                }
            }
            
            JOptionPane.showMessageDialog(this, "Jawaban berhasil dikirim", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);   
            
            ss.newSubmissionSiswa(ss);
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan jawab semua soal.", "Gagal Menyimpan",  JOptionPane.INFORMATION_MESSAGE);
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
        lblJudul = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblSoal1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea1 = new javax.swing.JTextArea();
        lblSoal2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtArea3 = new javax.swing.JTextArea();
        lblSoal3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtArea4 = new javax.swing.JTextArea();
        lblSoal4 = new javax.swing.JLabel();
        lblSoal5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtArea5 = new javax.swing.JTextArea();
        btnKembali = new components.MaterialButton();
        btnKirim = new components.MaterialButton();

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

        lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudul.setText("Submission");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(388, Short.MAX_VALUE)
                .addComponent(lblJudul)
                .addContainerGap(414, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblJudul))
        );

        PanelContent.add(jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setMaximumSize(new java.awt.Dimension(327812, 327812));

        lblSoal1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoal1.setText("1. Apa itu konsep OOP pada Java?");

        txtArea1.setColumns(20);
        txtArea1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtArea1.setRows(5);
        jScrollPane1.setViewportView(txtArea1);

        lblSoal2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoal2.setText("2.");

        txtArea2.setColumns(20);
        txtArea2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtArea2.setRows(5);
        jScrollPane2.setViewportView(txtArea2);

        txtArea3.setColumns(20);
        txtArea3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtArea3.setRows(5);
        jScrollPane3.setViewportView(txtArea3);

        lblSoal3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoal3.setText("2.");

        txtArea4.setColumns(20);
        txtArea4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtArea4.setRows(5);
        jScrollPane4.setViewportView(txtArea4);

        lblSoal4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoal4.setText("2.");

        lblSoal5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoal5.setText("2.");

        txtArea5.setColumns(20);
        txtArea5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtArea5.setRows(5);
        jScrollPane5.setViewportView(txtArea5);

        btnKembali.setBackground(new java.awt.Color(255, 214, 207));
        btnKembali.setForeground(new java.awt.Color(255, 92, 64));
        btnKembali.setText("Kembali");
        btnKembali.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        btnKirim.setBackground(new java.awt.Color(40, 167, 69));
        btnKirim.setText("Kirim Jawaban");
        btnKirim.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKirim, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoal5)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoal4)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoal3)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoal2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoal1)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblSoal1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSoal2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoal3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSoal4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSoal5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKirim, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelContent.add(jPanel4);

        getContentPane().add(PanelContent);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PanelContentformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelContentformComponentShown
        formLoad(id_quiz);
    }//GEN-LAST:event_PanelContentformComponentShown

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        ss.newSubmissionSiswa(ss);
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin mengirim jawaban?",
                "Peringatan", JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            saveData();
        }        
    }//GEN-LAST:event_btnKirimActionPerformed

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
            java.util.logging.Logger.getLogger(Jawaban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Jawaban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Jawaban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Jawaban.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Jawaban().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    private components.MaterialButton btnKembali;
    private components.MaterialButton btnKirim;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblSoal1;
    private javax.swing.JLabel lblSoal2;
    private javax.swing.JLabel lblSoal3;
    private javax.swing.JLabel lblSoal4;
    private javax.swing.JLabel lblSoal5;
    private javax.swing.JTextArea txtArea1;
    private javax.swing.JTextArea txtArea2;
    private javax.swing.JTextArea txtArea3;
    private javax.swing.JTextArea txtArea4;
    private javax.swing.JTextArea txtArea5;
    // End of variables declaration//GEN-END:variables
}
