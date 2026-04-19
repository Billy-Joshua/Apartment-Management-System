package com.ams.view;

import com.ams.controller.AuthController;
import com.ams.model.User;
import com.ams.utils.Constants;
import com.ams.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginFrame - Main login UI for the application
 * Users enter credentials and are authenticated based on their role
 */
public class LoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel messageLabel;
    private AuthController authController;
    
    public LoginFrame() {
        super(Constants.APP_TITLE + " - Login");
        authController = new AuthController();
        initializeUI();
    }
    
    /**
     * Initialize the login UI components
     */
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        JLabel titleLabel = new JLabel(Constants.APP_TITLE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);
        
        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);
        
        // Message Label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);
        gbc.gridwidth = 1;
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 13));
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.addActionListener(new LoginActionListener());
        
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 13));
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        // Demo Credentials Info
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(240, 240, 240));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel demoLabel = new JLabel("Demo Credentials:");
        demoLabel.setFont(new Font("Arial", Font.BOLD, 11));
        infoPanel.add(demoLabel);
        infoPanel.add(new JLabel("Admin: admin / admin123"));
        infoPanel.add(new JLabel("Manager: manager1 / manager123"));
        infoPanel.add(new JLabel("Tenant: tenant1 / tenant123"));
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(infoPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    /**
     * Login action listener
     */
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            // Validate input
            if (ValidationUtils.isEmpty(username) || ValidationUtils.isEmpty(password)) {
                messageLabel.setText("Please enter username and password!");
                return;
            }
            
            // Authenticate user
            User user = authController.login(username, password);
            
            if (user != null) {
                // Successful login - open dashboard based on role
                openDashboard(user);
                dispose();
            } else {
                messageLabel.setText("Invalid username or password!");
            }
        }
    }
    
    /**
     * Open appropriate dashboard based on user role
     */
    private void openDashboard(User user) {
        if (Constants.ROLE_ADMIN.equals(user.getRole())) {
            new AdminDashboard(user).setVisible(true);
        } else if (Constants.ROLE_MANAGER.equals(user.getRole())) {
            new ManagerDashboard(user).setVisible(true);
        } else if (Constants.ROLE_TENANT.equals(user.getRole())) {
            new TenantDashboard(user).setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
