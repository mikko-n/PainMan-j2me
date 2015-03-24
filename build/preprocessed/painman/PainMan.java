/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman;

import java.util.Hashtable;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;
import painman.data.DataManager;
import painman.screen.PointEditorForm;


/**
 * @author NIM
 */
public class PainMan extends MIDlet {

    private boolean midletPaused = false;
    private CommandManager _commands;
    private ActionManager _cmdListener;
    private FormManager _forms;
    private ImageUtil _imageUtil;
    private static Hashtable translation = new Hashtable();
    
    private static PainMan self;
        
    /**
     * Returns initialized CommandManager instance
     * @return the _commands
     */
    public CommandManager Commands() {
        if (_commands == null) {
            _commands = new CommandManager();
        }
        return _commands;
    }

    /**
     * Returns initialized CommandListener instance (ActionManager class)
     * @return _cmdListener
     */
    public ActionManager CommandListener() {
        if (_cmdListener == null) {
            _cmdListener = new ActionManager(this);
        }
        return _cmdListener;
    }
    /**
     * Returns initialized FormManager instance
     * @return the _forms
     */
    public FormManager Forms() {
        if (_forms == null) {
            _forms = new FormManager(this);
        }
        return _forms;
    }
    
    /**
     * Returns initialized ImageUtil instance
     * @return _imageUtil
     */
    public ImageUtil ImageUtil() {
        if (_imageUtil == null) {            
            _imageUtil = new ImageUtil(getDisplay().getCurrent().getWidth(), getDisplay().getCurrent().getHeight());
        }
        return _imageUtil;
    }
    
    // todo: replace this with java.util.Stack ???
    private java.util.Hashtable _previousDisplayables = new java.util.Hashtable();
    
    public PainMan() {
        self = this;
    }
    
    public static PainMan getInstance() {
        return self;
    }
    
    public Display getDisplay() {
        return Display.getDisplay(this);
    }
    
    /**
     * Returns a display string for the specified property name from the
     * UI properties file.
     * If a string for the property name can not be found then the
     * property name is simply returned.
     */
    public static String getUiString(String propertyName)
    {                   
        if (translation.isEmpty()) {
            Log(PainMan.class, "getUiString", "translation Hashtable is empty, trying to fill values...");
            try {
                translation = Properties.readUiProperties();
            } catch (Exception e) {
                
            }
        }

        String result = (String) translation.get(propertyName);

        if (result == null) {
            result = propertyName;
            result = propertyName.replace('-', ' ');
        }
        return result;
    }
    
//<editor-fold defaultstate="collapsed" desc=" Logging methods ">
    public static void Log(String msg){
        Log(null,"",msg);        
    }
    
    public static void Log(String method, String msg) {
        Log(null, method, msg);
    }
    
    public static void Log(Class kutsuja, String msg) {
        Log(kutsuja, "", msg);
    }
    
    public static void Log(Class kutsuja, String method, String msg) {
        String classname = "";     
                
        if (kutsuja != null) {
          classname = "["+kutsuja.getName();
        } 
        
        if (!("".equals(method))) {
            if ("".equals(classname)) {
                classname = "["+method+"] ";
            } else {
                classname = classname+"."+method;
            }
        }
        if (!("".equals(classname))) {
            classname = classname+"] ";
        }
        
        System.out.println(classname+msg);
    }        
    //</editor-fold>
    
    public void switchDisplay(Alert alert, Displayable nextDisplay) {
        Display display = getDisplay();
        Displayable _currentDisplay = display.getCurrent();
        if (_currentDisplay != null && nextDisplay != null &&
                nextDisplay.getClass() == PointEditorForm.class) {
            _previousDisplayables.put(nextDisplay, _currentDisplay);
//            Log(this.getClass(), "switchDisplay", "previous displayables current depth: "+_previousDisplayables.size());            
        } 
        
        if (alert == null) {
            if (nextDisplay != null) {
//                Log(this.getClass(), "switchDisplay", "switching to "+nextDisplay.getClass());
            } 
            display.setCurrent(nextDisplay);
        } else {
            display.setCurrent(alert, nextDisplay);            
        }
    }
    
    public void switchToPreviousDisplay() {
        Displayable _currentDisplay = getDisplay().getCurrent();
        if (_currentDisplay != null) {
            Displayable _nextDisplay = (Displayable) _previousDisplayables.get(_currentDisplay);
            if (_nextDisplay != null) {               
                // do not go back to editor form or try not to go back to self
                if (_nextDisplay.getClass() == PointEditorForm.class || _currentDisplay.getClass() == _nextDisplay.getClass()) {
//                    Log(this.getClass(), "switchToPreviousDisplay", "skipping over "+_nextDisplay.getClass());
                    _nextDisplay = (Displayable) _previousDisplayables.get(_nextDisplay);
//                    Log(this.getClass(), "switchToPreviousDisplay", "next display = "+_nextDisplay.getClass());
                }
 
                switchDisplay(null, _nextDisplay);
            } else {
                Log(this.getClass(), "switchToPreviousDisplay", "next display was null, going to main form");
                switchDisplay(null, Forms().getFrmMain());
            }
            
        }
    }
    
    /**
     * Called when MIDlet is started
     * Checks if already started and inits / starts or resumes the MIDlet
     */
    public void startApp() {
        if (midletPaused) {
            resumeMidlet();
        } else {
            initialize();
            startMidlet();
        }
        midletPaused = false;
    }
    
    /**
     * Resume point
     */
    public void resumeMidlet() {}
    
    private void initialize() {
        
        // todo: init data layer here
        
        translation = Properties.readUiProperties();
        
        _forms = new FormManager(this);
        _commands = new CommandManager();
        
        Log(this.getClass(), "initialize", "MIDlet initialized");        
    }
    
    
    
    public void startMidlet() {
        switchDisplay(null, Forms().getFrmMain());
//        switchDisplay(null, Forms().getCanvasViewManager());
    }
    /**
     * Called when midlet is paused
     */
    public void pauseApp() {
        midletPaused = true;
    }
    
    public void destroyApp(boolean unconditional) {        
        DataManager.getInstance().close();
    }
    
    /**
     * Exits midlet
     */
    public void exitMIDlet() {
        switchDisplay(null, null);
        destroyApp(true);
        notifyDestroyed();
    }   
}
