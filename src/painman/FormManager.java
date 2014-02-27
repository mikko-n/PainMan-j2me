/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.List;
import painman.data.Point;
import painman.screen.BaseCanvas;
import painman.screen.MainCanvas;
import painman.screen.LegCanvas;
import painman.screen.ArmCanvas;
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
    
    private MainCanvas cnvMain;
    private ArmCanvas cnvLeftArm;
    private ArmCanvas cnvRightArm;
    private LegCanvas cnvLeftLeg;
    private LegCanvas cnvRightLeg;
    private List listSettings;
    private PointEditorForm pointEditor;
    
    /**
     * Returns initialized instance of main screen canvas
     * @return 
     */
    public MainCanvas getFrmMain() {
        if (cnvMain == null) {
            PainMan.Log(this.getClass(), "getFrmMain", "cnvMain empty, initializing...");
            cnvMain = new MainCanvas(midlet);
            cnvMain.addCommand(midlet.Commands().CmdFlip());
            cnvMain.addCommand(midlet.Commands().CmdSettings());
            cnvMain.addCommand(midlet.Commands().CmdExit());            
            cnvMain.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getFrmMain", "cnvMain ready");
        }
        
        checkSanity(cnvMain);
        
        return cnvMain;
    }
    
    /**
     * Returns an initialized instance of left arm canvas
     * @return 
     */
    public ArmCanvas getCnvLeftArm() {
        if (cnvLeftArm == null) {
            PainMan.Log(this.getClass(), "getCnvLeftArm", "cnvLeftArm empty, initializing...");
            cnvLeftArm = new ArmCanvas(midlet, true);
            cnvLeftArm.addCommand(midlet.Commands().CmdFlip());
            cnvLeftArm.addCommand(midlet.Commands().CmdAddPoint());
            cnvLeftArm.addCommand(midlet.Commands().CmdBack());
            cnvLeftArm.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvLeftArm", "cnvLeftArm ready");
        }
        
        checkSanity(cnvLeftArm);
        
        return cnvLeftArm;
    }
    
    /**
     * Returns an initialized instance of right arm canvas
     * @return 
     */
    public ArmCanvas getCnvRightArm() {
        if (cnvRightArm == null) {
            PainMan.Log(this.getClass(), "getCnvRightArm", "cnvRightArm empty, initializing...");
            cnvRightArm = new ArmCanvas(midlet, false);
            cnvRightArm.addCommand(midlet.Commands().CmdFlip());
            cnvRightArm.addCommand(midlet.Commands().CmdAddPoint());
            cnvRightArm.addCommand(midlet.Commands().CmdBack());
            cnvRightArm.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvRightArm", "cnvRightArm ready");
        }
        
        checkSanity(cnvRightArm);
        
        return cnvRightArm;
    }
    
    /**
     * Returns left leg canvas
     * @return 
     */
    public LegCanvas getCnvLeftLeg() {
        if (cnvLeftLeg == null) {
            PainMan.Log(this.getClass(), "getCnvLeftLeg", "getCnvLeftLeg empty, initializing...");
            cnvLeftLeg = new LegCanvas(midlet, true);
            cnvLeftLeg.addCommand(midlet.Commands().CmdFlip());
            cnvLeftLeg.addCommand(midlet.Commands().CmdAddPoint());
            cnvLeftLeg.addCommand(midlet.Commands().CmdBack());
            cnvLeftLeg.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvLeftLeg", "cnvLeftLeg ready");            
        }
        
        checkSanity(cnvLeftLeg);
        
        return cnvLeftLeg;
    }
    
    /**
     * Returns right leg canvas
     * @return 
     */
    public LegCanvas getCnvRightLeg() {
        if (cnvRightLeg == null) {
            PainMan.Log(this.getClass(), "getCnvRightLeg", "getCnvRightLeg empty, initializing...");
            cnvRightLeg = new LegCanvas(midlet, false);
            cnvRightLeg.addCommand(midlet.Commands().CmdFlip());
            cnvRightLeg.addCommand(midlet.Commands().CmdAddPoint());
            cnvRightLeg.addCommand(midlet.Commands().CmdBack());
            cnvRightLeg.setCommandListener(midlet.CommandListener());
            PainMan.Log(this.getClass(), "getCnvRightLeg", "cnvRightLeg ready");            
        }
        
        checkSanity(cnvRightLeg);
        
        return cnvRightLeg;
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
