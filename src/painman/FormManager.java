/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import painman.data.Point;
import painman.screen.BaseCanvas;
import painman.screen.Button;
import painman.screen.ParentCanvas;
import painman.screen.ChildCanvas;
import painman.screen.PointEditorForm;

/**
 *
 * @author NIM
 */
public class FormManager {
    private PainMan midlet;
       
    public FormManager(PainMan midlet) {
        this.midlet = midlet;
    }
    
    private ParentCanvas cnvMain;
    private ChildCanvas cnvHead;
    private ChildCanvas cnvLeftArmUpper;
    private ChildCanvas cnvLeftArmLower;
    private ChildCanvas cnvRightArmUpper;
    private ChildCanvas cnvRightArmLower;
    private ChildCanvas cnvLeftLegUpper;
    private ChildCanvas cnvLeftLegLower;
    private ChildCanvas cnvRightLegUpper;
    private ChildCanvas cnvRightLegLower;
    private List listSettings;
    private PointEditorForm pointEditor;
    
    /**
     * Returns screen by it's associated screen id value
     * @param screenID See Properties
     * @return 
     */
    public Displayable getScreenById(int screenID) {
        if (screenID == Properties.SCREEN_HEAD) {
            return getCnvHead();
        } else if (screenID == Properties.SCREEN_LEFTARM_TORSO) {
            return getCnvLeftArmUpper();
        } else if (screenID == Properties.SCREEN_LEFTHAND) {
            return getCnvLeftArmLower();
        } else if (screenID == Properties.SCREEN_LEFTLEG_LOWER) {
            return getCnvLeftLegLower();
        } else if (screenID == Properties.SCREEN_LEFTLEG_UPPER) {
            return getCnvLeftLegUpper();
        } else if (screenID == Properties.SCREEN_RIGHTARM_TORSO) {
            return getCnvRightArmUpper();
        } else if (screenID == Properties.SCREEN_RIGHTHAND) {
            return getCnvRightArmLower();
        } else if (screenID == Properties.SCREEN_RIGHTLEG_LOWER) {
            return getCnvRightLegLower();
        } else if (screenID == Properties.SCREEN_RIGHTLEG_UPPER) {
            return getCnvRightLegUpper();
        } else {
            return getFrmMain();
        }
    }
    
    /**
     * Returns initialized instance of main screen canvas
     * @return 
     */
    public ParentCanvas getFrmMain() {
        if (cnvMain == null) {
            PainMan.Log(this.getClass(), "getFrmMain", "cnvMain empty, initializing...");
            cnvMain = new ParentCanvas(midlet, Properties.SCREEN_MAINSCREEN);
//            cnvMain.addCommand(midlet.Commands().CmdFlip());
            cnvMain.setFullScreenMode(true);
            
            Button exitBtn = new Button("", Button.BUTTON_EXIT, cnvMain.getWidth()-34, cnvMain.getHeight()-34);
            exitBtn.setListener(cnvMain);
            cnvMain.addButton(exitBtn);
            
            cnvMain.addCommand(midlet.Commands().CmdSettings());
            cnvMain.addCommand(midlet.Commands().CmdExit());            
            cnvMain.setCommandListener(midlet.CommandListener());
            cnvMain.setButtonListener(midlet.CommandListener());
            
            PainMan.Log(this.getClass(), "getFrmMain", "cnvMain ready");
        }
        
        checkSanity(cnvMain);
        
        return cnvMain;
    }
    
    /**
     * Returns an initialized instance of head canvas
     * @return 
     */
    public ChildCanvas getCnvHead() {
        if (cnvHead == null) {
            PainMan.Log(this.getClass(), "getCnvHead", "cnvHead empty, initializing...");
            cnvHead = new ChildCanvas(midlet, Properties.SCREEN_HEAD);
//            cnvHead.addCommand(midlet.Commands().CmdFlip());
            cnvHead.addCommand(midlet.Commands().CmdAddPoint());
            cnvHead.addCommand(midlet.Commands().CmdBack());
            cnvHead.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvHead", "cnvHead ready");
        }
        
        checkSanity(cnvHead);
        
        return cnvHead;
    }
    
