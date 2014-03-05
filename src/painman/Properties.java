/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package painman;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import javax.microedition.lcdui.Font;

/**
 *
 * @author NIM
 */
public final class Properties {
    
    /**
     * Platform-specific small font
     */
    public static final Font SMALL_FONT = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    
    /**
     * Platform-specific medium font
     */
    public static final Font MEDIUM_FONT = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    
    /**
     * Platform-specific large font
     */
    public static final Font LARGE_FONT = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
    
    /**
     * Default locale from platform
     */
    public static String LOCALE = System.getProperty("microedition.locale");
    
    /**
     * Ui translation file name
     */
    public static String UI_PROPERTIES_FILE_NAME = "/translations/uiTranslation_"+LOCALE+".properties";

    /**
     * Main screen id 0
     */
    public static final int SCREEN_MAINSCREEN = 0;
    
    /**
     * Head screen id 1
     */
    public static final int SCREEN_HEAD = 1;
    
    /**
     * Right upper arm & torso screen id 2
     */
    public static final int SCREEN_RIGHTARM_TORSO = 2;
    
    /**
     * Left upper arm & torso screen id 3
     */
    public static final int SCREEN_LEFTARM_TORSO = 3;
    
    /**
     * Right hand screen id 4
     */
    public static final int SCREEN_RIGHTHAND = 4;
    
    /**
     * Left hand screen id 5
     */
    public static final int SCREEN_LEFTHAND = 5;
    
    /**
     * Right upper leg (thigh & knee) screen id 6
     */
    public static final int SCREEN_RIGHTLEG_UPPER = 6;
    
    /**
     * Left upper leg (thigh & knee) screen id 7
     */
    public static final int SCREEN_LEFTLEG_UPPER = 7;
    
    /**
     * Right lower leg (knee to toes) screen id 8
     */
    public static final int SCREEN_RIGHTLEG_LOWER = 8;
    
    /**
     * Left lower leg (knee to toes) screen id 9
     */
    public static final int SCREEN_LEFTLEG_LOWER = 9;
    
    /**
     * Screen background color
     */
    public static final int COLOR_BACKGROUND = 0xdddddd;
    
    /**
     * Add datapoint crosshair color
     */
    public static final int COLOR_CROSSHAIR = 0xFF0000;
    
    /**
     * Default text color
     */
    public static final int COLOR_TEXT = 0x000000;
    
    /**
     * Time to perform rotate gesture in ms
     */
    public static final long GESTURE_ROTATE_INTERVAL_MS = 280;
    
    /**
     * System wide settings:
     * [0] Current locale
     */
    public static String[] SETTINGS = {LOCALE};        
    
    /**
     * Global boolean for forms: indicates which side should be visible
     */
    public static boolean IS_FRONTSIDE = true;
    
    /**
     * Global boolean for datapoint add mode
     */
    public static boolean ADD_DATAPOINT_MODE = false;
    
    
    /**
     * All of the visible strings are stored in the uiTranslation.properties file to allow
     * for internationalization.
     */
    public static Hashtable readUiProperties() {
        Hashtable translations = new Hashtable();
        try {
            String locale = SETTINGS[0];
            
            PainMan.Log(Properties.class, "readUiProperties", "system locale = "+LOCALE+", ui translation from: "+SETTINGS[0]);
            // Code snipped from:
            // http://stackoverflow.com/questions/739691/reading-text-file-in-j2me/739744#739744
            
            Reader in;
            try {
                in = new InputStreamReader(PainMan.class.getResourceAsStream(UI_PROPERTIES_FILE_NAME), "UTF-8");
            } catch (NullPointerException ex) {
                PainMan.Log(Properties.class, "readUiProperties", "localized properties file not found, trying recovery to default language...");
                UI_PROPERTIES_FILE_NAME = "/translations/uiTranslation.properties";
                in = new InputStreamReader(PainMan.class.getResourceAsStream(UI_PROPERTIES_FILE_NAME), "UTF-8");
                PainMan.Log(Properties.class, "readUiProperties", "Recovery successful!");
            }
            StringBuffer temp = new StringBuffer(1024);
            char[] buffer = new char[1024];
            int read;   
            while ((read=in.read(buffer, 0, buffer.length)) != -1) {
                temp.append(buffer, 0, read);
            }
            String uiPropertiesString = temp.toString();
                        
            in.close();
                        
            // Read each line
            int startOfLineIndex = 0;
            int endOfLineIndex = uiPropertiesString.indexOf('\n');

            while (endOfLineIndex != -1) {
                readUiPropertiesLine(translations, uiPropertiesString, startOfLineIndex, endOfLineIndex);

                // Read in next line
                startOfLineIndex = endOfLineIndex + 1;
                endOfLineIndex = uiPropertiesString.indexOf('\n', startOfLineIndex);
            }

            // Read in last line
            readUiPropertiesLine(translations, uiPropertiesString, startOfLineIndex, uiPropertiesString.length());

        } catch (IOException e) {
            PainMan.Log(Properties.class, "readUiProperties", "Read UI properties IOexception! " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            PainMan.Log(Properties.class, "readUiProperties", "Read UI properties exception! " + ex.getMessage());
            ex.printStackTrace();            
        }
        
        return translations;
    }
    
    /**
    * Parses a UI properties line and inserts it into the uiProperties
    * hashtable.
    */
    private static void readUiPropertiesLine(Hashtable fillable, String uiPropertiesString, int startOfLineIndex, int endOfLineIndex) {
        String lineString = uiPropertiesString.substring(startOfLineIndex, endOfLineIndex);

        // Get property name
        int colonIndex = lineString.indexOf('=');

        if (colonIndex >= 0) {
            String propertyName = lineString.substring(0, colonIndex).trim();
            String uiString = lineString.substring(colonIndex + 1, lineString.length()).trim();

            // Insert into hashtable
            fillable.put(propertyName, uiString);
        }
    }
}
