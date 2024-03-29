package Rehber;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class HomePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String isim;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Örnek bir kullanıcı adıyla ana sayfayı başlat
					HomePage frame = new HomePage("e");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomePage(String kullaniciAdi) {
		// Kullanıcı adını isim değişkenine atayın
		this.isim = kullaniciAdi;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Hesap resmini JLabel'e ekle
		JLabel account_picture = new JLabel(new ImageIcon(getClass().getResource("account(1).png")));
		account_picture.setBounds(0, 25, 101, 87);
		contentPane.add(account_picture);
		
		// Kullanıcı adını görüntülemek için bir JLabel oluşturun
		JLabel name = new JLabel("Hoş geldiniz, " + isim + "!");
		name.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Ekle ekle = new Ekle (isim);
		name.setBounds(10, 2, 200, 13);
		contentPane.add(name);
		
		JLabel lblNewLabel_2 = new JLabel("Yapmak İstediğiniz İşlemi Seçiniz");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(203, 129, 303, 22);
		contentPane.add(lblNewLabel_2);
		
		JButton rehber_btn = new JButton(new ImageIcon(getClass().getResource("rehber(1).png")));
		rehber_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
              
                Rehber rehber = new Rehber(isim);
                rehber.setVisible(true);
			}
		});
		rehber_btn.setBounds(95, 245, 85, 75);
		contentPane.add(rehber_btn);
		
		JButton ekle_btn =  new JButton(new ImageIcon(getClass().getResource("ekle(1).png")));
		ekle_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	              
                Ekle ekle = new Ekle(isim);
                ekle.setVisible(true);
				
			}
		});
		ekle_btn.setBounds(469, 245, 85, 75);
		contentPane.add(ekle_btn);
		
		JLabel lblNewLabel = new JLabel("Rehber");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(111, 217, 69, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Yeni Kayıt");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(478, 217, 95, 13);
		contentPane.add(lblNewLabel_1);
		
		
	

	}
}
