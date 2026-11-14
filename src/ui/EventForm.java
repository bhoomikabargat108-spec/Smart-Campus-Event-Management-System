
package ui;

import dao.EventDAO;
import model.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventForm extends JFrame {

    private JTextField nameField, categoryField, dateField;
    private JTextField timeField, venueField, organizerField;
    private JTextField capacityField, descriptionField;

    private JTable eventTable;
    private DefaultTableModel tableModel;

    private EventDAO eventDAO = new EventDAO();
    private int selectedEventId = -1;

    public EventForm() {

        setTitle("Smart Campus - Event Management");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("EVENT MANAGEMENT", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(new Color(0, 100, 160));

        mainPanel.add(heading, BorderLayout.NORTH);

        // Form panel
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Event Details"));

        nameField = new JTextField();
        categoryField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        venueField = new JTextField();
        organizerField = new JTextField();
        capacityField = new JTextField("100");
        descriptionField = new JTextField();

        form.add(new JLabel("Event Name:"));
        form.add(nameField);

        form.add(new JLabel("Category:"));
        form.add(categoryField);

        form.add(new JLabel("Date (YYYY-MM-DD):"));
        form.add(dateField);

        form.add(new JLabel("Time (HH:MM):"));
        form.add(timeField);

        form.add(new JLabel("Venue:"));
        form.add(venueField);

        form.add(new JLabel("Organizer:"));
        form.add(organizerField);

        form.add(new JLabel("Capacity:"));
        form.add(capacityField);

        form.add(new JLabel("Description:"));
        form.add(descriptionField);

        // Buttons
        JButton addButton = new JButton("Add Event");
        JButton updateButton = new JButton("Update Event");
        JButton deleteButton = new JButton("Delete Event");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(form, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Category", "Date",
                        "Time", "Venue", "Organizer",
                        "Capacity", "Description"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);
        eventTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("All Events"));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Button actions
        addButton.addActionListener(e -> addEvent());
        updateButton.addActionListener(e -> updateEvent());
        deleteButton.addActionListener(e -> deleteEvent());
        clearButton.addActionListener(e -> clearFields());
        refreshButton.addActionListener(e -> loadEvents());

        eventTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectEvent();
            }
        });

        loadEvents();
    }

    private Event getFormEvent() {

        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();
        String venue = venueField.getText().trim();
        String organizer = organizerField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty() || date.isEmpty() || venue.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Event Name, Date and Venue are required!");
            return null;
        }

        try {
            LocalDate eventDate = LocalDate.parse(date);

            LocalTime eventTime = time.isEmpty() ? null :
                    LocalTime.parse(time);

            int capacity = capacityField.getText().trim().isEmpty()
                    ? 100 : Integer.parseInt(capacityField.getText().trim());

            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive.");
            }

            return new Event(
                    selectedEventId,
                    name,
                    category,
                    eventDate,
                    eventTime,
                    venue,
                    organizer,
                    capacity,
                    description
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date, time or capacity!\n" +
                            "Date: YYYY-MM-DD\nTime: HH:MM");
            return null;
        }
    }

    private void addEvent() {

        Event event = getFormEvent();

        if (event == null) return;

        if (eventDAO.addEvent(event)) {
            JOptionPane.showMessageDialog(this, "Event Added Successfully!");
            clearFields();
            loadEvents();
        } else {
            JOptionPane.showMessageDialog(this, "Unable to Add Event!");
        }
    }

    private void loadEvents() {

        tableModel.setRowCount(0);

        List<Event> events = eventDAO.getAllEvents();

        for (Event event : events) {

            tableModel.addRow(new Object[]{
                    event.getEventId(),
                    event.getEventName(),
                    event.getCategory(),
                    event.getEventDate(),
                    event.getEventTime(),
                    event.getVenue(),
                    event.getOrganizer(),
                    event.getCapacity(),
                    event.getDescription()
            });
        }
    }

    private void selectEvent() {

        int row = eventTable.getSelectedRow();

        if (row == -1) return;

        selectedEventId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString());

        nameField.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        categoryField.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        dateField.setText(String.valueOf(tableModel.getValueAt(row, 3)));

        Object time = tableModel.getValueAt(row, 4);
        timeField.setText(time == null ? "" : time.toString());

        venueField.setText(String.valueOf(tableModel.getValueAt(row, 5)));
        organizerField.setText(String.valueOf(tableModel.getValueAt(row, 6)));
        capacityField.setText(String.valueOf(tableModel.getValueAt(row, 7)));
        descriptionField.setText(String.valueOf(tableModel.getValueAt(row, 8)));
    }

    private void updateEvent() {

        if (selectedEventId == -1) {
            JOptionPane.showMessageDialog(this, "Select an event first!");
            return;
        }

        Event event = getFormEvent();

        if (event == null) return;

        if (eventDAO.updateEvent(event)) {
            JOptionPane.showMessageDialog(this, "Event Updated!");
            clearFields();
            loadEvents();
        } else {
            JOptionPane.showMessageDialog(this, "Update Failed!");
        }
    }

    private void deleteEvent() {

        if (selectedEventId == -1) {
            JOptionPane.showMessageDialog(this, "Select an event first!");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this, "Delete this event?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {

            if (eventDAO.deleteEvent(selectedEventId)) {
                JOptionPane.showMessageDialog(this, "Event Deleted!");
                clearFields();
                loadEvents();
            } else {
                JOptionPane.showMessageDialog(this, "Delete Failed!");
            }
        }
    }

    private void clearFields() {

        nameField.setText("");
        categoryField.setText("");
        dateField.setText("");
        timeField.setText("");
        venueField.setText("");
        organizerField.setText("");
        capacityField.setText("100");
        descriptionField.setText("");

        selectedEventId = -1;
        eventTable.clearSelection();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EventForm().setVisible(true);
        });
    }
}
