/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package painman.data;

import java.util.Hashtable;
import net.sourceforge.floggy.persistence.Persistable;

/**
 *
 * @author NIM
 */
public class User implements Persistable {

    private String firstName;
    private String lastName;
    private char gender;
    private Hashtable painPoints = new Hashtable();
    
    public User() {        
    }
}
