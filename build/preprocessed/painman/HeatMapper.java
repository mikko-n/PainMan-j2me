package painman;

import painman.data.Point;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * HeatMapper class
 * @author NIM
 */
public class HeatMapper {
    
    public Image makeImage(Hashtable data, int width, int height) {
        Image img = Image.createRGBImage(createRGBData(data, width, height), width, height, true);
        System.out.println("HeatMapper.makeImage returns mutable image? "+img.isMutable());
        return img;
    }
           
    private static int defaultColor(float value) {   
        if (value > 0.9f) return 0xffff0000;
        if (value > 0.8f) return 0xe3ff2000;
        if (value > 0.7f) return 0xc7ff4000;
        if (value > 0.6f) return 0xabff6000;
        if (value > 0.5f) return 0x8fff8000;
        if (value > 0.4f) return 0x73ff9f00;
        if (value > 0.3f) return 0x57ffbf00;
        if (value > 0.2f) return 0x3bffdf00;
        if (value > 0.1f) return 0x1fffff00;
      
        return 0x00000000;        
    }
    
    public static int[] createRGBData(Hashtable datapoints, int width, int height) {
        
        float[] value = new float[width * height];
        int[] rgbData = new int[width * height];

        int step = 2;
        Enumeration keys = datapoints.keys();
//        long start, startDp, interval, intervalDp;
        float maxValue = 0.0f;
//        start = System.currentTimeMillis();
        while (keys.hasMoreElements()) {
//            startDp = System.currentTimeMillis();
            Integer pos = (Integer) keys.nextElement();            
//            System.out.println("Heatmapper.createRGBData position: "+pos.intValue());
            Point data = (Point) datapoints.get(pos);
//            System.out.println("Heatmapper.createRGBData calculating datapoint: "+data.toString());

            int radius = (int) Math.floor(data.value / step);
            int x = (int) Math.floor(pos.intValue() % width);
            int y = (int) Math.floor(pos.intValue() / width);
            
            // calculate point x.y
            for (int scanx = (int) (x - radius); scanx < (int) (x + radius); scanx += 1) {
                // out of extend
                if (scanx < 0 || scanx >= width) {
                    continue;
                }
                for (int scany = (int) (y - radius); scany < (int) (y + radius); scany += 1) {
                    if (scany < 0 || scany >= height) {
                        continue;
                    }

                    float dist = (float) Math.sqrt(((scanx - x)*(scanx-x)) + ((scany - y)*(scany-y)));
                    if (dist > radius) {
                        continue;
                    } else {
                        float v = (float) (data.value - step*dist);
                        int id = scanx + scany * width;
                        if (value[id] != 0.0) {
                            value[id] = (value[id] + v);                            
//                            System.out.println("Heatmapper.createRGBData x"+x+" y"+y+" from data("+data.toString()+") was not 0, new value = "+value[id]);
                        } else {                            
                            value[id] = v;
//                            System.out.println("Heatmapper.createRGBData v data("+data.toString()+") was 0.0, value set to = "+value[id]);
                        }
                        maxValue = Math.max(value[id], maxValue);
                    }
                }
            }
//            intervalDp = System.currentTimeMillis()-startDp;
//            System.out.println("Heatmapper.createRGBData datapoint calculation took "+intervalDp+"ms");
        }
//        System.out.println("Heatmapper.createRGBData values calculated, creating rgb data table...");
       
//        System.out.println("Heatmapper.createRGBData max value: "+maxValue);
                
        // todo: tästä kierroksia pois clippaamalla piirrettävää aluetta??
        
        for(int pos = 0; pos < value.length; pos++){
            int x = (int) Math.floor(pos % width);
            int y = (int) Math.floor(pos / width);
                        
            // data = [0xa1r1g11b, 0xa2r2g2b2, ... ]
            int pixelColorIndex = y * width + x;

            int color = defaultColor(value[pos]/maxValue);
            
            rgbData[pixelColorIndex] = color; // 0xaarrggbb

            if (color != -16777216 && color != 16777216) {
//                System.out.println("Heatmapper.createRGBData color for x"+x+" y"+y+" is: "+color);
            }
        }
//        interval = System.currentTimeMillis()-start;
//        System.out.println("Heatmapper.createRGBData whole calculation for "+datapoints.size()+" took "+interval+"ms");
        return rgbData;
        
    }  
}
