/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import painman.HeatMapper;
import painman.PainMan;
import painman.Properties;
import painman.data.BodyPart;
import painman.data.DataManager;
import painman.data.Point;

/**
 *
 * @author NIM
 */
public abstract class BaseCanvas extends Canvas implements Button.ButtonListener {

    protected PainMan midlet;
    protected Image image = null;
    private BodyPart data;
    private Vector buttons;
    private CustomButtonListener listener;
    
//    boolean IS_FRONTSIDE = true;
//    protected boolean IS_LEFT;
//    public boolean ADD_DATAPOINT_MODE = false;
    public boolean NEED_IMAGE_REFRESH = true;
    public boolean REPAINT_CALLED = false;
    private long lastButtonClick = System.currentTimeMillis();
    
    private int previous_data_size = 0;
    protected Point newData = null;
    int imageHeight = 0;
    int imageWidth = 0;
    
    protected int lastPointerX = -1;
    protected int lastPointerY = -1;
    protected int translationX = 0;
    protected int translationY = 0;
    
    // gesture variables
    protected int pointerStartX = -1;
    protected int pointerStartY = -1;
    protected long gestureStartMS = 0;
    
    private int SCREEN_ID = -1;

    /**
     * Constructor
     *
     * @param midlet
     * @param screenID Screen id, see Properties for values
     */
    public BaseCanvas(PainMan midlet, int screenID) {
//        this.data = new Hashtable();
        this.midlet = midlet;
        this.SCREEN_ID = screenID;
    }

    /**
     * Sets screen id
     * @param screenID 
     */
    protected void setScreenID(int screenID) {
        this.SCREEN_ID = screenID;
        //getData().setBodyPartId(screenID);        
    }

    protected int getScreenID() {
        return SCREEN_ID;
    }

    public void setButtonListener(CustomButtonListener listener) {
        this.listener = listener;
    }
    
    /**
     * @return the data
     */
    public BodyPart getData() {
        if (data == null) {
            data = DataManager.getInstance().getScreenData(getScreenID());
        }
        return data;
    }
    
    /**
     * Adds button to screen
     * @param button 
     */
    public void addButton(Button button) {        
        if (this.buttons == null) {
            this.buttons = new Vector();
        }
        button.setListener(this);
        buttons.addElement(button);
    }

    
    /**
     * Returns a button instance from screen with specific id
     * @param buttonID See Button class for valid id:s
     * @return 
     */
    public Button getButton(int buttonID) {
        if (this.buttons == null) {
            return null;
        }
        Enumeration btns = buttons.elements();
        while (btns.hasMoreElements()) {
            Button b = (Button)btns.nextElement();
            if (b.getID() == buttonID) {
//                return (Button)buttons.elementAt(buttons.indexOf(b));
                return b;
            }
        }
        return null;
    }
        
    /**
     * Refreshes screen data model from RMS
     */
    public void refreshData() {
        PainMan.Log(this.getClass(), "refreshData", "Fetching screen datamodel from RMS");
        data = DataManager.getInstance().getScreenData(getScreenID());
    }

//     /** 
//     * Stores data model to RMS
//     */
//    public void saveData() {
//        if (data != null) {
//            int rmsId = DataManager.getInstance().saveBodyPartData(data);
//            PainMan.Log(this.getClass(),"updateData", "body part data updated, RMS id= "+rmsId);
//        }
//    }
//    /**
//     * @param data the data to set
//     */
//    public void setData(BodyPart data) {
//        this.data = data;
//    }
    
    /**
     * Screen paint method
     * @param g 
     */
    protected void paint(Graphics g) {
        // todo: two-stage refresh for some kind of user info
        if (NEED_IMAGE_REFRESH) {
            refreshData();
            image = paintBackgroundImage();
            NEED_IMAGE_REFRESH = false;            
        }
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();

        g.drawImage(image, -translationX, -translationY, g.TOP | g.LEFT);
        paintHeaders(g);
        paintButtons(g);
        
        REPAINT_CALLED = false;
    }

    /**
     * Draws buttons to screen
     * @param g 
     */
    private void paintButtons(Graphics g) {
        if (buttons != null) {
            Enumeration btns = buttons.elements();
            while (btns.hasMoreElements()) {
                Button b = (Button) btns.nextElement();
                b.paint(g, this.getWidth(), this.getHeight());
            }
        }
    }
    
    /**
     * Turns person around
     */
    public void flip() {
        Properties.IS_FRONTSIDE = !Properties.IS_FRONTSIDE;
        NEED_IMAGE_REFRESH = true;
        PainMan.Log(this.getClass(), "flip", "turned around, currently showing " + (Properties.IS_FRONTSIDE ? "front" : "back"));
    }

