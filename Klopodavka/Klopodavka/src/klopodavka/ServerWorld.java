package klopodavka;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;


public class ServerWorld extends World {
	private static final long serialVersionUID = 1L;

	public ServerWorld() throws IOException {
		Dimension scrsize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) scrsize.getWidth() / 2 - 520, (int) scrsize.getHeight() / 2 - 279);
		ServerSocket serverSocket = new ServerSocket(6261);
		socket = serverSocket.accept();
		table.setSelf("Blue");
		isYourTurn = false;
		setLabelIsYourTurn();

		setUp();
	}

}