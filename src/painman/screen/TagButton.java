/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.screen;

import javax.microedition.amms.control.PriorityControl;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import painman.Properties;

/**
 *
 * @author NIM
 */
public class TagButton extends Button {

    // Text only -button
    public static final int BUTTON_TEXTONLY = -1;
    public static final int BTN_SPECIALTAG = 1;
    private boolean isRemovable = true;
    private Font fontti = null;
    private String btnText;
    private int margin = 4;
    private int removeButtonMargin = Properties.SMALL_FONT.charWidth('x');
    
    /**
     * Constructor for text only -button
     * @param label Button label     
     * @param text Text to show in button
     * @param font Font for drawing the text
     */
    public TagButton(String label, String text, Font font) {        
        super(label, BUTTON_TEXTONLY);
        this.btnText = text;
        if (font == null) {
            this.fontti = Properties.MEDIUM_FONT;
        }        
        this.boundingBox.setHeight(font.getHeight() + 2);
        this.boundingBox.setWidth(font.stringWidth(text) + 6);
        this.setPreferredSize(this.boundingBox.getWidth(), this.boundingBox.getHeight());
        painman.PainMan.Log(this.getClass(), "constructor", "New button with text: "+text);
    }
    
    /**
     * Special constructor to set tag non-removable
     * @param label
     * @param text
     * @param font
     * @param removable 
     */
    public TagButton(String label, String text, Font font, boolean removable) {
        this(label, text, font);
        this.isRemovable = removable;
        if (this.isRemovable == true) {
            int tmpWidth = this.boundingBox.getWidth();
            this.boundingBox.setWidth((tmpWidth + removeButtonMargin + margin + margin));            
            this.setPreferredSize(this.boundingBox.getWidth(), this.boundingBox.getHeight());
        }
    }
    
     /**
     * @return the fontti
     */
    public Font getFontti() {
        if (fontti == null) {
            fontti = Properties.MEDIUM_FONT;
        }
        return fontti;
    }
    
    /**
     * @param fontti the fontti to set
     */
    public void setFontti(Font fontti) {
        this.fontti = fontti;
    }
    
    /**
     * Gets the button text
     * @return the btnText
     */
    public String getBtnText() {
        return btnText;
    }

    /**
     * Sets the button text
     * @param btnText the btnText to set
     */
    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    
    protected void paint(Graphics g, int w, int h) {
          if (!visible) {
            return;
        }
        if (!enabled) {            
        }        
        
        else if (pressed || selected) {           
            drawTextButton(g, true);               
        }
        else {
            drawTextButton(g, false);                
        }
                
    }
    
     /**
     * Method to draw the text version of the button
     * @param g graphics object
     * @param highlighted is the button highlighted (pressed/selected)
     */
    private void drawTextButton(Graphics g, boolean highlighted) {
        g.setStrokeStyle(Graphics.SOLID);
        g.setColor(Properties.COLOR_BACKGROUND);
        g.setFont(Properties.MEDIUM_FONT);
        
        if (highlighted) {
            g.setColor(0x00dedede);
        } 
        
        g.fillRoundRect(0, 0, boundingBox.getWidth()-1, boundingBox.getHeight()-1, 4, 4);
        g.setColor(Properties.COLOR_TEXT);
        g.drawRoundRect(0, 0, boundingBox.getWidth()-1, boundingBox.getHeight()-1, 4, 4);   
        
        //painman.PainMan.Log(this.getClass(), "drawTextButton", "button width: "+getWidth()+", bounding box width: "+getPrefContentWidth(1));
        
        int txtWidth = this.getFontti().stringWidth(getBtnText());
        
        g.drawString(getBtnText(), boundingBox.getWidth()/2 - txtWidth/2, 1, 0); // 0 = anchor point TOP|LEFT
        
        if (isRemovable()) {
            g.setFont(Properties.SMALL_FONT);
            g.setColor(Properties.COLOR_CROSSHAIR);
            g.drawString("x", boundingBox.getWidth()-removeButtonMargin, -margin-2, 0);
        }
    }

    /**
     * Returns info if is the tag button removable or not
     * @return the nonRemovable
     */
    public boolean isRemovable() {
        return isRemovable;
    }
    
}
