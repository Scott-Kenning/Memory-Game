import javax.swing.border.*;
import javax.swing.*;

import java.util.Random;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.Font;
import java.awt.event.*;


import javax.swing.Timer;

public class MemoryGame extends JFrame implements ActionListener, KeyListener {

    private Timer timer;
    private JPanel contentPane;
    private JButton guessButton;
    private JButton resetButton;
    private JButton classicButton;
    private JButton streakButton;
    private JButton timedButton;
    private JButton menuButton;
    private JButton revealButton;
    private JButton reviewButton;
    private JTextField guessText;
    private JLabel classicScoreLabel;
    private JLabel resultLabel;
    private JLabel descriptionLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private JLabel answerLabel;
    private JLabel streakLabel;
    private JLabel timeLabel;
    private JLabel timedScoreLabel;
    private List<Item> list = new ArrayList<Item>();
    private List<Item> reviewList = new ArrayList<Item>();
    private String answer;
    private String description;
    private String guess;
    private int total = 0;
    private int correct = 0;
    private int streak = 0;
    private int score = 0;
    private int mode = 0;
    private int timeLeft = 59;
    private Item current;
    

    // Main Method
    public static void main(String[] args) {
        MemoryGame frame = new MemoryGame();
        frame.setVisible(true);
    }

    // Constructor Method

    public MemoryGame() {
        setGUI();
        setList();
        newItem();
        hideAll();
    }

