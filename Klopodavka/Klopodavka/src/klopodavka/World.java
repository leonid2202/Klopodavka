package klopodavka;

import java.awt.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.Socket;

import javax.swing.*;

import commands.Command;
import commands.EndGameCommand;

public class World extends JFrame implements ReaderListener {
	public static final int FRAMEWIDTH = 252;
	public static final int FRAMEHEIGHT = 252;
	private static final long serialVersionUID = 1L;
	
	protected Table table;
	protected Socket socket;

	protected int actionsPerTurn = 5;
	protected JLabel labelTurnsLeft;
	protected JLabel labelIsYourTurn;
	protected int turnsLeft = actionsPerTurn;
	protected boolean isYourTurn;

	private Font font;

	private ObjectOutputStream objOut;

	public World() throws IOException {
		setTitle("Klopodavka");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("Arial", Font.PLAIN, 14);
		setFocusable(true);
		setUpMenu();
		setSize(FRAMEWIDTH, FRAMEHEIGHT);
		setResizable(false);

		table = new Table(this);
		add(table);

		pack();
	}

	protected void setUp() throws IOException {
		new ReaderThread(socket, this).start();
		objOut = new ObjectOutputStream(socket.getOutputStream());

		setVisible(true);
	}

	private void setUpMenu()  {
		JMenuBar menu = new JMenuBar();

		labelIsYourTurn = new JLabel(String.valueOf(0));
		labelIsYourTurn.setFont(font);
		menu.add(labelIsYourTurn);

		labelTurnsLeft = new JLabel(turnsLeft + " actions left");
		labelTurnsLeft.setFont(font);
		menu.add(labelTurnsLeft);

		setJMenuBar(menu);
	}

	public void setLabelIsYourTurn() {
		if (isYourTurn)
			labelIsYourTurn.setText("  Your turn      |  ");
		else
			labelIsYourTurn.setText(" Enemy's turn |  ");
	}

	public boolean getIsYourTurn() {
		return isYourTurn;
	}

	public void makeTurn() {
		turnsLeft--;
		if (turnsLeft == 0) {
			turnsLeft = actionsPerTurn;
			isYourTurn = !isYourTurn;
			table.switchTurn(isYourTurn);
			setLabelIsYourTurn();
		}
		labelTurnsLeft.setText(turnsLeft + " actions left");
	}

	public synchronized void sendCommand(Command command) throws IOException {
		objOut.writeObject(command);
		objOut.flush();
	}


	@Override
	public void onObjectRead(Command command) {
		command.perform(this);
	}

	@Override
	public void onCloseSocket(Socket socket) {
		dispose();
	}

	public void putEnemyBlot(int x, int y) {
		table.putEnemyBlot(x,y);
		makeTurn();
	}

	public void handleEndOfGame(boolean isYourLoss) {
		if (isYourLoss) {
			JOptionPane.showMessageDialog(this, "You have lost");
			try {
				sendCommand(new EndGameCommand());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "You have won!");
		}
			System.exit(0);
	}
}