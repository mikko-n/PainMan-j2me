/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.screen;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.TextField;
import painman.PainMan;
import painman.data.BodyPart;
import painman.data.DataManager;
import painman.data.Point;

/**
 * Form for adding and editing pain points
 * @author NIM
 */
public class PointEditorForm extends Form {

    private Gauge gPainAmount;
    private TextField tfDescription;
    private Point painPoint;
    private Integer painPointIndex;
            
    /**
     * Constructor for point editor
     * @param title Screen title string
     */
    public PointEditorForm(String title) {
        super(title);
        this.append(getPainAmountGauge());
        this.append(getDescriptionTextField());
        this.painPoint = new Point();
    }
    
    /**
     * Constructor for point editor
     * @param title Screen title string
     */
    public PointEditorForm(String title, Point pointToEdit) {
        this(title);
        this.painPoint = pointToEdit;
    }

    /**
     * Sets pain point to screen for edit
     * @param pointPxId
     * @param pointToEdit 
     */
    public void setPainPoint(Integer pointPxId, Point pointToEdit) {
        this.painPointIndex = pointPxId;
        this.painPoint = pointToEdit;
        this.gPainAmount.setValue(pointToEdit.value);
        this.tfDescription.setString(pointToEdit.description);
    }
    
    public Point getPainPoint() {
        return painPoint;
    }
    
    /**
     * Returns an instance of Gauge used to enter perceived pain amount
     * @return 
     */
    private Gauge getPainAmountGauge() {
        if (gPainAmount == null) {
            gPainAmount = new Gauge(null, true, 100, 1);
        }
        return gPainAmount;
    }
    
    /**
     * Returns TextField element for describing pain
     * @return 
     */
    private TextField getDescriptionTextField() {
        if (tfDescription == null) {
            tfDescription = new TextField(PainMan.getUiString("Description"), null, 50, TextField.ANY);
        }        
        return tfDescription;
    }
    
    public boolean saveData() {
        painPoint.value = getPainAmountGauge().getValue();
        painPoint.description = getDescriptionTextField().getString();
        
        BodyPart screenData = DataManager.getInstance().getScreenData(painPoint.screenID);
        PainMan.Log(this.getClass(), "saveData", "Got previous screen data from RMS (screen id:"+ screenData.getBodyPartId()+")");
        screenData.addPoint(painPointIndex, painPoint);
        
        DataManager.getInstance().saveBodyPartData(screenData);
        PainMan.Log(this.getClass(), "saveData", "Data point (id "+painPointIndex.intValue()+") saved: "+painPoint.toString()+", dataset size: "+screenData.getDataSize());
        
        return true;
    }
}
