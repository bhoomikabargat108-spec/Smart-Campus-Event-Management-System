
package ui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("Smart Campus - Admin Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main layout
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(20, 25, 45));
        sidebar.setPreferredSize(new Dimension(230, 650));
        sidebar.setLayout(new GridLayout(4, 1, 5, 10));

        JLabel logo = new JLabel("  SMART CAMPUS");
        logo.setForeground(Color.CYAN);
        logo.setFont(new Font("Arial", Font.BOLD, 21));

        sidebar.add(logo);


        String[] menuItems = {
                "Dashboard",
                "Manage Events",
                "Manage Students"
        };

        for (String item : menuItems) {

            JButton menuButton = new JButton(item);

            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(new Color(20, 25, 45));
            menuButton.setFont(new Font("Arial", Font.BOLD, 14));
            menuButton.setFocusPainted(false);
            menuButton.setBorderPainted(false);

            menuButton.addActionListener(e -> {

                if (item.equals("Manage Events")) {

                    new EventForm().setVisible(true);

                } else if (item.equals("Manage Students")) {

                    new StudentForm().setVisible(true);

                }
            });

            sidebar.add(menuButton);
        }


        // Main content
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(240, 243, 248));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(750, 80));

        JLabel heading = new JLabel("  Admin Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 25));

        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        header.add(heading, BorderLayout.WEST);
        header.add(logoutButton, BorderLayout.EAST);

        // Dashboard welcome section
        JPanel center = new JPanel();
        center.setBackground(new Color(240, 243, 248));
        center.setLayout(new GridLayout(1, 2, 20, 20));
        center.setBorder(
                BorderFactory.createEmptyBorder(40, 30, 40, 30)
        );


        String[] cards = {
                "EVENT MANAGEMENT",
                "STUDENT MANAGEMENT"
        };

        for (String cardTitle : cards) {

            JButton cardButton = new JButton(cardTitle);

            cardButton.setFont(
                    new Font("Arial", Font.BOLD, 18)
            );

            cardButton.setForeground(new Color(20, 50, 90));
            cardButton.setBackground(Color.WHITE);
            cardButton.setFocusPainted(false);

            cardButton.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(210, 220, 235)
                    )
            );

            cardButton.addActionListener(e -> {

                if (cardTitle.equals("EVENT MANAGEMENT")) {

                    new EventForm().setVisible(true);

                } else if (cardTitle.equals("STUDENT MANAGEMENT")) {

                    new StudentForm().setVisible(true);

                }
            });

            center.add(cardButton);


            content.add(header, BorderLayout.NORTH);
            content.add(center, BorderLayout.CENTER);

            add(sidebar, BorderLayout.WEST);
            add(content, BorderLayout.CENTER);

            setVisible(true);
        }
    }

    public static void main (String[] args) {

        SwingUtilities.invokeLater(() -> {
            new AdminDashboard();
        });
    }
}
