package Models;
import java.awt.*;

public class CoordData
{
    private int width;
    private int height;
    private int x;
    private int y;
    private int antDirection;

    public CoordData(int width, int height, int x, int y)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.antDirection = 2;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getAntDirection() {
        return antDirection;
    }
    public void setAntDirection(int antDirection) {
        this.antDirection = antDirection;
    }
}
