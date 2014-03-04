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

/**
 *
 * @author NIM
 */
public class ActionManager implements CommandListener, BaseCanvas.CanvasButtonListener {

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
//        else if ( c == midlet.Commands().CmdFlip()) {
//            midlet.Forms().getFrmMain().flip();
//            midlet.Forms().getFrmMain().repaint();
//        } 
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
        
        else if ( c == midlet.Commands().CmdAddPoint()) {
            ((BaseCanvas)d).ADD_DATAPOINT_MODE = true;
            d.removeCommand(midlet.Commands().CmdAddPoint());
//            d.removeCommand(midlet.Commands().CmdFlip());
            d.removeCommand(midlet.Commands().CmdBack());
            
            d.addCommand(midlet.Commands().CmdAddPointOk());
            d.addCommand(midlet.Commands().CmdCancel());
            ((BaseCanvas)d).repaint();
        } 
        
        else if ( c == midlet.Commands().CmdAddPointOk() ) {
            d.removeCommand(midlet.Commands().CmdAddPointOk());
            d.removeCommand(midlet.Commands().CmdCancel());
            
            d.addCommand(midlet.Commands().CmdAddPoint());
//            d.addCommand(midlet.Commands().CmdFlip());
            d.addCommand(midlet.Commands().CmdBack());
            ((BaseCanvas)d).addDataPointConfirmed();
        }       
        
        else if ( c == midlet.Commands().CmdCancel() ) {
            ((BaseCanvas)d).ADD_DATAPOINT_MODE = false;
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

    public void buttonAction(Button button, Displayable d) {
        if (d == midlet.Forms().getFrmMain()) {
            if (button.getID() == Button.BUTTON_EXIT) {
                midlet.exitMIDlet();
            }            
        }
        
        
    }
    
}
