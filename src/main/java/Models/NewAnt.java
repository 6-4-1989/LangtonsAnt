package Models;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class NewAnt {
    private String antImage;
    private Integer crawlRate;
    private String color;
    private int ID;
        /* private ArrayList<String> colorPalettes;
    private ArrayList<String> colorPrevalence;  for the future */

    public String getAntImage() {
        return antImage;
    }

    public void setAntImage(String antImage) {
        this.antImage = antImage;
    }
    public int getCrawlRate() {
        return crawlRate;
    }
    public void setCrawlRate(int crawlRate) {
        this.crawlRate = crawlRate;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
}