    // Deals with all the different buttons and calls their apporpriate functions

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == guessButton) {
            guess();
        } else if(e.getSource() == resetButton) {
            reset();
        } else if(e.getSource() == classicButton) {
            classic();
        } else if(e.getSource() == streakButton) {
            streak();
        } else if(e.getSource() == timedButton) {
            timed();
        } else if(e.getSource() == menuButton) {
            menu();
        } else if(e.getSource() == revealButton) {
            reveal();
        } else if(e.getSource() == reviewButton) {
            review();
        }
    }

    // Functions for each of the buttons

    public void classic() {
        mode = 1;
        reset();
        hideMenu();
        showConstants();
        classicScoreLabel.setVisible(true);
        resetButton.setVisible(true);
        revealButton.setVisible(true);
    }

    public void streak() {
        streak = 0;
        reset();
        hideMenu();
        showConstants();
        streakLabel.setVisible(true);
        streakLabel.setText("Streak: 0");
        streakLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mode = 2;
    }

    public void timed() {
        timeLeft = 59;
        hideMenu();
        showConstants();
        timeLabel.setText("Seconds left: 60");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVisible(true);
        timedScoreLabel.setVisible(true);
        startTimer();
        mode = 3;
    }

    public void menu() {
        mode = 0;
        reset();
        hideAll();
        classicButton.setVisible(true);
        streakButton.setVisible(true);
        timedButton.setVisible(true);
    }

    public void review() {
        mode = 4;
        reset();
        setReviewList();
        reviewList.clear();
        classicScoreLabel.setText("Score: 0/" + total);
        classicScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setText("");
        revealButton.setVisible(true);
    }

    public void reveal() {
        messageLabel.setText("The answer for " + description + " was " + answer);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setText("");
        reviewList.add(current);
        list.remove(current);
        newItem();
    }
    
    // Allows the enter key to be used to submit a guess

    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            guess();
        }
    }
    public void keyTyped(KeyEvent e) {
    }

    // Starts the timer for timed mode

    private void startTimer(){
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(timeLeft < 1){
                    timer.stop();
                    if (mode == 3) {
                        timeLabel.setText("Seconds left: " + timeLeft);
                        resultLabel.setText("");
                        messageLabel.setText("Game Over! Your final score was " + score);
                        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        score = 0;
                        guessButton.setVisible(false);
                        resetButton.setVisible(true);
                    }
                }
                timeLabel.setText("Seconds left: " + timeLeft);
                timeLeft--;
            }
        });
        timer.start();
    }

    // Hides or shows various game elements

    public void hideMenu() {
        classicButton.setVisible(false);
        streakButton.setVisible(false);
        timedButton.setVisible(false);
    }

    public void hideAll() {
        guessText.setVisible(false);
        messageLabel.setVisible(false);
        classicScoreLabel.setVisible(false);
        resultLabel.setVisible(false);
        answerLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        streakLabel.setVisible(false);
        timeLabel.setVisible(false);
        resetButton.setVisible(false);
        guessButton.setVisible(false);
        menuButton.setVisible(false);
        revealButton.setVisible(false);
        reviewButton.setVisible(false);
        timedScoreLabel.setVisible(false);
    }

    public void showConstants() {
        guessText.setVisible(true);
        messageLabel.setVisible(true);
        messageLabel.setText("");
        resultLabel.setVisible(true);
        answerLabel.setVisible(true);
        descriptionLabel.setVisible(true);
        guessButton.setVisible(true);
        menuButton.setVisible(true);
    }
    
    // Resets all scores, labels, etc. also serves as the reset button's function

    public void reset() {
        total = 0;
        correct = 0;
        score = 0;
        streak = 0;
        timeLeft = 1;
        guessText.setText("");
        resultLabel.setText("");
        messageLabel.setText("");
        guessButton.setVisible(true);
        reviewButton.setVisible(false);

        if(mode == 1) {
            setList();
        }
        if(mode == 2) {
            resetButton.setVisible(false);
            resultLabel.setVisible(true);
        }
        if(mode == 3) {
            timedScoreLabel.setText("Score: 0");
            timedScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timeLabel.setText("Seconds left: 60");
            timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timeLeft = 59;
            resetButton.setVisible(false);
            startTimer();
        }
        if(mode == 4) {
            mode = 1;
        }
        if(mode != 0) {
            newItem();
        }
        if(mode == 1) {
            revealButton.setVisible(true);
        }

        
        

    }
   
    // Checks if guess matches the current product's code, deals with the result, then gets a new Item

    public void guess() {
        
        guess = guessText.getText();
        if (guess.equals(current.getAnswer())) {
            resultLabel.setText("Correct");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setText("");
            if(mode == 1) {
                correct++;
                list.remove(current);
                classicScoreLabel.setText("Score: " + correct + "/" + total);
                newItem();
            } else if(mode == 2) {
                streak++;
                streakLabel.setText("Streak: " + streak);
                streakLabel.setHorizontalAlignment(SwingConstants.CENTER);
                newItem();
            } else {
                score++;
                timedScoreLabel.setText("Score: " + score);
                timedScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
                newItem();
            }
        } else {
            resultLabel.setText("Incorrect");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
            if (mode == 1) {
                newItem();
            } else if(mode == 2) {
                messageLabel.setText("Game Over! Your final streak was " + streak);
                messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                streak = 0;
                streakLabel.setText("Streak: " + streak);
                streakLabel.setHorizontalAlignment(SwingConstants.CENTER);
                resetButton.setVisible(true);
                guessButton.setVisible(false);
                resultLabel.setVisible(false);
            } else {
                newItem();
            }
        }
        guessText.setText("");
    }
    
    // Checks if a classical game is over, then gets the next Item

    public void newItem() {
        if(mode == 1) {
            if(list.size()==0) {
                gameOver();
                return;
            }
        }

        Random rnd = new Random(System.currentTimeMillis()); //seeding random with current time
        current = list.get(rnd.nextInt(list.size()));

        answer = current.getAnswer();
        description = current.getDescription();
        descriptionLabel.setText(current.getDescription());
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Displays that a classical game has concluded. This does not reset the game

    public void gameOver() {
        if (correct == total) {
            resultLabel.setText("Congratulations! You have memorized all the items");
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            resultLabel.setText("Game over! Your final score was: " + correct + "/" + total);
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

            guessButton.setVisible(false);
            reviewButton.setVisible(true);
        }
        revealButton.setVisible(false);
    }

    // Scans items.txt file for all the Items and adds them to an ArrayList of items

    public void setList() {
        try {
            Scanner infileScanner = new Scanner(new File("items.txt"));

            list.clear();

            while(infileScanner.hasNextLine()) {
                String line = infileScanner.nextLine();
                int n = 0;
                answer = "";
                description = "";

                while(line.charAt(n) != ';') {
                    answer += line.charAt(n);
                    n++;
                }

                n++;

                while(n < line.length()) {
                    description += line.charAt(n);
                    n++;
                }

                list.add(new Item(description,answer));
                total++;
                
            }
            classicScoreLabel.setText("Score: 0/" + total);

        } catch (FileNotFoundException ex) {
			System.out.println("Error scanning file");
			System.exit(2);
		}
    }

    // Clears the main list, fills it with elements from the review list, then clears the review list

    public void setReviewList() {
        total = 0;
        list.clear();
        while(reviewList.size() != 0) {
            list.add(reviewList.remove(reviewList.size() - 1));
            total++;
        }
    }

    // Creates all the elements needed for the game

    private void setGUI() {

        setTitle("Memory Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(640,200,640,480);

        contentPane = new JPanel();
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        contentPane.setBorder(border);
        contentPane.setBackground(new Color(255, 245, 160));
        contentPane.setLayout(null);

        guessButton = new JButton("Guess");
        guessButton.setBackground(Color.WHITE);
        guessButton.setBounds(220, 270, 200, 20);
        guessButton.setFont(new Font("Verdana", Font.BOLD, 12));
        guessButton.addActionListener(this);
        contentPane.add(guessButton);

        reviewButton = new JButton("Review");
        reviewButton.setBackground(Color.WHITE);
        reviewButton.setBounds(220, 270, 200, 20);
        reviewButton.setFont(new Font("Verdana", Font.BOLD, 12));
        reviewButton.addActionListener(this);
        contentPane.add(reviewButton);

        classicButton = new JButton("Classic Mode");
        classicButton.setBackground(Color.WHITE);
        classicButton.setBounds(220, 140, 200, 40);
        classicButton.setFont(new Font("Verdana", Font.BOLD, 18));
        classicButton.addActionListener(this);
        contentPane.add(classicButton);

        streakButton = new JButton("Streak Mode");
        streakButton.setBackground(Color.WHITE);
        streakButton.setBounds(220, 200, 200, 40);
        streakButton.setFont(new Font("Verdana", Font.BOLD, 18));
        streakButton.addActionListener(this);
        contentPane.add(streakButton);

        timedButton = new JButton("Timed Mode");
        timedButton.setBackground(Color.WHITE);
        timedButton.setBounds(220, 260, 200, 40);
        timedButton.setFont(new Font("Verdana", Font.BOLD, 18));
        timedButton.addActionListener(this);
        contentPane.add(timedButton);

        resetButton = new JButton("Restart");
        resetButton.setBackground(Color.WHITE);
        resetButton.setBounds(10, 120, 100, 20);
        resetButton.setFont(new Font("Verdana", Font.BOLD, 12));
        resetButton.addActionListener(this);
        contentPane.add(resetButton);

        revealButton = new JButton("Reveal");
        revealButton.setBackground(Color.WHITE);
        revealButton.setBounds(10, 150, 100, 20);
        revealButton.setFont(new Font("Verdana", Font.BOLD, 12));
        revealButton.addActionListener(this);
        contentPane.add(revealButton);

        menuButton = new JButton("Menu");
        menuButton.setBackground(Color.WHITE);
        menuButton.setBounds(10, 90, 100, 20);
        menuButton.setFont(new Font("Verdana", Font.BOLD, 12));
        menuButton.addActionListener(this);
        contentPane.add(menuButton);

        titleLabel = new JLabel("Memory Game");
        titleLabel.setFont(new Font("Sans Serif", Font.ITALIC, 44));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(new Color(120, 0, 0));
        titleLabel.setOpaque(true);
        titleLabel.setBounds(0,0,640,80);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel);

        descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(0, 200, 400, 25);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        contentPane.add(descriptionLabel);

        answerLabel = new JLabel("Answer");
        answerLabel.setBounds(400,185,80,20);
        answerLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        answerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(answerLabel);

        resultLabel = new JLabel("");
        resultLabel.setBounds(0,300,640,20);
        resultLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        contentPane.add(resultLabel);

        messageLabel = new JLabel("");
        messageLabel.setBounds(0,320,640,20);
        messageLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        contentPane.add(messageLabel);

        classicScoreLabel = new JLabel("");
        classicScoreLabel.setBounds(420,400,100,20);
        classicScoreLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        contentPane.add(classicScoreLabel);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(270,400,100,20);
        streakLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        contentPane.add(streakLabel);

        timedScoreLabel = new JLabel("Score: 0");
        timedScoreLabel.setBounds(270,400,100,20);
        timedScoreLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        contentPane.add(timedScoreLabel);

        timeLabel = new JLabel("");
        timeLabel.setBounds(440,90,200,20);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        contentPane.add(timeLabel);

        guessText = new JTextField(50);
        guessText.setBounds(400,205,80,20);
        guessText.addKeyListener(this);
        contentPane.add(guessText);

        setContentPane(contentPane);
    }

}