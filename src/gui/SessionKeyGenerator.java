package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import qrcode.QRCodeGenerator;

public class SessionKeyGenerator {
	private static final String TITLE = "Code Generation";
	
	private JFrame frame;
	private JPanel panel, buttonsPanel;
	private JLabel imageLabel;
	private JTextArea textArea;
	private QRCodeGenerator qrCodeGenerator;
	
	private String qrString;
	private String[] blocks;
	
	public SessionKeyGenerator(String qrString, String[] blocks) {
		this.frame = new JFrame();
		this.panel = new JPanel();
		this.buttonsPanel = new JPanel();
		this.textArea = new JTextArea();
		this.qrCodeGenerator = new QRCodeGenerator();
		this.qrString = qrString;
		this.blocks = blocks;
		
		createFrame();
	}
	
	private void createFrame() {
		//Frame
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setIconImage(image);

		//Panel
		panel.setLayout(new BorderLayout());
		//QR-Code
		BufferedImage image = qrCodeGenerator.createQR(qrString, 450);
		Dimension size = new Dimension(image.getWidth(), image.getHeight());
		imageLabel = new JLabel(new ImageIcon(image));
		imageLabel.setPreferredSize(size);
		
		//Displaying the QR-Code as Text
		textArea.setEditable(false);
		textArea.setText(qrString);
		
		//Buttons for displaying the blocks
		for (String block : blocks) {
			JButton button = new JButton();
			button.setEnabled(false);
			button.setText(block);
			buttonsPanel.add(button);
		}
		
		//Add the objects to the frame
		panel.add(imageLabel, BorderLayout.CENTER);
		panel.add(textArea, BorderLayout.EAST);
		panel.add(buttonsPanel, BorderLayout.SOUTH);
		
		//Displaying all
		frame.add(panel);
		frame.setSize(660, image.getHeight() + 35);
		frame.setVisible(true);
	}
}