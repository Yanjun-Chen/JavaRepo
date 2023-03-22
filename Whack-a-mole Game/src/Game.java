import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


/**
 * Whack-a-mole Game.
 *
 */
public class Game {
    /**
     * Instance variable for firstNameLabel.
     */
    private final JButton startButton;
    /**
     * Instance variable for firstNameLabel.
     */
    private final JTextArea timeLeftTextArea;
    /**
     * Instance variable for firstNameLabel.
     */
    private final JLabel timeLeftLabel;
    /**
     * Instance variable for firstNameLabel.
     */
    private final JLabel scoreLabel;
    /**
     * Instance variable for firstNameLabel.
     */
    private final JTextArea scoreTextArea;
    /**
     * Instance variable for firstNameLabel.
     */
    private final JButton[] buttons;
    /**
     * Instance variable for firstNameLabel.
     */
    private int score;

    /**
     * Constructor.
     */
    public Game() {
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);

        // create a window
        JFrame window = new JFrame("Whack-a-mole Game");
        window.setSize(450, 450);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a container
        JPanel pane = new JPanel();

        // create a container
        JPanel controlPane = new JPanel();

        timeLeftLabel = new JLabel("Time Left:");
        timeLeftTextArea = new JTextArea(1, 8);
        timeLeftTextArea.setEnabled(false);

        startButton = new JButton("Start");
        // anonymous class that implements ActionListener

        controlPane.add(startButton);


        controlPane.add(timeLeftLabel);
        controlPane.add(timeLeftTextArea);

        scoreLabel = new JLabel("Score:");
        controlPane.add(scoreLabel);
        scoreTextArea = new JTextArea(1, 8);
        scoreTextArea.setEnabled(false);
        controlPane.add(scoreTextArea);

        pane.add(controlPane);

        JPanel buttonPane = new JPanel();
        buttons = new JButton[24];
        for (int i = 0; i < buttons.length; i++) {
            if (i % 4 == 0) {
                buttonPane = new JPanel();
            }
            buttons[i] = new JButton("   ");
            buttons[i].setBackground(Color.LIGHT_GRAY);
            buttons[i].setFont(font);
            buttons[i].setOpaque(true);
            buttonPane.add(buttons[i]);

            if (i % 4 == 0) {
                pane.add(buttonPane);
            }

        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score = 0;
                scoreTextArea.setText(String.valueOf(score));
                startButton.setEnabled(false);
                Thread start = new TimeThread(startButton, timeLeftTextArea);
                start.start();

                for (int i = 0; i < buttons.length; i++) {
                    // set every button to default state (neither walk nor stop)
                    Thread mole = new MoleThread(buttons[i], startButton);
                    mole.start();

                    ActionListener moleListener = new MoleButtonActionListener(buttons[i], startButton);
                    buttons[i].addActionListener(moleListener);
                }
            }
        });

        // set window's content pane to be the container
        window.setContentPane(pane);
        window.setVisible(true);
    }

    /**
     * Program to run example.
     *
     * @param args
     */
    public static void main(String[] args) {
        new Game();
    }

    private static class TimeThread extends Thread {
        /**
         * Instance variable for startButton.
         */
        private final JButton startButton;
        /**
         * Instance variable for timeLeftTextArea.
         */
        private final JTextArea timeLeftTextArea;
        /**
         * Instance variable for time.
         */
        private int time = 20;

        TimeThread(JButton button, JTextArea text) {
            startButton = button;
            timeLeftTextArea = text;
        }

        /**
         * Implement run method of Thread class.
         */
        @Override
        public void run() {
            // long-running task
            try {
                startButton.setVisible(false);
                while (time >= 0) {
                    synchronized (timeLeftTextArea) {
                        if (time == 0) {
                            startButton.setEnabled(true);
                        }
                        timeLeftTextArea.setText(String.valueOf(time));
                        time--;
                        if (time == -1) {
                            break;
                        }
                    }
                    sleep(1000);
                }
                startButton.setEnabled(true);
                sleep(5000);
                startButton.setVisible(true);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static class MoleThread extends Thread {
        /**
         * Instance variable for myButton.
         */
        private JButton myButton;
        /**
         * Instance variable for startButton.
         */
        private JButton startButton;
        /**
         * Instance variable for myText.
         */
        private final String myText = "Mole";
        /**
         * Instance variable for Random.
         */
        private final Random random = new Random();

        MoleThread(JButton button, JButton button1) {
            this.myButton = button;
            this.startButton = button1;
        }

        /**
         * Implement run method of Thread class.
         */
        @Override
        public void run() {
            try {
                // long-running task
                while (!startButton.isEnabled()) {
                    int sleepTime = random.nextInt(8000) + 2000;
                    Thread.sleep(sleepTime);

                    synchronized (this) {
                        if (!startButton.isEnabled()) {
                            myButton.setText(myText);
                            myButton.setBackground(Color.RED);
                        }

                        sleepTime = random.nextInt(2000) + 1000;
                        Thread.sleep(sleepTime);

                        myButton.setText("   ");
                        myButton.setBackground(Color.LIGHT_GRAY);
                    }

                    sleepTime = random.nextInt(8000) + 2000;
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
        }
    }

    private class MoleButtonActionListener implements ActionListener {
        /**
         * mole Button.
         */
        private JButton moleButton;
        /**
         * start Button.
         */
        private JButton startButton;

        private MoleButtonActionListener(JButton button1, JButton button2) {
            this.moleButton = button1;
            this.startButton = button2;
        }

        @Override
        public synchronized void actionPerformed(ActionEvent e) {
            // if the timer is zero, clicking on a button will not increment the score.
            if (!startButton.isEnabled()) {
                // If an up mole button is clicked:
                if (moleButton.getText().equals("Mole")) {
                    //  If the button is clicked multiple times when a particular mole is up, the score should not increment more than once.
                    // 1. Increment the score and display it in the score text field. Score field should not be editable.
                    score++;
                    scoreTextArea.setText(String.valueOf(score));
                    // 2. Change the button to display an indication that the mole has been hit.
                    moleButton.setText("Hit!");
                    moleButton.setBackground(Color.GREEN);
                }
            }
        }
    }

}
