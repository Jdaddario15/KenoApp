/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kenoapp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Joey D'Addario
 */
public class KenoApp {
    
    private static int numSelected = 0;
    private final static ArrayList<JButton> numberedButtons = new ArrayList<>(80);
    private final static ArrayList<Boolean> isSelected = new ArrayList<>(80); 
    
    public static void main(String[] args) {
        
        //Initial JFrame Setup
        JFrame frame = new JFrame("Keno");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(766,600);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints sections = new GridBagConstraints();
        
        //Title section
        JPanel panel = new JPanel();
        panel.setBounds(0, 0,750,100);
        JLabel title = new JLabel("Keno");
        title.setFont(new Font("Courier", Font.BOLD, 50));
        panel.add(title);
        sections.weightx = 1;
        sections.weighty = 1;
        sections.gridx = 0;
        sections.gridy = 0;
        frame.add(panel,sections);
        
        //Instructions Section
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Select up to 12 numbers!");
        instructionsPanel.add(instructions);
        sections.weightx = 0.5;
        sections.weighty = 0.5;
        sections.gridx = 0;
        sections.gridy = 1;
        frame.add(instructionsPanel,sections);
        
        
        //Keno board Section
        JPanel numberSelection = new JPanel();
        numberSelection.setBounds(0,100,750,400);
        numberSelection.setLayout(new GridLayout(8,10,2,2));
        isSelected.add(0, false);
        numberedButtons.add(0,null);
        for (int i = 1; i <= 80; i++) {
            JButton button = new JButton(""+i);
            numberedButtons.add(button);
            button.setBackground(Color.white);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setFont(new Font("Courier", Font.BOLD, 20));
            numberSelection.add(button);
            isSelected.add(false);
            button.addActionListener((ActionEvent e) -> {
                if (numSelected <= 12) {         
                    if (isSelected.get(Integer.parseInt(button.getText()))) {
                        button.setBackground(Color.white);
                        isSelected.set(Integer.parseInt(button.getText()), false);
                        numSelected--;
                    } else if (numSelected <= 11){
                       button.setBackground(Color.red); 
                       isSelected.set(Integer.parseInt(button.getText()), true);
                       numSelected++;
                    }
                }
            });
        }
        sections.weightx = 5;
        sections.weighty = 10;
        sections.fill = GridBagConstraints.BOTH;
        sections.gridx = 0;
        sections.gridy = 2;
        frame.add(numberSelection, sections);
        
        //Settings Section + picking 20 random numbers
        JPanel settings = new JPanel();
        settings.setBounds(0,200,750,400);
        ButtonGroup options = new ButtonGroup();
        JCheckBox bonus = new JCheckBox("Bonus", false);
        JButton startButton = new JButton("Start");
        JButton restartButton = new JButton("Play Again");
        JButton resetButton = new JButton("Reset");
        restartButton.setEnabled(false);
        resetButton.setEnabled(false);
        
        restartButton.addActionListener((ActionEvent e) -> {
            for (int i = 1; i < 81; i++) {
                numberedButtons.get(i).setEnabled(true);
                numberedButtons.get(i).setBackground(Color.white);
                numSelected = 0;
                isSelected.set(i, false);
            }
            startButton.setEnabled(true);
            restartButton.setEnabled(false);
        });
        
        resetButton.addActionListener((ActionEvent e) -> {
            for (int i = 1; i < 81; i++) {
                if (isSelected.get(i)) {
                    numberedButtons.get(i).setBackground(Color.red);
                } else {
                    numberedButtons.get(i).setBackground(Color.white);
                }
                numberedButtons.get(i).setEnabled(true);
            }
            startButton.setEnabled(true);
            restartButton.setEnabled(false);
            resetButton.setEnabled(false);
        });
        
        startButton.addActionListener((ActionEvent e) -> {
            int numSelectedGame = numSelected;
            int numCorrect = 0;
            Random random = new Random();
            ArrayList<Integer> generatedRandomVals = new ArrayList<>(20);
            for (int i = 1; i < 81; i++) {
                numberedButtons.get(i).setEnabled(false);
            }
            for (int i = 0; i < 20; i++) {
                int nextRandom = (int) Math.ceil(random.nextInt(80))+1;
                if (generatedRandomVals.contains(nextRandom)) {
                    i--;
                } else {
                    generatedRandomVals.add(nextRandom);
                    if (isSelected.get(nextRandom)) {
                        numberedButtons.get(nextRandom).setBackground(Color.green);
                        numCorrect++;
                    } else {
                        numberedButtons.get(nextRandom).setBackground(Color.black);
                    }
                }
            }
            double payout = getResults(numCorrect, numSelectedGame);
            System.out.println(payout);
            restartButton.setEnabled(true);
            startButton.setEnabled(false);
            resetButton.setEnabled(true);
        });
        FlowLayout options1 = new FlowLayout(FlowLayout.CENTER, 10, 10);
        options.add(bonus);
        settings.setLayout(options1);
        settings.add(bonus);
        settings.add(startButton);
        settings.add(restartButton);
        settings.add(resetButton);
        
        sections.gridx = 0;
        sections.gridy = 3;
        frame.add(settings, sections);
        
        frame.setVisible(true);
    }

