package com.ams.view;

import com.ams.controller.AuthController;
import com.ams.model.User;
import com.ams.utils.Constants;
import com.ams.utils.UITheme;
import com.ams.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginFrame - Professional login UI for the application
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
     * Initialize the login UI components with professional styling
     */
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        UITheme.stylePanel(mainPanel, UITheme.BACKGROUND);
        
        // Header Panel
        JPanel headerPanel = UITheme.createHeaderPanel(Constants.APP_TITLE);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center Panel with centered login card
        JPanel centerPanel = new JPanel();
        UITheme.stylePanel(centerPanel, UITheme.BACKGROUND);
        centerPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        
        // Login Card Panel
        JPanel loginCard = UITheme.createCardPanel();
        loginCard.setLayout(new GridBagLayout());
        loginCard.setPreferredSize(new Dimension(400, 350));
        
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;
        cardGbc.anchor = GridBagConstraints.CENTER;
        
        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Back");
        UITheme.styleLabel(welcomeLabel, UITheme.FONT_HEADING, UITheme.TEXT_PRIMARY);
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(0, UITheme.PADDING_MEDIUM, UITheme.PADDING_LARGE, UITheme.PADDING_MEDIUM);
        loginCard.add(welcomeLabel, cardGbc);
        
        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        UITheme.styleLabel(usernameLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        cardGbc.gridx = 0;
        cardGbc.gridy = 1;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM);
        loginCard.add(usernameLabel, cardGbc);
        
        // Username Field
        usernameField = new JTextField();
        UITheme.styleTextField(usernameField);
        cardGbc.gridx = 0;
        cardGbc.gridy = 2;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM);
        loginCard.add(usernameField, cardGbc);
        
        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        UITheme.styleLabel(passwordLabel, UITheme.FONT_BODY, UITheme.TEXT_PRIMARY);
        cardGbc.gridx = 0;
        cardGbc.gridy = 3;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM);
        loginCard.add(passwordLabel, cardGbc);
        
        // Password Field
        passwordField = new JPasswordField();
        UITheme.styleTextField(passwordField);
        cardGbc.gridx = 0;
        cardGbc.gridy = 4;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM);
        loginCard.add(passwordField, cardGbc);
        
        // Message Label
        messageLabel = new JLabel("");
        messageLabel.setFont(UITheme.FONT_SMALL);
        messageLabel.setForeground(UITheme.DANGER_COLOR);
        cardGbc.gridx = 0;
        cardGbc.gridy = 5;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM);
        loginCard.add(messageLabel, cardGbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        UITheme.stylePanel(buttonPanel, UITheme.SURFACE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, UITheme.PADDING_MEDIUM, 0));
        
        loginButton = new JButton("Login");
        UITheme.stylePrimaryButton(loginButton);
        loginButton.addActionListener(new LoginActionListener());
        buttonPanel.add(loginButton);
        
        clearButton = new JButton("Clear");
        UITheme.styleSecondaryButton(clearButton);
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        });
        buttonPanel.add(clearButton);
        
        cardGbc.gridx = 0;
        cardGbc.gridy = 6;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_LARGE, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM);
        loginCard.add(buttonPanel, cardGbc);
        
        // Demo Credentials Info
        JPanel infoPanel = new JPanel();
        UITheme.stylePanel(infoPanel, new Color(255, 250, 240));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.WARNING_COLOR, 1),
            BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));
        
        JLabel demoLabel = new JLabel("🔑 Demo Credentials:");
        demoLabel.setFont(UITheme.FONT_SUBHEADING);
        demoLabel.setForeground(UITheme.WARNING_COLOR);
        infoPanel.add(demoLabel);
        
        infoPanel.add(Box.createVerticalStrut(5));
        
        JLabel admin = new JLabel("Admin: admin / admin123");
        admin.setFont(UITheme.FONT_BODY);
        admin.setForeground(UITheme.TEXT_PRIMARY);
        infoPanel.add(admin);
        
        JLabel manager = new JLabel("Manager: manager1 / manager123");
        manager.setFont(UITheme.FONT_BODY);
        manager.setForeground(UITheme.TEXT_PRIMARY);
        infoPanel.add(manager);
        
        JLabel tenant = new JLabel("Tenant: tenant1 / tenant123");
        tenant.setFont(UITheme.FONT_BODY);
        tenant.setForeground(UITheme.TEXT_PRIMARY);
        infoPanel.add(tenant);
        
        cardGbc.gridx = 0;
        cardGbc.gridy = 7;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, 0, UITheme.PADDING_MEDIUM);
        loginCard.add(infoPanel, cardGbc);
        
        centerPanel.add(loginCard, gbc);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
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
