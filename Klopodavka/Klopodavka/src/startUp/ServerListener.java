package startUp;

import java.awt.event.ActionEvent;

import klopodavka.Klopodavka;

public class ServerListener extends SetUpListener {

	public ServerListener(Klopodavka titleSceen) {
		super(titleSceen);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		titleSceen.dispose();
		new ServerSetup();
	}
}
