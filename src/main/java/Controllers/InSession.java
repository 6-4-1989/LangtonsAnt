package Controllers;
import Models.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InSession extends JPanel implements ActionListener //act as an intermediary between viewer + model
{
    private ArrayList<NewAnt> newAnt = new ArrayList<>();
    private ArrayList<Image> imageChoices = new ArrayList<>();
    private ArrayList<CoordData> coordData = new ArrayList<>();
    private GridCharacteristics[][] gridCharacteristics = new GridCharacteristics[49][49];
    private Timer timer;
    private int x, y;

    private final int frame = 20; //50 fps
    private final int width = 768;
    private final int height = 768;
    private final int spriteWidth = 16;
    private final int spriteHeight = 16;

    public ArrayList<NewAnt> getNewAnt() {
        return newAnt;
    }

    public void setNewAnt(ArrayList<NewAnt> newAnt) {
        this.newAnt = newAnt;
    }

    public InSession() {
        setPreferredSize(new Dimension(width, height));

        for (GridCharacteristics[] characteristics : gridCharacteristics) {
            Arrays.fill(characteristics, new GridCharacteristics("white"));
        }
        this.timer = new Timer(this.frame, this); //Gameloop
        this.timer.start();
        System.out.println("Timer started");
    }

    private void drawAnt(NewAnt newAnt) {
        System.out.println("New Ant");
        x = spriteWidth * (26 - newAnt.getID());
        y = spriteHeight * (24 + newAnt.getID());
        coordData.add(new CoordData(spriteWidth, spriteHeight, x, y));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacter(g);
    }
    private void drawCharacter(Graphics g) {

        int i = 0;
        for (CoordData coordData : coordData)
        {
            if (gridCharacteristics[x / 16][y / 16].getColor().equals("white"))
            {
                int r = 0, gSpec = 0, b = 0;
                switch (newAnt.get(i).getColor())
                {
                    case "Blue" -> b = 255;
                    case "Green" -> gSpec = 255;
                    case "Red" -> r = 255;
                }
                Color c = new Color(r, gSpec, b);
                g.setColor(c);
                g.fillRect(x, y, spriteWidth, spriteHeight);
                gridCharacteristics[x / 16][y / 16].setColor(newAnt.get(i).getColor());
            }
            else
            {
                g.setColor(Color.WHITE);
                g.fillRect(x, y, spriteWidth, spriteHeight);
                gridCharacteristics[x / 16][y / 16].setColor("white");
            }


            g.drawImage(imageChoices.get(i), coordData.getX(), coordData.getY(), spriteWidth, spriteHeight, null);
            i++;
        }
    }

    private void UpdateDirection()
    {
        int i = 0;
        for (CoordData coordData : coordData)
        {
            if (coordData.getX() < 0 || coordData.getX() >= 768 || coordData.getY() < 0 || coordData.getY() >= 768)
            {
                JOptionPane.showMessageDialog(null, "Game Over");
                timer.stop();
            }
            if (gridCharacteristics[x / 16][y / 16].getColor().equals("white"))
            {
                x = coordData.getX();
                y = coordData.getY();
                gridCharacteristics[x / 16][y / 16].setColor(newAnt.get(i).getColor());
                updateAntWhite(coordData.getAntDirection(), coordData);
            }
            else
            {
                x = coordData.getX();
                y = coordData.getY();
                gridCharacteristics[x / 16][y / 16].setColor("white");
                updateAntBlack(coordData.getAntDirection(), coordData);
            }
            i++;
        }
    }
    private void updateAntWhite(int antDirection, CoordData coordData)
    {
        switch (antDirection)
        {
            case 0 -> {
                coordData.setAntDirection(3);
                coordData.setY(coordData.getY() + 16);
            }
            case 1 -> {
                coordData.setAntDirection(2);
                coordData.setY(coordData.getY() - 16);
            }
            case 2 -> {
                coordData.setAntDirection(0);
                coordData.setX(coordData.getX() + 16);
            }
            case 3 -> {
                coordData.setAntDirection(1);
                coordData.setX(coordData.getX() - 16);
            }
        }
    }
    private void updateAntBlack(int antDirection, CoordData coordData) {
        switch (antDirection) {
            case 0 -> {
                coordData.setAntDirection(2);
                coordData.setY(coordData.getY() - 16);
            }
            case 1 -> {
                coordData.setAntDirection(3);
                coordData.setY(coordData.getY() + 16);
            }
            case 2 -> {
                coordData.setAntDirection(1);
                coordData.setX(coordData.getX() - 16);
            }
            case 3 -> {
                coordData.setAntDirection(0);
                coordData.setX(coordData.getX() + 16);
            }
        }
    }
        /* mb I nested too hard here (all of this is slop from misreading)
        for (int i = 0; i < coordData.size(); i++) {
            if (doesRandValueWork(getRndValue(), i,  coordData.get(i)))
            {
                switch (i)
                {
                    case 0 -> coordData.get(i).setX(coordData.get(i).getX() + 16);
                    case 1 -> coordData.get(i).setX(coordData.get(i).getX() - 16);
                    case 2 -> coordData.get(i).setY(coordData.get(i).getY() + 16);
                    case 3 -> coordData.get(i).setY(coordData.get(i).getY() - 16);
                }
            }
            else
            {
                try
                {
                    i--;
                }
                catch (Exception e)
                {
                    i = 0;
                }
            }
        }

        for (CoordData coordData : coordData)
        {
            if (coordData.getX() >= 768 || coordData.getX() < 0 || coordData.getY() >= 768 || coordData.getY() < 0)
            {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Simulation Complete!");
            }
        }
    }
    private int getRndValue()
    {
        Random rand = new Random();
        return rand.nextInt(4);
    }
    private boolean doesRandValueWork(int randValue, int index, CoordData coord)
    {
        boolean canMove = false;

        switch (randValue)
        { //test move right
            case 0 ->  canMove = isOnSameArea(coord.getX() + 16, coord.getY(), coord.getAntDirection(), randValue);
            //test move left
            case 1 ->  canMove = isOnSameArea(coord.getX() - 16, coord.getY(), coord.getAntDirection(), randValue);
            //test move up
            case 2 -> canMove = isOnSameArea(coord.getX(), coord.getY() + 16, coord.getAntDirection(), randValue);
            //test move down
            case 3 -> canMove = isOnSameArea(coord.getX(), coord.getY() - 16, coord.getAntDirection(), randValue);
        }

        return canMove == true;
    }
    private boolean isOnSameArea(int newX, int newY, int antDirection, int randValue)
    {
        for (CoordData coord : coordData) {
            if (coord.getX() == newX || coord.getY() == newY)
            {
                return true;
            } //got lazy here
            if (antDirection == randValue || antDirection + 1 == randValue && antDirection + 1 > 2 ||
                    antDirection + 1 < 1  || antDirection - 1 == randValue
                    && antDirection - 1 < 1 || antDirection - 1 > 2)
            {
                return true;
            }
        }
        return false;
    } */

    @Override
    public void actionPerformed(ActionEvent e) {

        int i = 0;
        if (!newAnt.isEmpty() && newAnt.get(0).getID() == 0)
        {
            System.out.println("New Ant");
            newAnt.get(0).setID(1);
            imageChoices.add(new ImageIcon(getClass().getResource(newAnt.get(0).getAntImage())).getImage());
            drawAnt(newAnt.get(0));
        }
        for (NewAnt aNovelAnt : newAnt) //check for new ant instances
        {
            if (i >= 1 && i < 3 && aNovelAnt.getID() == 0) //3 instances max (prevent potential surrounding)
            {
                aNovelAnt.setID(i);
                imageChoices.add(new ImageIcon(getClass().getResource(aNovelAnt.getAntImage())).getImage());
                drawAnt(aNovelAnt);
            }
            i++;
        }

        if (!newAnt.isEmpty())
        {
            UpdateDirection();
        }
        repaint();
    }
}

