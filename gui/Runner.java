package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Runner extends JFrame {
	private static final long serialVersionUID = 1L;

	private Content contentPane = new Content();

	public Runner() {
		super();

		setResizable(false);
		setTitle("Blah title blah");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setContentPane(contentPane);
		pack();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				Runner frame = new Runner();
				frame.setVisible(true);

				Thread.sleep(2000);

				frame.contentPane.magicInterpolation();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
