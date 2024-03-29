package Rehber;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Ekle extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String kullaniciAd;
    private JTextField telefon_text;
    private JTextField adsoyad_text;
    private JLabel imageLabel; 

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ekle frame = new Ekle("isim");
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @param isim 
     */
    public Ekle(String isim) {
        this.kullaniciAd=isim;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 702, 454);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
        
        JLabel lblNewLabel_1 = new JLabel("Telefon Numarası Giriniz");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel_1.setBounds(166, 108, 158, 13);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Ad-Soyad Bilgisi Giriniz");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNewLabel_2.setBounds(166, 201, 158, 13);
        contentPane.add(lblNewLabel_2);
        
        telefon_text = new JTextField();
        telefon_text.setBounds(166, 131, 175, 19);
        contentPane.add(telefon_text);
        telefon_text.setColumns(10);
        
        adsoyad_text = new JTextField();
        adsoyad_text.setBounds(166, 224, 175, 19);
        contentPane.add(adsoyad_text);
        adsoyad_text.setColumns(10);
        
       
        imageLabel = new JLabel();
        imageLabel.setBounds(166, 160, 100, 100); 
        contentPane.add(imageLabel);
        
        JButton resimSecButton = new JButton("Resim Seç");
        resimSecButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
               
					String resimYolu = selectedFile.getPath();
					String resimAdi = selectedFile.getName();

					// Resmi JLabel'e yükle
					ImageIcon imageIcon = new ImageIcon(resimYolu);
					imageLabel.setIcon(imageIcon);

					
					Connection baglanti = null;
					PreparedStatement preparedStatement = null;

					try {
						 String telefon = telefon_text.getText();
					     String adSoyad = adsoyad_text.getText();
					    baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + kullaniciAd, "root", "1187");
					    
					    
					    String sql = "INSERT INTO rehberim (isim, telefon, resim_ad, resim_yol) VALUES (?, ?, ?, ?)";
					    preparedStatement = baglanti.prepareStatement(sql);

					    preparedStatement.setString(1, adSoyad);
					    preparedStatement.setString(2, telefon);
					    preparedStatement.setString(3, resimAdi); 
					    preparedStatement.setString(4, resimYolu); 

					   
					    int affectedRows = preparedStatement.executeUpdate();
					    if (affectedRows > 0) {
					          JOptionPane.showMessageDialog(null, "Kayıt Başarılı.");
					    } else {
					          JOptionPane.showMessageDialog(null, "Girdiğiniz veriler eklenemedi");
					    }
					} catch (SQLException ex) {
					    ex.printStackTrace();
					} finally {
					   
					    try {
					        if (preparedStatement != null) {
					            preparedStatement.close();
					        }
					        if (baglanti != null) {
					            baglanti.close();
					        }
					    } catch (SQLException ex) {
					        ex.printStackTrace();
					    }
					}
                }
            }
        });
        resimSecButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        resimSecButton.setBounds(166, 300, 120, 21);
        contentPane.add(resimSecButton);

        resimSecButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        resimSecButton.setBounds(166, 300, 120, 21);
        contentPane.add(resimSecButton);
        
        JButton Kaydet = new JButton("Kaydet");
        Kaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                  String telefon = telefon_text.getText();
                    String adSoyad = adsoyad_text.getText();

                    Connection baglanti = null;
                    PreparedStatement preparedStatement = null;

                    try {
                        
                        baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + kullaniciAd, "root", "1187");

                       
                        String sql = "INSERT INTO rehberim (isim, telefon) VALUES (?, ?)";
                        preparedStatement = baglanti.prepareStatement(sql);

                       
                        preparedStatement.setString(1, adSoyad);
                        preparedStatement.setString(2, telefon);

                 
                        int affectedRows = preparedStatement.executeUpdate();
                        if (affectedRows > 0) {
                              JOptionPane.showMessageDialog(null, "Kayıt Başarılı.");
                        } else {
                              JOptionPane.showMessageDialog(null, "Girdiğiniz veriler eklenemedi");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                      
                        try {
                            if (preparedStatement != null) {
                                preparedStatement.close();
                            }
                            if (baglanti != null) {
                                baglanti.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
            }
        });
        Kaydet.setFont(new Font("Times New Roman", Font.BOLD, 15));
        Kaydet.setBounds(193, 350, 113, 21);
        contentPane.add(Kaydet);
        
    }

}
