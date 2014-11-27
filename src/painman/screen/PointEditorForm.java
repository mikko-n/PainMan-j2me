/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.screen;

import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import painman.PainMan;
import painman.Properties;
import painman.data.BodyPart;
import painman.data.DataManager;
import painman.data.Point;

/**
 * Form for adding and editing pain points
 * @author NIM
 */
public class PointEditorForm extends Form implements Button.ButtonListener {

    private Gauge gPainAmount;
    private TextField tfDescription;
    private ChoiceGroup cgSelectPainCategory;
    private Vector btnPainCategoryTags;
    private Point painPoint;
    private Integer painPointIndex;
    private long lastButtonClick = System.currentTimeMillis();
    
    private CustomButtonListener listener;
            
    /**
     * Constructor for point editor
     * @param title Screen title string
     */
    public PointEditorForm(String title) {
        super(title);
        this.append(getPainAmountGauge());
        this.append(new StringItem("Tags:", "",StringItem.PLAIN));
        
        Enumeration tagButtons = getPainCategoryTagButtons().elements();
        while(tagButtons.hasMoreElements()) {
            this.append((Item) tagButtons.nextElement());
        }
        
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
    
    public void setButtonListener(CustomButtonListener listener) {
        this.listener = listener;
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
    
    /**
     * Returns Category choicegroup element for selecting the reason for the pain
     * @return 
     */
    private ChoiceGroup getPainCategoryChoiceGroup() {
        if (cgSelectPainCategory == null) {
            cgSelectPainCategory = new ChoiceGroup(PainMan.getUiString("Category"), ChoiceGroup.POPUP);
        }
        return cgSelectPainCategory;
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

    /**
     * @return the btnPainCategories
     */
    public Vector getPainCategoryTagButtons() {
        if (btnPainCategoryTags == null) {            
            btnPainCategoryTags = new Vector();            
            Button addButton = new TagButton(null, PainMan.getUiString("Add"), Properties.MEDIUM_FONT, false);
            addButton.setListener(this);
            btnPainCategoryTags.addElement(addButton);
        }
        return btnPainCategoryTags;
    }

    public void buttonClicked(Button button) {
        
        if (System.currentTimeMillis() - lastButtonClick > 50) {
            if (listener != null) {
                lastButtonClick = System.currentTimeMillis();
                listener.buttonAction(button, this);
                PainMan.Log(this.getClass(), "buttonClicked", "Button " + button.getID() + " clicked");
            } else {
                PainMan.Log(this.getClass(), "buttonClicked", "Button " + button.getID() + " clicked but there was no listener attached");
            }
        } else {
            PainMan.Log(this.getClass(), "buttonClicked", "Button " + button.getID() + " clicked but too little time elapsed since last click ("+(System.currentTimeMillis()-lastButtonClick)+" ms)");
        }
    }
    
    /**
     * Adds new tag to the screen
     * @param text 
     */
    public void addTag(String text) {
        
        PainMan.Log(this.getClass(), "addTag", "adding new tag, this.size() = " + this.size());
        int itemsBefore = this.size();

        PainMan.Log(this.getClass(), "addTag", "adding new tag");
        Button newBtn = new TagButton(null, "new tag", Properties.MEDIUM_FONT);
        newBtn.setListener(this);
        this.insert(itemsBefore - 1, newBtn);

        btnPainCategoryTags.addElement(newBtn);

    }
    
    public void removeTag(Button tag) {
        int itemsBefore = this.size();
        int itemToRemove = -1;
        PainMan.Log(this.getClass(), "removeTag", "removing tag");
        for(int i=0; i<itemsBefore; i++) {
            PainMan.Log(this.getClass(), "removeTag", "checking element "+i+"/"+itemsBefore);
            if (tag.equals(get(i))) {
                PainMan.Log(this.getClass(), "removeTag", "found a match");
                itemToRemove = i;                
            }
        }
        
        if (itemToRemove != -1) {
            PainMan.Log(this.getClass(), "removeTag", "remove complete.");
            this.delete(itemToRemove);
            btnPainCategoryTags.removeElement(tag);
        }
        
    }
}
