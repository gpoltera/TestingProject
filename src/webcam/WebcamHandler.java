package webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class WebcamHandler {
	private Webcam webcam;

	public WebcamHandler() {
		start();
	}
 	
 	public void start() {
 		if (!isOpen()) {
 			this.webcam = Webcam.getDefault();
 			this.webcam.setViewSize(WebcamResolution.VGA.getSize());
 		}
 	}
 	
 	public void stop() {
 		if (isOpen()) {
 			this.webcam.close();
 			//this.webcamExecutor.
 		}
 	}
 	
 	public Webcam getWebcam() { 		
 		return webcam;
 	}
 	
 	private boolean isOpen() {
 		boolean isOpen = false;
 		try { isOpen = this.webcam.isOpen();
 		} catch (Exception e) {
 			isOpen = false;
 		}
 		
 		return isOpen;
 	}
} 


