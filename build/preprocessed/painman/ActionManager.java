/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import painman.screen.BaseCanvas;
import painman.screen.Button;
import painman.screen.CustomButtonListener;
import painman.screen.PointEditorForm;
import painman.screen.TagButton;

/**
 *
 * @author NIM
 */
public class ActionManager implements CommandListener, CustomButtonListener {

    private PainMan midlet;
    
    public ActionManager(PainMan midlet) {
        this.midlet = midlet;
    }
    
    /**
     * Command handler. Called by system when a command has been invoked on a
     * particular display
     * @param c invoked Command
     * @param d Displayable where the command was invoked
     */
    public void commandAction(Command c, Displayable d) {        
        if (d == midlet.Forms().getFrmMain()) {
            handleMainScreenCommands(c);
        }        
        else if (d == midlet.Forms().getSettingsList()) {
            handleSettingsListCommands(c);
        }
        else if (d == midlet.Forms().getPointEditor()) {
            handlePointEditorCommands(c);
        }
        else if (d == midlet.Forms().getPainTagList()) {
            handlePainTagListCommands(c);
        }
        else {
            handleBodyPartCommands(c, d);
        } 
    }
    
    /**
     * Handles all commands invoked from main screen
     * @param c invoked Command
     */
    private void handleMainScreenCommands(Command c) {        
        if (c == midlet.Commands().CmdExit()) {
            midlet.exitMIDlet();
        } 

        else if ( c == midlet.Commands().CmdSettings() ) {
            midlet.switchDisplay(null, midlet.Forms().getSettingsList());
        }
    }
        
    /**
     * Handles all commands invoked from Settings screen
     * @param c invoked Command
     */
    private void handleSettingsListCommands(Command c) {
        if ( c == midlet.Commands().CmdBack()) {
            midlet.switchToPreviousDisplay();
        }
    }
    
    private void handlePainTagListCommands(Command c) {
        if ( c == midlet.Commands().CmdOk()) {
            boolean[] selectedArray_return = new boolean[midlet.Forms().getPainTagList().size()];
            for (int i = 0; i< selectedArray_return.length; i++) {
                if (selectedArray_return[i]) {
                    midlet.Forms().getPointEditor().addTag(midlet.Forms().getPainTagList().getString(i));
                }
            }
            midlet.switchToPreviousDisplay();            
        }
        else {
            midlet.switchToPreviousDisplay();
        }
    }
    
    /**
     * Handles all commands invoked from Point editor screen
     * @param c command to handle
     */
    private void handlePointEditorCommands(Command c) {
        if ( c == midlet.Commands().CmdBack()) {
            midlet.switchToPreviousDisplay();            
        }
        else if ( c == midlet.Commands().CmdOk()) {
            midlet.Forms().getPointEditor().saveData();
            midlet.switchToPreviousDisplay();
        }
        Properties.ADD_DATAPOINT_MODE = false;
    }
    
    /**
     * Handles all commands invoked from BaseCanvas derived screens
     * @param c invoked command
     * @param d Displayable instance
     */
    private void handleBodyPartCommands(Command c, Displayable d) {
        if (c == midlet.Commands().CmdBack()) {
            midlet.switchToPreviousDisplay();
        }         
        
        else if ( c == midlet.Commands().CmdAddPoint() && Properties.ADD_DATAPOINT_MODE == false) {
            Properties.ADD_DATAPOINT_MODE = true;
            d.removeCommand(midlet.Commands().CmdAddPoint());
//            d.removeCommand(midlet.Commands().CmdFlip());
            d.removeCommand(midlet.Commands().CmdBack());
            
            d.addCommand(midlet.Commands().CmdAddPointOk());
            d.addCommand(midlet.Commands().CmdCancel());
            ((BaseCanvas)d).repaint();
        } 
        
        else if ( c == midlet.Commands().CmdAddPointOk() && Properties.ADD_DATAPOINT_MODE ) {
            d.removeCommand(midlet.Commands().CmdAddPointOk());
            d.removeCommand(midlet.Commands().CmdCancel());
            
            d.addCommand(midlet.Commands().CmdAddPoint());
//            d.addCommand(midlet.Commands().CmdFlip());
            d.addCommand(midlet.Commands().CmdBack());
            ((BaseCanvas)d).addDataPointConfirmed();
        }       
        
        else if ( c == midlet.Commands().CmdCancel() && Properties.ADD_DATAPOINT_MODE ) {
            Properties.ADD_DATAPOINT_MODE = false;
            d.removeCommand(midlet.Commands().CmdAddPointOk());
            d.removeCommand(midlet.Commands().CmdCancel());
            
            d.addCommand(midlet.Commands().CmdAddPoint());
//            d.addCommand(midlet.Commands().CmdFlip());
            d.addCommand(midlet.Commands().CmdBack());
            ((BaseCanvas)d).repaint();
        }
        
//        else if ( c == midlet.Commands().CmdFlip()) {            
//            ((BaseCanvas)d).flip();            
//            ((BaseCanvas)d).repaint();
//        }
    }

