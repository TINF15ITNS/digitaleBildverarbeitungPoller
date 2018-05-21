package digitaleBildverarbeitungPoller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class MainClass {
	
	public final static String fileLocationFirstPic = "C:\\Users\\Nikolai\\OneDrive\\DHBW\\6.Semester\\Digitale Bildverarbeitung\\Image3.bmp";

	public static void main(String[] args) {
		
		BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocationFirstPic));
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        	throw new RuntimeException("Konnte Bild nicht einlesen");
        }
        
        int height = img.getHeight();
        int width = img.getWidth();


        System.out.println(height  + "  " +  width + " " + img.getRGB(30, 30));

        for (int h = 1; h<height; h++)
        {
            for (int w = 1; w<width; w++)
            {
                
            }
        }
       

	}

}
