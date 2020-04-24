package klopodavka;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import commands.PutBlotCommand;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;

	private final int GAME_WIDTH = 12;
	private final int GAME_HEIGHT = 12;
	private final int CELL_WIDTH = 20;
	private final int CELL_HEIGHT = 20;
	private final int BASE_KLOP_OFFSET = 3;
	private final int BORDER = 1;

	private int width = World.FRAMEWIDTH;
	private int height = World.FRAMEHEIGHT;
	private int baseX;
	private int baseY;
	private int countAvailable; // end of game detection

	private Image imgRed;
	private Image imgRedDead;
	private Image imgBlue;
	private Image imgBlueDead;
	private Image imgAvailable;
	private Image imgCrown;
	private Cell[][] cells;
	private Map<CellType, Image> imgMap;

	private CellType friendlyCell;
	private CellType friendlyDeadCell;
	private CellType enemyCell;
	private CellType enemyDeadCell;


	private World world;

	public Table(World world) throws IOException {
		this.world = world;
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));

		imgRed = ImageIO.read(getClass().getResource("pics/red.png"));
		imgRedDead = ImageIO.read(getClass().getResource("pics/redDeadRedemption.png"));
		imgBlue = ImageIO.read(getClass().getResource("pics/blue.png"));
		imgBlueDead = ImageIO.read(getClass().getResource("pics/blueDead.png"));
		imgAvailable = ImageIO.read(getClass().getResource("pics/available2.png"));
		imgCrown = ImageIO.read(getClass().getResource("pics/crown.png"));


		imgMap = new HashMap<CellType, Image>();
		imgMap.put(CellType.RED, imgRed);
		imgMap.put(CellType.DEAD_RED, imgRedDead);
		imgMap.put(CellType.BLUE, imgBlue);
		imgMap.put(CellType.DEAD_BLUE, imgBlueDead);

		cells = new Cell[GAME_WIDTH][GAME_HEIGHT];
		for (int i = 0; i < GAME_WIDTH; i++)
			for (int j = 0; j < GAME_HEIGHT; j++) {
				cells[i][j] = new Cell();
			}
		cells[GAME_WIDTH - BASE_KLOP_OFFSET - 1][BASE_KLOP_OFFSET].type = CellType.RED;
		cells[BASE_KLOP_OFFSET][GAME_HEIGHT - BASE_KLOP_OFFSET - 1].type = CellType.BLUE;


		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				handleClick(me);
			}
		});
	}

	private void handleClick(MouseEvent me) {
		int x = me.getX()  / (CELL_WIDTH + 1);
		int y = me.getY()  / (CELL_HEIGHT + 1);

		if (x < 0 || x > GAME_WIDTH-1 ||
			y < 0 || y > GAME_WIDTH-1 ||
			!world.getIsYourTurn() ||
			(x == (GAME_WIDTH-baseX-1) && y == (GAME_HEIGHT-baseY-1)))	 // is enemy's base
			return;

		if (cells[x][y].isAvailable) {
			if (cells[x][y].type == CellType.EMPTY)
				cells[x][y].type = friendlyCell;
			 else
				cells[x][y].type = enemyDeadCell;
			cells[x][y].isAvailable = false;
			cells[x][y].isChecked = false;
			checkCells(x,y);
			if (countAvailable == 0)
				world.handleEndOfGame(true);
			this.repaint();
			world.makeTurn();
			try {
				world.sendCommand(new PutBlotCommand(x, y));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawGrid(Graphics2D g) {
		for (int i = 0; i <= GAME_WIDTH; i++) {
			Line2D lin = new Line2D.Float(i * (CELL_WIDTH + 1), 0,
					i * (CELL_WIDTH + 1), GAME_HEIGHT * (CELL_HEIGHT + 1));
			g.draw(lin);
		}
		for (int i = 0; i <= GAME_HEIGHT; i++) {
			Line2D lin = new Line2D.Float(0, i * (CELL_HEIGHT+1),
					GAME_WIDTH * (CELL_HEIGHT + 1), i * (CELL_HEIGHT+1));
			g.draw(lin);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawGrid(g2);
		for (int i = 0; i < GAME_WIDTH; i++)
			for (int j = 0; j < GAME_HEIGHT; j++) {
				g.drawImage(imgMap.get(cells[i][j].type), i * (CELL_WIDTH + 1) + 1, j * (CELL_HEIGHT + 1) + 1,
						CELL_WIDTH, CELL_HEIGHT, null);
				if (cells[i][j].isAvailable)
					g.drawImage(imgAvailable, i * (CELL_WIDTH + 1) + 1, j * (CELL_HEIGHT + 1) + 1,
							CELL_WIDTH, CELL_HEIGHT, null);
//				if (cells[i][j].isChecked)
//					g.drawString("С", i * (CELL_WIDTH + 1) + 10, j * (CELL_HEIGHT + 1) + 10);
			}
		g.drawImage(imgCrown, baseX * (CELL_WIDTH + 1) + 1, baseY * (CELL_HEIGHT + 1) + 1, CELL_WIDTH, CELL_HEIGHT, null);
		g.drawImage(imgCrown, (GAME_WIDTH-baseX-1) * (CELL_WIDTH + 1) + 1, (GAME_HEIGHT-baseY-1) * (CELL_HEIGHT + 1) + 1, CELL_WIDTH, CELL_HEIGHT, null);

	}

	public void putEnemyBlot(int x, int y) {
		if (cells[x][y].type == CellType.EMPTY)
			cells[x][y].type = enemyCell;
		else if (cells[x][y].type == friendlyCell)
			cells[x][y].type = friendlyDeadCell;
		this.repaint();
	}

	public void setSelf(String color) {
		if (color == "Red") {
			friendlyCell = CellType.RED;
			friendlyDeadCell = CellType.DEAD_RED;
			enemyCell = CellType.BLUE;
			enemyDeadCell = CellType.DEAD_BLUE;
			baseX = GAME_WIDTH - BASE_KLOP_OFFSET - 1;
			baseY = BASE_KLOP_OFFSET;
		} else if (color == "Blue") {
			friendlyCell = CellType.BLUE;
			friendlyDeadCell = CellType.DEAD_BLUE;
			enemyCell = CellType.RED;
			enemyDeadCell = CellType.DEAD_RED;
			baseX = BASE_KLOP_OFFSET;
			baseY = GAME_HEIGHT - BASE_KLOP_OFFSET - 1;
		}
	}

	private void setTableUnchecked() {
		for (int i = 0; i < GAME_WIDTH; i++)
			for (int j = 0; j < GAME_HEIGHT; j++)
				cells[i][j].isChecked = false;
	}

	public void checkCells(int x, int y) {
		if (x < 0 || x > GAME_WIDTH-1 || y < 0 || y > GAME_HEIGHT-1 || cells[x][y].isChecked)
			return;

		if ((cells[x][y].type == CellType.EMPTY || cells[x][y].type == enemyCell) &&
			!(x == (GAME_WIDTH-baseX-1) && y == (GAME_HEIGHT-baseY-1))) {	 // isn't enemy's base
			cells[x][y].isChecked = true;
			cells[x][y].isAvailable = true;
			countAvailable++;
		} else if (cells[x][y].type == friendlyCell || cells[x][y].type == enemyDeadCell) {
			cells[x][y].isChecked = true;

			checkCells(x-1,y-1);
			checkCells(x-1,y);
			checkCells(x-1,y+1);
			checkCells(x,y-1);
			checkCells(x,y+1);
			checkCells(x+1,y-1);
			checkCells(x+1,y);
			checkCells(x+1,y+1);
		}
	}

	public void switchTurn(boolean isYourTurn) {
		if (isYourTurn) {
			setTableUnchecked();
			checkCells(baseX, baseY);
			if (countAvailable == 0)
				world.handleEndOfGame(true);
			this.repaint();
		} else {
			for (int i = 0; i < GAME_WIDTH; i++)
				for (int j = 0; j < GAME_HEIGHT; j++)
					cells[i][j].isAvailable = false;
			countAvailable = 0;
		}
	}
}
