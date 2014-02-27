/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import painman.PainMan;
import painman.Properties;

/**
 *
 * @author NIM
 */
public class LegCanvas extends BaseCanvas {
        
    public LegCanvas(PainMan midlet, boolean isLeft) {
        super(midlet);
        if (isLeft) {
            setScreenID(Properties.SCREEN_LEFTLEG);
        } else {
            setScreenID(Properties.SCREEN_RIGHTLEG);
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
            header = PainMan.getUiString("Front");
        } else {
            header = PainMan.getUiString("Back");
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
        
    }

    protected Image getLeftFrontImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgLeftLegFront();
        return bg;
    }

    protected Image getLeftBackImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgLeftLegBack();
        return bg;
    }

    protected Image getRightFrontImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgRightLegFront();
        return bg;
    }

    protected Image getRightBackImage() {
        Image bg;
        bg = midlet.ImageUtil().getImgRightLegBack();
        return bg;
    }
    
}
