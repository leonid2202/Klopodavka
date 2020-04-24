package commands;

import klopodavka.World;

public class PutBlotCommand implements Command {
    private static final long serialVersionUID = 1L;
    protected int x;
    protected int y;

    public PutBlotCommand(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void perform(World world) {
        world.putEnemyBlot(x,y);
    }
}