    public static double getResults(int numCorrect, int numPlayed) {
        double result = 0;
        switch(numPlayed) {
            case 1:
                if (numCorrect == 1) {
                   result = 2.5;
                }
                break;
            case 2:
                if (numCorrect == 1) {
                   result = 1;
                } else if (numCorrect == 2) {
                    result = 5;
                }
                break;
            case 3:
                if (numCorrect == 2) {
                    result = 2.5;
                } else if (numCorrect == 3) {
                    result = 25;
                }
                break;
            case 4:
                if (numCorrect == 2) {
                    result = 1;
                } else if (numCorrect == 3) {
                    result = 4;
                } else if (numCorrect == 4) {
                    result = 100;
                }
                break;
            case 5:
                if (numCorrect == 3) {
                    result = 2;
                } else if (numCorrect == 4) {
                    result = 20;
                } else if (numCorrect == 5) {
                    result = 450;
                }
                break;
            case 6:
                if (numCorrect == 3) {
                    result = 1;
                } else if (numCorrect == 4) {
                    result = 7;
                } else if (numCorrect == 5) {
                    result = 50;
                } else if (numCorrect == 6) {
                    result = 1600;
                }
                break;
            case 7:
                if (numCorrect == 3) {
                    result = 1;
                } else if (numCorrect == 4) {
                    result = 3;
                } else if (numCorrect == 5) {
                    result = 20;
                } else if (numCorrect == 6) {
                    result = 100;
                } else if (numCorrect == 7) {
                    result = 5000;
                } 
            case 8:
                if (numCorrect == 4) {
                    result = 2;
                } else if (numCorrect == 5) {
                    result = 10;
                } else if (numCorrect == 6) {
                    result = 50;
                } else if (numCorrect == 7) {
                    result = 1000;
                } else if (numCorrect == 8) {
                    result = 15000;
                } 
                break;
            case 9:
                if (numCorrect == 4) {
                    result = 1;
                } else if (numCorrect == 5) {
                    result = 5;
                } else if (numCorrect == 6) {
                    result = 25;
                } else if (numCorrect == 7) {
                    result = 200;
                } else if (numCorrect == 8) {
                    result = 4000;
                } else if (numCorrect == 9) {
                    result = 40000;
                }
                break;
            case 10:
                if (numCorrect == 0) {
                    result = 2;
                } else if (numCorrect == 5) {
                    result = 2;
                } else if (numCorrect == 6) {
                    result = 20;
                } else if (numCorrect == 7) {
                    result = 80;
                } else if (numCorrect == 8) {
                    result = 500;
                } else if (numCorrect == 9) {
                    result = 10000;
                } else if (numCorrect == 10) {
                    result = 100000;
                }
                break;
            case 11:
                if (numCorrect == 0) {
                    result = 2;
                } else if (numCorrect == 5) {
                    result = 1;
                } else if (numCorrect == 6) {
                    result = 10;
                } else if (numCorrect == 7) {
                    result = 50;
                } else if (numCorrect == 8) {
                    result = 250;
                } else if (numCorrect == 9) {
                    result = 1500;
                } else if (numCorrect == 10) {
                    result = 15000;
                } else if (numCorrect == 11) {
                    result = 500000;
                }
                break;
            case 12:
                if (numCorrect == 0) {
                    result = 4;
                } else if (numCorrect == 6) {
                    result = 5;
                } else if (numCorrect == 7) {
                    result = 25;
                } else if (numCorrect == 8) {
                    result = 150;
                } else if (numCorrect == 9) {
                    result = 1000;
                } else if (numCorrect == 10) {
                    result = 2500;
                } else if (numCorrect == 11) {
                    result = 25000;
                } else if (numCorrect == 12) {
                    result = 1000000;
                }
                break;           
        }
        return result;
    }
}