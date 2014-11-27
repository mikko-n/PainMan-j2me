/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

/**
 * Class defining a rectangle
 *   
 * @author NIM
 */
public class Rectangle {    
    
    private int x;
    private int y;
    private int height;
    private int width;

    /**
     * Constructor.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Check whether a point is inside the rectangle
     */
    public boolean contains(int x, int y) {
        boolean ret = false;
        
        if (x >= this.x && x <= (this.x + width)) {
            if (y >= this.y && y <= this.y + height) {
                ret = true;
            }
        }

        return ret;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public int getCenterX() {
        return x + width / 2;
    }

    public int getCenterY() {
        return y + height / 2;
    }
}

