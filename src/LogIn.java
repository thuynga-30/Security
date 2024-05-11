import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	String driver ="org.postgresql.Driver";
    String url = "jdbc:postgresql://localhost:5432/BT" ;
    String user= "postgres" ;	
    String pass= "123";
    Connection con ;
    ResultSet rs;
    String username = textField.getText();
    String password = String.valueOf(passwordField.getPassword());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 846, 543);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel left = new JPanel();
		left.setBackground(new Color(255, 255, 255));
		left.setBounds(0, 0, 831, 506);
		contentPane.add(left);
		left.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("LOGIN");
		lblNewLabel_2.setForeground(new Color(59, 178, 213));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 36));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(335, 47, 167, 49);
		left.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("UserName");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_3.setBounds(209, 137, 115, 26);
		left.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_4.setBounds(209, 254, 115, 26);
		left.add(lblNewLabel_4);
		
		textField = new JTextField();
		textField.setBounds(209, 180, 388, 42);
		left.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(209, 290, 388, 42);
		left.add(passwordField);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
                    Class.forName(driver);
                    con = DriverManager.getConnection(url,user,pass);
                    String sql ="SELECT * FROM \"user\""
                            + "WHERE \"username\" = ? AND \"password\"= ?;";
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, hashPassword(password));
                    rs = statement.executeQuery();

                     if (rs.next()) {
                    	Welcome welcome = new Welcome();
                    	welcome.setVisible(true);
                    	dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect username or password!");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
				
				
			
		});
		btnNewButton.setBackground(new Color(0, 204, 255));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(335, 354, 98, 49);
		left.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Sign Up");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp signUp = new SignUp();
				signUp.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(new Color(220, 20, 60));
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnNewButton_1.setBounds(335, 425, 98, 35);
		left.add(btnNewButton_1);
	}
	
	private String hashPassword(String password) {
		 try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = digest.digest(password.getBytes());
	            StringBuilder hexString = new StringBuilder();
	            for (byte b : hash) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) hexString.append('0');
	                hexString.append(hex);
	            }
	            return hexString.toString();
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	            return password;
	        }
	    
    }
}
