import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Stopwatch");

        JLabel timeLabel = new JLabel("00:00:00", JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 40));

        JTextArea lapArea = new JTextArea();
        lapArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(lapArea);

        int[] elapsedSeconds = {0};
        ArrayList<String> lapTimes = new ArrayList<>();

        Timer timer = new Timer(1000, e -> {
            elapsedSeconds[0]++;

            timeLabel.setText(String.format("%02d:%02d:%02d",
                    elapsedSeconds[0] / 3600,
                    (elapsedSeconds[0] / 60) % 60,
                    elapsedSeconds[0] % 60));
        });

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        JButton resetButton = new JButton("Reset");
        JButton lapButton = new JButton("Lap");

        lapButton.addActionListener(e -> {
            if (timer.isRunning()) {
                String currentTime = timeLabel.getText();
                lapTimes.add(currentTime);

                lapArea.append("Lap " + lapTimes.size() + ": " + currentTime + "\n");
            } else {
                JOptionPane.showMessageDialog(frame, "Start the stopwatch first!");
            }
        });

        startButton.addActionListener(e -> {
            timer.start();
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
        });

        pauseButton.addActionListener(e -> {
            timer.stop();
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
        });

        resetButton.addActionListener(e -> {
            timer.stop();
            elapsedSeconds[0] = 0;
            timeLabel.setText("00:00:00");

            lapTimes.clear();
            lapArea.setText("");

            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(lapButton);

        frame.setSize(450, 400);
        frame.add(timeLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}