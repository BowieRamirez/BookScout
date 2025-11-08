package com.booksscout.service;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Swing frame that mirrors the reference login design.
 */
final class LoginFrame extends JFrame {
    private final transient UserService userService;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JLabel statusLabel;

    LoginFrame(UserService userService) {
        super("BooksAvailability Management System");
        this.userService = userService;
        this.emailField = new JTextField(24);
        this.passwordField = new JPasswordField(24);
        this.statusLabel = new JLabel(" ");
        initializeLayout();
    }

    private void initializeLayout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(860, 520);
        setLocationRelativeTo(null);

        var content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        setContentPane(content);

        content.add(buildArtPanel());
        content.add(buildFormPanel());
    }

    private JComponent buildArtPanel() {
        var panel = new GradientPanel();
        panel.setPreferredSize(new Dimension(430, 520));
        panel.setLayout(new GridBagLayout());

        var title = new JLabel("BooksAvailability");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        var subtitle = new JLabel("MANAGEMENT SYSTEM");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(new Color(225, 232, 255));

        var welcome = new JLabel("Welcome back, Readers!");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcome.setForeground(Color.WHITE);

        var grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(0, 0, 16, 0);
        panel.add(createBookIcon(), grid);

        grid.gridy++;
        panel.add(title, grid);

        grid.gridy++;
        panel.add(subtitle, grid);

        grid.gridy++;
        grid.insets = new Insets(32, 0, 0, 0);
        panel.add(welcome, grid);

        return panel;
    }

    private JComponent buildFormPanel() {
        var panel = new JPanel();
        panel.setPreferredSize(new Dimension(430, 520));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(48));
        var loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        loginLabel.setAlignmentX(CENTER_ALIGNMENT);
        loginLabel.setForeground(new Color(32, 61, 144));
        panel.add(loginLabel);

        panel.add(Box.createVerticalStrut(32));
        panel.add(buildFormFields());
        panel.add(Box.createVerticalStrut(16));

        var forgotLabel = new JLabel("Forgot your password?");
        forgotLabel.setForeground(new Color(64, 99, 197));
        forgotLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        forgotLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        forgotLabel.setAlignmentX(CENTER_ALIGNMENT);
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onForgotPassword();
            }
        });
        panel.add(forgotLabel);

        panel.add(Box.createVerticalStrut(10));
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        statusLabel.setForeground(new Color(181, 42, 42));
        panel.add(statusLabel);

        panel.add(Box.createVerticalStrut(16));
        panel.add(buildActionButtons());

        return panel;
    }

    private JComponent buildFormFields() {
        var container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        var grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.WEST;
        grid.insets = new Insets(0, 0, 4, 0);

        var emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(90, 90, 90));
        container.add(emailLabel, grid);

        grid.gridy++;
        grid.insets = new Insets(0, 0, 16, 0);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setPreferredSize(new Dimension(320, 40));
        emailField.setBorder(createInputBorder());
        container.add(emailField, grid);

        grid.gridy++;
        grid.insets = new Insets(0, 0, 4, 0);
        var passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(90, 90, 90));
        container.add(passwordLabel, grid);

        grid.gridy++;
        grid.insets = new Insets(0, 0, 16, 0);
        passwordField.setPreferredSize(new Dimension(320, 40));
        passwordField.setBorder(createInputBorder());
        container.add(passwordField, grid);

        return container;
    }

    private Border createInputBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
    }

    private JComponent buildActionButtons() {
        var container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        var loginButton = new JButton("Login");
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        stylizePrimary(loginButton);
        loginButton.addActionListener(event -> onLogin());

        var registerButton = new JButton("Register");
        registerButton.setAlignmentX(CENTER_ALIGNMENT);
        stylizeSecondary(registerButton);
        registerButton.addActionListener(event -> onRegister());

        container.add(loginButton);
        container.add(Box.createVerticalStrut(12));
        container.add(registerButton);

        return container;
    }

    private void stylizePrimary(JButton button) {
        button.setBackground(new Color(66, 106, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(320, 44));
        button.setMaximumSize(new Dimension(320, 44));
    }

    private void stylizeSecondary(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(66, 106, 255));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(320, 44));
        button.setMaximumSize(new Dimension(320, 44));
        button.setBorder(BorderFactory.createLineBorder(new Color(66, 106, 255)));
    }

    private void onLogin() {
        statusLabel.setForeground(new Color(181, 42, 42));
        statusLabel.setText(" ");
        var email = emailField.getText().trim();
        var password = passwordField.getPassword();

        if (email.isEmpty()) {
            statusLabel.setText("Email is required.");
            return;
        }
        if (password.length == 0) {
            statusLabel.setText("Password is required.");
            return;
        }

        var success = userService.authenticate(email, password);
        clearSensitiveData();
        if (success) {
            dispose();
            var homeFrame = new HomeFrame(email);
            homeFrame.setVisible(true);
        } else {
            statusLabel.setText("Invalid credentials. Try again.");
        }
    }

    private void onRegister() {
        var emailField = new JTextField();
        var passwordField = new JPasswordField();
        var confirmField = new JPasswordField();

        var panel = new JPanel(new GridBagLayout());
        var grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(0, 0, 6, 6);
        grid.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Email:"), grid);
        grid.gridx = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        panel.add(emailField, grid);

        grid.gridx = 0;
        grid.gridy++;
        grid.weightx = 0;
        grid.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Password:"), grid);

        grid.gridx = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        panel.add(passwordField, grid);

        grid.gridx = 0;
        grid.gridy++;
        grid.weightx = 0;
        grid.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Confirm:"), grid);

        grid.gridx = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 1.0;
        panel.add(confirmField, grid);

        var result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Register new reader",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            var email = emailField.getText().trim();
            var password = passwordField.getPassword();
            var confirm = confirmField.getPassword();

            if (email.isEmpty() || password.length == 0 || confirm.length == 0) {
                showDialog("All fields are required.");
            } else if (!java.util.Arrays.equals(password, confirm)) {
                showDialog("Passwords do not match.");
            } else {
                try {
                    userService.register(email, password);
                    showDialog("Reader registered. You can now sign in.");
                } catch (IllegalArgumentException ex) {
                    showDialog(ex.getMessage());
                }
            }
            java.util.Arrays.fill(password, '\0');
            java.util.Arrays.fill(confirm, '\0');
        }
    }

    private void onForgotPassword() {
        var email = emailField.getText().trim();
        if (email.isEmpty()) {
            showDialog("Enter your email so we can help.");
            return;
        }
        var hint = userService.resetHint(email);
        showDialog(hint);
    }

    private void showDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "BooksAvailability", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearSensitiveData() {
        passwordField.setText("");
    }

    private JComponent createBookIcon() {
        return new JComponent() {
            private static final int SIZE = 160;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                var g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillRoundRect(10, 10, SIZE, SIZE, 24, 24);

                g2.setStroke(new BasicStroke(3f));
                g2.setColor(Color.WHITE);
                var bookWidth = SIZE - 40;
                var bookHeight = SIZE - 60;
                var x = 20;
                var y = 24;
                g2.drawRoundRect(x, y, bookWidth, bookHeight, 16, 16);
                g2.drawLine(x + bookWidth / 2, y, x + bookWidth / 2, y + bookHeight);

                g2.fillOval(x + bookWidth / 2 - 20, y + bookHeight - 4, 40, 40);
                g2.drawOval(x + bookWidth / 2 - 20, y + bookHeight - 4, 40, 40);
                g2.drawLine(x + bookWidth / 2 + 12, y + bookHeight + 22, x + bookWidth / 2 + 38, y + bookHeight + 48);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(SIZE + 20, SIZE + 20);
            }
        };
    }

    private static final class GradientPanel extends JPanel {
        private static final Color START = new Color(50, 92, 235);
        private static final Color END = new Color(28, 45, 163);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            var g2 = (Graphics2D) g;
            var paint = new GradientPaint(0, 0, START, getWidth(), getHeight(), END);
            var oldPaint = g2.getPaint();
            g2.setPaint(paint);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setPaint(oldPaint);
        }
    }
}