    /**
     * Returns an initialized instance of left torso & upper arm canvas
     * @return 
     */
    public ChildCanvas getCnvLeftArmUpper() {
        if (cnvLeftArmUpper == null) {
            PainMan.Log(this.getClass(), "getCnvLeftUpperArm", "cnvLeftArmUpper empty, initializing...");
            cnvLeftArmUpper = new ChildCanvas(midlet, Properties.SCREEN_LEFTARM_TORSO);
//            cnvLeftArmUpper.addCommand(midlet.Commands().CmdFlip());
            cnvLeftArmUpper.addCommand(midlet.Commands().CmdAddPoint());
            cnvLeftArmUpper.addCommand(midlet.Commands().CmdBack());
            cnvLeftArmUpper.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvLeftUpperArm", "cnvLeftArmUpper ready");
        }
        
        checkSanity(cnvLeftArmUpper);
        
        return cnvLeftArmUpper;
    }
    
    /**
     * Returns an initialized instance of left hand canvas
     * @return 
     */
    public ChildCanvas getCnvLeftArmLower() {
        if (cnvLeftArmLower == null) {
            PainMan.Log(this.getClass(), "getCnvLeftArmLower", "cnvLeftArmLower empty, initializing...");
            cnvLeftArmLower = new ChildCanvas(midlet, Properties.SCREEN_LEFTHAND);
//            cnvLeftArmLower.addCommand(midlet.Commands().CmdFlip());
            cnvLeftArmLower.addCommand(midlet.Commands().CmdAddPoint());
            cnvLeftArmLower.addCommand(midlet.Commands().CmdBack());
            cnvLeftArmLower.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvLeftArmLower", "cnvLeftArmLower ready");
        }
        
        checkSanity(cnvLeftArmLower);
        
        return cnvLeftArmLower;
    }
    
    /**
     * Returns an initialized instance of right torso & upper arm canvas
     * @return 
     */
    public ChildCanvas getCnvRightArmUpper() {
        if (cnvRightArmUpper == null) {
            PainMan.Log(this.getClass(), "getCnvRightArmUpper", "cnvRightArmUpper empty, initializing...");
            cnvRightArmUpper = new ChildCanvas(midlet, Properties.SCREEN_RIGHTARM_TORSO);
//            cnvRightArmUpper.addCommand(midlet.Commands().CmdFlip());
            cnvRightArmUpper.addCommand(midlet.Commands().CmdAddPoint());
            cnvRightArmUpper.addCommand(midlet.Commands().CmdBack());
            cnvRightArmUpper.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvRightArmUpper", "cnvRightArmUpper ready");
        }
        
        checkSanity(cnvRightArmUpper);
        
        return cnvRightArmUpper;
    }
    
    /**
     * Returns an initialized instance of right hand canvas
     * @return 
     */
    public ChildCanvas getCnvRightArmLower() {
        if (cnvRightArmLower == null) {
            PainMan.Log(this.getClass(), "getCnvRightArmLower", "cnvRightArmLower empty, initializing...");
            cnvRightArmLower = new ChildCanvas(midlet, Properties.SCREEN_RIGHTHAND);
//            cnvRightArmLower.addCommand(midlet.Commands().CmdFlip());
            cnvRightArmLower.addCommand(midlet.Commands().CmdAddPoint());
            cnvRightArmLower.addCommand(midlet.Commands().CmdBack());
            cnvRightArmLower.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvRightArmLower", "cnvRightArmLower ready");
        }
        
        checkSanity(cnvRightArmLower);
        
        return cnvRightArmLower;
    }
    
    /**
     * Returns left upper leg canvas
     * @return 
     */
    public ChildCanvas getCnvLeftLegUpper() {
        if (cnvLeftLegUpper == null) {
            PainMan.Log(this.getClass(), "getCnvLeftLegUpper", "cnvLeftLegUpper empty, initializing...");
            cnvLeftLegUpper = new ChildCanvas(midlet, Properties.SCREEN_LEFTLEG_UPPER);
//            cnvLeftLegUpper.addCommand(midlet.Commands().CmdFlip());
            cnvLeftLegUpper.addCommand(midlet.Commands().CmdAddPoint());
            cnvLeftLegUpper.addCommand(midlet.Commands().CmdBack());
            cnvLeftLegUpper.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvLeftLegUpper", "cnvLeftLegUpper ready");            
        }
        
        checkSanity(cnvLeftLegUpper);
        
        return cnvLeftLegUpper;
    }
    
    /**
     * Returns left lower leg canvas
     * @return 
     */
    public ChildCanvas getCnvLeftLegLower() {
        if (cnvLeftLegLower == null) {
            PainMan.Log(this.getClass(), "getCnvLeftLegLower", "cnvLeftLegLower empty, initializing...");
            cnvLeftLegLower = new ChildCanvas(midlet, Properties.SCREEN_LEFTLEG_LOWER);
//            cnvLeftLegLower.addCommand(midlet.Commands().CmdFlip());
            cnvLeftLegLower.addCommand(midlet.Commands().CmdAddPoint());
            cnvLeftLegLower.addCommand(midlet.Commands().CmdBack());
            cnvLeftLegLower.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvLeftLegLower", "cnvLeftLegLower ready");            
        }
        
        checkSanity(cnvLeftLegLower);
        
        return cnvLeftLegLower;
    }
    
