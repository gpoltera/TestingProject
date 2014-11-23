package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import listener.SessionKeyInputEvent;
import listener.SessionKeyListener;
import webcam.WebcamHandler;
import webcam.WebcamReader;

public class SessionKeyReader {

    private static final String TITLE = "Code Reader";
    private static final String START = "Start";
    private static final String STOP = "Stop";

    private JFrame frame;
    private JPanel panel;
    private WebcamPanel webcamPanel;
    //private JTextArea textArea;
    private JButton buttonStart, buttonStop;
    private WebcamHandler webcamHandler;
    private Webcam webcam;
    private WebcamReader webcamReader;
    private SessionKeyInputPanel sessionKeyInput;

    public SessionKeyReader() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        //this.textArea = new JTextArea();
        this.buttonStart = new JButton();
        this.buttonStop = new JButton();
        this.webcamHandler = new WebcamHandler();
        this.webcam = this.webcamHandler.getWebcam();
        this.webcamReader = new WebcamReader(this.webcam);
        this.sessionKeyInput = new SessionKeyInputPanel();

        createFrame();
    }

    private void createFrame() {

        //Panel
        panel.setLayout(new BorderLayout());

        //QR-Code-Reader
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSLimit(10);
        webcamPanel.setFPSLimited(true);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setAntialiasingEnabled(true);
        webcamPanel.setDisplayDebugInfo(true);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setMirrored(true);
        webcamReader.addSessionKeyListener(new SessionKeyListener() {
            @Override
            public void sessionKeyChange(SessionKeyInputEvent e) {
                sessionKeyInput.updateSessionKey(e.getSessionKey());
            }
        });

        //Button Start
        buttonStart.setText(START);
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcamHandler.start();
            }
        });

        //Buttons Stop
        buttonStop.setText(STOP);
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcamHandler.stop();
            }
        });

        //Add the objects to the frame
        panel.add(buttonStart, BorderLayout.WEST);
        panel.add(webcamPanel, BorderLayout.CENTER);
        panel.add(buttonStop, BorderLayout.EAST);
        panel.add(sessionKeyInput, BorderLayout.SOUTH);

        //Frame
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.setIconImage(image);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
