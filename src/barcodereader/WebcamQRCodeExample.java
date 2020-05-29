/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barcodereader;
import java.awt.Dimension;
import java.awt.FlowLayout;                    //sets a layout
import java.awt.image.BufferedImage;

import java.util.concurrent.Executor;        //executes a runnable thread
import java.util.concurrent.Executors;      //a factory class
import java.util.concurrent.ThreadFactory; //An object that creates new threads on demand

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;       //corebitmap class used to represent one bit data
import com.google.zxing.LuminanceSource;    //for requesting greyscale luminance values
import com.google.zxing.MultiFormatReader;  //it attempts to decode all barcode formats that the library supports
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;             //Encapsulates the result of decoding a barcode within an image
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;//implements a local thresholding algorithm

/**
 *
 * @author sabari
 */
public class WebcamQRCodeExample extends JFrame implements Runnable, ThreadFactory
{
    

	private Executor executor = Executors.newSingleThreadExecutor(this);

	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private JTextArea textarea = null;

	public WebcamQRCodeExample() 
        {
		super();

		setLayout(new FlowLayout());
		setTitle("Read QR Code With Webcam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension size = WebcamResolution.VGA.getSize(); //QVGA=Size 320x240

		webcam = Webcam.getWebcams().get(0);  //get list of webcams to use //get(0) first in the list
		webcam.setViewSize(size);

		panel = new WebcamPanel(webcam);
		panel.setPreferredSize(size);
		panel.setFPSDisplayed(true); //Frames per second displayed

		textarea = new JTextArea();
		textarea.setEditable(false);
		textarea.setPreferredSize(size);

		add(panel);
		add(textarea);

		pack();
		setVisible(true);

		executor.execute(this);
	}

	@Override
	public void run() 
        {

		do {
			try 
                        {
				Thread.sleep(100);
			} 
                        catch (InterruptedException e) 
                        {
				e.printStackTrace();
			}

			Result result = null;
			BufferedImage image = null;

			if (webcam.isOpen()) 
                        {

				if ((image = webcam.getImage()) == null)
                                {
					continue;
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try 
                                {
					result = new MultiFormatReader().decode(bitmap);
				} 
                                catch (NotFoundException e) 
                                {
					
				}
			}

			if (result != null) 
                        {
				textarea.setText(result.getText());
			}

		} while (true);
	}

	@Override
	public Thread newThread(Runnable r)
        {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}

	public static void main(String[] args)
        {
		new WebcamQRCodeExample();
	}
}
