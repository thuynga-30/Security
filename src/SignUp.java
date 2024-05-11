
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	String driver = "org.postgresql.Driver";
	String url = "jdbc:postgresql://localhost:5432/BT";
	String user = "postgres";
	String pass = "123";
	
	Connection con;
	Statement st;
	  String username = textField.getText();
	  String password = String.valueOf(passwordField.getPassword());

	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 846, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 832, 506);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("SIGNUP");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(59, 178, 213));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 36));
		lblNewLabel_2.setBounds(313, 39, 167, 49);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("UserName");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_3.setBounds(188, 142, 115, 26);
		panel.add(lblNewLabel_3);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(188, 185, 388, 42);
		panel.add(textField);

		JLabel lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_4.setBounds(188, 252, 115, 26);
		panel.add(lblNewLabel_4);

		passwordField = new JPasswordField();
		passwordField.setBounds(188, 288, 388, 42);
		panel.add(passwordField);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					
					con = DriverManager.getConnection(url, username, password);
					st = con.createStatement();
					String query = "INSERT INTO public.\"user\"(\"username\", \"password\") VALUES (?,?,?);";

					PreparedStatement statement = con.prepareStatement(query);
		            statement.setString(1, username);
		            statement.setString(2, hashPassword(password));
		            statement.executeUpdate();		           
		                JOptionPane.showMessageDialog(null, "done");
		           } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
			}
		});
		btnSignUp.setForeground(Color.BLACK);
		btnSignUp.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnSignUp.setBackground(new Color(0, 204, 255));
		btnSignUp.setBounds(316, 383, 98, 42);
		panel.add(btnSignUp);

		JButton btnNewButton_1 = new JButton("Log In");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogIn logIn = new LogIn();
				logIn.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setForeground(new Color(220, 20, 60));
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnNewButton_1.setBounds(316, 447, 98, 35);
		panel.add(btnNewButton_1);
		
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
