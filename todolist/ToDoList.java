import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoList extends JFrame {
    private DefaultListModel<Task> model;
    private JList<Task> taskList;
    private JButton addButton;

    public ToDoList() {
        setTitle("To Do List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultListModel<>();
        taskList = new JList<>(model);
        taskList.setCellRenderer(new TaskRenderer());
        taskList.setFont(new Font("Arial", Font.PLAIN, 24));
        taskList.setFixedCellHeight(50);

        addButton = new JButton("Add Task");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskText = JOptionPane.showInputDialog("Enter a new task:");
                if (taskText != null && !taskText.trim().isEmpty()) {
                    model.addElement(new Task(taskText));
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel headlineLabel = new JLabel("To Do List", SwingConstants.CENTER);
        headlineLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headlineLabel.setForeground(Color.BLACK);
        panel.add(headlineLabel, BorderLayout.NORTH);

        add(panel);

        // Add a mouse listener to handle "Done" button clicks
        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Task clickedTask = model.getElementAt(index);
                    if (e.getX() > taskList.getWidth() - 90) { // Check if "Done" button area clicked
                        model.remove(index); // Remove the task
                    }
                }
            }
        });
    }

    private class TaskRenderer extends JPanel implements ListCellRenderer<Task> {
        private JLabel taskLabel;
        private JButton doneButton;

        public TaskRenderer() {
            setLayout(new BorderLayout());
            taskLabel = new JLabel();
            taskLabel.setOpaque(true);
            taskLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            doneButton = new JButton("Done/Delete");
            doneButton.setPreferredSize(new Dimension(150, 30));
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {
            taskLabel.setText(value.getText());
            taskLabel.setBackground(index % 2 == 0 ? Color.ORANGE : Color.LIGHT_GRAY);

            // Add button styling
            removeAll(); // Clear old components
            add(taskLabel, BorderLayout.CENTER);
            add(doneButton, BorderLayout.EAST);

            return this;
        }
    }

    private class Task {
        private String text;

        public Task(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoList().setVisible(true);
            }
        });
    }
}
