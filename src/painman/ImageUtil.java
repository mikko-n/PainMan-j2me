/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author NIM
 */
public class ImageUtil {
    Image imgMainFront;
    Image imgMainBack;
    Image imgFrontFull;
    Image imgBackFull;
    Image imgLegFront;
    Image imgLegBack;
    Image imgArmPalm; // front
    Image imgArmKnuckle; // back
    
    /**
     * Returns an initialized main screen background image, front side
     * @return 
     */
    public Image getImgMainFront() {
        if (imgMainFront == null) {
            try {
                imgMainFront = Image.createImage("/images/painman-man-front-320.png");                
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgMainFront", "Main screen frontside image init failed");
                e.printStackTrace();                
            }
        }         
        return imgMainFront;
    }
    
    
    /**
     * Returns an initialized main screen background image, backside
     * @return 
     */
    public Image getImgMainBack() {        
        if (imgMainBack == null) {
            try {
                imgMainBack = Image.createImage("/images/painman-man-back-320.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgMainBack", "Main screen backside image init failed");
                e.printStackTrace();                
            }
        }         
        return imgMainBack;
    }
    
    /**
     * Returns an initialized left leg image from front view
     * by cropping full frontal image
     * @return 
     */
    public Image getImgLeftLegFront() {
        if (imgLegFront == null) {            
//                imgLegFront = Image.createImage("/images/leg-full-front.png");
                imgLegFront = Image.createImage(240,320);
                Graphics legFrontGraphics = imgLegFront.getGraphics();
                legFrontGraphics.drawImage(getImgFrontFull(), -156, -735, Graphics.TOP|Graphics.LEFT);            
                PainMan.Log(this.getClass(), "getImgLeftLegFront", "Left leg image init ok");
            
        }                 
//        Image leftLeg = Image.createImage(imgLegFront.getWidth(), imgLegFront.getHeight());
//        Graphics g = leftLeg.getGraphics();
//        g.drawRegion(imgLegFront, 0, 0, leftLeg.getWidth(), leftLeg.getHeight(), Sprite.TRANS_NONE, 0, 0, g.TOP|g.LEFT);
        return imgLegFront;
    }
    
    /**
     * Returns an initialized full body image, front view
     * @return 
     */
    private Image getImgFrontFull () {
        if (imgFrontFull == null) {
            try {
                imgFrontFull = Image.createImage("/images/painman-man-front.png");
//                PainMan.Log(this.getClass(), "getImgFrontFull", "front image size: x"+imgFrontFull.getWidth()+" y"+imgFrontFull.getHeight());
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgFrontFull", "full front image init failed");
                e.printStackTrace();                
            }
        }
        return imgFrontFull;
    }
    /**
     * Returns an initialized left leg image from back view
     * @return 
     */
    public Image getImgLeftLegBack() {
        if (imgLegBack == null) {
            try {
                imgLegBack = Image.createImage("/images/leg-full-back.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgLeftLegBack", "Left leg back image init failed");
                e.printStackTrace();                
            }
        }                 
        return imgLegBack;
    }
    
    /**
     * Returns an initialized left arm image from palm side view
     * @return 
     */
    public Image getImgLeftArmPalm() {
        if (imgArmPalm == null) {
            try {
                imgArmPalm = Image.createImage("/images/arm-full-palm.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgLeftArmPalm", "Left arm palm image init failed");
                e.printStackTrace();                
            }
        }                 

        return imgArmPalm;
    }
    
    /**
     * Returns an initialized left arm image from knuckle side view
     * @return 
     */
    public Image getImgLeftArmKnuckle() {
        if (imgArmKnuckle == null) {
            try {
                imgArmKnuckle = Image.createImage("/images/arm-full-knuckle.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgLeftArmKnuckle", "Left arm knuckle image init failed");
                e.printStackTrace();                
            }
        }                 
        return imgArmKnuckle;
    }
    
    /**
     * Returns an initialized right leg image from front view
     * @return 
     */
    public Image getImgRightLegFront() {
        if (imgLegFront == null) {
            try {
                imgLegFront = Image.createImage("/images/leg-full-front.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgRightLegFront", "Right leg image init failed");
                e.printStackTrace();                
            }
        }                 
        Image rightLeg = Image.createImage(imgLegFront.getWidth(), imgLegFront.getHeight());
        Graphics g = rightLeg.getGraphics();
        g.drawRegion(imgLegFront, 0, 0, rightLeg.getWidth(), rightLeg.getHeight(), Sprite.TRANS_MIRROR, 0, 0, g.TOP|g.LEFT);
        return rightLeg;
    }
    
    /**
     * Returns an initialized right leg image from back view
     * @return 
     */
    public Image getImgRightLegBack() {
        if (imgLegBack == null) {
            try {
                imgLegBack = Image.createImage("/images/leg-full-back.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgRightLegBack", "Right leg back image init failed");
                e.printStackTrace();                
            }
        }                 
        Image rightLeg = Image.createImage(imgLegBack.getWidth(), imgLegBack.getHeight());
        Graphics g = rightLeg.getGraphics();
        g.drawRegion(imgLegBack, 0, 0, rightLeg.getWidth(), rightLeg.getHeight(), Sprite.TRANS_MIRROR, 0, 0, g.TOP|g.LEFT);
        return rightLeg;
    }
    
    /**
     * Returns an initialized right arm image from palm view
     * @return 
     */
    public Image getImgRightArmPalm() {
        if (imgArmPalm == null) {
            try {
                imgArmPalm = Image.createImage("/images/arm-full-palm.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgRightArmPalm", "Right arm palm side image init failed");
                e.printStackTrace();                
            }
        }                 
        Image rightArm = Image.createImage(imgArmPalm.getWidth(), imgArmPalm.getHeight());
        Graphics g = rightArm.getGraphics();
        g.drawRegion(imgArmPalm, 0, 0, rightArm.getWidth(), rightArm.getHeight(), Sprite.TRANS_MIRROR, 0, 0, g.TOP|g.LEFT);
        return rightArm;
    }
    
    /**
     * Returns an initialized right arm image from knuckle side view
     * @return 
     */
    public Image getImgRightArmKnuckle() {
        if (imgArmKnuckle == null) {
            try {
                imgArmKnuckle = Image.createImage("/images/arm-full-knuckle.png");
            } catch (java.io.IOException e) {
//                PainMan.Log(this.getClass(), "getImgRightArmKnuckle", "Right arm knuckle side image init failed");
                e.printStackTrace();                
            }
        }                 
        Image rightArm = Image.createImage(imgArmKnuckle.getWidth(), imgArmKnuckle.getHeight());
        Graphics g = rightArm.getGraphics();
        g.drawRegion(imgArmKnuckle, 0, 0, rightArm.getWidth(), rightArm.getHeight(), Sprite.TRANS_MIRROR, 0, 0, g.TOP|g.LEFT);
        return rightArm;
    }
  
}
