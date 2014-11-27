/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author NIM
 */
public class ImageUtil {

    int screenWidth;
    int screenHeight;
    Image imgMain;
    Image imgFull;
    Image cropImage;
    
   

    /**
     * Default constructor
     */
    public ImageUtil() {
        this(240, 320);
    }
    
    /**
     * Initializes imageUtil to minimum screen size of 240x320
     * 
     * @param screenWidth 
     * @param screenHeight 
     */
    public ImageUtil (int screenWidth, int screenHeight) {                
        if (screenWidth > 240) { this.screenWidth = screenWidth; }
        else { this.screenWidth = 240; }
        
        if (screenHeight > 320) { this.screenHeight = screenHeight;}
        else { this.screenHeight = 320; }
        PainMan.Log(this.getClass(), "const("+screenWidth+","+screenHeight+")", "Imageutil ready, w"+this.screenWidth+" h"+this.screenHeight);
    }
    
    /**
     * Returns background image for screen
     * @param screenID desired screen
     * @param isFrontSide true if frontside image
     * @return 
     */
    public Image getScreenImg(int screenID, boolean isFrontSide) {
        if (screenID == Properties.SCREEN_HEAD) {
            return getImgHead(isFrontSide);
        } else if (screenID == Properties.SCREEN_LEFTARM_TORSO) {
            return getImgLeftUpperArm(isFrontSide);
        } else if (screenID == Properties.SCREEN_LEFTHAND) {
            return getImgLeftHand(isFrontSide);
        } else if (screenID == Properties.SCREEN_LEFTLEG_LOWER) {
            return getImgLeftLegLower(isFrontSide);
        } else if (screenID == Properties.SCREEN_LEFTLEG_UPPER) {
            return getImgLeftLegUpper(isFrontSide);
        } else if (screenID == Properties.SCREEN_RIGHTARM_TORSO) {
            return getImgRightUpperArm(isFrontSide);
        } else if (screenID == Properties.SCREEN_RIGHTHAND) {
            return getImgRightHand(isFrontSide);
        } else if (screenID == Properties.SCREEN_RIGHTLEG_LOWER) {
            return getImgRightLegLower(isFrontSide);
        } else if (screenID == Properties.SCREEN_RIGHTLEG_UPPER) {
            return getImgRightLegUpper(isFrontSide);
        } else if (screenID == Properties.SCREEN_MAINSCREEN) {
            return getImgMain(isFrontSide);
        }
        return null;
    }

    
    
