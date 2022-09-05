package io.nirahtech.utils.pathfinder;

public final class Node {
    private final int columns;
    private final int rows;

    private Node parent;

    private int gCost = 0;
    private int hCost = 0;
    private int fCost = 0;

    private boolean start;
    private boolean solid;
    private boolean goal;
    private boolean open;
    private boolean checked;
    private boolean isPath;

    public Node(final int columns, final int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public final void setAsStart() {
        this.start = true;
    }

    public final void setAsGoal() {
        this.goal = true;
    }

    public final void setAsSolid() {
        this.solid = true;
    }

    public final void setAsOpen() {
        this.open = true;
    }

    public final void setAsChecked() {
        this.checked = true;
    }

    public final void setAsPath() {
        this.isPath = true;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getRows() {
        return this.rows;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getgCost() {
        return this.gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return this.hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return this.fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    public boolean isStart() {
        return this.start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isGoal() {
        return this.goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setIsPath(boolean isPath) {
        this.isPath = isPath;
    }

    public boolean isPath() {
        return this.isPath;
    }
}
