/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import components.MaterialButton;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import admin.Instruktur;
import admin.Kelas;
import admin.Komputer;
import admin.Siswa;
import admin.User;
import admin.Jadwal;
import siswa.Feedback;
import siswa.Jawaban;
import siswa.Submission_Siswa;


/**
 *
 * @author ERROR404
 */
public class SiswaPage extends javax.swing.JFrame {

    // Deklarasi Layout Menu.
    CardLayout contentLayout;
    
    // Deklarasi Variabel Fungsi Maximize
    private int width = 240;
    private boolean state = true;
    
    public SiswaPage() 
    {
        initComponents();
        
        // Mengganti nama user dengan nama akun yang sudah login.
        User u = new User();
        Dashboard d = new Dashboard();
        Submission_Siswa ss = new Submission_Siswa(this);
        Feedback fd = new Feedback();
        Jawaban jwb = new Jawaban();
        
        Content.add("dashboard", d);
        Content.add("submission_siswa", ss.getPanel());
        Content.add("feedback", fd.getPanel());
        Content.add("jawaban", jwb.getPanel());
        
        
        contentLayout = (CardLayout) Content.getLayout();   

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | SiswaPage.MAXIMIZED_BOTH);
        
        setMark(mark1);
        
        // Membuat Fungsi Sidebar menjadi full saat maximize.
        this.addWindowStateListener(new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent we) {
                frame__windowStateChanged(we);
            }
            
