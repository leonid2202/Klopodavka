package klopodavka;

import java.awt.GridLayout;

import javax.swing.*;

import startUp.ClientListener;
import startUp.ServerListener;
import startUp.TestModeListener;

public class Klopodavka extends JFrame {
	private static final long serialVersionUID = 1L;

	public Klopodavka() {
		setSize(200, 200);
		setTitle("Klopodavka");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(3, 1, 5, 5));

		JButton server = new JButton("SERVER");
		server.addActionListener(new ServerListener(this));
		add(server);

		JButton client = new JButton("CLIENT");
		client.addActionListener(new ClientListener(this));
		add(client);

		JButton testMode = new JButton("TEST MODE");
		testMode.addActionListener(new TestModeListener(this));
		add(testMode);

		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			/* LookAndFeel lat = UIManager.getLookAndFeel();
			 * UIDefaults defaults = lat.getDefaults(); defaults.replace(key, value);
			 * for(Object key: UIManager.getLookAndFeel().getDefaults().keySet()) {
			 * System.out.println(key + " = " + UIManager.get(key));
			 * } */
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Klopodavka();
	}
}
