/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instruktur;

import java.sql.PreparedStatement;
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

/**
 *
 * @author samod
 */
public class Soal extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<String> id_quiz = new ArrayList<>();
    String id_soal;
    SimpleDateFormat datetimef = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    /**
     * Creates new form User
     */
    public Soal() {
        initComponents();
        formLoad();
    }
    
    
    private void formLoad() {
        loadData("");
        loadQuiz();
        addColumn();
        hideLabel();
    }

    
    private void addColumn() {
        
        String colTitles[] = {"No. ", "ID Soal", "Nama Kelas", "Judul Quiz", "Soal"};
        boolean[] isEditable = {false,false,false,false,false};
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
        OSLib.tableSettings(tblMaster);
        tblMaster.removeColumn(tblMaster.getColumnModel().getColumn(1));
        tblMaster.packAll();
    }
    
    private void loadData(String cari) {
        try 
        {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Soal s JOIN Quiz sm ON s.ID_Quiz = sm.ID_Quiz "
                    + "JOIN Kelas k ON k.ID_Kelas = sm.ID_Kelas WHERE k.ID_Instruktur = '" + OSSession.getId() + "' AND s.Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            int no = 1;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[5];
                obj[0] = no;
                obj[1] = r.getString("ID_Soal");
                obj[2] = r.getString("Nama_Kelas");
                obj[3] = r.getString("Judul");
                obj[4] = r.getString("Soal");
                
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
    
    private void loadQuiz() {
        try 
        {
            cmbQuiz.removeAllItems();
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Quiz s JOIN Kelas k ON s.ID_Kelas = k.ID_Kelas "
                    + "WHERE k.ID_Instruktur = '" + OSSession.getId() + "' AND s.Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            cmbQuiz.addItem("Pilih Quiz..");
            id_quiz.add("Pilih Quiz..");
            while(c.result.next()) {
                ResultSet r = c.result;
                id_quiz.add(r.getString("ID_Quiz"));
                cmbQuiz.addItem(r.getString("Judul"));
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data quiz "  + e);
        }
    }
    
    private int sumQuestion(String id_quiz) {
        int count = 0;
        try 
        {
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Soal WHERE ID_Quiz='"+id_quiz+"' AND Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            
            while(c.result.next()) {
                count++;
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data quiz "  + e);
        }
        
        return count;
    }
    
    private void saveData() {
        int i = cmbQuiz.getSelectedIndex();
        int count = sumQuestion(id_quiz.get(i));
        System.out.println(String.valueOf(count));
        if(count < 5) {
            
            try {
                String query = "INSERT INTO Soal (ID_Quiz, Soal) VALUES (?,?)";

                try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                    p.setString(1, id_quiz.get(i));
                    p.setString(2, txtSoal.getText());
                    
                    p.executeUpdate();
                }
                
                JOptionPane.showMessageDialog(this, "Data soal berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);            
            } catch(SQLException e) {
                System.out.println("Terjadi error pada saat tambah jadwal : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Soal sudah mencapai maksimum.", "Informasi",  JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateData() {
        int i = cmbQuiz.getSelectedIndex();
        
        
        try {
            String query = "UPDATE Soal SET ID_Quiz=?, Soal=? WHERE ID_Soal=?";
         
            try (PreparedStatement p = connection.conn.prepareStatement(query)) {
                p.setString(1, id_quiz.get(i));
                p.setString(2, txtSoal.getText());
                p.setString(3, id_soal);
                
                
                p.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Data Quiz berhasil diubah.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);            
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah jadwal : " + e);
        }
    }
    
    private void ClearForm() {
        id_soal = null;
        cmbQuiz.setSelectedItem("Pilih Quiz..");
        txtSoal.setText("");
        hideLabel();
    }
    
    private boolean validateAll() {
        boolean quiz = OSLib.comboRequired(cmbQuiz.getSelectedItem().toString(), "Pilih Quiz..", lblQuiz);
        boolean soal = OSLib.fieldRequired(txtSoal.getText(), lblSoal);
        
        return soal && quiz;
    }
    
    private void hideLabel() {
        lblSoal.setVisible(false);
        lblQuiz.setVisible(false);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbQuiz = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnTambah = new components.MaterialButton();
        btnUbah = new components.MaterialButton();
        btnHapus = new components.MaterialButton();
        btnBatal = new components.MaterialButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSoal = new org.jdesktop.swingx.JXTextArea();
        lblQuiz = new javax.swing.JLabel();
        lblSoal = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMaster = new org.jdesktop.swingx.JXTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        PanelContent.setBackground(new java.awt.Color(250, 250, 250));
        PanelContent.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                PanelContentformComponentShown(evt);
            }
        });
        PanelContent.setLayout(new javax.swing.BoxLayout(PanelContent, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 75));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(954, 75));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Soal");
        jPanel2.add(jLabel1);

        PanelContent.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(954, 50));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Data Soal");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Form");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(246, 246, 246)
                .addComponent(jLabel4)
                .addContainerGap(516, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        PanelContent.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(310, 32767));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(310, 449));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Quiz");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        cmbQuiz.setPreferredSize(new java.awt.Dimension(56, 30));
        jPanel1.add(cmbQuiz, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 188, -1));

        jLabel3.setText("Soal");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        btnTambah.setBackground(new java.awt.Color(40, 167, 69));
        btnTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        btnUbah.setBackground(new java.awt.Color(255, 193, 7));
        btnUbah.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnUbah.setText("Ubah");
        btnUbah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUbahMouseClicked(evt);
            }
        });
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });
        jPanel1.add(btnUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 85, -1));

        btnHapus.setBackground(new java.awt.Color(255, 51, 51));
        btnHapus.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, -1, -1));

        btnBatal.setBackground(new java.awt.Color(0, 49, 45));
        btnBatal.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 270, -1));

        txtSoal.setColumns(20);
        txtSoal.setLineWrap(true);
        txtSoal.setRows(5);
        txtSoal.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtSoal);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 188, -1));

        lblQuiz.setForeground(new java.awt.Color(255, 51, 51));
        lblQuiz.setText("Wajib diisi.");
        jPanel1.add(lblQuiz, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, -1, -1));

        lblSoal.setForeground(new java.awt.Color(255, 51, 51));
        lblSoal.setText("Wajib diisi.");
        jPanel1.add(lblSoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        jPanel4.add(jPanel1);

        tblMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMasterMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblMaster);

        jPanel4.add(jScrollPane3);

        PanelContent.add(jPanel4);

        getContentPane().add(PanelContent);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(validateAll()) {
            saveData();
            ClearForm();
            formLoad();
        } else {
            JOptionPane.showMessageDialog(this, "Isi data dengan lengkap.", "Gagal",  JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void PanelContentformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelContentformComponentShown
        formLoad();
    }//GEN-LAST:event_PanelContentformComponentShown

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(id_soal == null) 
        {
            JOptionPane.showMessageDialog(this, "Silahkan pilih data yang mau dihapus terlebih dahulu.", "Informasi",  JOptionPane.INFORMATION_MESSAGE);
        } 
        else 
        {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Peringatan", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
                OSLib.deleteData("Soal", "ID_Soal", id_soal);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
                ClearForm();
                formLoad();
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        if(id_soal == null) 
        {
            JOptionPane.showMessageDialog(this, "Silahkan pilih data yang mau diubah terlebih dahulu.", "Informasi",  JOptionPane.INFORMATION_MESSAGE);
        } 
        else 
        {
            updateData();
            ClearForm();
            formLoad();
        }
        
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnUbahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUbahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUbahMouseClicked

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        ClearForm();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMasterMouseClicked
        int i = tblMaster.getSelectedRow();
        if(i == -1) 
        {
            return;
        }
        
        id_soal = (String)model.getValueAt(i, 1);
        cmbQuiz.setSelectedItem((String)model.getValueAt(i, 3));
        txtSoal.setText((String) model.getValueAt(i, 4));
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
            java.util.logging.Logger.getLogger(Soal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Soal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Soal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Soal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Soal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    private components.MaterialButton btnBatal;
    private components.MaterialButton btnHapus;
    private components.MaterialButton btnTambah;
    private components.MaterialButton btnUbah;
    private javax.swing.JComboBox<String> cmbQuiz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblQuiz;
    private javax.swing.JLabel lblSoal;
    private org.jdesktop.swingx.JXTable tblMaster;
    private org.jdesktop.swingx.JXTextArea txtSoal;
    // End of variables declaration//GEN-END:variables
}