            public void frame__windowStateChanged(WindowEvent e){
                if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH){
                    width = 300;
                    topPanel.setDividerLocation(width);
                    bottomPanel.setDividerLocation(width);
                    //textRole.setText("Ladetani");
                    NavBrand.setMinimumSize(new Dimension(300, 50));
                    Slide.setMinimumSize(new Dimension(300, 50));
                    state = true;
                }
             }
        });
    }
    
    private void setMark(JPanel myPanel)
    {
        mark1.setVisible(false);
        mark2.setVisible(false);
        mark3.setVisible(false);
        
        myPanel.setVisible(true);
    }
    
    public void newJawaban(SiswaPage ss, String id_submission) {
        Jawaban jwb = new Jawaban(ss, id_submission);
        Content.remove(3);
        Content.add("jawaban", jwb.getPanel());
        contentLayout.show(Content, "jawaban");
    }
    
    public void newSubmissionSiswa(SiswaPage ss) {
        Submission_Siswa sub = new Submission_Siswa(ss);
        Content.remove(1);
        Content.add("submission_siswa", sub.getPanel());
        contentLayout.show(Content, "submission_siswa");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Container = new javax.swing.JSplitPane();
        topPanel = new javax.swing.JSplitPane();
        Header = new javax.swing.JPanel();
        leftHeader = new javax.swing.JPanel();
        iconMenu = new javax.swing.JLabel();
        btnMenu = new MaterialButton();
        rightHeader = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnLogout = new components.MaterialIconButton();
        NavBrand = new javax.swing.JPanel();
        iconOverskill = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JSplitPane();
        Slide = new javax.swing.JPanel();
        iconUser = new javax.swing.JLabel();
        textNama = new javax.swing.JLabel();
        iconJenis = new javax.swing.JLabel();
        btnFeedback = new MaterialButton();
        iconDashboard = new javax.swing.JLabel();
        btnDashboard = new MaterialButton();
        textRole = new javax.swing.JLabel();
        iconProduk = new javax.swing.JLabel();
        btnSubmission = new MaterialButton();
        mark1 = new javax.swing.JPanel();
        mark2 = new javax.swing.JPanel();
        mark3 = new javax.swing.JPanel();
        Separator2 = new javax.swing.JSeparator();
        Content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Overskill");
        setMinimumSize(new java.awt.Dimension(1280, 720));

        Container.setBorder(null);
        Container.setDividerLocation(50);
        Container.setDividerSize(0);
        Container.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        Container.setToolTipText("");
        Container.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Container.setPreferredSize(new java.awt.Dimension(804, 718));

        topPanel.setBorder(null);
        topPanel.setDividerLocation(300);
        topPanel.setDividerSize(0);

        Header.setBackground(new java.awt.Color(42, 79, 150));
        Header.setPreferredSize(new java.awt.Dimension(700, 52));
        Header.setLayout(new java.awt.GridLayout(1, 0));

        leftHeader.setBackground(new java.awt.Color(42, 64, 84));
        leftHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconMenu.setBackground(new java.awt.Color(42, 64, 84));
        iconMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu_25px.png"))); // NOI18N
        leftHeader.add(iconMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        btnMenu.setBackground(new java.awt.Color(42, 64, 84));
        btnMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        leftHeader.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        Header.add(leftHeader);

        rightHeader.setBackground(new java.awt.Color(42, 64, 84));
        rightHeader.setLayout(new javax.swing.BoxLayout(rightHeader, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(42, 64, 84));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        rightHeader.add(jPanel2);

        btnLogout.setBackground(new java.awt.Color(26, 41, 66));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shutdown_25px.png"))); // NOI18N
        btnLogout.setMaximumSize(new java.awt.Dimension(110, 3224));
        btnLogout.setPreferredSize(new java.awt.Dimension(50, 33));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        rightHeader.add(btnLogout);

        Header.add(rightHeader);

        topPanel.setRightComponent(Header);

        NavBrand.setBackground(new java.awt.Color(245, 121, 0));
        NavBrand.setMaximumSize(new java.awt.Dimension(240, 32767));
        NavBrand.setPreferredSize(new java.awt.Dimension(240, 47));
        NavBrand.setLayout(null);

        iconOverskill.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        iconOverskill.setForeground(new java.awt.Color(255, 255, 255));
        iconOverskill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconOverskill.setText("OVERSKILL");
        iconOverskill.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        iconOverskill.setIconTextGap(10);
        NavBrand.add(iconOverskill);
        iconOverskill.setBounds(0, 0, 300, 50);

        topPanel.setLeftComponent(NavBrand);

        Container.setLeftComponent(topPanel);

        bottomPanel.setBorder(null);
        bottomPanel.setDividerLocation(300);
        bottomPanel.setDividerSize(0);
        bottomPanel.setMinimumSize(new java.awt.Dimension(148, 60));

        Slide.setBackground(new java.awt.Color(42, 64, 84));
        Slide.setPreferredSize(new java.awt.Dimension(98, 695));
        Slide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/male_user_40px.png"))); // NOI18N
        Slide.add(iconUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 50, 50));

        textNama.setBackground(new java.awt.Color(255, 255, 255));
        textNama.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        textNama.setForeground(new java.awt.Color(255, 255, 255));
        textNama.setText("Samodra");
        Slide.add(textNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, 20));

        iconJenis.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        iconJenis.setForeground(new java.awt.Color(255, 255, 255));
        iconJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/feedback_25px.png"))); // NOI18N
        iconJenis.setText("   Feedback");
        Slide.add(iconJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 240, 200, 50));

        btnFeedback.setBackground(new java.awt.Color(42, 64, 84));
        btnFeedback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFeedback.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackActionPerformed(evt);
            }
        });
        Slide.add(btnFeedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 240, 295, 50));

        iconDashboard.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        iconDashboard.setForeground(new java.awt.Color(255, 255, 255));
        iconDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dashboard_25px.png"))); // NOI18N
        iconDashboard.setText("   Dashboard");
        Slide.add(iconDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 140, 200, 50));

        btnDashboard.setBackground(new java.awt.Color(42, 64, 84));
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDashboard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });
        Slide.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 140, 295, 50));

        textRole.setBackground(new java.awt.Color(255, 255, 255));
        textRole.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        textRole.setForeground(new java.awt.Color(255, 255, 255));
        textRole.setText("Siswa");
        Slide.add(textRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 20));

        iconProduk.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        iconProduk.setForeground(new java.awt.Color(255, 255, 255));
        iconProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submit_document_25px.png"))); // NOI18N
        iconProduk.setText("   Submission");
        Slide.add(iconProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 190, 200, 50));

        btnSubmission.setBackground(new java.awt.Color(42, 64, 84));
        btnSubmission.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSubmission.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmissionActionPerformed(evt);
            }
        });
        Slide.add(btnSubmission, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 190, 295, 50));

        mark1.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark1Layout = new javax.swing.GroupLayout(mark1);
        mark1.setLayout(mark1Layout);
        mark1Layout.setHorizontalGroup(
            mark1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark1Layout.setVerticalGroup(
            mark1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 5, 50));

        mark2.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark2Layout = new javax.swing.GroupLayout(mark2);
        mark2.setLayout(mark2Layout);
        mark2Layout.setHorizontalGroup(
            mark2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark2Layout.setVerticalGroup(
            mark2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 5, 50));

        mark3.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark3Layout = new javax.swing.GroupLayout(mark3);
        mark3.setLayout(mark3Layout);
        mark3Layout.setHorizontalGroup(
            mark3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark3Layout.setVerticalGroup(
            mark3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 5, 50));

        Separator2.setBackground(new java.awt.Color(91, 91, 91));
        Separator2.setForeground(new java.awt.Color(72, 88, 102));
        Slide.add(Separator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 125, 300, 15));

        bottomPanel.setLeftComponent(Slide);

        Content.setBackground(new java.awt.Color(230, 230, 230));
        Content.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));
        Content.setLayout(new java.awt.CardLayout());
        bottomPanel.setRightComponent(Content);

        Container.setRightComponent(bottomPanel);

        getContentPane().add(Container, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed

        Thread th = new Thread()
        {
            @Override
            public void run ()
            {
                try
                {
                    int lock = bottomPanel.getDividerLocation();
                    int size = lock;
                    for(int a=0;a<=60;a++)
                    {
                        
                        Thread.sleep(6);

                        if (lock == 300)
                        {
                            size -= 4;
                            if (size == 60)
                            {
                                iconOverskill.setText("OS");
                            }
                        }
                        else 
                        {
                            size += 4;
                            if (size == 300)
                            {
                                iconOverskill.setText("Overskill");
                            }
                        }
                        
                        bottomPanel.setDividerLocation(size);
                        topPanel.setDividerLocation(size);
                    }
                }
                catch (Exception ex)
                {
                    System.out.println("Terjadi Error Pada Button Menu: " + ex);
                }
            }
        }; th.start();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "feedback");
        setMark(mark3);
    }//GEN-LAST:event_btnFeedbackActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "dashboard");
        setMark(mark1);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmissionActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "submission_siswa");
        setMark(mark2);
    }//GEN-LAST:event_btnSubmissionActionPerformed

    private void btnNavLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavLoginActionPerformed
        // TODO add your handling code here:
//        Landing ld = new Landing();
//        ld.setVisible(true);
//        this.dispose();
    }//GEN-LAST:event_btnNavLoginActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        Login l = new Login();
        l.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Instruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Instruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Instruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Instruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane Container;
    private javax.swing.JPanel Content;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel NavBrand;
    private javax.swing.JSeparator Separator2;
    private javax.swing.JPanel Slide;
    private javax.swing.JSplitPane bottomPanel;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnFeedback;
    private components.MaterialIconButton btnLogout;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnSubmission;
    private javax.swing.JLabel iconDashboard;
    private javax.swing.JLabel iconJenis;
    private javax.swing.JLabel iconMenu;
    private javax.swing.JLabel iconOverskill;
    private javax.swing.JLabel iconProduk;
    private javax.swing.JLabel iconUser;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel leftHeader;
    private javax.swing.JPanel mark1;
    private javax.swing.JPanel mark2;
    private javax.swing.JPanel mark3;
    private javax.swing.JPanel rightHeader;
    private javax.swing.JLabel textNama;
    private javax.swing.JLabel textRole;
    private javax.swing.JSplitPane topPanel;
    // End of variables declaration//GEN-END:variables
}
