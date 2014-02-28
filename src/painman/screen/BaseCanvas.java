/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Canvas;
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
public abstract class BaseCanvas extends Canvas {

    protected PainMan midlet;
    protected Image image = null;
    private BodyPart data;
//    boolean IS_FRONTSIDE = true;
//    protected boolean IS_LEFT;
    public boolean ADD_DATAPOINT_MODE = false;
    public boolean NEED_IMAGE_REFRESH = true;
    private int previous_data_size = 0;
    protected Point newData = null;
    int imageHeight = 0;
    int imageWidth = 0;
    protected int lastPointerX = -1;
    protected int lastPointerY = -1;
    protected int translationX = 0;
    protected int translationY = 0;
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

    protected void setScreenID(int screenID) {
        this.SCREEN_ID = screenID;
        //getData().setBodyPartId(screenID);        
    }

    protected int getScreenID() {
        return SCREEN_ID;
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
    protected void paint(Graphics g) {
        if (NEED_IMAGE_REFRESH) {
            refreshData();
            image = paintBackgroundImage();
            NEED_IMAGE_REFRESH = false;
        }
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();

        g.drawImage(image, -translationX, -translationY, g.TOP | g.LEFT);
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

    protected Image paintBackgroundImage() {
        PainMan.Log(this.getClass(), "paintBackgroundImage", "drawing "+this.getClass().getName()+" from "+(Properties.IS_FRONTSIDE ? "front" : "back"));
        Image bg;

//        if (IS_LEFT) {
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
//        PainMan.Log(this.getClass(), "paintBackgroundImage", "background image size: x"+bg.getWidth()+" y"+bg.getHeight());        
        g.drawRegion(bg, 0, 0, getWidth(), imgHeight, Sprite.TRANS_NONE, 0, 0, g.TOP | g.LEFT);

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
        lastPointerX = x;
        lastPointerY = y;
    }

    /**
     * Method of handling touch screen release
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerReleased(int x, int y) {

        if (getData() != null && ADD_DATAPOINT_MODE) {
            lastPointerX = x;
            lastPointerY = y;

//            PainMan.Log(this.getClass(), "pointerRelease", "adding data point x" + x + " y" + y + ", image offset x" + translationX + " y" + translationY);
            int pointX = x + translationX;
            int pointY = y + translationY;
            newData = new Point(pointX, pointY, 20, SCREEN_ID, Properties.IS_FRONTSIDE);
            repaint();
//            getData().addPoint(id, newData);            
//            PainMan.Log(this.getClass(), "pointerRelease", "Data point (id "+id.intValue()+") added: "+newData.toString()+", dataset size: "+getData().getDataSize());


//            refreshData();
//            repaint();
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
            ADD_DATAPOINT_MODE = !ADD_DATAPOINT_MODE;
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
}
