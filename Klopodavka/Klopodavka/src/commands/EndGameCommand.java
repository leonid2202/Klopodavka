package commands;

import klopodavka.World;

public class EndGameCommand implements Command {
    private static final long serialVersionUID = 1L;

    @Override
    public void perform(World world) {
        world.handleEndOfGame(false);
    }
}
