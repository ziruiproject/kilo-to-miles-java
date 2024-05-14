import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class App extends JFrame {
    private JPanel panel;
    private JLabel messageLabel;
    private JTextField kiloTextField;
    private JButton calcButton;
    private final int WINDOW_WIDTH = 250;
    private final int WINDOW_HEIGHT = 100;

    private Clip clip;

    /**
     Constructor
     */

    public App() {

        // set the window title
        setTitle("Kilometer Converter");

        // set the size of the window
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // specify what happens when the close button is clicked
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // build the panel and add it to the frame
        buildPanel();

        // add the panel to the frames content pane.
        add(panel);

        // init sound file
        initSound();

        // display the window
        setVisible(true);
    }

    // init sound file
    private void initSound() {
        try {
            File soundFile = new File("src/wtf-is-kilometer.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // play sound
    private void playSound() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    // stop sound
    private void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // show dialog for result
    private void showDialog(String input, double kilos) {
        SwingUtilities.invokeLater(() -> {
            JDialog dialog = new JDialog(this, "Conversion Result", true);
            JLabel label = new JLabel(input + " miles is " + kilos + " kilometers.");
            label.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
            dialog.add(label);
            dialog.setSize(300, 100);
            dialog.setLocationRelativeTo(panel); // Show dialog relative to the panel
            dialog.setVisible(true);

            // Stop sound when dialog is closed
            stopSound();
        });
    }

    // add button, text field, etc to panel
    private void buildPanel() {

        // create a label to display instructions.
        messageLabel = new JLabel("Enter a distance "+
                "in Miles");

        // creat a text field 10 characters wide
        kiloTextField = new JTextField(10);

        // create a button with the caption "calculate"
        calcButton = new JButton("Calculate");

        // add an action listener to the button
        calcButton.addActionListener(new CalcButtonListener());

        // create a JPanel object and let the panel
        // field reference it
        panel = new JPanel();

        // define some color attributes

        // add the label, text field and button
        // components to the panel.
        panel.add(messageLabel);
        panel.add(kiloTextField);
        panel.add(calcButton);
    }

    private class CalcButtonListener implements ActionListener {

        // executed when user click the button
        public void actionPerformed(ActionEvent e) {

            final double CONVERSION = 1.609;
            String input;  // to hold user input
            double kilos;  // the number of miles

            // get the text entered by the user into the
            // text field.
            input = kiloTextField.getText();

            // conver the input to miles.
            kilos = Double.parseDouble(input) * CONVERSION;

            playSound();

            // display the result
            showDialog(input, kilos);
        }
    }

    /**
     main method
     */

    public static void main(String[] args) {
        new App();
    }
}