/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import painman.screen.BaseCanvas;

/**
 *
 * @author NIM
 */
public class ActionManager implements CommandListener {

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
        else if ( c == midlet.Commands().CmdFlip()) {
            midlet.Forms().getFrmMain().flip();
            midlet.Forms().getFrmMain().repaint();
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
        } 
        
        else if ( c == midlet.Commands().CmdFlip()) {            
            ((BaseCanvas)d).flip();            
            ((BaseCanvas)d).repaint();
        }
    }
    
}
