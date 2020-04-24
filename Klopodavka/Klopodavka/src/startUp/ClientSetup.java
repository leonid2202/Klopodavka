package startUp;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import klopodavka.ClientWorld;

public class ClientSetup extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton connectButton;
	private JTextField ip;
	private JLabel ipLabel;

	public ClientSetup() {
		setSize(200, 100);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(3, 1));

		ipLabel= new JLabel("Enter Server's IP address:");
		ip = new JTextField("localhost");
		connectButton = new JButton("Connect");
		add(ipLabel);
		add(ip);
		add(connectButton);

		ip.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				((JTextField)e.getSource()).selectAll();
			}
		});

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String ipAddress = ip.getText();
				try {
					new ClientWorld(ipAddress);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		setVisible(true);
	}
}
