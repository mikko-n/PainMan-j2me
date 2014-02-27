/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import painman.PainMan;
import painman.Properties;

/**
 *
 * @author NIM
 */
public class HeadCanvas extends BaseCanvas {

    HeadCanvas(PainMan midlet) {
        super(midlet);
        setScreenID(Properties.SCREEN_HEAD);
    }
    
    protected void paint(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void paintHeaders(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected Image getLeftFrontImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected Image getLeftBackImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected Image getRightFrontImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected Image getRightBackImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
