/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import painman.PainMan;
import painman.data.Point;
import painman.Properties;

/**
 *
 * @author NIM
 */
public class ArmCanvas extends BaseCanvas {
    
    public ArmCanvas(PainMan midlet, boolean isLeft) {
        super(midlet);        
        if (isLeft) {
            setScreenID(Properties.SCREEN_LEFTARM);
        } else {
            setScreenID(Properties.SCREEN_RIGHTARM);
        }
        this.IS_LEFT = isLeft;
    }
    
    protected void paint(Graphics g) {
        super.paint(g);                
        paintHeaders(g);        
    }
    
    protected void paintHeaders(Graphics g) {
        String header;               
        if (IS_FRONTSIDE) {
            header = PainMan.getUiString("Palm");
        } else {
            header = PainMan.getUiString("Knuckle");
        }
        
        int headerXpos;
        int ypos = Properties.LARGE_FONT.getBaselinePosition();
        int headerWidth = Properties.LARGE_FONT.stringWidth(header);
        
        if (IS_LEFT) {
            headerXpos = 3;
        } else {
            headerXpos = image.getWidth() - headerWidth - 3;
        }
        g.setColor(0x0c0c0c);
        g.setFont(Properties.LARGE_FONT);
        g.drawString(header, headerXpos, ypos, g.TOP|g.LEFT);
        //PainMan.Log(this.getClass(), "paintHeaders", "finished drawing "+(IS_LEFT ? "left" : "right")+" headers to arm image from "+(IS_FRONTSIDE ? "palm" : "knuckle"));
        
    }
    
    protected Image getLeftFrontImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgLeftArmPalm();
        return bg;
    }

    protected Image getLeftBackImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgLeftArmKnuckle();
        return bg;
    }

    protected Image getRightFrontImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgRightArmPalm();
        return bg;
    }

    protected Image getRightBackImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgRightArmKnuckle();
        return bg;
    }
}
