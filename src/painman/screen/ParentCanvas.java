/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import painman.ImageUtil;
import painman.PainMan;
import painman.data.Point;
import painman.Properties;
import painman.Util;

/**
 *
 * @author NIM
 */
public class ParentCanvas extends BaseCanvas {

    // todo: move region definitions to another class for extending screens
    // currently this supports only main screen usage
    
    private int selection = 0;
    private Hashtable clickableRegions;
    private int xOffset, yOffset;    
    /**
     * ParentCanvas extends BaseCanvas and is used when screen needs
     * to have user-clickable regions for accessing subcanvases. 
     * 
     * For example, main screen has several subcanvases such as
     * left upper arm, left hand and head.
     * @param midlet
     * @param screenID 
     */
    public ParentCanvas(PainMan midlet, int screenID) {
        super(midlet, screenID);        
    }    
    
    private Hashtable getClickableRegions() {
        if (clickableRegions == null) {
            clickableRegions = new Hashtable();

            clickableRegions.clear();
            clickableRegions.put(new Integer(Properties.SCREEN_HEAD), head());
            clickableRegions.put(new Integer(Properties.SCREEN_RIGHTARM_TORSO), rightArm_upper());
            clickableRegions.put(new Integer(Properties.SCREEN_RIGHTHAND), rightArm_lower());
            clickableRegions.put(new Integer(Properties.SCREEN_RIGHTLEG_UPPER), rightLeg_upper());
            clickableRegions.put(new Integer(Properties.SCREEN_RIGHTLEG_LOWER), rightLeg_lower());
            clickableRegions.put(new Integer(Properties.SCREEN_LEFTARM_TORSO), leftArm_upper());
            clickableRegions.put(new Integer(Properties.SCREEN_LEFTHAND), leftArm_lower());
            clickableRegions.put(new Integer(Properties.SCREEN_LEFTLEG_UPPER), leftLeg_upper());
            clickableRegions.put(new Integer(Properties.SCREEN_LEFTLEG_LOWER), leftLeg_lower());
            PainMan.Log(this.getClass(), "getClickableRegions", "clickable regions set, xOffset="+xOffset+" yOffset="+yOffset
                    +"\n  Head[0] x"+head()[0].x+"y"+head()[0].y);
        }
        
        return clickableRegions;
    }    
      
// <editor-fold defaultstate="collapsed" desc=" Clickable region definitions, points arranged clockwise."> 
    
    /**
     * Clickable region definition for head and neck     
     */
    private Point[] head() {
        return new Point[] {
        new Point(96+xOffset, 55+yOffset),
        new Point(108+xOffset, 43+yOffset),
        new Point(101+xOffset, 28+yOffset),
        new Point(107+xOffset, 2+yOffset),
        new Point(128+xOffset, 2+yOffset),
        new Point(134+xOffset, 28+yOffset),
        new Point(129+xOffset, 43+yOffset),
        new Point(140+xOffset, 55+yOffset),
        new Point(118+xOffset, 58+yOffset)            
        };
    }
    
    /**
     * Clickable region definition for upper arm (from shoulder to elbow)
     * and half of the torso positioned in right side of the screen.
     */
    private Point[] rightArm_upper() {
        return new Point[]{
            new Point(144 + xOffset, 55 + yOffset),
            new Point(120 + xOffset, 59 + yOffset),
            new Point(120 + xOffset, 135 + yOffset),
            new Point(155 + xOffset, 133 + yOffset),
            new Point(152 + xOffset, 91 + yOffset),
            new Point(169 + xOffset, 104 + yOffset),
            new Point(185 + xOffset, 89 + yOffset),
            new Point(158 + xOffset, 56 + yOffset)
        };
    }
    
    /**
     * Clickable region definition for lower arm (from elbow to fingertips)
     * positioned in right side of the screen.
     */
    private Point[] rightArm_lower() {
        return new Point[]{
            new Point(169+ xOffset, 104+ yOffset),
            new Point(201+ xOffset, 134+ yOffset),
            new Point(221+ xOffset, 161+ yOffset),
            new Point(240+ xOffset, 146+ yOffset),
            new Point(232+ xOffset, 126+ yOffset),
            new Point(210+ xOffset, 120+ yOffset),
            new Point(185+ xOffset, 89+ yOffset)
        };
    }
    
