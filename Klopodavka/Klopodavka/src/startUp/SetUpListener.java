package startUp;

import java.awt.event.ActionListener;

import klopodavka.Klopodavka;

public abstract class SetUpListener implements ActionListener {
	protected Klopodavka titleSceen;

	public SetUpListener(Klopodavka titleSceen) {
		this.titleSceen = titleSceen;
	}
}
