package com.booksscout.service;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Homepage for BooksScout after successful login.
 */
final class HomeFrame extends JFrame {
    // Royal blue and light gray color palette
    private static final Color ROYAL_BLUE = new Color(65, 105, 225);
    private static final Color ACCENT_PURPLE = new Color(138, 123, 195);
    private static final Color LIGHT_GRAY = new Color(240, 240, 245);
    private static final Color MEDIUM_GRAY = new Color(220, 220, 230);
    private static final Color TEXT_DARK = new Color(60, 60, 70);
    private static final Color CARD_WHITE = new Color(255, 255, 255);

    HomeFrame(String userEmail) {
        super("BooksScout - Home");
        initializeLayout();
    }

    private void initializeLayout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(LIGHT_GRAY);

        add(buildSidebar(), BorderLayout.WEST);
        add(buildMainContent(), BorderLayout.CENTER);
        add(buildChatPanel(), BorderLayout.EAST);
    }

    private JComponent buildSidebar() {
        var sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(CARD_WHITE);
        sidebar.setPreferredSize(new Dimension(80, 800));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, MEDIUM_GRAY));

        sidebar.add(Box.createVerticalStrut(20));

        // Logo
        var logo = createIconButton("📚", "Home", ROYAL_BLUE);
        sidebar.add(logo);

        sidebar.add(Box.createVerticalStrut(30));

        // Navigation icons
        var menuIcon = createIconButton("☰", "Menu", TEXT_DARK);
        sidebar.add(menuIcon);
        sidebar.add(Box.createVerticalStrut(20));

        var starIcon = createIconButton("⭐", "Favorites", TEXT_DARK);
        sidebar.add(starIcon);
        sidebar.add(Box.createVerticalStrut(20));

        var clockIcon = createIconButton("🕐", "History", TEXT_DARK);
        sidebar.add(clockIcon);
        sidebar.add(Box.createVerticalStrut(20));

        var playIcon = createIconButton("▶", "Media", TEXT_DARK);
        sidebar.add(playIcon);
        sidebar.add(Box.createVerticalStrut(20));

        var bookmarkIcon = createIconButton("🔖", "Bookmarks", TEXT_DARK);
        sidebar.add(bookmarkIcon);

        sidebar.add(Box.createVerticalGlue());

        var userIcon = createIconButton("👤", "Profile", ROYAL_BLUE);
        sidebar.add(userIcon);
        sidebar.add(Box.createVerticalStrut(20));

        return sidebar;
    }

    private JComponent createIconButton(String emoji, String tooltip, Color fg) {
        var button = new JButton(emoji);
        button.setToolTipText(tooltip);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(60, 50));
        button.setMaximumSize(new Dimension(60, 50));
        return button;
    }

    private JComponent buildMainContent() {
        var panel = new JPanel(new BorderLayout(0, 16));
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(buildHeader(), BorderLayout.NORTH);

        var scrollPane = new JScrollPane(buildContentArea());
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JComponent buildHeader() {
        var header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);

        var searchField = new JTextField("Search for books");
        searchField.setPreferredSize(new Dimension(400, 44));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_GRAY, 1, true),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        var searchBtn = new JButton("Search");
        searchBtn.setBackground(ROYAL_BLUE);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        searchBtn.setFont(new Font("SansSerif", Font.BOLD, 14));

        var leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(searchField);
        leftPanel.add(searchBtn);

        var cartBtn = new JButton("🛒");
        cartBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        cartBtn.setFocusPainted(false);
        cartBtn.setBorderPainted(false);
        cartBtn.setContentAreaFilled(false);

        var chatBtn = new JButton("Chat");
        chatBtn.setBackground(CARD_WHITE);
        chatBtn.setForeground(TEXT_DARK);
        chatBtn.setFocusPainted(false);
        chatBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_GRAY),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));

        var rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(cartBtn);
        rightPanel.add(chatBtn);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JComponent buildContentArea() {
        var content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(LIGHT_GRAY);

        content.add(buildCategoryBar());
        content.add(Box.createVerticalStrut(24));
        content.add(buildPopularSection());
        content.add(Box.createVerticalStrut(24));
        content.add(buildBestsellersSection());
        content.add(Box.createVerticalStrut(40));

        return content;
    }

    private JComponent buildCategoryBar() {
        var bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        bar.setOpaque(false);
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        String[] categories = {"Books", "eBooks", "New", "BestSellers", "AudioBooks", "Fiction", "Romance", "BestDeals", "Novels", "Novels"};
        Color[] colors = {
            new Color(139, 69, 19),
            new Color(176, 196, 222),
            new Color(205, 92, 92),
            new Color(70, 130, 180),
            new Color(144, 238, 144),
            new Color(147, 112, 219),
            new Color(240, 128, 128),
            new Color(100, 149, 237),
            new Color(152, 251, 152),
            new Color(186, 85, 211)
        };
        String[] icons = {"📚", "📱", "📕", "📘", "📗", "📙", "📔", "📖", "📓", "📒"};

        for (int i = 0; i < categories.length; i++) {
            bar.add(createCategoryCard(icons[i], categories[i], colors[i]));
        }

        return bar;
    }

    private JComponent createCategoryCard(String icon, String label, Color bg) {
        var card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setPreferredSize(new Dimension(70, 70));
        card.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        var iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setAlignmentX(CENTER_ALIGNMENT);

        var textLabel = new JLabel(label);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        textLabel.setForeground(Color.WHITE);
        textLabel.setAlignmentX(CENTER_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(textLabel);

        return card;
    }

    private JComponent buildPopularSection() {
        var section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        var header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        var title = new JLabel("Popular");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(TEXT_DARK);

        var viewAll = new JButton("View All");
        viewAll.setForeground(ROYAL_BLUE);
        viewAll.setFont(new Font("SansSerif", Font.PLAIN, 13));
        viewAll.setFocusPainted(false);
        viewAll.setBorderPainted(false);
        viewAll.setContentAreaFilled(false);

        header.add(title, BorderLayout.WEST);
        header.add(viewAll, BorderLayout.EAST);

        section.add(header);
        section.add(Box.createVerticalStrut(16));

        var booksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        booksPanel.setOpaque(false);
        booksPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        String[] titles = {"Fairy Tale", "Never After", "Klara and the Sun", "Mist and Fury", "Hamnet"};
        String[] authors = {"Stephen King", "Stephen James", "Kazuo Ishiguro", "Sarah J. Mass", "Maggie O'Farrell"};
        Color[] bookColors = {
            new Color(70, 90, 120),
            new Color(180, 160, 200),
            new Color(220, 100, 100),
            new Color(100, 150, 130),
            new Color(80, 110, 160)
        };

        for (int i = 0; i < titles.length; i++) {
            booksPanel.add(createBookCard(titles[i], authors[i], bookColors[i]));
        }

        section.add(booksPanel);
        return section;
    }

    private JComponent createBookCard(String title, String author, Color bookColor) {
        var card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_WHITE);
        card.setPreferredSize(new Dimension(140, 280));
        card.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var bookCover = new BookCoverPanel(title, bookColor);
        bookCover.setMaximumSize(new Dimension(116, 180));
        bookCover.setAlignmentX(CENTER_ALIGNMENT);

        var titleLabel = new JLabel("<html>" + title + "</html>");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        var authorLabel = new JLabel(author);
        authorLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        authorLabel.setForeground(new Color(120, 120, 130));
        authorLabel.setAlignmentX(CENTER_ALIGNMENT);

        card.add(bookCover);
        card.add(Box.createVerticalStrut(8));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(authorLabel);

        return card;
    }

    private JComponent buildBestsellersSection() {
        var section = new JPanel(new BorderLayout(20, 0));
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        var illustration = new BestsellerIllustration();
        illustration.setPreferredSize(new Dimension(280, 280));

        var textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        var heading = new JLabel("2022 year 50 most");
        heading.setFont(new Font("SansSerif", Font.BOLD, 24));
        heading.setForeground(TEXT_DARK);
        heading.setAlignmentX(LEFT_ALIGNMENT);

        var heading2 = new JLabel("popular Bestsellers");
        heading2.setFont(new Font("SansSerif", Font.BOLD, 24));
        heading2.setForeground(TEXT_DARK);
        heading2.setAlignmentX(LEFT_ALIGNMENT);

        var desc = new JLabel("<html>List of the most interesting books<br>of the year based on customers<br>and critics reviews</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setForeground(new Color(120, 120, 130));
        desc.setAlignmentX(LEFT_ALIGNMENT);

        var viewBtn = new JButton("View All");
        viewBtn.setBackground(new Color(240, 100, 100));
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFocusPainted(false);
        viewBtn.setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));
        viewBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewBtn.setAlignmentX(LEFT_ALIGNMENT);

        textPanel.add(heading);
        textPanel.add(heading2);
        textPanel.add(Box.createVerticalStrut(16));
        textPanel.add(desc);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(viewBtn);

        var listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        listPanel.add(createListItem("🏆", "Top 50 books for kids", "Discover bestselling book series..."));
        listPanel.add(Box.createVerticalStrut(12));
        listPanel.add(createListItem("📖", "Top 50 Classic books", "Discover the most influential..."));
        listPanel.add(Box.createVerticalStrut(12));
        listPanel.add(createListItem("🎯", "Top 50 Sci-fi books", "Discover the best sci-fi books..."));

        var container = new JPanel(new BorderLayout(16, 0));
        container.setOpaque(false);
        container.add(illustration, BorderLayout.WEST);
        container.add(textPanel, BorderLayout.CENTER);
        container.add(listPanel, BorderLayout.EAST);

        section.add(container, BorderLayout.CENTER);
        return section;
    }

    private JComponent createListItem(String icon, String title, String desc) {
        var item = new JPanel(new BorderLayout(12, 4));
        item.setBackground(CARD_WHITE);
        item.setPreferredSize(new Dimension(240, 70));
        item.setMaximumSize(new Dimension(240, 70));
        item.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        var textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        var titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(TEXT_DARK);

        var descLabel = new JLabel(desc);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        descLabel.setForeground(new Color(140, 140, 150));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descLabel);

        item.add(iconLabel, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);

        return item;
    }

    private JComponent buildChatPanel() {
        var panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(320, 800));
        panel.setBackground(CARD_WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, MEDIUM_GRAY));

        var header = new JPanel(new BorderLayout());
        header.setBackground(CARD_WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        var chatTitle = new JLabel("Chat");
        chatTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        chatTitle.setForeground(TEXT_DARK);

        var menuBtn = new JButton("⋮");
        menuBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        menuBtn.setFocusPainted(false);
        menuBtn.setBorderPainted(false);
        menuBtn.setContentAreaFilled(false);

        header.add(chatTitle, BorderLayout.WEST);
        header.add(menuBtn, BorderLayout.EAST);

        var chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setBackground(CARD_WHITE);
        chatArea.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        var supportCard = createChatCard("Privacy and Support", "Get Immediate Support", LIGHT_GRAY);
        chatArea.add(supportCard);
        chatArea.add(Box.createVerticalStrut(12));

        var msg1 = createChatBubble("I'm looking for gift editions of the Harry Potter books. Do you have them?", ACCENT_PURPLE, true);
        chatArea.add(msg1);
        chatArea.add(Box.createVerticalStrut(8));

        var msg2 = createChatBubble("Good Day!", LIGHT_GRAY, false);
        chatArea.add(msg2);
        chatArea.add(Box.createVerticalStrut(8));

        var msg3 = createChatBubble("Yes, have 3 special gift editions. Unfortunately, the latest editions are not available yet.", LIGHT_GRAY, false);
        chatArea.add(msg3);
        chatArea.add(Box.createVerticalStrut(12));

        var booksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        booksPanel.setOpaque(false);
        booksPanel.add(createMiniBook(new Color(139, 69, 19)));
        booksPanel.add(createMiniBook(new Color(220, 100, 100)));
        booksPanel.add(createMiniBook(new Color(147, 112, 219)));
        chatArea.add(booksPanel);
        chatArea.add(Box.createVerticalStrut(12));

        var msg4 = createChatBubble("I think the middle one is the one I was looking for.", ACCENT_PURPLE, true);
        chatArea.add(msg4);
        chatArea.add(Box.createVerticalStrut(8));

        var msg5 = createChatBubble("Can you add it to the cart please?", ACCENT_PURPLE, true);
        chatArea.add(msg5);

        chatArea.add(Box.createVerticalGlue());

        var scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        var inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setBackground(CARD_WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));

        var messageField = new JTextField("Write a message...");
        messageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_GRAY, 1, true),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        var sendBtn = new JButton("➤");
        sendBtn.setBackground(new Color(240, 120, 140));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        sendBtn.setFocusPainted(false);
        sendBtn.setPreferredSize(new Dimension(50, 44));

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JComponent createChatCard(String title, String subtitle, Color bg) {
        var card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        var titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        titleLabel.setForeground(TEXT_DARK);

        var subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(120, 120, 130));

        var arrow = new JLabel("→");
        arrow.setFont(new Font("SansSerif", Font.BOLD, 16));
        arrow.setForeground(TEXT_DARK);

        var topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(arrow, BorderLayout.EAST);

        card.add(topPanel);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitleLabel);

        return card;
    }

    private JComponent createChatBubble(String text, Color bg, boolean isUser) {
        var bubble = new JPanel();
        bubble.setBackground(bg);
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        bubble.setMaximumSize(new Dimension(220, 200));

        var label = new JLabel("<html><body style='width: 180px'>" + text + "</body></html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(isUser ? Color.WHITE : TEXT_DARK);
        bubble.add(label);

        var container = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0));
        container.setOpaque(false);
        container.add(bubble);

        return container;
    }

    private JComponent createMiniBook(Color color) {
        var book = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                var g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, 50, 70, 8, 8);
                g2.setColor(Color.WHITE);
                g2.drawLine(10, 20, 40, 20);
                g2.drawLine(10, 28, 40, 28);
                g2.drawLine(10, 36, 30, 36);
            }
        };
        book.setPreferredSize(new Dimension(50, 70));
        book.setOpaque(false);
        return book;
    }

    private static final class BookCoverPanel extends JPanel {
        private final String title;
        private final Color bookColor;

        BookCoverPanel(String title, Color bookColor) {
            this.title = title;
            this.bookColor = bookColor;
            setPreferredSize(new Dimension(116, 180));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            var g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Book background
            g2.setColor(bookColor);
            g2.fillRoundRect(0, 0, 116, 170, 12, 12);

            // Title area
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            var metrics = g2.getFontMetrics();
            var titleWidth = metrics.stringWidth(title);
            g2.drawString(title.length() > 12 ? title.substring(0, 10) + "..." : title, 
                         (116 - titleWidth) / 2, 30);

            // Bookmark
            g2.setColor(new Color(255, 200, 100));
            int[] xPoints = {50, 58, 54, 58, 66};
            int[] yPoints = {0, 0, 12, 24, 0};
            g2.fillPolygon(xPoints, yPoints, 5);

            // Decorative lines
            g2.setColor(new Color(255, 255, 255, 80));
            g2.drawLine(15, 50, 101, 50);
            g2.drawLine(15, 60, 101, 60);
            g2.drawLine(15, 70, 80, 70);
        }
    }

    private static final class BestsellerIllustration extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            var g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Stack of books
            Color[] bookColors = {
                new Color(240, 128, 128),
                new Color(147, 112, 219),
                new Color(100, 149, 237),
                new Color(205, 92, 92),
                new Color(176, 196, 222)
            };

            int y = 180;
            for (Color color : bookColors) {
                g2.setColor(color);
                g2.fillRoundRect(40, y, 160, 30, 8, 8);
                g2.setColor(Color.WHITE);
                g2.drawLine(50, y + 15, 180, y + 15);
                y -= 32;
            }

            // Decorative elements
            g2.setColor(new Color(255, 200, 100));
            g2.fillOval(180, 80, 40, 40);
            g2.setColor(new Color(100, 149, 237));
            g2.fillOval(30, 40, 35, 35);
        }
    }
}
