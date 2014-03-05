/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.screen;

import java.io.IOException;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import painman.PainMan;

/**
 *
 * @author NIM
 */
public class Button extends CustomItem {

    int buttonID;
    private Rectangle boundingBox;
    private ButtonListener listener;
    private boolean enabled = true;
    private boolean pressed = false;
    private boolean selectable = true;
    private boolean selected = false;
    private boolean visible = true;    
    
    Image icons;
    
    // Icon definitions, +1 for highlight
    public static final int BUTTON_SETTINGS = 0;
    //private static final int BUTTON_SETTINGS_HIGHLIGHT = 1;
    public static final int BUTTON_INFO = 2;
    //private static final int BUTTON_INFO_HIGHLIGHT = 3;
    public static final int BUTTON_EXIT = 4;    
    //private static final int BUTTON_EXIT_HIGHLIGHT = 5;    
    public static final int BUTTON_SHUTDOWN = 6;
    //private static final int BUTTON_SHUTDOWN_HIGHLIGHT = 6;
    public static final int BUTTON_APPLY = 8;
    public static final int BUTTON_TRASH = 10;
    public static final int BUTTON_ADD_ENTRY = 12;
    public static final int BUTTON_STATISTICS = 14;
    public static final int BUTTON_BACK = 16;
    public static final int BUTTON_GEAR = 18;
    public static final int BUTTON_SEARCH = 20;
    public static final int BUTTON_CANCEL = 22;
    
    public Button(String label) {
        super(label);
        boundingBox = new Rectangle(0, 0, 32, 32);
    }
    
    public Button(String label, int buttonID) {        
        this(label);
        this.buttonID = buttonID;
    }
    
    public Button(String label, int buttonID, int xpos, int ypos) {
        this(label, buttonID);
        this.boundingBox.setX(xpos);
        this.boundingBox.setY(ypos);
    }
    
    public int getID() {
        return this.buttonID;
    }
    
    public interface ButtonListener {
        void buttonClicked(Button button);
    }
    
    public void setListener(ButtonListener listener) {
        this.listener = listener;                
    }
    
    public void setPosition(int x, int y) {
        boundingBox.setX(x);
        boundingBox.setY(y);        
    }
    
    /**
     * Handle dragged events
     * @param x
     * @param y
     */
    public void pointerDragged(int x, int y) {
        pointerPressed(x, y);
    }
    
    /**
     * Handle pressed events
     * @param x
     * @param y
     */
    public void pointerPressed(int x, int y) {
        if (!enabled || !visible) {
            return;
        }        
        pressed = contains(x, y);
    }
    
    public void pointerReleased(int x, int y) {
//        PainMan.Log(this.getClass(), "pointerReleased", "Button "+this.getID()+" clicked\n"+
//                " click at x"+x+"y"+y+" button located at x"+getXPos()+"y"+getYPos());
        if (!enabled || !visible) {
            return;
        }
        if (pressed && contains(x,y)) {
            if (selectable) {
                selected = !selected;
            }
            if (listener != null) {
//                PainMan.Log(this.getClass(), "pointerReleased", "calling eventhandler");
                listener.buttonClicked(this);
            }
        }
    }
    
    /**
     * Check whether a point is inside the bounding box of the button
     * @param x
     * @param y
     */
    public boolean contains(int x, int y) {
        return boundingBox.contains(x, y);
    }
    
     public int getWidth() {
        return boundingBox.getWidth();
    }

    public int getHeight() {
        return boundingBox.getHeight();
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }
    
    public void setVisible(boolean visible) {        
        this.visible = visible;
        this.enabled = visible;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public int getXPos() {
        return boundingBox.getX();
    }

    public void setXPos(int x) {
        boundingBox.setX(x);
    }

    public int getYPos() {
        return boundingBox.getY();
    }

    public void setYPos(int y) {
        boundingBox.setY(y);
    }
    
    /**
     * @return True if button can be toggled
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * Determine whether button can be toggled between two states
     * @param selectable
     */
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * Select the button manually
     */
    public void select() {
        selectable = true;
        selected = true;
    }

    /**
     * Deselect the button manually
     */
    public void deselect() {
        selected = false;
    }

    protected boolean isPressed() {
        return pressed;
    }

    protected void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    
    protected int getMinContentWidth() {
        return 32;
    }

    protected int getMinContentHeight() {
        return 32;
    }

    protected int getPrefContentWidth(int height) {
        return 32;
    }

    protected int getPrefContentHeight(int width) {
        return 32;
    }
    
    protected void paint(Graphics g, int w, int h) {
        if (!visible) {
            return;
        }
        if (!enabled) {            
        }
        else if (pressed || selected) {
            // +1 for highlight icon
            g.drawImage(getIcon(buttonID+1), boundingBox.x, boundingBox.y, g.TOP|g.LEFT);
        }else {        
            g.drawImage(getIcon(buttonID), boundingBox.x, boundingBox.y, g.TOP|g.LEFT);
        }
    }
    
    private Image getIcon(int iconID) {        
        
        int row = (int)Math.ceil(iconID / 4);
        if (row == -1) row = 0;
        int col = (iconID)%4;
        
//        PainMan.Log(this.getClass(), "getIcon", "called for icon id "+iconID+", returning icon from r"+row+" c"+col);

        int x = 32*col;
        int y = 32*row;
        
        Image icon = Image.createImage(32, 32);        
        
        Graphics g = icon.getGraphics();
        g.drawImage(getIconImg(), -x, -y, Graphics.TOP | Graphics.LEFT);
        
        return icon;
    }
    
    private Image getIconImg() {        
        if (icons == null) {
            try {
                icons = Image.createImage("/images/icons.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return icons;
    }
    
    
    
    /**
    * Class defining a rectangle
    */
    private class Rectangle {
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

}
