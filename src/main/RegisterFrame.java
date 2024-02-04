package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    public static boolean loggedIn = false;
    public static String username;

    public RegisterFrame() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
       

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Please enter both username and password.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (saveUser(username, password)) {
                        JOptionPane.showMessageDialog(RegisterFrame.this,
                                "Registration successful!: " + username +" ",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        loggedIn = true;
                        RegisterFrame.username = username; 
                        dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(RegisterFrame.this,
                                "Failed to register user.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (checkLogin(username, password)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Login successful!: " + username+ "",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    loggedIn= true;
                    RegisterFrame.username = username; 
                    dispose();
                    
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Invalid username or password.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signUpButton);
        panel.add(loginButton);
        
        int width = 300;
        int height = 180; 
        Dimension dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
		this.setLocationRelativeTo(null);

        this.getContentPane().add(panel);
        this.pack();
    }

    private boolean saveUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    String savedUsername = parts[0];
                    if (savedUsername.equals(username)) {
                        JOptionPane.showMessageDialog(RegisterFrame.this,
                                "Username already exists.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true));
            writer.println(username + "," + password);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String savedUsername = parts[0];
                    String savedPassword = parts[1];
                    if (savedUsername.equals(username) && savedPassword.equals(password)) {
                        return true; 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
    }
    public static String getUsername() {
        return username;
    }
}
