package Rehber;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField giris_kullaniciadi_txt;
    private JPasswordField giris_sifre_txt;
    private JTextField kayit_kullanici_txt;
    private JPasswordField kayit_sifre_txt;
    private static String NAME;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login(NAME);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static Connection Connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/database";

            String kullaniciAdi = "root";
            String sifre = "1187";
            Connection baglanti = DriverManager.getConnection(url, kullaniciAdi, sifre);
            return baglanti;
        } catch (Exception e) {
            System.out.println("Veritabanı bağlantısı başarısız: " + e.getMessage());
            return null;
        }
    }

    public Login(String name) {
        this.NAME = name;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Telefon Rehberinize Girin!");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(221, 32, 213, 17);
        contentPane.add(lblNewLabel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 85, 567, 294);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Giriş Yap", null, panel, null);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Kullanıcı Adı");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_1.setBounds(83, 77, 91, 20);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Şifre");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_2.setBounds(83, 121, 91, 20);
        panel.add(lblNewLabel_2);

        giris_kullaniciadi_txt = new JTextField();
        giris_kullaniciadi_txt.setBounds(315, 77, 96, 19);
        panel.add(giris_kullaniciadi_txt);
        giris_kullaniciadi_txt.setColumns(10);

        giris_sifre_txt = new JPasswordField();
        giris_sifre_txt.setBounds(315, 121, 96, 19);
        panel.add(giris_sifre_txt);

        JButton Giris_btn = new JButton("Giriş Yap");
        Giris_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kullaniciadi = giris_kullaniciadi_txt.getText();
                String sifre = new String(giris_sifre_txt.getPassword());

                try {
                    Connection baglanti = Connection();
                    String sorgu = "SELECT COUNT(*) AS giris FROM accounts WHERE username = ? AND password = ?";
                    PreparedStatement statement = baglanti.prepareStatement(sorgu);
                    statement.setString(1, kullaniciadi);
                    statement.setString(2, sifre);
                    ResultSet sonuc = statement.executeQuery();
                    if (sonuc.next()) {
                        int girisSayisi = sonuc.getInt("giris");
                        if (girisSayisi > 0) {
                            JOptionPane.showMessageDialog(null, "Giriş başarılı!");
                            setVisible(false);

                            HomePage homePage = new HomePage(kullaniciadi);
                            homePage.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Kullanıcı adı veya şifre yanlış!");
                        }
                    }
                    statement.close();

                } catch (Exception ex) {
                    System.out.println("Veritabanı hatası: " + ex.getMessage());
                }
            }
        });

        Giris_btn.setBounds(215, 191, 91, 21);
        panel.add(Giris_btn);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Kayıt Ol", null, panel_1, null);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBounds(0, 0, 562, 267);
        panel_1.add(panel_2);

        JLabel lblNewLabel_1_1 = new JLabel("Kullanıcı Adı");
        lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(83, 77, 91, 20);
        panel_2.add(lblNewLabel_1_1);

        JLabel lblNewLabel_2_1 = new JLabel("Şifre");
        lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel_2_1.setBounds(83, 121, 91, 20);
        panel_2.add(lblNewLabel_2_1);

        kayit_kullanici_txt = new JTextField();
        kayit_kullanici_txt.setColumns(10);
        kayit_kullanici_txt.setBounds(315, 77, 96, 19);
        panel_2.add(kayit_kullanici_txt);

        kayit_sifre_txt = new JPasswordField();
        kayit_sifre_txt.setColumns(10);
        kayit_sifre_txt.setBounds(315, 121, 96, 19);
        panel_2.add(kayit_sifre_txt);

        JButton Kayit_btn = new JButton("Kayıt Ol");
        Kayit_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String kullaniciAdi = kayit_kullanici_txt.getText();
            	String sifre = new String(kayit_sifre_txt.getPassword());

            	try {
            	    Connection baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "1187");

            	    // Veritabanı oluşturuldu
            	    DatabaseMetaData meta = baglanti.getMetaData();
            	    ResultSet rs = meta.getCatalogs();
            	    boolean kullaniciVar = false;
            	    while (rs.next()) {
            	        String dbName = rs.getString(1);
            	        if (dbName.equals(kullaniciAdi)) {
            	            kullaniciVar = true;
            	            break;
            	        }
            	    }
            	    rs.close();

            	    if (kullaniciVar) {
            	        JOptionPane.showMessageDialog(null, "Bu kullanıcı mevcut. Başka bir isim giriniz.", "Dikkat!", JOptionPane.ERROR_MESSAGE);
            	    } else {
            	        String sorgu = "CREATE DATABASE " + kullaniciAdi;
            	        Statement statement = baglanti.createStatement();
            	        statement.executeUpdate(sorgu);

            	      
            	        String tabloSorgu = "CREATE TABLE " + kullaniciAdi + ".rehberim (" +
            	                "isim VARCHAR(255)," +
            	                "telefon VARCHAR(20)," +
            	                "resim_ad VARCHAR(255)," +
            	                "resim_yol VARCHAR(255)" +
            	                ")";
            	        statement.executeUpdate(tabloSorgu);

            	      
            	        String accountsSorgu = "INSERT INTO accounts (username, password) VALUES (?, ?)";
            	        PreparedStatement preparedStatement = baglanti.prepareStatement(accountsSorgu);
            	        preparedStatement.setString(1, kullaniciAdi);
            	        preparedStatement.setString(2, sifre);
            	        preparedStatement.executeUpdate();

            	        JOptionPane.showMessageDialog(null, "Kayıt Başarılı. Ana Sayfaya yönlendiriliyorsunuz.");

            	        setVisible(false);
            	        statement.close();
            	        HomePage homePage = new HomePage(kullaniciAdi);
            	        homePage.setVisible(true);
            	    }

            	    baglanti.close();
            	} catch (SQLException ex) {
            	    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            	}

            }
        });

        Kayit_btn.setBounds(215, 191, 85, 21);
        panel_2.add(Kayit_btn);

        JLabel resim = new JLabel(new ImageIcon(getClass().getResource("resim.jpg")));
        resim.setBounds(56, 0, 123, 75);
        contentPane.add(resim);
        setSize(702, 501);

        setResizable(false);
    }
}
