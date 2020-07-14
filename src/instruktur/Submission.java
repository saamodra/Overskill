/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instruktur;

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

/**
 *
 * @author samod
 */
public class Submission extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    ArrayList<String> id_kelas = new ArrayList<>();
    String id_submission;
    SimpleDateFormat datetimef = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    /**
     * Creates new form User
     */
    public Submission() {
        initComponents();
        formLoad();
    }
    
    
    private void formLoad() {
        loadData("");
        loadKelas();
        addColumn();
        txtDueDate.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        txtDueDate.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );
    }

    
    private void addColumn() {
        
        String colTitles[] = {"No. ", "ID Submission", "ID Kelas", "Nama Kelas", "Judul", "Duedate", "Deskripsi"};
        boolean[] isEditable = {false,false,false,false,false,false,false};
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
    
    private void loadData(String cari) {
        try 
        {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Submission s JOIN Kelas k ON k.ID_Kelas = s.ID_Kelas WHERE s.Status='1'";
            
            c.result = c.stat.executeQuery(sql);
            int no = 1;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[7];
                obj[0] = no;
                obj[1] = r.getString("ID_Submission");
                obj[2] = r.getString("ID_Kelas");
                obj[3] = r.getString("Nama_Kelas");
                obj[4] = r.getString("Judul");
                obj[5] = datetimef.format(r.getTimestamp("Duedate"));
                obj[6] = r.getString("Deskripsi");
                
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
    
    private void loadKelas() {
        try 
        {
            
            cmbKelas.removeAllItems();
            DBConnect c = connection;
            
            c.stat = c.conn.createStatement();
            String sql = "SELECT * FROM Kelas WHERE Status='1' AND Status_Kelas='1'";
            
            c.result = c.stat.executeQuery(sql);
            
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
    
    private void saveData() {
        int i = cmbKelas.getSelectedIndex();
        
        java.sql.Timestamp tglDuedate = new java.sql.Timestamp(txtDueDate.getDate().getTime());
        try {
            String id_sm = OSLib.AutoNumber("Submission", "ID_Submission", "SM");
            String query = "INSERT INTO Submission (ID_Submission, ID_Kelas, Judul, Duedate, Deskripsi) VALUES (?,?,?,?,?)";
         
            PreparedStatement p = connection.conn.prepareStatement(query);
            p.setString(1, id_sm);
            p.setString(2, id_kelas.get(i));
            p.setString(3, txtJudul.getText());
            p.setTimestamp(4, tglDuedate);
            p.setString(5, txtDeskripsi.getText());
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data Submission berhasil disimpan.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);            
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah jadwal : " + e);
        }
    }
    
    private void updateData() {
        int i = cmbKelas.getSelectedIndex();
        
        java.sql.Timestamp tglDuedate = new java.sql.Timestamp(txtDueDate.getDate().getTime());
        
        try {
            String query = "UPDATE Submission SET ID_Kelas=?, Judul=?, Duedate=?, Deskripsi=? WHERE ID_Submission=?";
         
            PreparedStatement p = connection.conn.prepareStatement(query);
            
            p.setString(1, id_kelas.get(i));
            p.setString(2, txtJudul.getText());
            p.setTimestamp(3, tglDuedate);
            p.setString(4, txtDeskripsi.getText());
            p.setString(5, id_submission);
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data Submission berhasil diubah.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);            
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah jadwal : " + e);
        }
    }
    
    private void ClearForm() {
        id_submission = null;
        txtDeskripsi.setText("");
        txtDueDate.setDate(null);
        txtJudul.setText("");
        cmbKelas.setSelectedIndex(-1);
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
        cmbKelas = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtJudul = new components.UITextField();
        jLabel5 = new javax.swing.JLabel();
        txtDeskripsi = new components.UITextField();
        jLabel6 = new javax.swing.JLabel();
        btnTambah = new components.MaterialButton();
        btnUbah = new components.MaterialButton();
        btnHapus = new components.MaterialButton();
        txtDueDate = new components.DateTimePicker();
        btnBatal = new components.MaterialButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaster = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        PanelContent.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                PanelContentformComponentShown(evt);
            }
        });
        PanelContent.setLayout(new javax.swing.BoxLayout(PanelContent, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 75));
        jPanel2.setPreferredSize(new java.awt.Dimension(954, 75));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Submission");
        jPanel2.add(jLabel1);

        PanelContent.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(954, 50));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Data Submission");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Form");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(211, 211, 211)
                .addComponent(jLabel4)
                .addContainerGap(461, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        PanelContent.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(280, 32767));
        jPanel1.setPreferredSize(new java.awt.Dimension(280, 449));

        jLabel2.setText("Kelas");

        cmbKelas.setPreferredSize(new java.awt.Dimension(56, 30));

        jLabel3.setText("Judul");

        txtJudul.setAe_Placeholder("Judul");

        jLabel5.setText("Deskripsi");

        txtDeskripsi.setAe_Placeholder("Deskripsi");

        jLabel6.setText("Duedate");

        btnTambah.setBackground(new java.awt.Color(40, 167, 69));
        btnTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

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

        btnHapus.setBackground(new java.awt.Color(255, 51, 51));
        btnHapus.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtDueDate.setPreferredSize(new java.awt.Dimension(112, 30));

        btnBatal.setBackground(new java.awt.Color(0, 49, 45));
        btnBatal.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbKelas, txtDeskripsi, txtJudul});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1);

        tblMaster.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
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
        tblMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMaster);

        jPanel4.add(jScrollPane1);

        PanelContent.add(jPanel4);

        getContentPane().add(PanelContent);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        saveData();
        ClearForm();
        formLoad();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void PanelContentformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelContentformComponentShown
        formLoad();
    }//GEN-LAST:event_PanelContentformComponentShown

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(id_submission == null) 
        {
            JOptionPane.showMessageDialog(this, "Silahkan pilih data yang mau dihapus terlebih dahulu.", "Informasi",  JOptionPane.INFORMATION_MESSAGE);
        } 
        else 
        {
            OSLib.deleteData("Submission", "ID_Submission", id_submission);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
            formLoad();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMasterMouseClicked
        int i = tblMaster.getSelectedRow();
        if(i == -1) 
        {
            return;
        }
        
        id_submission = (String)model.getValueAt(i, 1);
        cmbKelas.setSelectedItem((String)model.getValueAt(i, 3));
        txtJudul.setText((String)model.getValueAt(i, 4));
        try {
            txtDueDate.setDate(datetimef.parse((String)model.getValueAt(i, 5)));
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        txtDeskripsi.setText((String)model.getValueAt(i, 6));
        
        
    }//GEN-LAST:event_tblMasterMouseClicked

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        if(id_submission == null) 
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
            java.util.logging.Logger.getLogger(Submission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Submission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Submission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Submission.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Submission().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContent;
    private components.MaterialButton btnBatal;
    private components.MaterialButton btnHapus;
    private components.MaterialButton btnTambah;
    private components.MaterialButton btnUbah;
    private javax.swing.JComboBox<String> cmbKelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMaster;
    private components.UITextField txtDeskripsi;
    private components.DateTimePicker txtDueDate;
    private components.UITextField txtJudul;
    // End of variables declaration//GEN-END:variables
}