    /**
     * Clickable region definition for upper arm (from shoulder to elbow) and
     * half of the torso positioned in left side of the screen.
     */
    private Point[] leftArm_upper() {
        return new Point[]{
            new Point(95 + xOffset, 55 + yOffset),
            new Point(120 + xOffset, 59 + yOffset),
            new Point(120 + xOffset, 135 + yOffset),
            new Point(84 + xOffset, 133 + yOffset),
            new Point(87 + xOffset, 91 + yOffset),
            new Point(70 + xOffset, 104 + yOffset),
            new Point(54 + xOffset, 89 + yOffset),
            new Point(81 + xOffset, 56 + yOffset)
        };
    }
    
    /**
     * Clickable region definition for lower arm (from elbow to fingertips)
     * positioned in left side of the screen.
     */
    private Point[] leftArm_lower() {
        return new Point[]{
            new Point(70 + xOffset, 104 + yOffset),
            new Point(38 + xOffset, 134 + yOffset),
            new Point(18 + xOffset, 161 + yOffset),
            new Point(0 + xOffset, 146 + yOffset),
            new Point(7 + xOffset, 126 + yOffset),
            new Point(29 + xOffset, 120 + yOffset),
            new Point(54 + xOffset, 89 + yOffset)
        };
    }
    
    /**
     * Clickable region definition for upper part of the leg (from hip to knee)
     * positioned in right side of the screen.
     */
    private Point[] rightLeg_upper() {
        return new Point[]{
            new Point(155 + xOffset, 133 + yOffset),
            new Point(120 + xOffset, 135 + yOffset),
            new Point(120 + xOffset, 165 + yOffset),
            new Point(137 + xOffset, 226 + yOffset),
            new Point(161 + xOffset, 226 + yOffset)
        };
    }
    
    
    /**
     * Clickable region definition for lower part of the leg (from knee to toes)
     * positioned in right side of the screen.
     */
    private Point[] rightLeg_lower() {
        return new Point[]{
            new Point(161 + xOffset, 226 + yOffset),
            new Point(137 + xOffset, 226 + yOffset),
            new Point(146 + xOffset, 311 + yOffset),
            new Point(172 + xOffset, 311 + yOffset),
            new Point(163 + xOffset, 278 + yOffset)
        };
    }
    
    /**
     * Clickable region definition for upper part of the leg (from hip to knee)
     * positioned in left side of the screen.
     */
    private Point[] leftLeg_upper() {
        return new Point[]{
            new Point(84 + xOffset, 133 + yOffset),
            new Point(120 + xOffset, 135 + yOffset),
            new Point(120 + xOffset, 165 + yOffset),
            new Point(102 + xOffset, 226 + yOffset),
            new Point(78 + xOffset, 226 + yOffset)
        };
    }
    
    /**
     * Clickable region definition for lower part of the leg (from knee to toes)
     * positioned in left side of the screen.
     */
    private Point[] leftLeg_lower() {
        return new Point[]{
            new Point(78 + xOffset, 226 + yOffset),
            new Point(102 + xOffset, 226 + yOffset),
            new Point(93 + xOffset, 311 + yOffset),
            new Point(67 + xOffset, 311 + yOffset),
            new Point(76 + xOffset, 278 + yOffset)
        };
    }
      
 // </editor-fold>
    
    protected void paint(Graphics g) {       
        
        NEED_IMAGE_REFRESH = false;
        image = printMainScreen();        
        super.paint(g);        

    }
    
    /**
     * Draws the main screen
     * 
     * @return Main screen image
     */
    public Image printMainScreen() {
//        PainMan.Log(this.getClass(), "printMainScreen", "drawing main screen image...");
        
        Image bg = null;
        
        if (Properties.IS_FRONTSIDE) {
            bg = getFrontImage();
        } else {
            bg = getBackImage();
        }
        
        int imgHeight = bg.getHeight();
        if (imgHeight < getHeight()) {
            imgHeight = getHeight();
        }
        
        Image mainScreenImg = Image.createImage(getWidth(), imgHeight);
        Graphics g = mainScreenImg.getGraphics();
        
        g.setColor(Properties.COLOR_BACKGROUND);
        g.fillRect(0, 0, mainScreenImg.getWidth(), mainScreenImg.getHeight());        

//        PainMan.Log(this.getClass(), "printMainScreen", "g.drawregion parameter check:\nimages same? "+mainScreenImg.equals(bg)+"\n"
//                +"x_src >= 0 ? "+(0>=0)+"\n"
//                +"y_src >= 0 ? "+(0>=0)+"\n"
//                +"x_src(0) + width("+getWidth()+") <= source width("+mainScreenImg.getWidth()+") ? "+(0+getWidth() <= mainScreenImg.getWidth())+"\n"
//                +"y_src(0) + height("+imgHeight+") <= source height("+mainScreenImg.getHeight()+") ? "+(0+imgHeight <= mainScreenImg.getHeight())+"\n"
//                +"anchor Graphics.TOP|Graphics.LEFT = "+(Graphics.TOP|Graphics.LEFT));
        yOffset = (mainScreenImg.getHeight() - bg.getHeight())/2;
        g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), Sprite.TRANS_NONE, 0,yOffset, g.TOP|g.LEFT);
        
        Enumeration regions = getClickableRegions().keys();
        
        while(regions.hasMoreElements()) {
            Integer screen = (Integer)regions.nextElement();
            paintBodyRegion(g, (Point[])getClickableRegions().get(screen), selection == screen.intValue());
        }
        selection = -1;

