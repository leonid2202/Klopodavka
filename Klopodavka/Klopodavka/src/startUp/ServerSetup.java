package startUp;

import klopodavka.ServerWorld;

import java.io.IOException;

public class ServerSetup {

	public ServerSetup()  {
		try {
			new ServerWorld();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
