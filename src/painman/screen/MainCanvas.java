/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

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
public class MainCanvas extends BaseCanvas {

    private int selection = 0;
    
    public MainCanvas(PainMan midlet) {
        super(midlet);
        setScreenID(Properties.SCREEN_MAINSCREEN);
    }    
      
// <editor-fold defaultstate="collapsed" desc=" Clickable region definitions, points arranged clockwise."> 
    
    /**
     * Head perimeter
     */
    private Point[] head = {
        new Point(100, 1),
        new Point(138, 1),
        new Point(145, 48),
        new Point(95, 48)
    };
    
    /**
     * Torso perimeter
     */
    private Point[] torso = {
        new Point(96, 48),
        new Point(145, 48), 
        new Point(153, 90),
        new Point(157, 162),        
        new Point(136, 156),
        new Point(122, 172),
        new Point(117, 172),
        new Point(104, 156),
        new Point(83, 162),
        new Point(87, 88)
    };
    
    /**
     * Right arm perimeter
     */
    private Point[] rightArm = {
        new Point(145, 48),
        new Point(168, 49),
        new Point(210, 138),
        new Point(236, 157),
        new Point(236, 186),
        new Point(208, 186),
        new Point(154, 90),        
    };
    
    /**
     * Left arm perimeter
     */
    private Point[] leftArm = {
        new Point(76, 48),
        new Point(95, 48),
        new Point(87, 90),
        new Point(29, 187),
        new Point(4, 188),
        new Point(3, 154),
        new Point(27, 144)
    };
    
    /**
     * Right leg perimeter
     */
    private Point[] rightLeg = {
        new Point(157, 162),
        new Point(167, 196),
        new Point(164, 222),
        new Point(173, 246),
        new Point(182, 293),
        new Point(216, 302),
        new Point(208, 319),
        new Point(166, 318),
        new Point(140, 264),
        new Point(138, 235),
        new Point(120, 174),
        new Point(137, 156)
    };
    
    /**
     * Left leg perimeter
     */
    private Point[] leftLeg = {
        new Point(82, 163),
        new Point(103, 156),
        new Point(118, 173),
        new Point(100, 233),
        new Point(98, 260),
        new Point(71, 319),
        new Point(31, 318),
        new Point(22, 300),
        new Point(53, 293),
        new Point(65, 245),
        new Point(74, 219),
        new Point(73, 185)
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
        
        if (IS_FRONTSIDE) {
            bg = getRightFrontImage();
        } else {
            bg = getRightBackImage();
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
              
        paintBodyRegion(g, head, selection==Properties.SCREEN_HEAD);
        paintBodyRegion(g, torso, selection== Properties.SCREEN_TORSO);
        paintBodyRegion(g, leftArm, selection==Properties.SCREEN_LEFTARM);
        paintBodyRegion(g, rightArm, selection==Properties.SCREEN_RIGHTARM);
        paintBodyRegion(g, leftLeg, selection==Properties.SCREEN_LEFTLEG);
        paintBodyRegion(g, rightLeg, selection==Properties.SCREEN_RIGHTLEG);
        
        PainMan.Log(this.getClass(), "printMainScreen", "finished drawing main screen image");
        return mainScreenImg;
    }
    
    /**
     * Method of handling touch screen release
     *
     * @param x touch coordinate x
     * @param y touch coordinate y
     */
    protected void pointerReleased(int x, int y) {
        // note: check has to include scroll correction
        if (Util.bodyRegionClicked(x+translationX, y+translationY, torso)) {
            PainMan.Log(this.getClass(), "pointerReleased", "clicked inside torso");
            selection = Properties.SCREEN_TORSO;
        } else if (Util.bodyRegionClicked(x+translationX, y+translationY, rightArm)) {
            PainMan.Log(this.getClass(), "pointerReleased", "clicked inside right arm");
            selection = Properties.SCREEN_RIGHTARM;
            midlet.switchDisplay(null, midlet.Forms().getCnvRightArm());
        } else if (Util.bodyRegionClicked(x+translationX, y+translationY, leftArm)) {
            PainMan.Log(this.getClass(), "pointerReleased", "clicked inside left arm");
            selection = Properties.SCREEN_LEFTARM;
            midlet.switchDisplay(null, midlet.Forms().getCnvLeftArm());
        } else if (Util.bodyRegionClicked(x+translationX, y+translationY, rightLeg)) {
            PainMan.Log(this.getClass(), "pointerReleased", "clicked inside right leg");
            selection = Properties.SCREEN_RIGHTLEG;
            midlet.switchDisplay(null, midlet.Forms().getCnvRightLeg());
        } else if (Util.bodyRegionClicked(x+translationX, y+translationY, leftLeg)) { 
            PainMan.Log(this.getClass(), "pointerReleased", "clicked inside left leg");            
            selection = Properties.SCREEN_LEFTLEG;
            midlet.switchDisplay(null, midlet.Forms().getCnvLeftLeg());
        } else if (Util.bodyRegionClicked(x+translationX, y+translationY, head)) {
            PainMan.Log(this.getClass(), "pointerReleased", "clicked inside head");
            selection = Properties.SCREEN_HEAD;
        } else {
            selection = Properties.SCREEN_MAINSCREEN;
        }
        
        PainMan.Log(this.getClass(), "pointerReleased", "selection = "+selection);        
        
        repaint();
        
        lastPointerX = -1;
        lastPointerY = -1;

    }

    protected void paintHeaders(Graphics g) {
        int yPos = Properties.LARGE_FONT.getBaselinePosition();
        
        g.setColor(0x0c0c0c);
        g.setFont(Properties.LARGE_FONT);
        String header = "PainMan";
        int headerWidth = Properties.LARGE_FONT.stringWidth(header);
        g.drawString(header, (image.getWidth()/2-headerWidth/2), yPos, g.TOP|g.LEFT);
        
    }

    protected Image getLeftFrontImage() {
        return getRightFrontImage();
    }

    protected Image getLeftBackImage() {
        return getRightBackImage();
    }

    protected Image getRightFrontImage() {
        return midlet.ImageUtil().getImgMainFront();
    }

    protected Image getRightBackImage() {
        return midlet.ImageUtil().getImgMainBack();
    }
}
