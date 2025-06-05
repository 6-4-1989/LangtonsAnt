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
    private GridCharacteristics[][] gridCharacteristics = new GridCharacteristics[48][48];
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
    private void drawCharacter(Graphics g)
    {
        for (int y = 0; y < 48; y++)
        {
            for (int x = 0; x < 48; x++)
            {
                if (!gridCharacteristics[y][x].getColor().equals("white")) {
                    Color c = convertToRgb(gridCharacteristics[y][x].getColor());
                    g.setColor(c);
                    g.fillRect(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
                    System.out.println(x * 16 + "," + y * 16);
                }
            }
        }

        int i = 0;
        for (CoordData coordData : coordData)
        {
            g.drawImage(imageChoices.get(i), coordData.getX(), coordData.getY(), spriteWidth, spriteHeight, null);
            i++;
        }
    }
    private Color convertToRgb(String color)
    {
        int r = 0, gSpec = 0, b = 0;
        switch (color)
        {
            case "Blue" -> {
                b = 255;
                break;
            }
            case "Green" -> {
                gSpec = 255;
                break;
            }
            case "Red" -> {
                r = 255;
                break;
            }
        }
        return new Color(r, gSpec, b);
    }

    private void UpdateDirection() throws ArrayIndexOutOfBoundsException
    {
        int i = 0;
        for (CoordData coordData : coordData)
        {
            if (coordData.getX() < 0 || coordData.getX() >= 768 || coordData.getY() < 0 || coordData.getY() >= 768)
            {
                JOptionPane.showMessageDialog(null, "Game Over");
                timer.stop();
            }
            if (gridCharacteristics[coordData.getY() / 16][coordData.getX() / 16].getColor().equals("white"))
            {
                gridCharacteristics[coordData.getY() / 16][coordData.getX() / 16] =
                        new GridCharacteristics(newAnt.get(i).getColor());
                updateAntWhite(coordData.getAntDirection(), coordData);
                int gridX = coordData.getX() / 16;
                int gridY = coordData.getY() / 16;
                System.out.println("Ant " + i + ": pixelPos(" + coordData.getX() + "," + coordData.getY() + ") -> gridPos(" + gridX + "," + gridY + ")");
            }
            else
            {
                gridCharacteristics[coordData.getY() / 16][coordData.getX() / 16] =
                        new GridCharacteristics("white");
                updateAntBlack(coordData.getAntDirection(), coordData);
                int gridX = coordData.getX() / 16;
                int gridY = coordData.getY() / 16;
                System.out.println("Ant " + i + ": pixelPos(" + coordData.getX() + "," + coordData.getY() + ") -> gridPos(" + gridX + "," + gridY + ")");
            }
            i++;
        }
    }
    private void updateAntWhite(int antDirection, CoordData coordData)
    {
        switch (antDirection)
        {
            case 0 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(3);
                coordData.setY(coordData.getY() + 16);
                System.out.println("90 clockwise down" + coordData.getAntDirection());
            }
            case 1 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(2);
                coordData.setY(coordData.getY() - 16);
                System.out.println("90 clockwise up" + coordData.getAntDirection());
            }
            case 2 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(0);
                coordData.setX(coordData.getX() + 16);
                System.out.println("90 clockwise right" + coordData.getAntDirection());
            }
            case 3 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(1);
                coordData.setX(coordData.getX() - 16);
                System.out.println("90 clockwise left" + coordData.getAntDirection());
            }
        }
    }
    private void updateAntBlack(int antDirection, CoordData coordData) {
        switch (antDirection) {
            case 0 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(2);
                coordData.setY(coordData.getY() - 16);
                System.out.println("90 cc up"  + coordData.getAntDirection());
            }
            case 1 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(3);
                coordData.setY(coordData.getY() + 16);
                System.out.println("90 cc down" + coordData.getAntDirection());
            }
            case 2 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(1);
                coordData.setX(coordData.getX() - 16);
                System.out.println("90 cc left" + coordData.getAntDirection());
            }
            case 3 -> {
                System.out.println(antDirection);
                coordData.setAntDirection(0);
                coordData.setX(coordData.getX() + 16);
                System.out.println("90 cc right"  + coordData.getAntDirection());
            }
        }
    }

    public void pauseGame()
    {
        timer.stop();
    }

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
            repaint();
        }
    }
}

