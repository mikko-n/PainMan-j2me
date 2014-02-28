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
        clickableRegions = new Hashtable();
        clickableRegions.put(new Integer(Properties.SCREEN_HEAD), head);
        clickableRegions.put(new Integer(Properties.SCREEN_RIGHTARM_TORSO), rightArm_upper);
        clickableRegions.put(new Integer(Properties.SCREEN_RIGHTHAND), rightArm_lower);
        clickableRegions.put(new Integer(Properties.SCREEN_RIGHTLEG_UPPER), rightLeg_upper);
        clickableRegions.put(new Integer(Properties.SCREEN_RIGHTLEG_LOWER), rightLeg_lower);
        clickableRegions.put(new Integer(Properties.SCREEN_LEFTARM_TORSO), leftArm_upper);
        clickableRegions.put(new Integer(Properties.SCREEN_LEFTHAND), leftArm_lower);
        clickableRegions.put(new Integer(Properties.SCREEN_LEFTLEG_UPPER), leftLeg_upper);
        clickableRegions.put(new Integer(Properties.SCREEN_LEFTLEG_LOWER), leftLeg_lower);
    }    
      
// <editor-fold defaultstate="collapsed" desc=" Clickable region definitions, points arranged clockwise."> 
    
    /**
     * Clickable region definition for head and neck     
     */
    private Point[] head = {
        new Point(96, 55),
        new Point(108, 43),
        new Point(101, 28),
        new Point(107, 2),
        new Point(128, 2),
        new Point(134, 28),
        new Point(129, 43),
        new Point(140, 55),
        new Point(118, 58)            
    };
    
    /**
     * Clickable region definition for upper arm (from shoulder to elbow)
     * and half of the torso positioned in right side of the screen.
     */
    private Point[] rightArm_upper = {
        new Point(144, 55),
        new Point(120, 59),
        new Point(120, 135),
        new Point(155, 133),
        new Point(152, 91),
        new Point(169, 104),
        new Point(185, 89),
        new Point(158, 56)        
    };
    
    /**
     * Clickable region definition for lower arm (from elbow to fingertips) 
     * positioned in right side of the screen.
     */
    private Point[] rightArm_lower = {
        new Point(169, 104),
        new Point(201, 134),
        new Point(221, 161),
        new Point(240, 146),
        new Point(232, 126),
        new Point(210, 120),
        new Point(185, 89)
    };
    
    /**
     * Clickable region definition for upper arm (from shoulder to elbow)
     * and half of the torso positioned in left side of the screen.
     */
    private Point[] leftArm_upper = {
        new Point(95, 55),
        new Point(120, 59),
        new Point(120, 135),
        new Point(84, 133),
        new Point(87, 91),
        new Point(70, 104),
        new Point(54, 89),
        new Point(81, 56)
    };    
    
    /**
     * Clickable region definition for lower arm (from elbow to fingertips) 
     * positioned in left side of the screen.
     */
    private Point[] leftArm_lower = {
        new Point(70, 104),
        new Point(38, 134),
        new Point(18, 161),
        new Point(0, 146),
        new Point(7, 126),
        new Point(29, 120),
        new Point(54, 89)
    };
    
    /**
     * Clickable region definition for upper part of the leg (from hip to knee)
     * positioned in right side of the screen.
     */
    private Point[] rightLeg_upper = {
        new Point(155, 133),
        new Point(120, 135),
        new Point(120, 165),
        new Point(137, 226),
        new Point(161, 226)
    };    
    
    
    /**
     * Clickable region definition for lower part of the leg (from knee to toes)
     * positioned in right side of the screen.
     */
    private Point[] rightLeg_lower = {
        new Point(161, 226),
        new Point(137, 226),
        new Point(146, 311),
        new Point(172, 311),
        new Point(163, 278)            
    };
    
    /**
     * Clickable region definition for upper part of the leg (from hip to knee)
     * positioned in left side of the screen.
     */
    private Point[] leftLeg_upper = {
        new Point(84, 133),
        new Point(120, 135),
        new Point(120, 165),
        new Point(102, 226),
        new Point(78, 226)
    };
    
    /**
     * Clickable region definition for lower part of the leg (from knee to toes)
     * positioned in left side of the screen.
     */
    private Point[] leftLeg_lower = {
        new Point(78, 226),
        new Point(102, 226),
        new Point(93, 311),
        new Point(67, 311),
        new Point(76, 278)
    };
      
 // </editor-fold>
    
    protected void paint(Graphics g) {       
        
        NEED_IMAGE_REFRESH = false;
        image = printMainScreen();        
        super.paint(g);
        paintHeaders(g);

    }
    
    /**
     * Draws the main screen
     * 
     * @return Main screen image
     */
    public Image printMainScreen() {
        PainMan.Log(this.getClass(), "printMainScreen", "drawing main screen image...");
        
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
        
        g.setColor(0xffffff);
        g.fillRect(0, 0, mainScreenImg.getWidth(), mainScreenImg.getHeight());        
        g.drawRegion(bg, 0, 0, getWidth(), imgHeight ,Sprite.TRANS_NONE, 0,0, g.TOP|g.LEFT);
//        g.drawImage(midlet.ImageUtil().getImgMainFront(), 0,0, g.TOP|g.LEFT);
              
        Enumeration regions = clickableRegions.keys();
        
        while(regions.hasMoreElements()) {
            Integer screen = (Integer)regions.nextElement();
            paintBodyRegion(g, (Point[])clickableRegions.get(screen), selection == screen.intValue());
        }
        selection = -1;

        PainMan.Log(this.getClass(), "printMainScreen", "finished drawing main screen image");
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
    
    /**
     * Method of handling touch screen release
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerReleased(int x, int y) {       
        
        Enumeration regions = clickableRegions.keys();
        
        while(regions.hasMoreElements()) {
            Integer screen = (Integer)regions.nextElement();
            // note: check has to include scroll correction
            if (Util.bodyRegionClicked(x+translationX, y+translationY, (Point[])clickableRegions.get(screen))) {
                PainMan.Log(this.getClass(), "pointerReleased", "clicked inside "+getRegionName(screen.intValue()));
                selection = screen.intValue();
                switchToChildScreen(selection);
            }
        }
  
        PainMan.Log(this.getClass(), "pointerReleased", "selection = "+selection);        
        
        repaint();
        
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
        int yPos = Properties.LARGE_FONT.getBaselinePosition();
        
        g.setColor(0x0c0c0c);
        g.setFont(Properties.LARGE_FONT);
        String header = "PainMan";
        int headerWidth = Properties.LARGE_FONT.stringWidth(header);
        g.drawString(header, (image.getWidth()/2-headerWidth/2), yPos, g.TOP|g.LEFT);
        
    }    
}