    /**
     * Returns an initialized main screen background image
     *
     * @param isFrontSide true to return front side image
     * @return
     */
    private Image getImgMain(boolean isFrontSide) {
        try {
            // discard image to reduce heap size
            imgMain = null;
            if (isFrontSide) {                
                imgMain = Image.createImage("/images/painman-man-front-320.png");
            } else {
                imgMain = Image.createImage("/images/painman-man-back-320.png");
            }
        } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgMainFront", "Main screen frontside image init failed");
            e.printStackTrace();
        }
        return imgMain;
    }

    /**
     * Returns an initialized full body image
     *
     * @param isFrontSide true to return front side image
     * @return
     */
    private Image getImgFull(boolean isFrontSide) {
        try {
            // discard image to reduce heap size
            imgFull = null;
            if (isFrontSide) {
                imgFull = Image.createImage("/images/painman-man-front.png");
            } else {
                imgFull = Image.createImage("/images/painman-man-back.png");
            }
//          PainMan.Log(this.getClass(), "getImgFull", "image size: x"+imgFrontFull.getWidth()+" y"+imgFrontFull.getHeight());
        } catch (java.io.IOException e) {
//          PainMan.Log(this.getClass(), "getImgFull", "full image init failed");
            e.printStackTrace();
        }

        return imgFull;
    }

    /**
     * Returns screen sized image filled with background color
     *
     * @return
     */
    private Image getCropImage() {
        if (cropImage == null) {
            PainMan.Log(this.getClass(), "getCropImage", "initializing crop image to: "+screenWidth+"x"+screenHeight);
            cropImage = Image.createImage(screenWidth, screenHeight);
            Graphics g = cropImage.getGraphics();
            g.setColor(Properties.COLOR_BACKGROUND);
            g.fillRect(0, 0, cropImage.getWidth(), cropImage.getHeight());
        }
        return cropImage;
    }

    /**
     * Sets cropped image contents
     *
     * @param x
     * @param y
     * @param frontSide
     */
    private void setCropImage(int x, int y, boolean frontSide) {
        Graphics g = getCropImage().getGraphics();
        g.drawImage(getImgFull(frontSide), x, y, Graphics.TOP | Graphics.LEFT);
    }

    /**
     * Returns an initialized head and neck image
     *
     * @return
     */
    private Image getImgHead(boolean isFrontside) {
        setCropImage(-278, 0, isFrontside);
        return cropImage;
    }

    /**
     * Returns an initialized lower left leg image by cropping full frontal
     * image
     *
     * @return
     */
    private Image getImgLeftLegLower(boolean isFrontside) {
        setCropImage(-156, -735, isFrontside);
        PainMan.Log(this.getClass(), "getImgLeftLegLower", "Left lower leg " + (isFrontside ? "front" : "back") + " image init ok");
        return cropImage;
    }

    /**
     * Returns an initialized lower right leg image
     *
     * @return
     */
    private Image getImgRightLegLower(boolean isFrontside) {
        setCropImage(-399, -735, isFrontside);
        PainMan.Log(this.getClass(), "getImgRightLegLower", "Right lower leg " + (isFrontside ? "front" : "back") + " image init ok");
        return cropImage;
    }

    /**
     * Returns an initialized right upper leg image
     *
     * @param isFrontside true for front side image
     * @return
     */
    private Image getImgRightLegUpper(boolean isFrontside) {
        setCropImage(-399, -445, isFrontside);
        PainMan.Log(this.getClass(), "getImgRightLegUpper", "Right upper leg " + (isFrontside ? "front" : "back") + " image init ok");
        return cropImage;
    }

    /**
     * Returns an initialized left side torso & upper arm image view
     *
     * @return
     */
    private Image getImgLeftUpperArm(boolean isFrontside) {
        setCropImage(-159, -171, isFrontside);
        PainMan.Log(this.getClass(), "getImgLeftUpperArm", "left side torso & upper arm image from " + (isFrontside ? "front" : "back"));
        return cropImage;
    }

    /**
     * Returns an initialized image of left side hand
     *
     * @return
     */
    private Image getImgLeftHand(boolean isFrontside) {
        setCropImage(0, -236, isFrontside);
        PainMan.Log(this.getClass(), "getImgLeftHand", "left side hand image from " + (isFrontside ? "front" : "back"));
        return cropImage;
    }

    /**
     * Returns an initialized image of left side upper leg
     *
     * @param isFrontside
     * @return
     */
    private Image getImgLeftLegUpper(boolean isFrontside) {
        setCropImage(-159, -445, isFrontside);
        PainMan.Log(this.getClass(), "getImgLeftLegUpper", "left side upper leg image from " + (isFrontside ? "front" : "back"));
        return cropImage;
    }

    /**
     * Returns an initialized image of right side upper arm
     *
     * @param isFrontside true for front side image
     * @return
     */
    private Image getImgRightUpperArm(boolean isFrontside) {
        setCropImage(-399, -171, isFrontside);
        PainMan.Log(this.getClass(), "getImgRightUpperArm", "right side torso & upper arm image from " + (isFrontside ? "front" : "back"));
        return cropImage;
    }

    /**
     * Returns an initialized image of right side hand
     *
     * @param isFrontside true for front side image
     * @return
     */
    private Image getImgRightHand(boolean isFrontside) {
        setCropImage(-569, -262, isFrontside);
        PainMan.Log(this.getClass(), "getImgRightHand", "right side hand image from " + (isFrontside ? "front" : "back"));
        return cropImage;
    }
    
    private static Image icons;
    
    public static Image getIcon(int iconID) {        
        
        int row = (int)Math.ceil(iconID / 4);
        if (row == -1) row = 0;
        int col = (iconID)%4;
        
//        PainMan.Log(this.getClass(), "getIcon", "called for icon id "+iconID+", returning icon from r"+row+" c"+col);

        int x = 32*col;
        int y = 32*row;
        
        int[] iconRGB = new int[32*32];
        getIconImg().getRGB(iconRGB, 0, 32, x, y, 32, 32);
        Image icon = Image.createRGBImage(iconRGB, 32, 32, true);
        
        return icon;
    }
    
    private static Image getIconImg() {        
        if (icons == null) {
            try {
                icons = Image.createImage("/images/icons.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return icons;
    }
}
