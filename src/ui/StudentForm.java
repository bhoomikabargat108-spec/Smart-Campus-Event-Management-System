
package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentForm extends JFrame {

    private JTextField idField, nameField, departmentField, emailField;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    private final StudentDAO studentDAO = new StudentDAO();

    public StudentForm() {

        setTitle("Smart Campus - Student Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel heading = new JLabel(
                "STUDENT MANAGEMENT", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(new Color(30, 100, 170));

        add(heading, BorderLayout.NORTH);

        // Input panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(
                BorderFactory.createTitledBorder("Student Details"));

        idField = new JTextField();
        nameField = new JTextField();
        departmentField = new JTextField();
        emailField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);


        // Student table
        tableModel = new DefaultTableModel(
                new String[]{"Student ID", "Name",
                        "Department", "Email"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(studentTable);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(
                BorderFactory.createTitledBorder("All Students"));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());
        refreshButton.addActionListener(e -> loadStudents());

        // Table selection
        studentTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = studentTable.getSelectedRow();

                if (row >= 0) {
                    idField.setText(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    departmentField.setText(
                            tableModel.getValueAt(row, 2).toString());
                    emailField.setText(
                            tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        loadStudents();
    }

    // ADD STUDENT
    private void addStudent() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        String email = emailField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Student ID and Name are required!");
            return;
        }

        Student student = new Student(id, name, department, email);

        if (studentDAO.addStudent(student)) {

            JOptionPane.showMessageDialog(this,
                    "Student Added Successfully!");

            clearFields();
            loadStudents();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Unable to Add Student!");
        }
    }

    // UPDATE STUDENT
    private void updateStudent() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Select a student and enter required details!");
            return;
        }

        Student student = new Student(
                id,
                name,
                departmentField.getText().trim(),
                emailField.getText().trim()
        );

        if (studentDAO.updateStudent(student)) {

            JOptionPane.showMessageDialog(this,
                    "Student Updated Successfully!");

            clearFields();
            loadStudents();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Unable to Update Student!");
        }
    }

    // DELETE STUDENT
    private void deleteStudent() {

        String id = idField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student first!");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            if (studentDAO.deleteStudent(id)) {

                JOptionPane.showMessageDialog(this,
                        "Student Deleted Successfully!");

                clearFields();
                loadStudents();

            } else {
                JOptionPane.showMessageDialog(this,
                        "Unable to Delete Student!");
            }
        }
    }

    // LOAD STUDENTS
    private void loadStudents() {

        tableModel.setRowCount(0);

        List<Student> students = studentDAO.getAllStudents();

        for (Student student : students) {

            tableModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getName(),
                    student.getDepartment(),
                    student.getEmail()
            });
        }
    }

    // CLEAR FIELDS
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        departmentField.setText("");
        emailField.setText("");

        studentTable.clearSelection();
    }

    // MAIN METHOD FOR TESTING
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new StudentForm().setVisible(true);
        });
    }
}
