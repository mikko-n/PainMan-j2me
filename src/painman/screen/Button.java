/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.screen;

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;
import painman.ImageUtil;


/**
 *
 * @author NIM
 */
public class Button extends CustomItem {

    int buttonID;
    protected Rectangle boundingBox;
    private ButtonListener listener;
        
    protected boolean enabled = true;
    protected boolean pressed = false;
    protected boolean selectable = true;
    protected boolean selected = false;
    protected boolean visible = true;    
    

    
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
    
//    /**
//     * Constructor for text only -button
//     * @param label Button label     
//     * @param text Text to show in button
//     * @param font Font for drawing the text
//     */
//    public Button(String label, String text, Font font) {        
//        this(label, BUTTON_TEXTONLY);
//        this.btnText = text;
//        if (font == null) {
//            this.fontti = Properties.MEDIUM_FONT;
//        }        
//        this.boundingBox.setHeight(font.getHeight() + 2);
//        this.boundingBox.setWidth(font.stringWidth(text) + 6);
//        this.setPreferredSize(this.boundingBox.getWidth(), this.boundingBox.getHeight());
//        painman.PainMan.Log(this.getClass(), "constructor", "New button with text: "+text);
//    }
    
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
        return boundingBox.getWidth();
    }

    protected int getMinContentHeight() {
        return 32;
    }

    protected int getPrefContentWidth(int height) {        
        return boundingBox.getWidth();
        
    }

    protected int getPrefContentHeight(int width) {
        return boundingBox.getHeight();
    }
    
    protected void paint(Graphics g, int w, int h) {
        if (!visible) {
            return;
        }
        if (!enabled) {            
        }        
        
        else if (pressed || selected) {          
            // +1 for highlight icon
            g.drawImage(ImageUtil.getIcon(buttonID+1), boundingBox.getX(), boundingBox.getY(), g.TOP|g.LEFT);
        }
        else {  
            g.drawImage(ImageUtil.getIcon(buttonID), boundingBox.getX(), boundingBox.getY(), g.TOP|g.LEFT);
        }
    }
    
   
   
}
