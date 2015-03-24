/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman.data;

import net.sourceforge.floggy.persistence.Persistable;

/**
 * Point is a class for heatmap visualization purposes.
 * It marks a point in x-y coordinates and holds additional
 * data for screen identification
 * @author NIM
 */
public class Point implements Persistable {
    
        public int x = 0;
        public int y = 0;
        public int value = 0;
        public String description = "";
        
        /**
         * Screen where point belongs to.
         * See possible values from Properties
         */        
        public int screenID = -1;
        public boolean frontside = true;
        
        /**
         * Default constructor for Floggy persistence framework
         */
        public Point() {            
        }
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Point(int x, int y, int value, int screenID) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.screenID = screenID;
        }

        
        public Point(int x, int y, int value, int screenID, boolean isFrontside) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.screenID = screenID;
            this.frontside = isFrontside;
        }

        public String toString() {
            return "x"+x+" y"+y+" value="+value+" \ndescription="+description+", \nscreenid="+screenID+" frontside? "+frontside;
        }
        
    }
