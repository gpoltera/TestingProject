package webcam;

import helpers.QRString;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import listener.SessionKeyInputEvent;
import listener.SessionKeyWrapper;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import crypto.SessionKey;

public class WebcamReader extends SessionKeyWrapper implements ThreadFactory, Runnable{
	private Executor executor;
	private Webcam webcam;
	
	public WebcamReader(Webcam webcam) {
		this.executor = Executors.newSingleThreadExecutor(this);
		this.webcam = webcam;
		this.executor.execute(this);
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "webcam-runner");
		t.setDaemon(true);
		return t;
	}
	
	@Override
	public void run() {
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Result result = null;
			BufferedImage image = null;

			if (webcam.isOpen()) {

				if ((image = webcam.getImage()) == null) {
					continue;
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {
					result = new MultiFormatReader().decode(bitmap);
				} catch (NotFoundException e) {
					// no QR-Code in Image
				}
			}

			if (result != null) {
				SessionKey sessionKey = new SessionKey(QRString.getSessionKey(result.getText()));
				notifySessionKeyChange(new SessionKeyInputEvent(this, sessionKey));
			}

		} while (true);
	}
}

