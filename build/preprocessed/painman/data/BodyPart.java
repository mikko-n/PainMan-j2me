/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.data;

import java.util.Hashtable;
import net.sourceforge.floggy.persistence.Persistable;
import painman.PainMan;

/**
 *
 * @author NIM
 */
public class BodyPart implements Persistable {

    /**
     * Body part / screen id, see Properties for values
     */
    private int bodyPartId;
    
    private int screenWidth;
        
    /**
     * Hashtable for pain points
     */
    private Hashtable painPoints;
        
    public BodyPart() {            
    }
    
    public void setBodyPartId(int id) {
        this.bodyPartId = id;
    }
    
    /**
     * Body part / screen id
     * @return 
     */
    public int getBodyPartId() {
        return bodyPartId;
    }
    
    public Hashtable getPainPoints() {                
        if (painPoints == null) {
            PainMan.Log(this.getClass(), "getPainPoints", "painpoint data null, initializing new hashtable");
            painPoints = new Hashtable();
        }
        return painPoints;
    }
    
    /**
     * Returns data point count for bodyPart
     * @return -1 if no data
     */
    public int getDataSize() {
        if (painPoints != null) {
            return painPoints.size();
        }
        return -1;
    }
    
    public void addPoint(Integer pixelID, Point point) {
//        Integer id = new Integer(point.x + point.y * screenWidth);

        if (painPoints == null) {
            painPoints = new Hashtable();
        }        
        painPoints.put(pixelID, point);
    }
}
