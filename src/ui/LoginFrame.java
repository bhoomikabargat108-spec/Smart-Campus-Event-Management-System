
package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    // Constructor
    public LoginFrame() {

        setTitle("Smart Campus Event Management System");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 25, 45));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("SMART CAMPUS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitle = new JLabel("EVENT MANAGEMENT SYSTEM");
        subtitle.setForeground(Color.CYAN);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);

        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);

        passwordField = new JPasswordField(20);

        JLabel roleLabel = new JLabel("Login As");
        roleLabel.setForeground(Color.WHITE);

        roleBox = new JComboBox<>(
                new String[]{"Admin", "Student"}
        );

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(0, 130, 220));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Subtitle
        gbc.gridy = 1;
        panel.add(subtitle, gbc);

        // Username
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        panel.add(roleBox, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        // Button Action
        loginButton.addActionListener(e -> login());

        add(panel);

        setVisible(true);
    }

    // Login Authentication
    private void login() {

        String username = usernameField.getText();

        String password = new String(
                passwordField.getPassword()
        );

        String role = (String) roleBox.getSelectedItem();

        if (username.trim().isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password!"
            );

            return;
        }

        UserDAO userDAO = new UserDAO();

        boolean valid = userDAO.authenticate(
                username,
                password,
                role
        );

        if (valid) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful! Welcome " + role
            );
            if (role.equalsIgnoreCase("Admin")) {
                AdminDashboard dashboard = new AdminDashboard();
                dashboard.setVisible(true);
                this.dispose();
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username, Password or Role!"
            );
        }
    }

    // Main Method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}
