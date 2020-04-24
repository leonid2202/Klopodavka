package startUp;

import java.awt.event.ActionEvent;

import klopodavka.Klopodavka;

public class ClientListener extends SetUpListener {

	public ClientListener(Klopodavka titleSceen) {
		super(titleSceen);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ClientSetup();
		titleSceen.dispose();
	}

}
