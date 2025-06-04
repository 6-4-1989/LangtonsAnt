package org;
import Controllers.*;
import Models.NewAnt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*
Name: Kevin Lin
Date Due: 6/4/2025 (anniversary of Tiananmen :D)
Description: Langton's Ant but with customizable
colors, characters, and the potential for multiple
instances. Play as your favorite politician if you
want
 */

public class Main
{
    private static InSession _inSession;

    private static final int boardWidth = 768;
    private static final int boardHeight = 768;
    private static final ArrayList<NewAnt> newAnts = new ArrayList<>();

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Funny Langton's Ant :D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(boardWidth, boardHeight);

        JPanel myGamePanel = new JPanel();

        String[] characterOptions = { "Donald Trump", "Jack Morozov", "Henry Clay" };
        String[] colorOptions = { "Red", "Blue", "Green" };
        //Integer[] antRate = { 1, 5, 10};
        JComboBox<String> comboBox = new JComboBox<>(characterOptions);
        comboBox.setSelectedIndex(0);
        JComboBox<String> comboBox2 = new JComboBox<>(colorOptions);
        comboBox2.setSelectedIndex(0);
        //JComboBox<Integer> comboBox3 = new JComboBox<>(antRate);

        JButton startButton = new JButton("Start");
        startButton.setFocusable(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //I forgor to use constructor for NewAnt :C
                System.out.println("Start");
                NewAnt antInstance = new NewAnt();

                String selectedCharacter = (String) comboBox.getSelectedItem();
                String imagePath = "";

                antInstance.setColor((String) comboBox2.getSelectedItem());
                //antInstance.setCrawlRate((Integer) comboBox3.getSelectedItem());

                switch (selectedCharacter) {
                    case "Donald Trump" -> imagePath = "/donald-trump.png";
                    case "Jack Morozov" -> imagePath = "/jack-morozov.png";
                    case "Henry Clay" -> imagePath = "/henry-clay.png";
                }
                antInstance.setAntImage(imagePath);
                newAnts.add(antInstance);

                _inSession.setNewAnt(newAnts);
            }
        });
        myGamePanel.add(startButton);
        myGamePanel.add(comboBox);
        myGamePanel.add(comboBox2);
        //myGamePanel.add(comboBox3);
        frame.add(myGamePanel, BorderLayout.SOUTH);

        _inSession = new InSession();
        frame.add(_inSession);
        frame.pack();
        _inSession.requestFocus();
        frame.setVisible(true);
    }
}