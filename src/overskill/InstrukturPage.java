/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overskill;

import components.MaterialButton;
import instruktur.Absensi;
import instruktur.Feedback;
import instruktur.Soal;
import instruktur.Quiz;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import admin.MasterInstruktur;
import instruktur.Ins_QuizSiswa;
import instruktur.Jawaban_Siswa;
import report.LaporanAbsensi;
import report.LaporanFeedback;
import report.LaporanQuizSiswa;


/**
 *
 * @author ERROR404
 */
public class InstrukturPage extends javax.swing.JFrame {

    // Deklarasi Layout Menu.
    CardLayout contentLayout;
    
    // Deklarasi Variabel Fungsi Maximize
    private int width = 240;
    private boolean state = true;
    
    public InstrukturPage() 
    {
        initComponents();
        
        // Mengganti nama user dengan nama akun yang sudah login.
        txtNamaUser.setText(OSSession.getNama());
        
        Dashboard d = new Dashboard();
        Absensi absen = new Absensi();
        Quiz s = new Quiz();
        Soal so = new Soal();
        Ins_QuizSiswa ss = new Ins_QuizSiswa(this);
        Jawaban_Siswa jwb = new  Jawaban_Siswa();
        Feedback fb = new Feedback();
        LaporanQuizSiswa qs = new LaporanQuizSiswa();
        LaporanAbsensi ls = new LaporanAbsensi();
        LaporanFeedback lf = new LaporanFeedback();
        
        
        Content.add("dashboard", d);
        
        Content.add("absensi", absen.getPanel());
        Content.add("submission", s.getPanel());
        Content.add("soal", so.getPanel());
        Content.add("submission_siswa", ss.getPanel());
        Content.add("jawaban_siswa", jwb.getPanel());
        Content.add("feedback", fb.getPanel());
        Content.add("laporanAbsensi", ls.getPanel());
        Content.add("laporanQuizSiswa", qs.getPanel());
        Content.add("laporanFeedback", lf.getPanel());
        
        contentLayout = (CardLayout) Content.getLayout();   

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        
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
        mark4.setVisible(false);
        mark5.setVisible(false);
        mark6.setVisible(false);
        mark7.setVisible(false);
        mark8.setVisible(false);
        mark9.setVisible(false);
        
        myPanel.setVisible(true);
    }
    
    public void newJawaban(InstrukturPage ss, String id_submission_siswa) {
        Jawaban_Siswa jwb = new Jawaban_Siswa(ss, id_submission_siswa);
        Content.remove(5);
        Content.add("jawaban_siswa", jwb.getPanel());
        contentLayout.show(Content, "jawaban_siswa");
    }
    
