/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.screen;

import javax.microedition.lcdui.Graphics;
import painman.PainMan;
import painman.Properties;

/**
 *
 * @author NIM
 */
public class ChildCanvas extends BaseCanvas {
        
    public ChildCanvas(PainMan midlet, int screenID) {
        super(midlet, screenID);
    }
    
    /**
     * Paint method inherited from Canvas class
     * @param g 
     */
    protected void paint(Graphics g) {
        super.paint(g);
        paintHeaders(g);  
        
        if (ADD_DATAPOINT_MODE) {
            drawCrossHair(g);
        }
    }

   /**
     * Method for printing headers & info text to screen.
     * Implements abstract method inherited from BaseCanvas
     * @param g 
     */
    protected void paintHeaders(Graphics g) {
        String header;               
        if (Properties.IS_FRONTSIDE) {
            header = PainMan.getUiString("Front");
        } else {
            header = PainMan.getUiString("Back");
        }
        
        if (ADD_DATAPOINT_MODE) {
//            PainMan.Log(this.getClass(), "paintHeaders", "drawing info text to screen");
            header = PainMan.getUiString("Place-pain-point");
            g.setFont(Properties.SMALL_FONT);
        } else {        
            g.setFont(Properties.LARGE_FONT);
        }
        int headerXpos;
        int ypos = Properties.LARGE_FONT.getBaselinePosition();
        int headerWidth = Properties.LARGE_FONT.stringWidth(header);

        headerXpos = 3;
        
        g.setColor(0x0c0c0c);
        
        g.drawString(header, headerXpos, ypos, g.TOP|g.LEFT);
        
    }
    
    /**
     * Method for painting a small crosshair to indicate data point location
     * @param g 
     */
    protected void drawCrossHair(Graphics g) {
//        PainMan.Log(this.getClass(), "drawCrossHair", "Data point add mode, drawing crosshair for data point add to x"+lastPointerX+" y"+lastPointerY);
        
        g.setColor(Properties.COLOR_CROSSHAIR);
        g.drawLine(lastPointerX - 2, lastPointerY, lastPointerX + 2, lastPointerY);
        g.drawLine(lastPointerX, lastPointerY - 2, lastPointerX, lastPointerY + 2);

    }
    
}
