package com.booksscout.service;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point that launches the Swing based login window.
 */
public final class LoginApp {
    private LoginApp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fall back to default look and feel when system one is unavailable.
            }
            var userService = new InMemoryUserService();
            var frame = new LoginFrame(userService);
            frame.setVisible(true);
        });
    }
}
