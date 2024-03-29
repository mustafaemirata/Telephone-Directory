package Rehber;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

public class Rehber extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private String isim;
    private JTextField girilen_numara_text_box;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Rehber frame = new Rehber("e");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Rehber(String isim) {
        this.isim = isim;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 706, 506);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 63, 656, 339);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Tüm Rehberim", null, panel, null);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 630, 250);
        panel.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Ara", null, panel_1, null);
        panel_1.setLayout(null);

        JLabel lblNewLabel = new JLabel("Telefon Numarasını Giriniz");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lblNewLabel.setBounds(207, 10, 228, 27);
        panel_1.add(lblNewLabel);

        girilen_numara_text_box = new JTextField();
        girilen_numara_text_box.setBounds(207, 142, 188, 19);
        panel_1.add(girilen_numara_text_box);
        girilen_numara_text_box.setColumns(10);

        JButton sorgula_btn = new JButton("Sorgula");
        sorgula_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String girilentelefon = girilen_numara_text_box.getText();

              
                new Thread(new Runnable() {
                    public void run() {
                        Connection baglanti = null;
                        PreparedStatement preparedStatement = null;

                        try {
                            baglanti = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + isim, "root",
                                    "1187");

                            String sql = "SELECT COUNT(*) FROM rehberim WHERE telefon = ?";
                            preparedStatement = baglanti.prepareStatement(sql);
                            preparedStatement.setString(1, girilentelefon);

                            
                            ResultSet rs = preparedStatement.executeQuery();

                            
                            rs.next();
                            int count = rs.getInt(1);
                            if (count > 0) {
                              
                                sql = "SELECT isim, resim_yol FROM rehberim WHERE telefon = ?";
                                preparedStatement = baglanti.prepareStatement(sql);
                                preparedStatement.setString(1, girilentelefon);
                                ResultSet resultSet = preparedStatement.executeQuery();
                                if (resultSet.next()) {
                                    String isim = resultSet.getString("isim");
                                    String resimYolu = resultSet.getString("resim_yol");

                                    ImageIcon imageIcon = new ImageIcon(resimYolu);
                                   
                                    Image image = imageIcon.getImage();
                                    Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                                    imageIcon = new ImageIcon(newImage);

                                   
                                    JOptionPane.showMessageDialog(null, "Telefon Numarası Bulundu.\nİsim: " + isim,
                                            "Sonuç", JOptionPane.INFORMATION_MESSAGE, imageIcon);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Telefon Numarası Bulundu, ancak isim bilgisi bulunamadı.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Telefon Numarası Bulunamadı.");
                            }

                            // Bağlantıyı kapat
                            baglanti.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        sorgula_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        sorgula_btn.setBounds(249, 192, 103, 27);
        panel_1.add(sorgula_btn);

 
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + isim, "root", "1187");
            Statement statement = connection.createStatement();
            String query = "SELECT isim, telefon FROM rehberim";
            ResultSet resultSet = statement.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("İsim");
            model.addColumn("Telefon");

            while (resultSet.next()) {
                Object[] row = new Object[2];
                row[0] = resultSet.getString("isim");
                row[1] = resultSet.getString("telefon");
                model.addRow(row);
            }

            table.setModel(model);

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
