package webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamResolution;

public class WebcamHandler {

    private Webcam webcam;

    public WebcamHandler() {
        start();
    }

    public void start() {
        try {            
            webcam = Webcam.getDefault();
        } catch (WebcamException e) {
            System.out.println("FEHLER: " + e);
        }
        
        //webcam.setViewSize(WebcamResolution.VGA.getSize());
    }

    public Webcam getWebcam() {
        return webcam;
    }
}
