/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barcodereader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.github.sarxos.webcam.Webcam;

/**
 *
 * @author sabari
 */
public class webcam 
{
    public static void main(String[] args) throws IOException 
    {
           Webcam webcam = Webcam.getDefault();
            webcam.open();
            BufferedImage image = webcam.getImage();
            ImageIO.write(image, "PNG", new File("/home/sabari/NetBeansProjects/BarcodeReader/test.png"));
            
        
    }
}
