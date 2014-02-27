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
    private Command cmdFlip;
    private Command cmdAddPoint;
    private Command cmdOk;
    
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
    /**
     * Returns an initialized instance of cmdFlip component
     * @return 
     */
    public Command CmdFlip() {
        if (cmdFlip == null) {
            cmdFlip = new Command(PainMan.getUiString("Flip"), PainMan.getUiString("Flip"), Command.SCREEN, 1);
        }
        return cmdFlip;
    }
    
    /**
     * Returns an initialized instance of command used to add data points
     * @return 
     */
    public Command CmdAddPoint() {
        if (cmdAddPoint == null) {
            cmdAddPoint = new Command(PainMan.getUiString("Add"), PainMan.getUiString("Add"), Command.SCREEN, 1);
        }
        return cmdAddPoint;
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
}