    /**
     * Paints touch areas to screen & highlights selected, mainly for debugging
     * purposes
     *
     * @param g
     * @param region
     * @param selected
     */
    protected void paintBodyRegion(Graphics g, Point[] region, boolean selected) {
        if (selected) {
            g.setColor(0xff0000);
        } else {
            g.setColor(0xcccccc);
        }
        int point1;
        int point2;

        for (point1 = 0; point1 < region.length; point1++) {
            point2 = point1 + 1;
            if (point2 == region.length) {
                point2 = 0;
            }
            g.drawLine(region[point1].x, region[point1].y, region[point2].x, region[point2].y);
        }
    }

    /**
     * Method to generate heatmap image from data points
     *
     * @param g
     * @param screenBgImage Image to draw on
     */
    protected void paintHeatMap(Graphics g, Image screenBgImage) {
        Hashtable screenData = getScreenSpecificData();
        if (screenData != null && !screenData.isEmpty() && (screenData.size() != previous_data_size || NEED_IMAGE_REFRESH)) {
            g.drawRGB(HeatMapper.createRGBData(screenData, screenBgImage.getWidth(), screenBgImage.getHeight()), 0, getWidth(), 0, 0, getWidth(), screenBgImage.getHeight(), true);
            previous_data_size = screenData.size();
            PainMan.Log(this.getClass(), "paintHeatMap", "finished painting heatmap with "+previous_data_size+" data points");        
        }
    }

    private Hashtable getScreenSpecificData() {
        PainMan.Log(this.getClass(), "getScreenSpecificData", "getting data points for screen id " + getScreenID() + ", " + (Properties.IS_FRONTSIDE ? "front" : "back") + "side");

        Hashtable palautus = new Hashtable();
        Enumeration dataPoints = getData().getPainPoints().elements();

        while (dataPoints.hasMoreElements()) {
            Point p = (Point) dataPoints.nextElement();
            if (p.screenID == SCREEN_ID && p.frontside == Properties.IS_FRONTSIDE) {
                // lasketaan id uudelleen
                Integer id = new Integer(p.x + p.y * getWidth());
                palautus.put(id, p);
            }

        }
        return palautus;
    }

    /**
     * Draws background image and heatmap if there is data points
     * @return 
     */
    protected Image paintBackgroundImage() {
        PainMan.Log(this.getClass(), "paintBackgroundImage", "drawing "+this.getClass().getName()+" from "+(Properties.IS_FRONTSIDE ? "front" : "back"));
        Image bg;

        if (Properties.IS_FRONTSIDE) {
            bg = getFrontImage();
        } else {
            bg = getBackImage();
        }

        // check if display area is taller than bg image
        int imgHeight = bg.getHeight();
        if (imgHeight < getHeight()) {
            imgHeight = getHeight();
        }

        Image visibleScreenImage = Image.createImage(getWidth(), imgHeight);
        Graphics g = visibleScreenImage.getGraphics();
        g.setColor(Properties.COLOR_BACKGROUND);
        g.fillRect(0, 0, visibleScreenImage.getWidth(), visibleScreenImage.getHeight());        
        
//        PainMan.Log(this.getClass(), "paintBackgroundImage", "background image size: x"+bg.getWidth()+" y"+bg.getHeight());        
        g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), Sprite.TRANS_NONE, 0,0, g.TOP|g.LEFT);

        paintHeatMap(g, visibleScreenImage);
        