    /**
     * Returns right upper leg canvas
     * @return 
     */
    public ChildCanvas getCnvRightLegUpper() {
        if (cnvRightLegUpper == null) {
            PainMan.Log(this.getClass(), "getCnvRightLegUpper", "cnvRightLegUpper empty, initializing...");
            cnvRightLegUpper = new ChildCanvas(midlet, Properties.SCREEN_RIGHTLEG_UPPER);
//            cnvRightLegUpper.addCommand(midlet.Commands().CmdFlip());
            cnvRightLegUpper.addCommand(midlet.Commands().CmdAddPoint());
            cnvRightLegUpper.addCommand(midlet.Commands().CmdBack());
            cnvRightLegUpper.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvRightLegUpper", "cnvRightLegUpper ready");            
        }
        
        checkSanity(cnvRightLegUpper);
        
        return cnvRightLegUpper;
    }

    /**
     * Returns right lower leg canvas
     * @return 
     */
    public ChildCanvas getCnvRightLegLower() {
        if (cnvRightLegLower == null) {
            PainMan.Log(this.getClass(), "getCnvRightLegLower", "cnvRightLegLower empty, initializing...");
            cnvRightLegLower = new ChildCanvas(midlet, Properties.SCREEN_RIGHTLEG_LOWER);
//            cnvRightLegLower.addCommand(midlet.Commands().CmdFlip());
            cnvRightLegLower.addCommand(midlet.Commands().CmdAddPoint());
            cnvRightLegLower.addCommand(midlet.Commands().CmdBack());
            cnvRightLegLower.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvRightLegLower", "cnvRightLegLower ready");            
        }
        
        checkSanity(cnvRightLegLower);
        
        return cnvRightLegLower;
    }
    
    /**
     * Returns an initialized instance of Settings list
     * @return 
     */
    public List getSettingsList() {
        if (listSettings == null) {
            listSettings = new List(PainMan.getUiString("Settings"), Choice.IMPLICIT);
            listSettings.append(PainMan.getUiString("About"), null);
            listSettings.addCommand(midlet.Commands().CmdBack());
            listSettings.setCommandListener(midlet.CommandListener());
            listSettings.setFitPolicy(Choice.TEXT_WRAP_DEFAULT);
            boolean[] flags = new boolean[listSettings.size()];
            listSettings.setSelectedFlags(flags);
        }
        return listSettings;
    }
    
    /**
     * Returns an initialized instance of Point editor
     * @return 
     */
    public PointEditorForm getPointEditor() {
        if (pointEditor == null) {
            pointEditor = new PointEditorForm(PainMan.getUiString("Add-point"));
            pointEditor.addCommand(midlet.Commands().CmdBack());
            pointEditor.addCommand(midlet.Commands().CmdOk());
            pointEditor.setCommandListener(midlet.CommandListener());
        }
        pointEditor.setTitle(PainMan.getUiString("Add-point"));
        return pointEditor;
    }
    
    /**
     * Returns an initialized instance of Point editor and initializes it's
     * values to a given point
     * @return 
     */
    public PointEditorForm getPointEditor(Integer pointId, Point pointToEdit, boolean newPoint) {
        if (pointEditor == null) {
            getPointEditor();            
        }
        if (newPoint) {
            pointEditor.setTitle(PainMan.getUiString("Edit-point"));
        } else {
            pointEditor.setTitle(PainMan.getUiString("Add-point"));
        }
        
        pointEditor.setPainPoint(pointId, pointToEdit);
        return pointEditor;
    }
    
    /**
     * Performs some checks for changed display resolution
     * @param cn 
     */
    private void checkSanity(BaseCanvas cn) {
         if (cn != null && midlet.getDisplay().getCurrent() != null) {
            if (cn.getHeight() != midlet.getDisplay().getCurrent().getHeight()) {
                midlet.Log(this.getClass(), 
                        "constr",
                        cn.getClass()+" canvas height ("+cn.getHeight()+") should be "+midlet.getDisplay().getCurrent().getHeight()+" - calling repaint");
                cn.refreshData();
                cn.repaint();
            }
        }
    }
}
