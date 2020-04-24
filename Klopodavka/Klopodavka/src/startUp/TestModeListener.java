package startUp;

import java.awt.event.ActionEvent;
import java.io.IOException;

import klopodavka.Klopodavka;
import klopodavka.ClientWorld;
import klopodavka.ServerWorld;

public class TestModeListener extends SetUpListener {

	public TestModeListener(Klopodavka titleSceen) {
		super(titleSceen);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread threadServer = new Thread() {
			public void run() {
				try {
					new ServerWorld();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		Thread threadPlayer1 = new Thread() {
			public void run() {
				try {
					new ClientWorld("localhost");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

//		Thread threadPlayer2 = new Thread() {
//			public void run() {
//				try {
//					new ClientWorld("localhost");
//				}
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		};


		threadServer.start();
		threadPlayer1.start();
//		threadPlayer2.start();


		titleSceen.dispose();
	}

}