//        PainMan.Log(this.getClass(), "paintBackgroundImage", "finished drawing "+(IS_LEFT ? "left" : "right")+" arm image from "+(IS_FRONTSIDE ? "palm" : "knuckle"));
        return visibleScreenImage;
    }

    /**
     * Method of handling touch screen press
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerPressed(int x, int y) {
        if (buttons != null) {
            Enumeration btns = buttons.elements();
            while (btns.hasMoreElements()) {
                Button b = (Button)btns.nextElement();
                if (b.isVisible() && b.isEnabled()) {
                    b.pointerPressed(x, y);
                    continue;
                }
            }
        }
        
        lastPointerX = pointerStartX = x;
        lastPointerY = pointerStartY = y;
        gestureStartMS = System.currentTimeMillis();
    }

    /**
     * Method of handling touch screen release
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerReleased(int x, int y) {
        
        // check if user wants to switch direction
        long gestureTime = (System.currentTimeMillis() - gestureStartMS);
//        PainMan.Log(this.getClass(), "pointerReleased", "pointer moved "+ Math.abs(pointerStartX-x)+" px in "+gestureTime+" ms");
        
        // user has swiped across 2/3 screen in less than ~300 ms, 
        // that counts to rotate gesture
        if (Math.abs(pointerStartX-x) > (getWidth()/3)*2) {
            if ( gestureTime < Properties.GESTURE_ROTATE_INTERVAL_MS) {
                flip();
                doRepaint();
            }
        }
        
        // buttons
        if (buttons != null) {
            Enumeration btns = buttons.elements();
            while (btns.hasMoreElements()) {
                Button b = (Button)btns.nextElement();
                if (b.isVisible() && b.isEnabled()) {
                    b.pointerReleased(x, y);
                    continue;
                }
            }
        }
        
        // handle datapoint set after button click because that way point doesn't change
        if (getData() != null && Properties.ADD_DATAPOINT_MODE) {
            lastPointerX = x;
            lastPointerY = y;

//            PainMan.Log(this.getClass(), "pointerRelease", "adding data point x" + x + " y" + y + ", image offset x" + translationX + " y" + translationY);
            int pointX = x + translationX;
            int pointY = y + translationY;
            newData = new Point(pointX, pointY, 20, SCREEN_ID, Properties.IS_FRONTSIDE);
            doRepaint();
        }
        
        // reset gesture variables    
        gestureStartMS = 0;
        pointerStartX = pointerStartY = -1;
    }

    /**
     * Call this in child classes instead of Canvas.repaint() method
     */
    public void doRepaint() {
        
        if (!REPAINT_CALLED) {
            REPAINT_CALLED = true;
            repaint();
        }        
    }
    
    /**
     * Callback method for actionManager
     */
    public void addDataPointConfirmed() {
        
        if (newData != null) {
            int pointX = lastPointerX + translationX;
            int pointY = lastPointerY + translationY;
            Integer id = new Integer(pointX + pointY * getWidth());
            Properties.ADD_DATAPOINT_MODE = false;
            NEED_IMAGE_REFRESH = true;

            PainMan.Log(this.getClass(), "addDataPointConfirmed", "data point add ok, switching to point editor");
            
            midlet.switchDisplay(null, midlet.Forms().getPointEditor(id, newData, true));
            
        }
    }

    /**
     * Method of handling touch screen drag
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerDragged(int x, int y) {
        scrollImage(lastPointerX - x, lastPointerY - y);
        if (buttons != null) {
            Enumeration btns = buttons.elements();
            while (btns.hasMoreElements()) {
                Button b = (Button)btns.nextElement();
                if (b.isVisible()) {
                    b.pointerDragged(x, y);
                }
            }
        }
        lastPointerX = x;
        lastPointerY = y;
    }

    /**
     * Scrolls image by the difference between previous and current position
     *
     * @param deltaX difference in coordinate x
     * @param deltaY difference in coordinate y
     */
    void scrollImage(int deltaX, int deltaY) {
        if (imageWidth > getWidth()) {
            translationX += deltaX;

            if (translationX < 0) {
                translationX = 0;
            } else if (translationX + getWidth() > imageWidth) {
                translationX = imageWidth - getWidth();
            }
        }

        if (imageHeight > getHeight()) {
            translationY += deltaY;

            if (translationY < 0) {
                translationY = 0;
            } else if (translationY + getHeight() > imageHeight) {
                translationY = imageHeight - getHeight();
            }
        }

        // no doRepaint() call here, causes problems to region identification
        repaint();
    }

    /**
     * Method for printing headers & info text to screen.
     */
    protected abstract void paintHeaders(Graphics g);

    protected Image getFrontImage() {
        return midlet.ImageUtil().getScreenImg(getScreenID(), true);
    }

    protected Image getBackImage() {
        return midlet.ImageUtil().getScreenImg(getScreenID(), false);
    }

    /**
     * Button.ButtonListener event implementation.
     * Relays event to form's buttonClickListener
     * @param button 
     */
    public void buttonClicked(Button button) {
//        PainMan.Log(this.getClass(), "buttonClicked", "Button "+button.getID()+" clicked");
        
        // filter out quick successive clicks and random event firing mayhem
        if (System.currentTimeMillis() - lastButtonClick > 50) {
            if (listener != null) {
                lastButtonClick = System.currentTimeMillis();
                listener.buttonAction(button, this);
            } else {
                PainMan.Log(this.getClass(), "buttonClicked", "Button " + button.getID() + " clicked but there was no listener attached");
            }
        } else {
            PainMan.Log(this.getClass(), "buttonClicked", "Button " + button.getID() + " clicked but too little time elapsed since last click ("+(System.currentTimeMillis()-lastButtonClick)+" ms)");
        }
    }   
    
}
