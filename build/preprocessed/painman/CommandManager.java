/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman;

import javax.microedition.lcdui.Command;

/**
 *
 * @author NIM
 */
public class CommandManager {
    
    private Command cmdExit;
    private Command cmdBack;
    private Command cmdSettings;
//    private Command cmdFlip;
    private Command cmdAddPoint;
    private Command cmdAddPointOk;
    private Command cmdCancel;
    private Command cmdOk;
    private Command cmdAddTag;
    
    public CommandManager() {}
    
    /**
     * Returns an initialized instance of cmdExit component.
     *
     * @return the initialized component instance
     */
    public Command CmdExit() {
        if (cmdExit == null) {                        
            cmdExit = new Command(PainMan.getUiString("Exit"), PainMan.getUiString("Exit"), Command.EXIT, 0);
        }
        return cmdExit;
    }    

    /**
     * Returns an initialized instance of cmdBack component
     * @return 
     */
    public Command CmdBack() {
        if (cmdBack == null) {
            cmdBack = new Command(PainMan.getUiString("Back"), PainMan.getUiString("Back"), Command.BACK, 1);
        }
        return cmdBack;        
    }
    
    /**
     * Returns an initialized instance of cmdBack component
     * @return 
     */
    public Command CmdSettings() {
        if (cmdSettings == null) {
            cmdSettings = new Command(PainMan.getUiString("Settings"), PainMan.getUiString("Settings"), Command.SCREEN, 1);
        }
        return cmdSettings;
    }
//    /**
//     * Returns an initialized instance of cmdFlip component
//     * @return 
//     */
//    public Command CmdFlip() {
//        if (cmdFlip == null) {
//            cmdFlip = new Command(PainMan.getUiString("Flip"), PainMan.getUiString("Flip"), Command.SCREEN, 1);
//        }
//        return cmdFlip;
//    }
    
    /**
     * Returns an initialized instance of command used to enter add data point mode
     * @return 
     */
    public Command CmdAddPoint() {
        if (cmdAddPoint == null) {
            cmdAddPoint = new Command(PainMan.getUiString("Add"), PainMan.getUiString("Add"), Command.SCREEN, 1);
        }
        return cmdAddPoint;
    }
    
    /**
     * Returns an initialized instance of command used to confirm data point add
     * @return 
     */
    public Command CmdAddPointOk() {
        if (cmdAddPointOk == null) {
            cmdAddPointOk = new Command(PainMan.getUiString("Ok"), PainMan.getUiString("Ok"), Command.OK, 1);
        }
        return cmdAddPointOk;
    }
    
    /**
     * Returns an initialized instance of command used to cancel things
     * @return 
     */
    public Command CmdCancel() {
        if (cmdCancel == null) {
            cmdCancel = new Command(PainMan.getUiString("Cancel"), PainMan.getUiString("Cancel"), Command.STOP, 1);
        }
        return cmdCancel;
    }
    
    /**
     * Returns an initialized instance of command used to accept things
     * @return 
     */
    public Command CmdOk() {
        if (cmdOk == null) {
            cmdOk = new Command(PainMan.getUiString("Ok"), PainMan.getUiString("Ok"), Command.OK, 1);
        }
        return cmdOk;
    }

    /**
     * Returns an initialized instance of command used to add tags
     * @return the cmdAddTag
     */
    public Command CmdAddTag() {
        if (cmdAddTag == null) {
            cmdAddTag = new Command(PainMan.getUiString("Add"),PainMan.getUiString("Add"), Command.ITEM, 1);
        }
        return cmdAddTag;
    }
}
