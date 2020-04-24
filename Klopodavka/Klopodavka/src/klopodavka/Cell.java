package klopodavka;

enum CellType {
    EMPTY,
    RED,
    BLUE,
    DEAD_RED,
    DEAD_BLUE,
}

public class Cell {
    public CellType type;
    public boolean isChecked;
    public boolean isAvailable;

    public Cell() {
        type = CellType.EMPTY;
        isChecked = false;
        isAvailable = false;
    }
}