    /**
     * Handles button presses for all screens
     * @param b
     * @param d 
     */
    public void buttonAction(Button b, Displayable d) {
        if (d == midlet.Forms().getFrmMain()) {
            handleMainScreenButtons(b);
        } 
        else if (d == midlet.Forms().getPointEditor()) {
            handlePointEditorButtons(b, d);
        }
        else {
            handleBodyPartButtons(b, d);
        }           
    }

    /**
     * Main screen button actions
     * @param b 
     */
    private void handleMainScreenButtons(Button b) {
        if (b.getID() == Button.BUTTON_EXIT) {
            midlet.exitMIDlet();
        }       
        else if (b.getID() == Button.BUTTON_SETTINGS ) {
            midlet.switchDisplay(null, midlet.Forms().getSettingsList());
        }
    }
    
    private void handlePointEditorButtons(Button b, Displayable d) {
        PainMan.Log(this.getClass(), "handlePointEditorButtons", b.getID()+" add tag button clicked, button class: "+b.getClass());
        if (b.getClass() == TagButton.class) {
            PainMan.Log(this.getClass(), "handlePointEditorButtons", "tag button \""+((TagButton)b).getBtnText()+"\"clicked, removable = "+((TagButton)b).isRemovable());
            
            if (b.getID() == TagButton.BUTTON_TEXTONLY && ((TagButton)b).isRemovable() == false) {
//                midlet.s
//                midlet.Forms().getTagList();
                midlet.switchDisplay(null, midlet.Forms().getPainTagList());
                //midlet.Forms().getPointEditor().addTag("Testi");
            }
            else {
                midlet.Forms().getPointEditor().removeTag(b);
            }
        }
    }
    
    /**
     * Handle other canvas based screens
     * @param b
     * @param d 
     */
    private void handleBodyPartButtons(Button b, Displayable d) {
        if (b.getID() == Button.BUTTON_BACK) {
            midlet.switchToPreviousDisplay();
        }         
        
        else if ( b.getID() == Button.BUTTON_ADD_ENTRY && Properties.ADD_DATAPOINT_MODE == false) {
            PainMan.Log(this.getClass(), "handleBodyPartButtons", b.getID()+" add entry button clicked");
            Properties.ADD_DATAPOINT_MODE = true;
            
            if (((BaseCanvas)d).getButton(Button.BUTTON_ADD_ENTRY) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_ADD_ENTRY).setVisible(false); }                      
            if (((BaseCanvas)d).getButton(Button.BUTTON_BACK) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_BACK).setVisible(false); }
   
            if (((BaseCanvas)d).getButton(Button.BUTTON_APPLY) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_APPLY).setVisible(true); }                       
            if (((BaseCanvas)d).getButton(Button.BUTTON_CANCEL) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_CANCEL).setVisible(true); }

            ((BaseCanvas)d).doRepaint();
        } 
        
        else if ( b.getID() == Button.BUTTON_APPLY && Properties.ADD_DATAPOINT_MODE ) {
            PainMan.Log(this.getClass(), "handleBodyPartButtons", b.getID()+" apply button clicked");
            
            if (((BaseCanvas)d).getButton(Button.BUTTON_APPLY) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_APPLY).setVisible(false); }                       
            if (((BaseCanvas)d).getButton(Button.BUTTON_CANCEL) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_CANCEL).setVisible(false); }
            
            if (((BaseCanvas)d).getButton(Button.BUTTON_ADD_ENTRY) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_ADD_ENTRY).setVisible(true); }                      
            if (((BaseCanvas)d).getButton(Button.BUTTON_BACK) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_BACK).setVisible(true); }
            ((BaseCanvas)d).addDataPointConfirmed(); 
        }
        
        else if (b.getID() == Button.BUTTON_CANCEL && Properties.ADD_DATAPOINT_MODE  ){
            PainMan.Log(this.getClass(), "handleBodyPartButtons", b.getID()+" cancel button clicked");
            
            if (((BaseCanvas)d).getButton(Button.BUTTON_APPLY) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_APPLY).setVisible(false); }                       
            if (((BaseCanvas)d).getButton(Button.BUTTON_CANCEL) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_CANCEL).setVisible(false); }
            
            if (((BaseCanvas)d).getButton(Button.BUTTON_ADD_ENTRY) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_ADD_ENTRY).setVisible(true); }                      
            if (((BaseCanvas)d).getButton(Button.BUTTON_BACK) != null) { ((BaseCanvas)d).getButton(Button.BUTTON_BACK).setVisible(true); }
            Properties.ADD_DATAPOINT_MODE = false;
            ((BaseCanvas)d).doRepaint();
        }
    }
    
}
