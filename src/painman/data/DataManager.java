/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.data;

import java.util.Hashtable;
import net.sourceforge.floggy.persistence.Filter;
import net.sourceforge.floggy.persistence.FloggyException;
import net.sourceforge.floggy.persistence.ObjectSet;
import net.sourceforge.floggy.persistence.Persistable;
import net.sourceforge.floggy.persistence.PersistableManager;
import painman.PainMan;

/**
 * Data manager class for PainMan 
 * @author NIM
 */
public class DataManager {    
    private static final DataManager INSTANCE = new DataManager();
    private PersistableManager data = PersistableManager.getInstance();
    
    private DataManager() {}
 
    public static DataManager getInstance() {
        return INSTANCE;
    }
    
    public void close() {
        try {
            data.shutdown();
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Stores a point to RMS
     * @param p
     * @return id for later retrieval, -1 for failure
     */
    public int savePoint(Point p) {
        try {
            return data.save(p);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    /**
     * Stores all data related to a body part (=screen) to RMS
     * @param part Body part to save
     * @return id for later retrieval, -1 for failure
     */
    public int saveBodyPartData(BodyPart part) {
        try {            
            PainMan.Log(this.getClass(), "saveBodyPartData", "saving data for screenID "+part.getBodyPartId()+" from RMS...");
            return data.save(part);
        } catch (FloggyException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Returns data for specified screen
     * @param screenID screen id (see Properties for values)
     * @return 
     */
    public BodyPart getScreenData(int screenID) {
        try {
            PainMan.Log(this.getClass(), "getScreenData", "loading data for screenID "+screenID+" from RMS...");
            ObjectSet dataset = data.find(BodyPart.class, new ScreenIDFilter(screenID), null);
            if (dataset.size() != 0) {
                PainMan.Log(this.getClass(), "getScreenData", "data found (size="+dataset.size()+")");
                return (BodyPart)dataset.get(0);
            }
        } catch (FloggyException ex) {
            PainMan.Log(this.getClass(), "getScreenData", "loading data from RMS failed");
            ex.printStackTrace();
        }
        
        PainMan.Log(this.getClass(), "getScreenData", "previous data not found, creating new dataset for screenID "+screenID);
        // no such body part in RMS, return brand new instance
        BodyPart newBodyPart = new BodyPart();
        newBodyPart.setBodyPartId(screenID);
        
        return newBodyPart;
    }
    
    /**
     * Filter class for fetching data for specific screen id
     */
    private class ScreenIDFilter implements Filter {

        private int screenId;
        public ScreenIDFilter(int screenId) {
            this.screenId = screenId;
        }
        
        public boolean matches(Persistable prstbl) {
            if (((BodyPart) prstbl).getBodyPartId() == screenId) {
                return true;
            }
            return false;
        }
        
    }
}