//        PainMan.Log(this.getClass(), "printMainScreen", "finished drawing main screen image");
        return mainScreenImg;
    }
    
    /**
     * Returns human readable name for screen id
     * @param screenID
     * @return 
     */
    protected String getRegionName(int screenID) {
        if (screenID == Properties.SCREEN_HEAD) {
            return "Head";
        } else if (screenID == Properties.SCREEN_LEFTARM_TORSO) {
            return "Left torso & upper arm";
        } else if (screenID == Properties.SCREEN_LEFTHAND) {
            return "Left hand";
        } else if (screenID == Properties.SCREEN_LEFTLEG_LOWER) {
            return "Left lower leg";
        } else if (screenID == Properties.SCREEN_LEFTLEG_UPPER) {
            return "Left upper leg";
        } else if (screenID == Properties.SCREEN_RIGHTARM_TORSO) {
            return "Right torso & upper arm";
        } else if (screenID == Properties.SCREEN_RIGHTHAND) {
            return "Right hand";
        } else if (screenID == Properties.SCREEN_RIGHTLEG_LOWER) {
            return "Right lower leg";
        } else if (screenID == Properties.SCREEN_RIGHTLEG_UPPER) {
            return "Right upper leg";
        } else {
            return "no region defined with id "+screenID;
        }
    }
//    
//    /**
//     * returns height
//     * @return 
//     */
//    public int getHeight() {
//        if (image == null) {
//            image = printMainScreen();
//        }
//        return image.getHeight();
//        
//    }
    /**
     * Method of handling touch screen release
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerReleased(int x, int y) {       
        
        super.pointerReleased(x, y);
        Enumeration regions = getClickableRegions().keys();
        
        if (!REPAINT_CALLED) {
            while (regions.hasMoreElements()) {
                Integer screen = (Integer) regions.nextElement();
                // note: check has to include scroll correction
                if (Util.bodyRegionClicked(x + translationX, y + translationY, (Point[]) getClickableRegions().get(screen))) {
                    PainMan.Log(this.getClass(), "pointerReleased", "clicked inside " + getRegionName(screen.intValue()));
                    selection = screen.intValue();
                    switchToChildScreen(selection);
                }
            }
        }
  
        PainMan.Log(this.getClass(), "pointerReleased", "selection = "+selection);        
        
        doRepaint();
        
        lastPointerX = -1;
        lastPointerY = -1;

    }
    
    /**
     * Method for opening child screen corresponding clicked region
     * @param childScreenID 
     */
    private void switchToChildScreen(int childScreenID) {
        BaseCanvas cnv = (BaseCanvas) midlet.Forms().getScreenById(childScreenID);
        midlet.switchDisplay(null, cnv);
    }
    
    /**
     * Method for printing headers & info text to screen.
     * Implements abstract method inherited from BaseCanvas
     * @param g 
     */
    protected void paintHeaders(Graphics g) {
//        int yPos = Properties.LARGE_FONT.getBaselinePosition();
        
        g.setColor(0x0c0c0c);
        g.setFont(Properties.LARGE_FONT);
        String header = "PainMan";
        int headerWidth = Properties.LARGE_FONT.stringWidth(header);
        g.drawString(header, 2, 2, g.TOP|g.LEFT);
        
    }    
    
//    protected void paintButtons(Graphics g) {
//        g.drawImage(midlet.ImageUtil().getIcon(ImageUtil.ICON_GEAR), 2, getHeight()-34, g.TOP|g.LEFT);
//        g.drawImage(midlet.ImageUtil().getIcon(ImageUtil.ICON_EXIT), getWidth()-34, getHeight()-34, g.TOP|g.LEFT);
//    }
}
