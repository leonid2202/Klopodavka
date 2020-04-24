package commands;

import java.io.Serializable;

import klopodavka.World;

public interface Command extends Serializable {

	public void perform(World world);

}
