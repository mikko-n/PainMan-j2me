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
     * Main screen id
     */
    public static final int SCREEN_MAINSCREEN = 0;
    
    /**
     * Head screen id
     */
    public static final int SCREEN_HEAD = 1;
    
    /**
     * Torso screen id
     */
    public static final int SCREEN_TORSO = 2;
    
    /**
     * Left arm screen id
     */
    public static final int SCREEN_LEFTARM = 3;
    
    /**
     * Right arm screen id
     */
    public static final int SCREEN_RIGHTARM = 4;
    
    /**
     * Left leg screen id
     */
    public static final int SCREEN_LEFTLEG = 5;
    
    /**
     * Right leg screen id
     */
    public static final int SCREEN_RIGHTLEG = 6;
    
    /**
     * System wide settings:
     * [0] Current locale
     */
    public static String[] SETTINGS = {LOCALE};        
    
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
