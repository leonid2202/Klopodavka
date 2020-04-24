package klopodavka;

import java.io.IOException;
import java.net.Socket;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;

	public ClientWorld(String serverAddress) throws IOException {
		setLocationRelativeTo(null);
		socket = new Socket(serverAddress, 6261);
		table.setSelf("Red");
		isYourTurn = true;
		setLabelIsYourTurn();
		table.switchTurn(isYourTurn);

		setUp();
	}

}
