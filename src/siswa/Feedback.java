/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siswa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
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
public class Feedback extends javax.swing.JFrame {
    DBConnect connection = new DBConnect();
    DefaultTableModel model = new DefaultTableModel();
    /**
     * Creates new form Feedback
     */
    public Feedback() {
        initComponents();
    }
    
    private void formLoad() {
        model = new DefaultTableModel();
        addColumn();
        loadData();
    }
    
    private void addColumn() {
        
        String colTitles[] = {"No. ", "ID Feedback", "ID Kelas", "Nama Kelas", "Rating", "Feedback", "Tgl. Feedback", "Keterangan"};
        boolean[] isEditable = {false,false,false,false,false,false,false,false};
        model = new DefaultTableModel(colTitles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make read only fields except column 0,13,14
                return isEditable[column];
            }
        };
        // The 0 argument is number rows. 
        tblKelas.setModel(model);
        tblKelas.removeColumn(tblKelas.getColumnModel().getColumn(1));
//        tblKelas.removeColumn(tblKelas.getColumnModel().getColumn(2));
        tblKelas.packAll();
    }
    
    
//    private boolean checkFeedback(String id_kelas) {
//        boolean result = false;
//        try 
//        {
//            DBConnect c = new DBConnect();
//            
//            c.stat = c.conn.createStatement();
//            String sql = "SELECT * FROM Feedback WHERE ID_Kelas = '" + id_kelas + "' AND ID_Siswa='" + OSSession.getId() + "'";
//            
//            c.result = c.stat.executeQuery(sql);
//            int count = 0;
//            
//            while(c.result.next()) {
//                count++;
//                
//            }
//            
//            if(count > 0) {
//                result = true;
//            }
//            c.stat.close();
//            c.result.close();
//        } 
//        catch(SQLException e) 
//        {
//            System.out.println("Terjadi error saat load data jawaban "  + e);
//        }
//        
//        return result;
//    }
    
    private void loadData() {
        try 
        {
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            
            DBConnect c = connection;
            c.stat = c.conn.createStatement();
//            String sql = "SELECT f.ID_Kelas, k.Nama_Kelas, ISNULL(f.Rating, '-') as 'Rating', "
//                    + "ISNULL(f.Feedback, '-') as 'Feedback', f.Tgl_Feedback FROM Feedback f "
//                    + "JOIN Kelas k ON k.ID_Kelas = f.ID_Kelas WHERE f.ID_Siswa='"+ OSSession.getId() +"'";
//            String sql = "SELECT * FROM Pendaftaran p JOIN Kelas k ON k.ID_Kelas = p.ID_Kelas WHERE ID_Siswa='"+ OSSession.getId() +"'";
            String sql = "SELECT * FROM Feedback f JOIN Kelas k ON k.ID_Kelas = f.ID_Kelas WHERE f.ID_Siswa = '" + OSSession.getId() + "'";

            c.result = c.stat.executeQuery(sql);
            int no = 1;
            
            while(c.result.next()) {
                ResultSet r = c.result;
                Object obj[] = new Object[8];
                obj[0] = no;
                obj[1] = r.getString("ID_Feedback");
                obj[2] = r.getString("ID_Kelas");
                obj[3] = r.getString("Nama_Kelas");
                obj[4] = (r.getString("Rating").equals("") ? "-" : r.getString("Rating"));
                obj[5] = (r.getString("Feedback").equals("") ? "-" : r.getString("Feedback"));
//                obj[5] = "-";
                obj[6] = (r.getString("Tgl_Feedback") == null ? "-" : r.getString("Tgl_Feedback"));
                obj[7] = (r.getString("Status").equals("0") ? "Belum ada feedback" : "Sudah ada feedback");
                
                no++;
                model.addRow(obj);
            }
            c.stat.close();
            c.result.close();
        } 
        catch(SQLException e) 
        {
            System.out.println("Terjadi error saat load data feedback "  + e);
        }
    }  
    
    private void saveData() {
        
        try {
            
            String query = "UPDATE Feedback SET Feedback=?, Rating=?, Tgl_Feedback=?, Status=? WHERE ID_Feedback=?";
         
            java.sql.Date dateNow = new java.sql.Date(new Date().getTime());
            
            PreparedStatement p = connection.conn.prepareStatement(query);
            p.setString(1, txtFeedback.getText());
            p.setInt(2, (int)txtRating.getValue());
            p.setDate(3, dateNow);
            p.setString(4, "1");
            p.setString(5, lblID.getText());
            
            p.executeUpdate();
            p.close();

            JOptionPane.showMessageDialog(this, "Data feedback berhasil diupdate.", "Berhasil",  JOptionPane.INFORMATION_MESSAGE);
        } catch(SQLException e) {
            System.out.println("Terjadi error pada saat tambah feedback : " + e);
        }
    }
    
    public JPanel getPanel() {
        return Panel;
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
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKelas = new org.jdesktop.swingx.JXTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNamaKelas = new components.UITextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnKirim = new components.UIFlatButton();
        lblID = new javax.swing.JLabel();
        txtRating = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFeedback = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                PanelformComponentShown(evt);
            }
        });
        Panel.setLayout(new javax.swing.BoxLayout(Panel, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 75));
        jPanel2.setPreferredSize(new java.awt.Dimension(954, 75));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Feedback");
        jPanel2.add(jLabel1);

        Panel.add(jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 11, 11, 11));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 45));
        jPanel7.setPreferredSize(new java.awt.Dimension(280, 45));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Kelas");
        jLabel10.setToolTipText("");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(470, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel7);

        tblKelas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblKelas.setHorizontalScrollEnabled(true);
        tblKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKelasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKelas);

        jPanel6.add(jScrollPane2);

        jPanel4.add(jPanel6);

        jPanel3.setMaximumSize(new java.awt.Dimension(350, 32767));
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 459));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Form Feedback");
        jLabel2.setToolTipText("");

        jLabel3.setText("Nama Kelas");

        txtNamaKelas.setAe_Placeholder("Nama Kelas");
        txtNamaKelas.setEnabled(false);

        jLabel4.setText("Rating");

        jLabel5.setText("Feedback");

        btnKirim.setText("Kirim Feedback");
        btnKirim.setMouseHover(new java.awt.Color(36, 168, 110));
        btnKirim.setWarnaBackground(new java.awt.Color(40, 167, 69));
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
            }
        });

        lblID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblID.setText("ID");
        lblID.setToolTipText("");

        txtRating.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRating.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        txtRating.setPreferredSize(new java.awt.Dimension(39, 30));

        jScrollPane1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        txtFeedback.setColumns(20);
        txtFeedback.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtFeedback.setLineWrap(true);
        txtFeedback.setRows(5);
        txtFeedback.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtFeedback);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblID))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNamaKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1)
                            .addComponent(btnKirim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblID))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNamaKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtRating, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnKirim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
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

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        saveData();
    }//GEN-LAST:event_btnKirimActionPerformed

    private void PanelformComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PanelformComponentShown
        formLoad();
    }//GEN-LAST:event_PanelformComponentShown

    private void tblKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKelasMouseClicked
        int i = tblKelas.getSelectedRow();

        if(i == -1) {
            return;
        }
        
        String rating = (((String)model.getValueAt(i, 4)).equals("-") ? "0" : (String)model.getValueAt(i, 4));
        lblID.setText((String)model.getValueAt(i, 1));
        txtNamaKelas.setText((String)model.getValueAt(i, 3));
        txtRating.setValue(Integer.parseInt(rating));
        txtFeedback.setText((String)model.getValueAt(i, 5));
    }//GEN-LAST:event_tblKelasMouseClicked

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
            java.util.logging.Logger.getLogger(Feedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Feedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Feedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Feedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Feedback().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel;
    private components.UIFlatButton btnKirim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblID;
    private org.jdesktop.swingx.JXTable tblKelas;
    private javax.swing.JTextArea txtFeedback;
    private components.UITextField txtNamaKelas;
    private javax.swing.JSpinner txtRating;
    // End of variables declaration//GEN-END:variables
}
