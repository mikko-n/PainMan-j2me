/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman;

import painman.data.Point;

/**
 *
 * @author NIM
 */
public class Util {
    
    /**
     * Test to see if user clicked inside pre-defined polygon
     * Based to "crossings-multiply" -algorithm which counts the crossings
     * of lines between vertices made by a ray from the test point (and avoids division).
     * Basically this shoots a test ray along +X axis.  The strategy is to compare vertex Y values
     * to the testing point's Y and quickly discard edges which are entirely to one
     * side of the test ray. 
     * 
     * @see CrossingsMultiplyTest(pgon, numverts, point) method in http://tog.acm.org/resources/GraphicsGems/gemsiv/ptpoly_haines/ptinpoly.c
     * @param clickX x point of touch event
     * @param clickY y point of touch event
     * @param region predefined region
     * @return 
     */
    public static boolean bodyRegionClicked(int clickX, int clickY, Point[] region) {
        boolean yflag0, yflag1, inside;       
        Point vtx0, vtx1;        
        
        vtx0 = region[region.length-1];
        // get test bit for above/below X-axis
        yflag0 = (vtx0.y >= clickY);
        vtx1 = region[0];
        
        inside = false;

//        PainMan.Log(this.getClass(), "bodyRegionClicked", "click at x"+clickX+" y"+clickY);
        
        int vtx1pos = 0;
        for (int j = region.length; j>0; j-- ) {
            yflag1 = vtx1.y >= clickY;
            
//            PainMan.Log(this.getClass(), "bodyRegionClicked", "vtx0 = "+vtx0.toString());
//            PainMan.Log(this.getClass(), "bodyRegionClicked", "vtx1 = "+vtx1.toString());
//            PainMan.Log(this.getClass(), "bodyRegionClicked", "comparing points x"+vtx0.x+"y"+vtx0.y+" and x"+vtx1.x+"y"+vtx1.y);
            
            // check if endpoints are on opposite sides of X axis (i.e. the Y's differ);
            // if so, there is possibly an edge that +X ray could intersect
            if (yflag0 != yflag1) {
                // check intersection of polygon segment with +X ray.
                // if >= point's X, the ray hits it.
                // The division operation is avoided for the ">=" test
                // by checking the sign of the first vertex wr to the test point
                if ( ((vtx1.y - clickY) * (vtx0.x - vtx1.x) >=
                      (vtx1.x - clickX) * (vtx0.y - vtx1.y) )== yflag1) {
                    inside = !inside;
                }
            }
            // move to next pair of vertices
            yflag0 = yflag1;
            vtx0 = vtx1;
            vtx1pos++;
//            PainMan.Log(this.getClass(), "bodyRegionClicked", "j = "+j+" vtx1pos = "+vtx1pos);
           
            // last round sanity check
            if (vtx1pos < region.length) {
                vtx1 = region[vtx1pos];
            } else {
//                PainMan.Log(this.getClass(), "bodyRegionClicked", "vtx1pos outside vertex num, loop end");
            }
        }
        
        return inside;
    }
}