    public void newSubmissionSiswa(InstrukturPage ss) {
        Ins_QuizSiswa sub = new Ins_QuizSiswa(ss);
        Content.remove(4);
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
        materialIconButton1 = new components.MaterialIconButton();
        NavBrand = new javax.swing.JPanel();
        iconOS = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JSplitPane();
        Slide = new javax.swing.JPanel();
        iconDriver = new javax.swing.JLabel();
        iconKelolaUser = new javax.swing.JLabel();
        btnJawaban = new MaterialButton();
        btnFeedback = new MaterialButton();
        iconUser = new javax.swing.JLabel();
        txtNamaUser = new javax.swing.JLabel();
        iconJenis = new javax.swing.JLabel();
        btnSubmission = new MaterialButton();
        iconDashboard = new javax.swing.JLabel();
        btnDashboard = new MaterialButton();
        textRole = new javax.swing.JLabel();
        iconProdusen = new javax.swing.JLabel();
        btnSoal = new MaterialButton();
        iconProduk = new javax.swing.JLabel();
        btnAbsensi = new MaterialButton();
        mark1 = new javax.swing.JPanel();
        mark2 = new javax.swing.JPanel();
        mark3 = new javax.swing.JPanel();
        mark4 = new javax.swing.JPanel();
        mark5 = new javax.swing.JPanel();
        mark6 = new javax.swing.JPanel();
        Separator2 = new javax.swing.JSeparator();
        mark7 = new javax.swing.JPanel();
        iconDriver1 = new javax.swing.JLabel();
        btnLaporanAbsensi = new MaterialButton();
        mark8 = new javax.swing.JPanel();
        iconDriver2 = new javax.swing.JLabel();
        btnLaporanQuizSiswa = new MaterialButton();
        mark9 = new javax.swing.JPanel();
        iconDriver3 = new javax.swing.JLabel();
        btnLaporanFeedback = new MaterialButton();
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

        materialIconButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shutdown_25px.png"))); // NOI18N
        materialIconButton1.setMaximumSize(new java.awt.Dimension(110, 3224));
        materialIconButton1.setPreferredSize(new java.awt.Dimension(50, 33));
        materialIconButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialIconButton1ActionPerformed(evt);
            }
        });
        rightHeader.add(materialIconButton1);

        Header.add(rightHeader);

        topPanel.setRightComponent(Header);

        NavBrand.setBackground(new java.awt.Color(245, 121, 0));
        NavBrand.setMaximumSize(new java.awt.Dimension(240, 32767));
        NavBrand.setPreferredSize(new java.awt.Dimension(240, 47));
        NavBrand.setLayout(null);

        iconOS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        iconOS.setForeground(new java.awt.Color(255, 255, 255));
        iconOS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconOS.setText("OVERSKILL");
        iconOS.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        iconOS.setIconTextGap(10);
        NavBrand.add(iconOS);
        iconOS.setBounds(20, 0, 190, 50);

        topPanel.setLeftComponent(NavBrand);

        Container.setLeftComponent(topPanel);

        bottomPanel.setBorder(null);
        bottomPanel.setDividerLocation(300);
        bottomPanel.setDividerSize(0);
        bottomPanel.setMinimumSize(new java.awt.Dimension(148, 60));

        Slide.setBackground(new java.awt.Color(42, 64, 84));
        Slide.setPreferredSize(new java.awt.Dimension(98, 695));
        Slide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconDriver.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconDriver.setForeground(new java.awt.Color(255, 255, 255));
        iconDriver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/feedback_25px.png"))); // NOI18N
        iconDriver.setText("   Feedback");
        Slide.add(iconDriver, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 390, 200, 50));

        iconKelolaUser.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconKelolaUser.setForeground(new java.awt.Color(255, 255, 255));
        iconKelolaUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stack_exchange_answer_25px.png"))); // NOI18N
        iconKelolaUser.setText("   Jawaban");
        Slide.add(iconKelolaUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 340, 200, 50));

        btnJawaban.setBackground(new java.awt.Color(42, 64, 84));
        btnJawaban.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnJawaban.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnJawaban.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnJawabanMouseClicked(evt);
            }
        });
        btnJawaban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJawabanActionPerformed(evt);
            }
        });
        Slide.add(btnJawaban, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 340, 295, 50));

        btnFeedback.setBackground(new java.awt.Color(42, 64, 84));
        btnFeedback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFeedback.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackActionPerformed(evt);
            }
        });
        Slide.add(btnFeedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 390, 295, 50));

        iconUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/male_user_40px.png"))); // NOI18N
        Slide.add(iconUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 50, 50));

        txtNamaUser.setBackground(new java.awt.Color(255, 255, 255));
        txtNamaUser.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaUser.setText("Samodra");
        Slide.add(txtNamaUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, 20));

        iconJenis.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconJenis.setForeground(new java.awt.Color(255, 255, 255));
        iconJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/submit_document_25px.png"))); // NOI18N
        iconJenis.setText("   Quiz");
        Slide.add(iconJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 240, 200, 50));

        btnSubmission.setBackground(new java.awt.Color(42, 64, 84));
        btnSubmission.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSubmission.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSubmission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmissionActionPerformed(evt);
            }
        });
        Slide.add(btnSubmission, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 240, 295, 50));

        iconDashboard.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
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
        textRole.setText("Instruktur");
        Slide.add(textRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 20));

        iconProdusen.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconProdusen.setForeground(new java.awt.Color(255, 255, 255));
        iconProdusen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/questionnaire_25px.png"))); // NOI18N
        iconProdusen.setText("   Soal");
        Slide.add(iconProdusen, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 290, 200, 50));

        btnSoal.setBackground(new java.awt.Color(42, 64, 84));
        btnSoal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSoal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnSoal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoalActionPerformed(evt);
            }
        });
        Slide.add(btnSoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 290, 295, 50));

        iconProduk.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconProduk.setForeground(new java.awt.Color(255, 255, 255));
        iconProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/attendance_25px.png"))); // NOI18N
        iconProduk.setText("   Absensi");
        Slide.add(iconProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 190, 200, 50));

        btnAbsensi.setBackground(new java.awt.Color(42, 64, 84));
        btnAbsensi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAbsensi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAbsensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbsensiActionPerformed(evt);
            }
        });
        Slide.add(btnAbsensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 190, 295, 50));

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

        mark4.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark4Layout = new javax.swing.GroupLayout(mark4);
        mark4.setLayout(mark4Layout);
        mark4Layout.setHorizontalGroup(
            mark4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark4Layout.setVerticalGroup(
            mark4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 5, 50));

        mark5.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark5Layout = new javax.swing.GroupLayout(mark5);
        mark5.setLayout(mark5Layout);
        mark5Layout.setHorizontalGroup(
            mark5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark5Layout.setVerticalGroup(
            mark5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 5, 50));

        mark6.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark6Layout = new javax.swing.GroupLayout(mark6);
        mark6.setLayout(mark6Layout);
        mark6Layout.setHorizontalGroup(
            mark6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark6Layout.setVerticalGroup(
            mark6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 5, 50));

        Separator2.setBackground(new java.awt.Color(91, 91, 91));
        Separator2.setForeground(new java.awt.Color(72, 88, 102));
        Slide.add(Separator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 125, 300, 15));

        mark7.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark7Layout = new javax.swing.GroupLayout(mark7);
        mark7.setLayout(mark7Layout);
        mark7Layout.setHorizontalGroup(
            mark7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark7Layout.setVerticalGroup(
            mark7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 5, 50));

        iconDriver1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconDriver1.setForeground(new java.awt.Color(255, 255, 255));
        iconDriver1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/questionnaire_25px.png"))); // NOI18N
        iconDriver1.setText("   Laporan Absensi");
        Slide.add(iconDriver1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 440, 200, 50));

        btnLaporanAbsensi.setBackground(new java.awt.Color(42, 64, 84));
        btnLaporanAbsensi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanAbsensi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnLaporanAbsensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanAbsensiActionPerformed(evt);
            }
        });
        Slide.add(btnLaporanAbsensi, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 440, 295, 50));

        mark8.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark8Layout = new javax.swing.GroupLayout(mark8);
        mark8.setLayout(mark8Layout);
        mark8Layout.setHorizontalGroup(
            mark8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark8Layout.setVerticalGroup(
            mark8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 5, 50));

        iconDriver2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconDriver2.setForeground(new java.awt.Color(255, 255, 255));
        iconDriver2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/questionnaire_25px.png"))); // NOI18N
        iconDriver2.setText("   Laporan Quiz Siswa");
        Slide.add(iconDriver2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 490, 200, 50));

        btnLaporanQuizSiswa.setBackground(new java.awt.Color(42, 64, 84));
        btnLaporanQuizSiswa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanQuizSiswa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnLaporanQuizSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanQuizSiswaActionPerformed(evt);
            }
        });
        Slide.add(btnLaporanQuizSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 490, 295, 50));

        mark9.setBackground(new java.awt.Color(245, 121, 0));

        javax.swing.GroupLayout mark9Layout = new javax.swing.GroupLayout(mark9);
        mark9.setLayout(mark9Layout);
        mark9Layout.setHorizontalGroup(
            mark9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        mark9Layout.setVerticalGroup(
            mark9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        Slide.add(mark9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 5, 50));

        iconDriver3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        iconDriver3.setForeground(new java.awt.Color(255, 255, 255));
        iconDriver3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/questionnaire_25px.png"))); // NOI18N
        iconDriver3.setText("   Laporan Feedback");
        Slide.add(iconDriver3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 540, 200, 50));

        btnLaporanFeedback.setBackground(new java.awt.Color(42, 64, 84));
        btnLaporanFeedback.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporanFeedback.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnLaporanFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanFeedbackActionPerformed(evt);
            }
        });
        Slide.add(btnLaporanFeedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 540, 295, 50));

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
                                iconOS.setText("OS");
                            }
                        }
                        else 
                        {
                            size += 4;
                            if (size == 300)
                            {
                                iconOS.setText("Overskill");
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

    private void btnJawabanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJawabanActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "submission_siswa");
        setMark(mark5);
    }//GEN-LAST:event_btnJawabanActionPerformed

    private void btnFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "feedback");
        setMark(mark6);
    }//GEN-LAST:event_btnFeedbackActionPerformed

    private void btnSubmissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmissionActionPerformed
        
        contentLayout.show(Content, "submission");
        setMark(mark3);
    }//GEN-LAST:event_btnSubmissionActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "dashboard");
        setMark(mark1);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnSoalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoalActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "soal");
        setMark(mark4);
    }//GEN-LAST:event_btnSoalActionPerformed

    private void btnAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsensiActionPerformed
        // TODO add your handling code here:
        contentLayout.show(Content, "absensi");
        setMark(mark2);
    }//GEN-LAST:event_btnAbsensiActionPerformed

    private void btnNavLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavLoginActionPerformed

    }//GEN-LAST:event_btnNavLoginActionPerformed

    private void btnJawabanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJawabanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnJawabanMouseClicked

    private void materialIconButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialIconButton1ActionPerformed
        Login l = new Login();
        l.setVisible(true);
        dispose();
    }//GEN-LAST:event_materialIconButton1ActionPerformed

    private void btnLaporanAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanAbsensiActionPerformed
        contentLayout.show(Content, "laporanAbsensi");
        setMark(mark7);
    }//GEN-LAST:event_btnLaporanAbsensiActionPerformed

    private void btnLaporanQuizSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanQuizSiswaActionPerformed
        contentLayout.show(Content, "laporanQuizSiswa");
        setMark(mark8);
    }//GEN-LAST:event_btnLaporanQuizSiswaActionPerformed

    private void btnLaporanFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanFeedbackActionPerformed
        contentLayout.show(Content, "laporanFeedback");
        setMark(mark9);
    }//GEN-LAST:event_btnLaporanFeedbackActionPerformed

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
            java.util.logging.Logger.getLogger(MasterInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterInstruktur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JButton btnAbsensi;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnFeedback;
    private javax.swing.JButton btnJawaban;
    private javax.swing.JButton btnLaporanAbsensi;
    private javax.swing.JButton btnLaporanFeedback;
    private javax.swing.JButton btnLaporanQuizSiswa;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnSoal;
    private javax.swing.JButton btnSubmission;
    private javax.swing.JLabel iconDashboard;
    private javax.swing.JLabel iconDriver;
    private javax.swing.JLabel iconDriver1;
    private javax.swing.JLabel iconDriver2;
    private javax.swing.JLabel iconDriver3;
    private javax.swing.JLabel iconJenis;
    private javax.swing.JLabel iconKelolaUser;
    private javax.swing.JLabel iconMenu;
    private javax.swing.JLabel iconOS;
    private javax.swing.JLabel iconProduk;
    private javax.swing.JLabel iconProdusen;
    private javax.swing.JLabel iconUser;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel leftHeader;
    private javax.swing.JPanel mark1;
    private javax.swing.JPanel mark2;
    private javax.swing.JPanel mark3;
    private javax.swing.JPanel mark4;
    private javax.swing.JPanel mark5;
    private javax.swing.JPanel mark6;
    private javax.swing.JPanel mark7;
    private javax.swing.JPanel mark8;
    private javax.swing.JPanel mark9;
    private components.MaterialIconButton materialIconButton1;
    private javax.swing.JPanel rightHeader;
    private javax.swing.JLabel textRole;
    private javax.swing.JSplitPane topPanel;
    private javax.swing.JLabel txtNamaUser;
    // End of variables declaration//GEN-END:variables
}
